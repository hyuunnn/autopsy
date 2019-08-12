/*
 * Autopsy Forensic Browser
 *
 * Copyright 2019 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.modules.plaso;

import org.sleuthkit.autopsy.ingest.IngestModuleIngestJobSettings;
import org.sleuthkit.autopsy.ingest.IngestModuleIngestJobSettingsPanel;

/**
 * Settings panel for the PlasoIngestModule.
 */
public class PlasoModuleSettingsPanel extends IngestModuleIngestJobSettingsPanel {

    private final PlasoModuleSettings settings;

    public PlasoModuleSettingsPanel(PlasoModuleSettings settings) {
        this.settings = settings;
        initComponents();
    }

    /** This method is called from within the constructor to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jFileChooser1 = new javax.swing.JFileChooser();
        winRegCheckBox = new javax.swing.JCheckBox();
        peCheckBox = new javax.swing.JCheckBox();
        plasoParserInfoTextArea = new javax.swing.JTextArea();
        noteLabel = new javax.swing.JLabel();
        disabledNoteLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(winRegCheckBox, org.openide.util.NbBundle.getMessage(PlasoModuleSettingsPanel.class, "PlasoModuleSettingsPanel.winRegCheckBox.text")); // NOI18N
        winRegCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                winRegCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 5, 15);
        add(winRegCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(peCheckBox, org.openide.util.NbBundle.getMessage(PlasoModuleSettingsPanel.class, "PlasoModuleSettingsPanel.peCheckBox.text")); // NOI18N
        peCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                peCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 9, 15);
        add(peCheckBox, gridBagConstraints);

        plasoParserInfoTextArea.setEditable(false);
        plasoParserInfoTextArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        plasoParserInfoTextArea.setColumns(20);
        plasoParserInfoTextArea.setLineWrap(true);
        plasoParserInfoTextArea.setRows(1);
        plasoParserInfoTextArea.setText(org.openide.util.NbBundle.getMessage(PlasoModuleSettingsPanel.class, "PlasoModuleSettingsPanel.plasoParserInfoTextArea.text")); // NOI18N
        plasoParserInfoTextArea.setWrapStyleWord(true);
        plasoParserInfoTextArea.setBorder(null);
        plasoParserInfoTextArea.setPreferredSize(new java.awt.Dimension(160, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 9, 15);
        add(plasoParserInfoTextArea, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(noteLabel, org.openide.util.NbBundle.getMessage(PlasoModuleSettingsPanel.class, "PlasoModuleSettingsPanel.noteLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(9, 15, 9, 15);
        add(noteLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(disabledNoteLabel, org.openide.util.NbBundle.getMessage(PlasoModuleSettingsPanel.class, "PlasoModuleSettingsPanel.disabledNoteLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 15);
        add(disabledNoteLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void winRegCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_winRegCheckBoxActionPerformed
        settings.setParserEnabled("winreg", winRegCheckBox.isSelected());
    }//GEN-LAST:event_winRegCheckBoxActionPerformed

    private void peCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_peCheckBoxActionPerformed
        settings.setParserEnabled("pe", peCheckBox.isSelected());
    }//GEN-LAST:event_peCheckBoxActionPerformed

    @Override
    public IngestModuleIngestJobSettings getSettings() {
        return settings;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel disabledNoteLabel;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel noteLabel;
    private javax.swing.JCheckBox peCheckBox;
    private javax.swing.JTextArea plasoParserInfoTextArea;
    private javax.swing.JCheckBox winRegCheckBox;
    // End of variables declaration//GEN-END:variables
}