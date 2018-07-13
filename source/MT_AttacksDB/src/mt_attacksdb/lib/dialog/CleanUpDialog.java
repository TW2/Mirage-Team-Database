/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib.dialog;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import mt_attacksdb.lib.Arc;
import mt_attacksdb.lib.Attaque;
import mt_attacksdb.lib.ImagePerso;
import mt_attacksdb.lib.Style;
import mt_attacksdb.lib.TemplateGen;
import mt_attacksdb.lib.charts.BarChart3D;
import mt_attacksdb.lib.charts.PieChart3D;
import mt_attacksdb.lib.filter.ImagesFilter;
import mt_attacksdb.lib.renderer.ImagePersoListRenderer;

/**
 *
 * @author Yves
 */
public class CleanUpDialog extends javax.swing.JDialog {
    
    private final DefaultListModel dlmPerssonnages = new DefaultListModel();
    private final DefaultListModel dlmEquipes = new DefaultListModel();
    private final DefaultListModel dlmStyles = new DefaultListModel();
    private final DefaultListModel dlmArcs = new DefaultListModel();
    private final DefaultListModel dlmImages = new DefaultListModel();
    private final DefaultListModel dlmMessages = new DefaultListModel();
    private final DefaultListModel dlmTemplates = new DefaultListModel();
    
    private List<Attaque> attaques = new ArrayList<>();
    private List<Arc> arcs = new ArrayList<>();
    private List<ImagePerso> images = new ArrayList<>();
    private List<Style> styles = new ArrayList<>();
    private List<TemplateGen> templates = new ArrayList<>();
    private List<String> personnages = new ArrayList<>();
    private List<String> equipages = new ArrayList<>();
    
    private final String MODIFY_yes = "Cet objet peut être modifié";
    private final String DELETE_yes = "peut être supprimé";
    private final String DELETE_no = "NE PEUT PAS être supprimé";
    
    private BarChart3D bc3d = null;
    private PieChart3D pc3d = null;
    
    public enum Dependency{
        Arc, Image, Style, Template, Personnage, Equipe;
    }

    /**
     * Creates new form CleanUpDialog
     * @param parent
     * @param modal
     */
    public CleanUpDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public void init(List<Arc> arcs, List<ImagePerso> images, List<Style> styles, 
            List<TemplateGen> templates, List<String> personnages, List<String> equipages,
            List<Attaque> attaques){
        this.arcs = arcs;
        this.images = images;
        this.styles = styles;
        this.templates = templates;
        this.personnages = personnages;
        this.equipages = equipages;
        this.attaques = attaques;
        
        initList(listArcs, arcs, dlmArcs);
        initList(listImages, images, dlmImages);
        initList(listStyles, styles, dlmStyles);
        initList(listPersonnages, personnages, dlmPerssonnages);
        initList(listEquipes, equipages, dlmEquipes);
        initList(listTemplates, templates, dlmTemplates);
        
        listMessages.setModel(dlmMessages);
        
        listImages.setCellRenderer(new ImagePersoListRenderer());
        listImages.setVisibleRowCount(-1);
        listImages.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        
        updateCharts();
    }
    
    private void initList(JList l, List list, DefaultListModel dlm){
        l.setModel(dlm);
        dlm.clear();
        for(Object o : list.toArray()){
            dlm.addElement(o);
        }
    }
    
    private int getLinksCount(Object o, Dependency dependency){
        int counter = 0;
        
        if(o instanceof Arc){
            Arc arc = (Arc)o;
            for(Attaque att : attaques){
                if(att.getArc().getArcName().equalsIgnoreCase(arc.getArcName())){
                    counter += 1;
                }
            }
            return counter;
        }
        
        if(o instanceof ImagePerso){
            ImagePerso img = (ImagePerso)o;
            for(Attaque att : attaques){
                if(att.getImagePerso().getCharacterName().equalsIgnoreCase(img.getCharacterName())){
                    counter += 1;
                }
            }
            return counter;
        }
        
        if(o instanceof Style){
            Style sty = (Style)o;
            for(Attaque att : attaques){
                if(att.getStyle().getStyleName().equalsIgnoreCase(sty.getStyleName())){
                    counter += 1;
                }
            }
            return counter;
        }
        
        if(o instanceof TemplateGen){
            TemplateGen t = (TemplateGen)o;
            for(Attaque att : attaques){
                TemplateGen template = att.getTemplateGens().get(att.getTemplateGens().size()-1);
                if(template.getTemplateGenName().equalsIgnoreCase(t.getTemplateGenName())){
                    counter += 1;
                }
            }
            return counter;
        }
        
        if(o instanceof String && dependency == Dependency.Personnage){
            String str = (String)o;
            for(Attaque att : attaques){
                if(att.getCharacter().equalsIgnoreCase(str)){
                    counter += 1;
                }
            }
            return counter;
        }
        
        if(o instanceof String && dependency == Dependency.Equipe){
            String str = (String)o;
            for(Attaque att : attaques){
                if(att.getTeam().equalsIgnoreCase(str)){
                    counter += 1;
                }
            }
            return counter;
        }
        
        return counter;
    }
    
    private void updateCharts(){
        chartsPanel.removeAll();
        chartsPanel.setLayout(null);
        Map<String, Integer> generalChart = new HashMap<>();
        generalChart.put("Arcs", arcs.size());
        generalChart.put("Personnages", personnages.size());
        generalChart.put("Portaits", images.size());
        generalChart.put("Styles", styles.size());
        generalChart.put("Templates", templates.size());
        generalChart.put("Equipes", equipages.size());
        generalChart.put("Attaques", attaques.size());
        pc3d = new PieChart3D("Données", generalChart, chartsPanel.getWidth()-200, chartsPanel.getHeight());
        pc3d.setLocation(100, 0);
        chartsPanel.add(pc3d);
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        chartsPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listPersonnages = new javax.swing.JList();
        btnModifyCharacter = new javax.swing.JButton();
        btnRemoveCharacter = new javax.swing.JButton();
        lblExiCharacter = new javax.swing.JLabel();
        btnAddCharacter = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listEquipes = new javax.swing.JList();
        btnModifyTeam = new javax.swing.JButton();
        btnRemoveTeam = new javax.swing.JButton();
        btnAddTeam = new javax.swing.JButton();
        lblExiTeam = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listStyles = new javax.swing.JList();
        btnModifyStyles = new javax.swing.JButton();
        btnRemoveStyles = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        taStyles = new javax.swing.JTextArea();
        btnAddStyles = new javax.swing.JButton();
        lblExiStyles = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        taTemplate = new javax.swing.JTextArea();
        btnModifyTemplate = new javax.swing.JButton();
        btnRemoveTemplate = new javax.swing.JButton();
        lblExiTemplate = new javax.swing.JLabel();
        btnAddTemplate = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        listTemplates = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        tfVersion = new javax.swing.JTextField();
        btnChangeVersion = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        listArcs = new javax.swing.JList();
        btnModifyArcs = new javax.swing.JButton();
        btnRemoveArcs = new javax.swing.JButton();
        lblExiArcs = new javax.swing.JLabel();
        btnAddArcs = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        listImages = new javax.swing.JList();
        btnModifyImages = new javax.swing.JButton();
        btnRemoveImages = new javax.swing.JButton();
        btnAddImages = new javax.swing.JButton();
        lblExistence = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        listMessages = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        chartsPanel.setBackground(new java.awt.Color(255, 255, 255));
        chartsPanel.setLayout(null);
        jTabbedPane1.addTab("Analyse", chartsPanel);

        listPersonnages.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listPersonnages.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listPersonnagesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listPersonnages);

        btnModifyCharacter.setText("Modifier");
        btnModifyCharacter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyCharacterActionPerformed(evt);
            }
        });

        btnRemoveCharacter.setText("Supprimer");
        btnRemoveCharacter.setEnabled(false);
        btnRemoveCharacter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveCharacterActionPerformed(evt);
            }
        });

        lblExiCharacter.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblExiCharacter.setForeground(new java.awt.Color(204, 0, 153));
        lblExiCharacter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExiCharacter.setText("--");

        btnAddCharacter.setText("Ajouter");
        btnAddCharacter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCharacterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblExiCharacter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnModifyCharacter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRemoveCharacter, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btnAddCharacter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblExiCharacter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAddCharacter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModifyCharacter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveCharacter)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Personnages", jPanel3);

        listEquipes.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listEquipes.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listEquipesValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(listEquipes);

        btnModifyTeam.setText("Modifier");
        btnModifyTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyTeamActionPerformed(evt);
            }
        });

        btnRemoveTeam.setText("Supprimer");
        btnRemoveTeam.setEnabled(false);
        btnRemoveTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveTeamActionPerformed(evt);
            }
        });

        btnAddTeam.setText("Ajouter");
        btnAddTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTeamActionPerformed(evt);
            }
        });

        lblExiTeam.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblExiTeam.setForeground(new java.awt.Color(204, 0, 153));
        lblExiTeam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExiTeam.setText("--");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblExiTeam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnModifyTeam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRemoveTeam, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btnAddTeam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblExiTeam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnAddTeam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModifyTeam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveTeam)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Equipes", jPanel4);

        listStyles.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listStyles.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listStylesValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(listStyles);

        btnModifyStyles.setText("Modifier");
        btnModifyStyles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyStylesActionPerformed(evt);
            }
        });

        btnRemoveStyles.setText("Supprimer");
        btnRemoveStyles.setEnabled(false);
        btnRemoveStyles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveStylesActionPerformed(evt);
            }
        });

        taStyles.setEditable(false);
        taStyles.setColumns(20);
        taStyles.setRows(5);
        jScrollPane4.setViewportView(taStyles);

        btnAddStyles.setText("Ajouter");
        btnAddStyles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddStylesActionPerformed(evt);
            }
        });

        lblExiStyles.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblExiStyles.setForeground(new java.awt.Color(204, 0, 153));
        lblExiStyles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExiStyles.setText("--");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblExiStyles, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnRemoveStyles, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(btnModifyStyles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddStyles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblExiStyles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnAddStyles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModifyStyles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveStyles)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Styles", jPanel5);

        taTemplate.setEditable(false);
        taTemplate.setColumns(20);
        taTemplate.setRows(5);
        jScrollPane6.setViewportView(taTemplate);

        btnModifyTemplate.setText("Modifier");
        btnModifyTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyTemplateActionPerformed(evt);
            }
        });

        btnRemoveTemplate.setText("Supprimer");
        btnRemoveTemplate.setEnabled(false);
        btnRemoveTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveTemplateActionPerformed(evt);
            }
        });

        lblExiTemplate.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblExiTemplate.setForeground(new java.awt.Color(204, 0, 153));
        lblExiTemplate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExiTemplate.setText("--");

        btnAddTemplate.setText("Ajouter");
        btnAddTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTemplateActionPerformed(evt);
            }
        });

        listTemplates.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listTemplates.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listTemplatesValueChanged(evt);
            }
        });
        jScrollPane10.setViewportView(listTemplates);

        jLabel1.setText("Auteur et/ou version :");

        btnChangeVersion.setText("Changer");
        btnChangeVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeVersionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblExiTemplate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                            .addComponent(jScrollPane10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRemoveTemplate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModifyTemplate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddTemplate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnChangeVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblExiTemplate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnAddTemplate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModifyTemplate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveTemplate)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfVersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChangeVersion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Templates", jPanel6);

        listArcs.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listArcs.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listArcsValueChanged(evt);
            }
        });
        jScrollPane7.setViewportView(listArcs);

        btnModifyArcs.setText("Modifier");
        btnModifyArcs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyArcsActionPerformed(evt);
            }
        });

        btnRemoveArcs.setText("Supprimer");
        btnRemoveArcs.setEnabled(false);
        btnRemoveArcs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveArcsActionPerformed(evt);
            }
        });

        lblExiArcs.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblExiArcs.setForeground(new java.awt.Color(204, 0, 153));
        lblExiArcs.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExiArcs.setText("--");

        btnAddArcs.setText("Ajouter");
        btnAddArcs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddArcsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExiArcs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnModifyArcs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddArcs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRemoveArcs, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblExiArcs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnAddArcs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModifyArcs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveArcs)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Arcs", jPanel7);

        listImages.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listImages.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listImagesValueChanged(evt);
            }
        });
        jScrollPane8.setViewportView(listImages);

        btnModifyImages.setText("Modifier");
        btnModifyImages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyImagesActionPerformed(evt);
            }
        });

        btnRemoveImages.setText("Supprimer");
        btnRemoveImages.setEnabled(false);
        btnRemoveImages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveImagesActionPerformed(evt);
            }
        });

        btnAddImages.setText("Ajouter");
        btnAddImages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddImagesActionPerformed(evt);
            }
        });

        lblExistence.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblExistence.setForeground(new java.awt.Color(204, 0, 153));
        lblExistence.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExistence.setText("--");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExistence, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddImages, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModifyImages, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRemoveImages, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblExistence)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btnAddImages)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModifyImages)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveImages)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Images", jPanel8);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Modifications des sous-tableaux", jPanel2);

        listMessages.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane9.setViewportView(listMessages);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 844, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Messages", jPanel10);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listImagesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listImagesValueChanged
        ImagePerso img = (ImagePerso)listImages.getSelectedValue();
        int existence = getLinksCount(img, Dependency.Image);
        if(existence == 0){
            lblExistence.setText(MODIFY_yes+" et "+DELETE_yes+" : il n'est pas attaché à une attaque.");
            btnRemoveImages.setEnabled(true);
        }else{
            lblExistence.setText(MODIFY_yes+" et "+DELETE_no+" : il est attaché à "+ existence +" attaque(s).");
            btnRemoveImages.setEnabled(false);
        }
    }//GEN-LAST:event_listImagesValueChanged

    private void btnAddImagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddImagesActionPerformed
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
            }else{
                img = new ImagePerso(ii, fcImage.getSelectedFile().getName());
            }
            images.add(img);
            dlmImages.addElement(img);
        }
    }//GEN-LAST:event_btnAddImagesActionPerformed

    private void btnModifyImagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyImagesActionPerformed
        if(listImages.getSelectedIndex()!=-1){
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

                ImagePerso img = (ImagePerso)listImages.getSelectedValue();

                if(text!=null){
                    img.setCharacterName(text);
                    img.setImage(ii);                
                }else{
                    img.setCharacterName(fcImage.getSelectedFile().getName());
                    img.setImage(ii);
                }
                
                listImages.updateUI();
            }
        }        
    }//GEN-LAST:event_btnModifyImagesActionPerformed

    private void btnRemoveImagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveImagesActionPerformed
        if(listImages.getSelectedIndex()!=-1){
            ImagePerso img = (ImagePerso)listImages.getSelectedValue();
            dlmImages.removeElement(img);
            images.remove(img);
        }
    }//GEN-LAST:event_btnRemoveImagesActionPerformed

    private void listArcsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listArcsValueChanged
        Arc arc = (Arc)listArcs.getSelectedValue();
        int existence = getLinksCount(arc, Dependency.Arc);
        if(existence == 0){
            lblExiArcs.setText(MODIFY_yes+" et "+DELETE_yes+" : il n'est pas attaché à une attaque.");
            btnRemoveArcs.setEnabled(true);
        }else{
            lblExiArcs.setText(MODIFY_yes+" et "+DELETE_no+" : il est attaché à "+ existence +" attaque(s).");
            btnRemoveArcs.setEnabled(false);
        }
    }//GEN-LAST:event_listArcsValueChanged

    private void btnAddArcsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddArcsActionPerformed
        InputDialog id = new InputDialog(null, true);
        String text = id.showDialog();
        if(text!=null){
            Arc arc = new Arc(text);
            dlmArcs.addElement(arc);
            arcs.add(arc);
        }
    }//GEN-LAST:event_btnAddArcsActionPerformed

    private void btnModifyArcsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyArcsActionPerformed
        if(listArcs.getSelectedIndex()!=-1){
            InputDialog id = new InputDialog(null, true);
            String text = id.showDialog();
            if(text!=null){
                Arc arc = (Arc)listArcs.getSelectedValue();
                arc.setArcName(text);
                listArcs.updateUI();
            }
        }        
    }//GEN-LAST:event_btnModifyArcsActionPerformed

    private void btnRemoveArcsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveArcsActionPerformed
        if(listArcs.getSelectedIndex()!=-1){
            Arc arc = (Arc)listArcs.getSelectedValue();
            dlmArcs.removeElement(arc);
            arcs.remove(arc);
        }
    }//GEN-LAST:event_btnRemoveArcsActionPerformed

    private void listStylesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listStylesValueChanged
        Style sty = (Style)listStyles.getSelectedValue();
        taStyles.setText(sty.getStyle());
        int existence = getLinksCount(sty, Dependency.Style);
        if(existence == 0){
            lblExiStyles.setText(MODIFY_yes+" et "+DELETE_yes+" : il n'est pas attaché à une attaque.");            
            btnRemoveStyles.setEnabled(true);
        }else{
            lblExiStyles.setText(MODIFY_yes+" et "+DELETE_no+" : il est attaché à "+ existence +" attaque(s).");
            btnRemoveStyles.setEnabled(false);
        }
    }//GEN-LAST:event_listStylesValueChanged

    private void btnAddStylesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStylesActionPerformed
        AssDialog ad = new AssDialog(null, true);
        Style sty = ad.showStyleDialog();
        if(sty!=null){
            dlmStyles.addElement(sty);
            styles.add(sty);
        }
    }//GEN-LAST:event_btnAddStylesActionPerformed

    private void btnModifyStylesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyStylesActionPerformed
        if(listStyles.getSelectedIndex()!=-1){
            AssDialog ad = new AssDialog(null, true);
            Style sty = ad.showStyleDialog();
            if(sty!=null){
                Style style = (Style)listStyles.getSelectedValue();
                style.setStyle(sty.getStyle());
                style.setStyleName(sty.getStyleName());
                listStyles.updateUI();
            }
        }
    }//GEN-LAST:event_btnModifyStylesActionPerformed

    private void btnRemoveStylesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveStylesActionPerformed
        if(listStyles.getSelectedIndex()!=-1){
            Style style = (Style)listStyles.getSelectedValue();
            dlmStyles.removeElement(style);
            styles.remove(style);
        }
    }//GEN-LAST:event_btnRemoveStylesActionPerformed

    private void listEquipesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listEquipesValueChanged
        String str = (String)listEquipes.getSelectedValue();
        int existence = getLinksCount(str, Dependency.Equipe);
        if(existence == 0){
            lblExiTeam.setText(MODIFY_yes+" et "+DELETE_yes+" : il n'est pas attaché à une attaque.");            
            btnRemoveTeam.setEnabled(true);
        }else{
            lblExiTeam.setText(MODIFY_yes+" et "+DELETE_no+" : il est attaché à "+ existence +" attaque(s).");
            btnRemoveTeam.setEnabled(false);
        }
    }//GEN-LAST:event_listEquipesValueChanged

    private void btnAddTeamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTeamActionPerformed
        InputDialog id = new InputDialog(null, true);
        String text = id.showDialog();
        if(text!=null){
            dlmEquipes.addElement(text);
            equipages.add(text);
        }
    }//GEN-LAST:event_btnAddTeamActionPerformed

    private void btnModifyTeamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyTeamActionPerformed
        if(listEquipes.getSelectedIndex()!=-1){
            InputDialog id = new InputDialog(null, true);
            String text = id.showDialog();
            if(text!=null){
                String s = (String)listEquipes.getSelectedValue();
                equipages.set(equipages.indexOf(s), text);
                listEquipes.updateUI();
            }
        }
    }//GEN-LAST:event_btnModifyTeamActionPerformed

    private void btnRemoveTeamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveTeamActionPerformed
        if(listEquipes.getSelectedIndex()!=-1){
            String s = (String)listEquipes.getSelectedValue();
            dlmEquipes.removeElement(s);
            equipages.remove(s);            
        }
    }//GEN-LAST:event_btnRemoveTeamActionPerformed

    private void listPersonnagesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listPersonnagesValueChanged
        String str = (String)listPersonnages.getSelectedValue();
        int existence = getLinksCount(str, Dependency.Personnage);
        if(existence == 0){
            lblExiCharacter.setText(MODIFY_yes+" et "+DELETE_yes+" : il n'est pas attaché à une attaque.");            
            btnRemoveCharacter.setEnabled(true);
        }else{
            lblExiCharacter.setText(MODIFY_yes+" et "+DELETE_no+" : il est attaché à "+ existence +" attaque(s).");
            btnRemoveCharacter.setEnabled(false);
        }
    }//GEN-LAST:event_listPersonnagesValueChanged

    private void btnAddCharacterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCharacterActionPerformed
        InputDialog id = new InputDialog(null, true);
        String text = id.showDialog();
        if(text!=null){
            dlmPerssonnages.addElement(text);
            personnages.add(text);
        }
    }//GEN-LAST:event_btnAddCharacterActionPerformed

    private void btnModifyCharacterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyCharacterActionPerformed
        if(listPersonnages.getSelectedIndex()!=-1){
            InputDialog id = new InputDialog(null, true);
            String text = id.showDialog();
            if(text!=null){
                String s = (String)listPersonnages.getSelectedValue();
                personnages.set(personnages.indexOf(s), text);
                listPersonnages.updateUI();
            }
        }
    }//GEN-LAST:event_btnModifyCharacterActionPerformed

    private void btnRemoveCharacterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveCharacterActionPerformed
        if(listPersonnages.getSelectedIndex()!=-1){
            String s = (String)listPersonnages.getSelectedValue();
            dlmPerssonnages.removeElement(s);
            personnages.remove(s);
        }
    }//GEN-LAST:event_btnRemoveCharacterActionPerformed

    private void listTemplatesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listTemplatesValueChanged
        TemplateGen tpg = (TemplateGen)listTemplates.getSelectedValue();
        int existence = getLinksCount(tpg, Dependency.Template);
        if(existence == 0){
            lblExiTemplate.setText(MODIFY_yes+" et "+DELETE_yes+" : il n'est pas attaché à une attaque.");
            tfVersion.setText(tpg.getTemplateGenVersion());
            taTemplate.setText(tpg.getTemplateGen());
            btnRemoveTemplate.setEnabled(true);
        }else{
            lblExiTemplate.setText(MODIFY_yes+" et "+DELETE_no+" : il est attaché à "+ existence +" attaque(s).");
            tfVersion.setText(tpg.getTemplateGenVersion());
            taTemplate.setText(tpg.getTemplateGen());
            btnRemoveTemplate.setEnabled(false);
        }
    }//GEN-LAST:event_listTemplatesValueChanged

    private void btnAddTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTemplateActionPerformed
        AssDialog ad = new AssDialog(null, true);
        TemplateGen t = ad.showTemplateDialog();
        if(t!=null){
            dlmTemplates.addElement(t);
            templates.add(t);
        }
    }//GEN-LAST:event_btnAddTemplateActionPerformed

    private void btnModifyTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyTemplateActionPerformed
        if(listTemplates.getSelectedIndex()!=-1){
            AssDialog ad = new AssDialog(null, true);
            TemplateGen t = ad.showTemplateDialog();
            if(t!=null){
                TemplateGen tpg = (TemplateGen)listTemplates.getSelectedValue();
                tpg.setTemplateGen(t.getTemplateGen());
                tpg.setTemplateGenName(t.getTemplateGenName());
                tpg.setTemplateGenVersion(t.getTemplateGenVersion());
                listTemplates.updateUI();
            }
        }
    }//GEN-LAST:event_btnModifyTemplateActionPerformed

    private void btnRemoveTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveTemplateActionPerformed
        if(listTemplates.getSelectedIndex()!=-1){
            TemplateGen tpg = (TemplateGen)listTemplates.getSelectedValue();
            dlmTemplates.removeElement(tpg);
            templates.remove(tpg);
        }
    }//GEN-LAST:event_btnRemoveTemplateActionPerformed

    private void btnChangeVersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeVersionActionPerformed
        if(listTemplates.getSelectedIndex()!=-1){
            TemplateGen tpg = (TemplateGen)listTemplates.getSelectedValue();
            tpg.setTemplateGenVersion(tfVersion.getText());
        }
    }//GEN-LAST:event_btnChangeVersionActionPerformed

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
            java.util.logging.Logger.getLogger(CleanUpDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                CleanUpDialog dialog = new CleanUpDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAddArcs;
    private javax.swing.JButton btnAddCharacter;
    private javax.swing.JButton btnAddImages;
    private javax.swing.JButton btnAddStyles;
    private javax.swing.JButton btnAddTeam;
    private javax.swing.JButton btnAddTemplate;
    private javax.swing.JButton btnChangeVersion;
    private javax.swing.JButton btnModifyArcs;
    private javax.swing.JButton btnModifyCharacter;
    private javax.swing.JButton btnModifyImages;
    private javax.swing.JButton btnModifyStyles;
    private javax.swing.JButton btnModifyTeam;
    private javax.swing.JButton btnModifyTemplate;
    private javax.swing.JButton btnRemoveArcs;
    private javax.swing.JButton btnRemoveCharacter;
    private javax.swing.JButton btnRemoveImages;
    private javax.swing.JButton btnRemoveStyles;
    private javax.swing.JButton btnRemoveTeam;
    private javax.swing.JButton btnRemoveTemplate;
    private javax.swing.JPanel chartsPanel;
    private javax.swing.JFileChooser fcImage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblExiArcs;
    private javax.swing.JLabel lblExiCharacter;
    private javax.swing.JLabel lblExiStyles;
    private javax.swing.JLabel lblExiTeam;
    private javax.swing.JLabel lblExiTemplate;
    private javax.swing.JLabel lblExistence;
    private javax.swing.JList listArcs;
    private javax.swing.JList listEquipes;
    private javax.swing.JList listImages;
    private javax.swing.JList listMessages;
    private javax.swing.JList listPersonnages;
    private javax.swing.JList listStyles;
    private javax.swing.JList listTemplates;
    private javax.swing.JTextArea taStyles;
    private javax.swing.JTextArea taTemplate;
    private javax.swing.JTextField tfVersion;
    // End of variables declaration//GEN-END:variables
}
