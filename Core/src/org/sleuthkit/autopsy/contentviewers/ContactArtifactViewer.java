/*
 * Autopsy Forensic Browser
 *
 * Copyright 2011-2020 Basis Technology Corp.
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
package org.sleuthkit.autopsy.contentviewers;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.datamodel.BlackboardArtifact;
import org.sleuthkit.datamodel.BlackboardAttribute;
import org.sleuthkit.datamodel.TskCoreException;

/**
 * This class displays TSK_CONTACT artifact.
 */
public class ContactArtifactViewer extends javax.swing.JPanel implements ArtifactContentViewer {

    private final static Logger logger = Logger.getLogger(ContactArtifactViewer.class.getName());
    private static final long serialVersionUID = 1L;

    /**
     * Creates new form ContactArtifactViewer
     */
    public ContactArtifactViewer() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        namePanel = new javax.swing.JPanel();
        contactNameLabel = new javax.swing.JLabel();
        phonesLabel = new javax.swing.JLabel();
        phoneNumbersPanel = new javax.swing.JPanel();
        emailsLabel = new javax.swing.JLabel();
        emailsPanel = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        contactNameLabel.setFont(new java.awt.Font("Dialog", 2, 18)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(contactNameLabel, org.openide.util.NbBundle.getMessage(ContactArtifactViewer.class, "ContactArtifactViewer.contactNameLabel.text")); // NOI18N

        javax.swing.GroupLayout namePanelLayout = new javax.swing.GroupLayout(namePanel);
        namePanel.setLayout(namePanelLayout);
        namePanelLayout.setHorizontalGroup(
            namePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(namePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contactNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(233, Short.MAX_VALUE))
        );
        namePanelLayout.setVerticalGroup(
            namePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(namePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contactNameLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 227;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(namePanel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(phonesLabel, org.openide.util.NbBundle.getMessage(ContactArtifactViewer.class, "ContactArtifactViewer.phonesLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 19, 0, 0);
        add(phonesLabel, gridBagConstraints);

        phoneNumbersPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.ipady = 101;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        add(phoneNumbersPanel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(emailsLabel, org.openide.util.NbBundle.getMessage(ContactArtifactViewer.class, "ContactArtifactViewer.emailsLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 19, 0, 0);
        add(emailsLabel, gridBagConstraints);

        emailsPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.ipady = 106;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 0, 0);
        add(emailsPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void setArtifact(BlackboardArtifact artifact) {
            
        // wipe the panel clean
        this.removeAll();
        initComponents();
        
        List<BlackboardAttribute> phoneNumList = new ArrayList<>();
        List<BlackboardAttribute> emailList = new ArrayList<>();
        List<BlackboardAttribute> nameList = new ArrayList<>();
        List<BlackboardAttribute> otherList = new ArrayList<>();
            
            
        
        try {
            // RAMAN TBD: populate the display components from the artifact
           for (BlackboardAttribute bba : artifact.getAttributes()) {
                if (bba.getAttributeType().getTypeName().startsWith("TSK_PHONE")) {
                    phoneNumList.add(bba);
                } else if (bba.getAttributeType().getTypeName().startsWith("TSK_EMAIL")) {
                    emailList.add(bba);
                } else if (bba.getAttributeType().getTypeName().startsWith("TSK_NAME")) {
                    nameList.add(bba);
                } else {
                    otherList.add(bba);
                }
            }
        } catch (TskCoreException ex) {
           logger.log(Level.SEVERE, String.format("Error getting attributes for artifact (artifact_id=%d, obj_id=%d)", artifact.getArtifactID(), artifact.getObjectID()), ex);
        }
        
       setContactName(nameList);
       
       // TBD: set the phones
       //this.phonesLabel.setVisible(true);
       
       // TBD: set the emails
       
       
       // repaint
      
       this.revalidate();
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public boolean isSupported(BlackboardArtifact artifact) {
        return artifact.getArtifactTypeID() == BlackboardArtifact.ARTIFACT_TYPE.TSK_CONTACT.getTypeID();
    }

    private void setContactName(List<BlackboardAttribute> attributesList) {
        for (BlackboardAttribute bba : attributesList) {
            if (bba.getAttributeType().getTypeName().startsWith("TSK_NAME")) {
                contactNameLabel.setText(bba.getDisplayString());
                System.out.println("Setting contact name to: " + bba.getDisplayString());
                break;
            }
        }
        
        contactNameLabel.revalidate();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contactNameLabel;
    private javax.swing.JLabel emailsLabel;
    private javax.swing.JPanel emailsPanel;
    private javax.swing.JPanel namePanel;
    private javax.swing.JPanel phoneNumbersPanel;
    private javax.swing.JLabel phonesLabel;
    // End of variables declaration//GEN-END:variables
}
