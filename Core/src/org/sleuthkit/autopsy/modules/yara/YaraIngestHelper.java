/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sleuthkit.autopsy.modules.yara;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.NbBundle;
import org.sleuthkit.autopsy.coreutils.ExecUtil;
import org.sleuthkit.autopsy.ingest.IngestModule;
import org.sleuthkit.autopsy.ingest.IngestModule.IngestModuleException;
import org.sleuthkit.autopsy.modules.yara.rules.RuleSet;
import org.sleuthkit.autopsy.modules.yara.rules.RuleSetManager;
import org.sleuthkit.autopsy.yara.YaraJNIWrapper;
import org.sleuthkit.autopsy.yara.YaraWrapperException;
import org.sleuthkit.datamodel.AbstractFile;
import org.sleuthkit.datamodel.BlackboardArtifact;
import static org.sleuthkit.datamodel.BlackboardArtifact.ARTIFACT_TYPE.TSK_YARA_HIT;
import static org.sleuthkit.datamodel.BlackboardAttribute.ATTRIBUTE_TYPE.TSK_SET_NAME;
import static org.sleuthkit.datamodel.BlackboardAttribute.ATTRIBUTE_TYPE.TSK_RULE;
import org.sleuthkit.datamodel.BlackboardAttribute;
import org.sleuthkit.datamodel.TskCoreException;

/**
 * Methods for scanning files for yara rule matches.
 */
final class YaraIngestHelper {

    private static final String YARA_DIR = "yara";
    private static final String YARA_C_EXE = "yarac64.exe";
    private static final String MODULE_NAME = YaraIngestModuleFactory.getModuleName();

    private YaraIngestHelper() {
    }

    /**
     * Uses the yarac tool to compile the rules in the given rule sets.
     *
     * @param ruleSetNames List of names of the selected rule sets.
     * @param tempDir      Path of the directory to put the compiled rule files.
     *
     * @throws org.sleuthkit.autopsy.ingest.IngestModule.IngestModuleException
     */
    static void compileRules(List<String> ruleSetNames, Path outputDir) throws IngestModuleException {
        if (ruleSetNames == null || ruleSetNames.isEmpty()) {
            throw new IngestModule.IngestModuleException(Bundle.YaraIngestModule_no_ruleSets());
        }

        // Find javac
        File exeFile = InstalledFileLocator.getDefault().locate(
                Paths.get(YARA_DIR, YARA_C_EXE).toString(),
                YaraIngestModule.class.getPackage().getName(), false);

        if (exeFile == null) {
            throw new IngestModuleException(Bundle.YaraIngestModule_yarac_not_found());
        }

        for (RuleSet set : getRuleSetsForNames(ruleSetNames)) {
            compileRuleSet(set, outputDir, exeFile);
        }
    }

    /**
     * Scan the given AbstractFile for yara rule matches from the rule sets in
     * the given directory creating a blackboard artifact for each matching
     * rule.
     *
     * The baseDirectory should contain a series of directories one for each
     * rule set.
     *
     * @param file          The file to scan.
     * @param baseDirectory Base directory for the compiled rule sets.
     *
     * @throws TskCoreException
     */
    static List<BlackboardArtifact> scanFileForMatches(AbstractFile file, File baseDirectory) throws TskCoreException, YaraWrapperException {
        List<BlackboardArtifact> artifacts = new ArrayList<>();

        byte[] fileBytes = new byte[(int) file.getSize()];
        file.read(fileBytes, 0, fileBytes.length);

        File[] ruleSetDirectories = baseDirectory.listFiles();
        for (File ruleSetDirectory : ruleSetDirectories) {
            List<String> ruleMatches = YaraIngestHelper.scanFileForMatches(fileBytes, ruleSetDirectory);
            if (!ruleMatches.isEmpty()) {
                artifacts.addAll(YaraIngestHelper.createArtifact(file, ruleSetDirectory.getName(), ruleMatches));
            }
        }

        return artifacts;
    }

    /**
     * Scan the given file byte array for rule matches using the YaraJNIWrapper
     * API.
     *
     * @param fileBytes
     * @param ruleSetDirectory
     *
     * @return List of rules that match from the given file from the given rule
     *         set. Empty list is returned if no matches where found.
     *
     * @throws TskCoreException
     */
    private static List<String> scanFileForMatches(byte[] fileBytes, File ruleSetDirectory) throws TskCoreException, YaraWrapperException {
        List<String> matchingRules = new ArrayList<>();

        File[] ruleSetCompiledFileList = ruleSetDirectory.listFiles();

        for (File ruleFile : ruleSetCompiledFileList) {
            matchingRules.addAll(YaraJNIWrapper.findRuleMatch(ruleFile.getAbsolutePath(), fileBytes));
        }

        return matchingRules;
    }

    /**
     * Create a list of Blackboard Artifacts, one for each matching rule.
     *
     * @param abstractFile  File to add artifact to.
     * @param ruleSetName   Name rule set with matching rule.
     * @param matchingRules Matching rule.
     *
     * @return List of artifacts or empty list if none were found.
     *
     * @throws TskCoreException
     */
    private static List<BlackboardArtifact> createArtifact(AbstractFile abstractFile, String ruleSetName, List<String> matchingRules) throws TskCoreException {
        List<BlackboardArtifact> artifacts = new ArrayList<>();
        for (String rule : matchingRules) {
            BlackboardArtifact artifact = abstractFile.newArtifact(TSK_YARA_HIT);
            List<BlackboardAttribute> attributes = new ArrayList<>();

            attributes.add(new BlackboardAttribute(TSK_SET_NAME, MODULE_NAME, ruleSetName));
            attributes.add(new BlackboardAttribute(TSK_RULE, MODULE_NAME, rule));

            artifact.addAttributes(attributes);
            artifacts.add(artifact);
        }
        return artifacts;
    }

    @NbBundle.Messages({
        "YaraIngestModule_yarac_not_found=Unable to compile YARA rules files. Unable to find executable at.",
        "YaraIngestModule_no_ruleSets=Unable to run YARA ingest, list of YARA rule sets was empty."
    })

    /**
     * Compiles the rule files in the given rule set.
     *
     * The compiled rule files are created in outputDir\RuleSetName.
     *
     * @param set       RuleSet for which to compile files.
     * @param outputDir Output directory for the compiled rule files.
     * @param yarac     yarac executeable file.
     *
     * @throws org.sleuthkit.autopsy.ingest.IngestModule.IngestModuleException
     */
    static private void compileRuleSet(RuleSet set, Path outputDir, File yarac) throws IngestModuleException {
        File tempFolder = Paths.get(outputDir.toString(), set.getName()).toFile();
        if (!tempFolder.exists()) {
            tempFolder.mkdir();
        }

        List<File> fileList = set.getRuleFiles();
        for (File file : fileList) {
            List<String> commandList = new ArrayList<>();
            commandList.add(String.format("\"%s\"", yarac.toString()));
            commandList.add(String.format("\"%s\"", file.toString()));
            commandList.add(String.format("\"%s\"", Paths.get(tempFolder.getAbsolutePath(), "compiled_" + file.getName())));

            ProcessBuilder builder = new ProcessBuilder(commandList);
            try {
                ExecUtil.execute(builder);
            } catch (SecurityException | IOException ex) {
                throw new IngestModuleException(String.format("Failed to compile Yara rules file", file.toString()), ex);
            }

        }
    }

    /**
     * Returns a list of RuleSet objects for the given list of RuleSet names.
     *
     * @param names List of RuleSet names.
     *
     * @return List of RuleSet or empty list if none of the names matched
     *         existing rules.
     */
    private static List<RuleSet> getRuleSetsForNames(List<String> names) {
        List<RuleSet> ruleSetList = new ArrayList<>();

        RuleSetManager manager = new RuleSetManager();
        for (RuleSet set : manager.getRuleSetList()) {
            if (names.contains(set.getName())) {
                ruleSetList.add(set);
            }
        }

        return ruleSetList;
    }
}