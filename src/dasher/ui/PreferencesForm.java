/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PreferencesForm.java
 *
 * Created on Sep 18, 2009, 3:59:21 PM
 */
package dasher.ui;

import dasher.utils.FontListener;
import dasher.core.CDasherInterfaceBase;
import dasher.settings.Ebp_parameters;
import dasher.settings.Elp_parameters;
import dasher.settings.Esp_parameters;
import dasher.utils.ExtensionFileFilter;
import dasher.utils.JFontDialog;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.ListModel;

/**
 *
 * @author joshua
 */
public class PreferencesForm extends javax.swing.JFrame implements FontListener {

    private CDasherInterfaceBase dasher;
    private JTextArea edit;
    private DefaultListModel colours = new DefaultListModel();
    private DefaultListModel alphabets = new DefaultListModel();
    String[] controlStyles = {"Normal Control", "Click Mode"};
    private DefaultComboBoxModel controlStyle = new DefaultComboBoxModel(controlStyles);
    String[] speeds = {"Slow", "Normal", "Fast", "Fastest", "Auto-adjust"};
    private DefaultComboBoxModel speed = new DefaultComboBoxModel(speeds);
    String[] fontSizes = {"Large", "Medium", "Small"};
    private DefaultComboBoxModel fontSize = new DefaultComboBoxModel(fontSizes);
    private Font font = null;
    private JFontDialog fontDialog;

    protected JFileChooser fileChooser;

    /** Creates new form PreferencesForm */
    public PreferencesForm(CDasherInterfaceBase dasher, JTextArea edit) {
        this.dasher = dasher;
        this.edit = edit;
        fontDialog = new JFontDialog(this, edit.getFont());
        fileChooser = new JFileChooser(dasher.getSetting(Esp_parameters.SP_USER_LOC));
        fileChooser.addChoosableFileFilter(new ExtensionFileFilter("xml", "XML Files"));
        initComponents();
    }

    public ListModel makeListModel(Collection items) {
        DefaultListModel model = new DefaultListModel();
        for (Object item : items) {
            model.addElement(item);
        }
        return model;
    }

    public void resetPreferencesForm() {
        ArrayList<String> newColours = new ArrayList<String>();
        dasher.GetColours(newColours);
        jListColours.setModel(makeListModel(newColours));
        jListColours.setSelectedValue(dasher.getSetting(Esp_parameters.SP_COLOUR_ID), true);

        ArrayList<String> alphs = new ArrayList<String>();
        dasher.GetAlphabets(alphs);
        jListAlphabets.setModel(makeListModel(alphs));
        jListAlphabets.setSelectedValue(dasher.getSetting(Esp_parameters.SP_ALPHABET_ID), true);

//        newMenuBar.setSelectedFontSize((int) Dasher.getSetting(Elp_parameters.LP_DASHER_FONTSIZE));
        // TODO: Arghhh!  LP_DASHER_FONTSIZE really should be an enum
        switch ((int) dasher.getSetting(Elp_parameters.LP_DASHER_FONTSIZE)) {
            case 1:
                jComboBoxDasherFontSize.setSelectedItem("Small");
                break;
            case 2:
                jComboBoxDasherFontSize.setSelectedItem("Medium");
                break;
            case 4:
                jComboBoxDasherFontSize.setSelectedItem("Large");
                break;
        }
//        newMenuBar.setInputFilter(Dasher.getSetting(Esp_parameters.SP_INPUT_FILTER));
        // TODO: maybe SP_INPUT_FILTER should be an enum
        jComboBoxControlStyle.setSelectedItem(dasher.getSetting(Esp_parameters.SP_INPUT_FILTER));
//        newMenuBar.setMouseLine(Dasher.getSetting(Ebp_parameters.BP_DRAW_MOUSE_LINE));
        jCheckBoxDrawMouseLine.setSelected(dasher.getSetting(Ebp_parameters.BP_DRAW_MOUSE_LINE));
//        newMenuBar.setStartMouse(Dasher.getSetting(Ebp_parameters.BP_START_MOUSE));
        jCheckBoxStartOnMouseClick.setSelected(dasher.getSetting(Ebp_parameters.BP_START_MOUSE));
//        newMenuBar.setStartSpace(Dasher.getSetting(Ebp_parameters.BP_START_SPACE));
        jCheckBoxStartOnSpacebar.setSelected(dasher.getSetting(Ebp_parameters.BP_START_SPACE));
        // TODO: LP_LANGUAGE_MODEL_ID should be an enum
//      TODO: newMenuBar.setSelectedLM((int) Dasher.getSetting(Elp_parameters.LP_LANGUAGE_MODEL_ID));
//        newMenuBar.setSpeedAuto(Dasher.getSetting(Ebp_parameters.BP_AUTO_SPEEDCONTROL));
        jSliderSpeed.setValue((int) dasher.getSetting(Elp_parameters.LP_MAX_BITRATE));
        jCheckBoxAutoSpeed.setSelected(dasher.getSetting(Ebp_parameters.BP_AUTO_SPEEDCONTROL));
//        newMenuBar.setLangModelLearns(Dasher.getSetting(Ebp_parameters.BP_LM_ADAPTIVE));
        jCheckBoxLearns.setSelected(dasher.getSetting(Ebp_parameters.BP_LM_ADAPTIVE));
        font = edit.getFont();
        fontDialog.setOriginalFont(font);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelLanguage = new javax.swing.JPanel();
        jCheckBoxLearns = new javax.swing.JCheckBox();
        jScrollPaneAlphabets = new javax.swing.JScrollPane();
        jListAlphabets = new javax.swing.JList();
        jButtonLoadAlph = new javax.swing.JButton();
        jPanelAppearance = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListColours = new javax.swing.JList();
        jPanel5 = new javax.swing.JPanel();
        jButtonSelectFont = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxDasherFontSize = new javax.swing.JComboBox();
        jPanelControl = new javax.swing.JPanel();
        jPanelStartStop = new javax.swing.JPanel();
        jCheckBoxStartOnMouseClick = new javax.swing.JCheckBox();
        jCheckBoxStartOnSpacebar = new javax.swing.JCheckBox();
        jPanelControlStyle = new javax.swing.JPanel();
        jComboBoxControlStyle = new javax.swing.JComboBox();
        jPanelSpeed = new javax.swing.JPanel();
        jSliderSpeed = new javax.swing.JSlider();
        jCheckBoxAutoSpeed = new javax.swing.JCheckBox();
        jCheckBoxDrawMouseLine = new javax.swing.JCheckBox();
        jPanelOkApplyCancel = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonApply = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Preferences");

        jTabbedPane1.setBackground(java.awt.Color.gray);

        jCheckBoxLearns.setSelected(true);
        jCheckBoxLearns.setText("Dasher learns as you type");

        jListAlphabets.setModel(alphabets);
        jListAlphabets.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPaneAlphabets.setViewportView(jListAlphabets);

        jButtonLoadAlph.setText("Load alphabet file...");
        jButtonLoadAlph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoadAlphActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelLanguageLayout = new org.jdesktop.layout.GroupLayout(jPanelLanguage);
        jPanelLanguage.setLayout(jPanelLanguageLayout);
        jPanelLanguageLayout.setHorizontalGroup(
            jPanelLanguageLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelLanguageLayout.createSequentialGroup()
                .addContainerGap()
                .add(jCheckBoxLearns)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 114, Short.MAX_VALUE)
                .add(jButtonLoadAlph)
                .addContainerGap())
            .add(jScrollPaneAlphabets, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
        );
        jPanelLanguageLayout.setVerticalGroup(
            jPanelLanguageLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelLanguageLayout.createSequentialGroup()
                .add(jScrollPaneAlphabets, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelLanguageLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jCheckBoxLearns)
                    .add(jButtonLoadAlph))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Language", jPanelLanguage);

        jListColours.setModel(colours);
        jListColours.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jListColours);

        jButtonSelectFont.setText("Select Font...");
        jButtonSelectFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectFontActionPerformed(evt);
            }
        });

        jLabel1.setText("Dasher Font Size");

        jComboBoxDasherFontSize.setModel(fontSize);
        jComboBoxDasherFontSize.setSelectedItem("Small");

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabel1)
                    .add(jComboBoxDasherFontSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButtonSelectFont))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jButtonSelectFont)
                .add(18, 18, 18)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jComboBoxDasherFontSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(173, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanelAppearanceLayout = new org.jdesktop.layout.GroupLayout(jPanelAppearance);
        jPanelAppearance.setLayout(jPanelAppearanceLayout);
        jPanelAppearanceLayout.setHorizontalGroup(
            jPanelAppearanceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelAppearanceLayout.createSequentialGroup()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanelAppearanceLayout.setVerticalGroup(
            jPanelAppearanceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Appearance", jPanelAppearance);

        jPanelStartStop.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.darkGray), "Starting and Stopping"));

        jCheckBoxStartOnMouseClick.setSelected(true);
        jCheckBoxStartOnMouseClick.setText("Start on mouse click");

        jCheckBoxStartOnSpacebar.setText("Start on spacebar");

        org.jdesktop.layout.GroupLayout jPanelStartStopLayout = new org.jdesktop.layout.GroupLayout(jPanelStartStop);
        jPanelStartStop.setLayout(jPanelStartStopLayout);
        jPanelStartStopLayout.setHorizontalGroup(
            jPanelStartStopLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelStartStopLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanelStartStopLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jCheckBoxStartOnMouseClick)
                    .add(jCheckBoxStartOnSpacebar)))
        );
        jPanelStartStopLayout.setVerticalGroup(
            jPanelStartStopLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelStartStopLayout.createSequentialGroup()
                .addContainerGap()
                .add(jCheckBoxStartOnMouseClick)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jCheckBoxStartOnSpacebar)
                .addContainerGap(133, Short.MAX_VALUE))
        );

        jPanelControlStyle.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.darkGray), "Control Style"));

        jComboBoxControlStyle.setModel(controlStyle);
        jComboBoxControlStyle.setSelectedItem("Normal Control");

        org.jdesktop.layout.GroupLayout jPanelControlStyleLayout = new org.jdesktop.layout.GroupLayout(jPanelControlStyle);
        jPanelControlStyle.setLayout(jPanelControlStyleLayout);
        jPanelControlStyleLayout.setHorizontalGroup(
            jPanelControlStyleLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jComboBoxControlStyle, 0, 252, Short.MAX_VALUE)
        );
        jPanelControlStyleLayout.setVerticalGroup(
            jPanelControlStyleLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jComboBoxControlStyle)
        );

        jPanelSpeed.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.darkGray), "Speed"));

        jSliderSpeed.setMajorTickSpacing(100);
        jSliderSpeed.setMaximum(800);
        jSliderSpeed.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderSpeedStateChanged(evt);
            }
        });

        jCheckBoxAutoSpeed.setText("Adjust Speed Automaticly");

        org.jdesktop.layout.GroupLayout jPanelSpeedLayout = new org.jdesktop.layout.GroupLayout(jPanelSpeed);
        jPanelSpeed.setLayout(jPanelSpeedLayout);
        jPanelSpeedLayout.setHorizontalGroup(
            jPanelSpeedLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jSliderSpeed, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
            .add(jPanelSpeedLayout.createSequentialGroup()
                .addContainerGap()
                .add(jCheckBoxAutoSpeed)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanelSpeedLayout.setVerticalGroup(
            jPanelSpeedLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelSpeedLayout.createSequentialGroup()
                .add(jSliderSpeed, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 9, Short.MAX_VALUE)
                .add(jCheckBoxAutoSpeed)
                .addContainerGap())
        );

        jCheckBoxDrawMouseLine.setSelected(true);
        jCheckBoxDrawMouseLine.setText("Draw mouse line");

        org.jdesktop.layout.GroupLayout jPanelControlLayout = new org.jdesktop.layout.GroupLayout(jPanelControl);
        jPanelControl.setLayout(jPanelControlLayout);
        jPanelControlLayout.setHorizontalGroup(
            jPanelControlLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelControlLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelControlLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelControlLayout.createSequentialGroup()
                        .add(jCheckBoxDrawMouseLine)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                    .add(jPanelControlLayout.createSequentialGroup()
                        .add(jPanelSpeed, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(jPanelControlStyle, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(16, 16, 16)
                .add(jPanelStartStop, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelControlLayout.setVerticalGroup(
            jPanelControlLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelControlLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelControlLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelControlLayout.createSequentialGroup()
                        .add(jPanelStartStop, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(jPanelControlLayout.createSequentialGroup()
                        .add(jPanelControlStyle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanelSpeed, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jCheckBoxDrawMouseLine)
                        .add(43, 43, 43))))
        );

        jTabbedPane1.addTab("Control", jPanelControl);

        jButtonOk.setText("Ok");
        jButtonOk.setSelected(true);
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jButtonApply.setText("Apply");
        jButtonApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApplyActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelOkApplyCancelLayout = new org.jdesktop.layout.GroupLayout(jPanelOkApplyCancel);
        jPanelOkApplyCancel.setLayout(jPanelOkApplyCancelLayout);
        jPanelOkApplyCancelLayout.setHorizontalGroup(
            jPanelOkApplyCancelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelOkApplyCancelLayout.createSequentialGroup()
                .addContainerGap(261, Short.MAX_VALUE)
                .add(jButtonCancel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonApply)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonOk)
                .addContainerGap())
        );
        jPanelOkApplyCancelLayout.setVerticalGroup(
            jPanelOkApplyCancelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelOkApplyCancelLayout.createSequentialGroup()
                .add(jPanelOkApplyCancelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonOk)
                    .add(jButtonApply)
                    .add(jButtonCancel))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelOkApplyCancel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanelOkApplyCancel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApplyActionPerformed
        dasher.SetStringParameter(Esp_parameters.SP_COLOUR_ID,
                (String) jListColours.getSelectedValue());
        if (!dasher.getSetting(Esp_parameters.SP_ALPHABET_ID).equals((String) jListAlphabets.getSelectedValue())) {
            dasher.SetStringParameter(Esp_parameters.SP_ALPHABET_ID,
                    (String) jListAlphabets.getSelectedValue());
        }

        String size = (String) jComboBoxDasherFontSize.getSelectedItem();
        if (size.equals("Small"))
            dasher.SetLongParameter(Elp_parameters.LP_DASHER_FONTSIZE, 1);
        else if (size.equals("Medium"))
            dasher.SetLongParameter(Elp_parameters.LP_DASHER_FONTSIZE, 2);
        else if (size.equals("Large"))
            dasher.SetLongParameter(Elp_parameters.LP_DASHER_FONTSIZE, 4);
        
        dasher.SetStringParameter(Esp_parameters.SP_INPUT_FILTER,
                (String) jComboBoxControlStyle.getSelectedItem());
        dasher.SetBoolParameter(Ebp_parameters.BP_DRAW_MOUSE_LINE,
                jCheckBoxDrawMouseLine.getModel().isSelected());
        dasher.SetBoolParameter(Ebp_parameters.BP_START_MOUSE,
                jCheckBoxStartOnMouseClick.getModel().isSelected());
        dasher.SetBoolParameter(Ebp_parameters.BP_START_SPACE,
                jCheckBoxStartOnSpacebar.getModel().isSelected());
        dasher.SetBoolParameter(Ebp_parameters.BP_LM_ADAPTIVE,
                jCheckBoxLearns.getModel().isSelected());
        
        if (jCheckBoxAutoSpeed.getModel().isSelected())
            dasher.SetBoolParameter(Ebp_parameters.BP_AUTO_SPEEDCONTROL, true);
        else {
            dasher.SetBoolParameter(Ebp_parameters.BP_AUTO_SPEEDCONTROL, false);
            dasher.SetLongParameter(Elp_parameters.LP_MAX_BITRATE,
                    jSliderSpeed.getValue());
        }
        
        edit.setFont(font);
    }//GEN-LAST:event_jButtonApplyActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        jButtonApplyActionPerformed(evt);
        setVisible(false);
    }//GEN-LAST:event_jButtonOkActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        resetPreferencesForm();
        setVisible(false);
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonSelectFontActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectFontActionPerformed
        fontDialog.setVisible(true);
    }//GEN-LAST:event_jButtonSelectFontActionPerformed

    private void jSliderSpeedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderSpeedStateChanged
        jCheckBoxAutoSpeed.setSelected(false);
    }//GEN-LAST:event_jSliderSpeedStateChanged

    private void jButtonLoadAlphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoadAlphActionPerformed
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            dasher.readAlphabetFile(fileChooser.getSelectedFile());
        }
    }//GEN-LAST:event_jButtonLoadAlphActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonApply;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonLoadAlph;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JButton jButtonSelectFont;
    private javax.swing.JCheckBox jCheckBoxAutoSpeed;
    private javax.swing.JCheckBox jCheckBoxDrawMouseLine;
    private javax.swing.JCheckBox jCheckBoxLearns;
    private javax.swing.JCheckBox jCheckBoxStartOnMouseClick;
    private javax.swing.JCheckBox jCheckBoxStartOnSpacebar;
    private javax.swing.JComboBox jComboBoxControlStyle;
    private javax.swing.JComboBox jComboBoxDasherFontSize;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jListAlphabets;
    private javax.swing.JList jListColours;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelAppearance;
    private javax.swing.JPanel jPanelControl;
    private javax.swing.JPanel jPanelControlStyle;
    private javax.swing.JPanel jPanelLanguage;
    private javax.swing.JPanel jPanelOkApplyCancel;
    private javax.swing.JPanel jPanelSpeed;
    private javax.swing.JPanel jPanelStartStop;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneAlphabets;
    private javax.swing.JSlider jSliderSpeed;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

    public void setNewFont(Font f) {
        font = f;
    }
}
