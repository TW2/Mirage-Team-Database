/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib.dialog;

import java.util.Collections;
import mt_attacksdb.lib.filter.ImagesFilter;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import mt_attacksdb.lib.Arc;
import mt_attacksdb.lib.Attaque;
import mt_attacksdb.lib.Clipboard;
import mt_attacksdb.lib.ImagePerso;
import mt_attacksdb.lib.renderer.ImagePersoComboRenderer;
import mt_attacksdb.lib.Style;
import mt_attacksdb.lib.TemplateGen;
import mt_attacksdb.lib.sort.TrierArcs;
import mt_attacksdb.lib.sort.TrierEquipes;
import mt_attacksdb.lib.sort.TrierImages;
import mt_attacksdb.lib.sort.TrierPersonnages;
import mt_attacksdb.lib.sort.TrierStyles;
import mt_attacksdb.lib.sort.TrierTemplates;

/**
 *
 * @author Yves
 */
public class AttaqueDialog extends javax.swing.JDialog {

    private ButtonPressed bp;
    private final DefaultComboBoxModel dcbmCharacters = new DefaultComboBoxModel();
    private final DefaultComboBoxModel dcbmTeams = new DefaultComboBoxModel();
    private final DefaultComboBoxModel dcbmStyle = new DefaultComboBoxModel();
    private final DefaultComboBoxModel dcbmTemplates = new DefaultComboBoxModel();
    private final DefaultComboBoxModel dcbmArcs = new DefaultComboBoxModel();
    private final DefaultComboBoxModel dcbmImages = new DefaultComboBoxModel();
    private JTextField TextFieldInUse = null;
    
    public enum ButtonPressed{
        NONE, OK_BUTTON, CANCEL_BUTTON;
    }
    
    /**
     * Creates new form AttaqueDialog
     * @param parent
     * @param modal
     */
    public AttaqueDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        bp = ButtonPressed.NONE;
        
        cbCharacter.setModel(dcbmCharacters);
        cbTeam.setModel(dcbmTeams);
        cbStyle.setModel(dcbmStyle);
        cbTemplate.setModel(dcbmTemplates);
        cbArc.setModel(dcbmArcs);
        cbImage.setModel(dcbmImages);
        cbImage.setRenderer(new ImagePersoComboRenderer());
    }
    
    public Attaque showDialog(Attaque a){
        if(a!=null){
            try{
                tfAttaque.setText(a.getAttaqueName());
                tfAttaqueFR.setText(a.getFrenchName());
                tfAttaqueUS.setText(a.getUSName());
                cbCharacter.setSelectedItem(a.getCharacter());
                cbTeam.setSelectedItem(a.getTeam());
                cbStyle.setSelectedItem(a.getStyle());
                cbTemplate.setSelectedItem(a.getTemplateGens().get(a.getTemplateGens().size()-1));
                cbArc.setSelectedItem(a.getArc());
                cbImage.setSelectedItem(a.getImagePerso());
            }catch(Exception e){
                //Si une liste est vide alors on arrive ici.
            }            
        }
        
        setLocationRelativeTo(null);
        setVisible(true);
        
        if(bp.equals(ButtonPressed.OK_BUTTON)){
            Attaque att = new Attaque(
                    tfAttaque.getText(), 
                    tfAttaqueFR.getText(), 
                    tfAttaqueUS.getText(), 
                    (String)cbCharacter.getSelectedItem(),
                    (String)cbTeam.getSelectedItem(),
                    (Style)cbStyle.getSelectedItem(), 
                    (TemplateGen)cbTemplate.getSelectedItem(), 
                    (Arc)cbArc.getSelectedItem(), 
                    (ImagePerso)cbImage.getSelectedItem());
            return att;
        }else{
            return null;
        }
    }
    
    public void fillCharacters(List<String> personnages){
        dcbmCharacters.removeAllElements();
        Collections.sort(personnages, new TrierPersonnages());
        for(String s : personnages){
            dcbmCharacters.addElement(s);
        }        
    }
    
    public void fillTeams(List<String> equipes){
        dcbmTeams.removeAllElements();
        Collections.sort(equipes, new TrierEquipes());
        for(String s : equipes){
            dcbmTeams.addElement(s);
        }
    }
    
    public void fillStyles(List<Style> styles){
        dcbmStyle.removeAllElements();
        Collections.sort(styles, new TrierStyles());
        for(Style s : styles){
            dcbmStyle.addElement(s);
        }
    }
    
    public void fillTemplates(List<TemplateGen> templates){
        dcbmTemplates.removeAllElements();
        Collections.sort(templates, new TrierTemplates());
        for(TemplateGen s : templates){
            dcbmTemplates.addElement(s);
        }        
    }
    
    public void fillArcs(List<Arc> arcs){
        dcbmArcs.removeAllElements();
        Collections.sort(arcs, new TrierArcs());
        for(Arc s : arcs){
            dcbmArcs.addElement(s);
        }
    }
    
    public void fillImages(List<ImagePerso> images){
        dcbmImages.removeAllElements();
        Collections.sort(images, new TrierImages());
        for(ImagePerso s : images){
            dcbmImages.addElement(s);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fcImage = new javax.swing.JFileChooser();
        popCopyPaste = new javax.swing.JPopupMenu();
        popmCopy = new javax.swing.JMenuItem();
        popmPaste = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        tfAttaque = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfAttaqueFR = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfAttaqueUS = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbCharacter = new javax.swing.JComboBox();
        btnEditPerso = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cbTeam = new javax.swing.JComboBox();
        btnEditTeam = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cbStyle = new javax.swing.JComboBox();
        btnEditStyle = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cbTemplate = new javax.swing.JComboBox();
        btnEditTemplate = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cbArc = new javax.swing.JComboBox();
        btnEditArc = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        OK_Button = new javax.swing.JButton();
        Cancel_Button = new javax.swing.JButton();
        btnEditImage = new javax.swing.JButton();
        cbImage = new javax.swing.JComboBox();
        btnRemovePerso = new javax.swing.JButton();
        btnRemoveTeam = new javax.swing.JButton();
        btnRemoveStyle = new javax.swing.JButton();
        btnRemoveTemplate = new javax.swing.JButton();
        btnRemoveArc = new javax.swing.JButton();
        btnRemoveImage = new javax.swing.JButton();

        popmCopy.setText("Copier");
        popmCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmCopyActionPerformed(evt);
            }
        });
        popCopyPaste.add(popmCopy);

        popmPaste.setText("Coller");
        popmPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmPasteActionPerformed(evt);
            }
        });
        popCopyPaste.add(popmPaste);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Nom de l'attaque tel qu'elle doit apparaître à l'encodge :");

        tfAttaque.setText("?");
        tfAttaque.setComponentPopupMenu(popCopyPaste);
        tfAttaque.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfAttaqueFocusGained(evt);
            }
        });

        jLabel2.setText("Nom de l'attaque en RÔMAJI :");

        tfAttaqueFR.setText("?");
        tfAttaqueFR.setComponentPopupMenu(popCopyPaste);
        tfAttaqueFR.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfAttaqueFRFocusGained(evt);
            }
        });

        jLabel3.setText("Nom de l'attaque tel qu'elle apparait dans les scripts anglais :");

        tfAttaqueUS.setText("?");
        tfAttaqueUS.setComponentPopupMenu(popCopyPaste);
        tfAttaqueUS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfAttaqueUSFocusGained(evt);
            }
        });

        jLabel4.setText("Nom du personnage faisant l'attaque :");

        cbCharacter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnEditPerso.setText("+");
        btnEditPerso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditPersoActionPerformed(evt);
            }
        });

        jLabel5.setText("Nom du groupe ou de l'équipage :");

        cbTeam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnEditTeam.setText("+");
        btnEditTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditTeamActionPerformed(evt);
            }
        });

        jLabel6.setText("Style à utiliser pour l'attaque :");

        cbStyle.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnEditStyle.setText("+");
        btnEditStyle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditStyleActionPerformed(evt);
            }
        });

        jLabel7.setText("Nom du dernier template à utiliser :");

        cbTemplate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnEditTemplate.setText("+");
        btnEditTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditTemplateActionPerformed(evt);
            }
        });

        jLabel8.setText("Nom de l'arc où est vue l'attaque :");

        cbArc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnEditArc.setText("+");
        btnEditArc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditArcActionPerformed(evt);
            }
        });

        jLabel9.setText("Image du personnage :");

        OK_Button.setText("OK");
        OK_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OK_ButtonActionPerformed(evt);
            }
        });

        Cancel_Button.setText("Cancel");
        Cancel_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cancel_ButtonActionPerformed(evt);
            }
        });

        btnEditImage.setText("+");
        btnEditImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditImageActionPerformed(evt);
            }
        });

        cbImage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnRemovePerso.setText("-");
        btnRemovePerso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemovePersoActionPerformed(evt);
            }
        });

        btnRemoveTeam.setText("-");
        btnRemoveTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveTeamActionPerformed(evt);
            }
        });

        btnRemoveStyle.setText("-");
        btnRemoveStyle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveStyleActionPerformed(evt);
            }
        });

        btnRemoveTemplate.setText("-");
        btnRemoveTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveTemplateActionPerformed(evt);
            }
        });

        btnRemoveArc.setText("-");
        btnRemoveArc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveArcActionPerformed(evt);
            }
        });

        btnRemoveImage.setText("-");
        btnRemoveImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveImageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfAttaque)
                    .addComponent(tfAttaqueFR)
                    .addComponent(tfAttaqueUS)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 241, Short.MAX_VALUE)
                        .addComponent(Cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(OK_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cbImage, 0, 315, Short.MAX_VALUE)
                            .addComponent(cbArc, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbTemplate, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbStyle, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbTeam, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbCharacter, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnEditArc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditTemplate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditStyle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditTeam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditPerso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRemoveArc)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnRemoveTemplate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRemoveStyle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRemoveTeam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRemovePerso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnRemoveImage))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfAttaque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfAttaqueFR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfAttaqueUS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCharacter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditPerso)
                    .addComponent(btnRemovePerso))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTeam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditTeam)
                    .addComponent(btnRemoveTeam))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbStyle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditStyle)
                    .addComponent(btnRemoveStyle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditTemplate)
                    .addComponent(btnRemoveTemplate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbArc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditArc)
                    .addComponent(btnRemoveArc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbImage, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(OK_Button)
                            .addComponent(Cancel_Button))
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditImage)
                            .addComponent(btnRemoveImage))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OK_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OK_ButtonActionPerformed
        bp = ButtonPressed.OK_BUTTON;
        dispose();
    }//GEN-LAST:event_OK_ButtonActionPerformed

    private void Cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cancel_ButtonActionPerformed
        bp = ButtonPressed.CANCEL_BUTTON;
        dispose();
    }//GEN-LAST:event_Cancel_ButtonActionPerformed

    private void btnEditPersoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditPersoActionPerformed
        InputDialog id = new InputDialog(null, true);
        String text = id.showDialog();
        if(text!=null){
            dcbmCharacters.addElement(text);
            cbCharacter.setSelectedItem(text);
        }
    }//GEN-LAST:event_btnEditPersoActionPerformed

    private void btnEditTeamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditTeamActionPerformed
        InputDialog id = new InputDialog(null, true);
        String text = id.showDialog();
        if(text!=null){
            dcbmTeams.addElement(text);
            cbTeam.setSelectedItem(text);
        }
    }//GEN-LAST:event_btnEditTeamActionPerformed

    private void btnEditStyleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditStyleActionPerformed
        AssDialog ad = new AssDialog(null, true);
        Style sty = ad.showStyleDialog();
        if(sty!=null){
            dcbmStyle.addElement(sty);
            cbStyle.setSelectedItem(sty);
        }
    }//GEN-LAST:event_btnEditStyleActionPerformed

    private void btnEditTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditTemplateActionPerformed
        AssDialog ad = new AssDialog(null, true);
        TemplateGen t = ad.showTemplateDialog();
        if(t!=null){
            dcbmTemplates.addElement(t);
            cbTemplate.setSelectedItem(t);
        }
    }//GEN-LAST:event_btnEditTemplateActionPerformed

    private void btnEditArcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditArcActionPerformed
        InputDialog id = new InputDialog(null, true);
        String text = id.showDialog();
        if(text!=null){
            Arc arc = new Arc(text);
            dcbmArcs.addElement(arc);
            cbArc.setSelectedItem(arc);
        }
    }//GEN-LAST:event_btnEditArcActionPerformed

    private void btnEditImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditImageActionPerformed
        for (FileFilter f : fcImage.getChoosableFileFilters()){
            fcImage.removeChoosableFileFilter(f);
        }
        fcImage.addChoosableFileFilter(new ImagesFilter());
        fcImage.setDialogTitle("Choisir l'image...");
        SwingUtilities.updateComponentTreeUI(fcImage);
        int z = fcImage.showOpenDialog(this);
        if (z == JFileChooser.APPROVE_OPTION){
            ImageIcon ii = new ImageIcon(fcImage.getSelectedFile().getAbsolutePath());
            InputDialog id = new InputDialog(null, true);
            String text = id.showDialog();
            ImagePerso img;
            if(text!=null){
                img = new ImagePerso(ii, text);
                dcbmImages.addElement(img);                
            }else{
                img = new ImagePerso(ii, fcImage.getSelectedFile().getName());
                dcbmImages.addElement(img);
            }
            cbImage.setSelectedItem(img);
        }
    }//GEN-LAST:event_btnEditImageActionPerformed

    private void btnRemovePersoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemovePersoActionPerformed
        cbCharacter.remove(cbCharacter.getSelectedIndex());
    }//GEN-LAST:event_btnRemovePersoActionPerformed

    private void btnRemoveTeamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveTeamActionPerformed
        cbTeam.remove(cbTeam.getSelectedIndex());
    }//GEN-LAST:event_btnRemoveTeamActionPerformed

    private void btnRemoveStyleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveStyleActionPerformed
        cbStyle.remove(cbStyle.getSelectedIndex());
    }//GEN-LAST:event_btnRemoveStyleActionPerformed

    private void btnRemoveTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveTemplateActionPerformed
        cbTemplate.remove(cbTemplate.getSelectedIndex());
    }//GEN-LAST:event_btnRemoveTemplateActionPerformed

    private void btnRemoveArcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveArcActionPerformed
        cbArc.remove(cbArc.getSelectedIndex());
    }//GEN-LAST:event_btnRemoveArcActionPerformed

    private void btnRemoveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveImageActionPerformed
        cbImage.remove(cbImage.getSelectedIndex());
    }//GEN-LAST:event_btnRemoveImageActionPerformed

    private void tfAttaqueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfAttaqueFocusGained
        TextFieldInUse = tfAttaque;
    }//GEN-LAST:event_tfAttaqueFocusGained

    private void tfAttaqueFRFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfAttaqueFRFocusGained
        TextFieldInUse = tfAttaqueFR;
    }//GEN-LAST:event_tfAttaqueFRFocusGained

    private void tfAttaqueUSFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfAttaqueUSFocusGained
        TextFieldInUse = tfAttaqueUS;
    }//GEN-LAST:event_tfAttaqueUSFocusGained

    private void popmCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmCopyActionPerformed
        if(TextFieldInUse!=null){
            Clipboard c = new Clipboard();
            c.CCopy(TextFieldInUse.getText());
        }
    }//GEN-LAST:event_popmCopyActionPerformed

    private void popmPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmPasteActionPerformed
        if(TextFieldInUse!=null){
            Clipboard c = new Clipboard();
            TextFieldInUse.setText(c.CPaste());
        }
    }//GEN-LAST:event_popmPasteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AttaqueDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                AttaqueDialog dialog = new AttaqueDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cancel_Button;
    private javax.swing.JButton OK_Button;
    private javax.swing.JButton btnEditArc;
    private javax.swing.JButton btnEditImage;
    private javax.swing.JButton btnEditPerso;
    private javax.swing.JButton btnEditStyle;
    private javax.swing.JButton btnEditTeam;
    private javax.swing.JButton btnEditTemplate;
    private javax.swing.JButton btnRemoveArc;
    private javax.swing.JButton btnRemoveImage;
    private javax.swing.JButton btnRemovePerso;
    private javax.swing.JButton btnRemoveStyle;
    private javax.swing.JButton btnRemoveTeam;
    private javax.swing.JButton btnRemoveTemplate;
    private javax.swing.JComboBox cbArc;
    private javax.swing.JComboBox cbCharacter;
    private javax.swing.JComboBox cbImage;
    private javax.swing.JComboBox cbStyle;
    private javax.swing.JComboBox cbTeam;
    private javax.swing.JComboBox cbTemplate;
    private javax.swing.JFileChooser fcImage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPopupMenu popCopyPaste;
    private javax.swing.JMenuItem popmCopy;
    private javax.swing.JMenuItem popmPaste;
    private javax.swing.JTextField tfAttaque;
    private javax.swing.JTextField tfAttaqueFR;
    private javax.swing.JTextField tfAttaqueUS;
    // End of variables declaration//GEN-END:variables
}
