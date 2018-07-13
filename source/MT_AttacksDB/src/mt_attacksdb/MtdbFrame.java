/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.xml.parsers.ParserConfigurationException;
import mt_attacksdb.lib.Arc;
import mt_attacksdb.lib.Attaque;
import mt_attacksdb.lib.dialog.AttaqueDialog;
import mt_attacksdb.lib.ImagePerso;
import mt_attacksdb.lib.renderer.ImagePersoTableRenderer;
import mt_attacksdb.lib.Style;
import mt_attacksdb.lib.TemplateGen;
import mt_attacksdb.lib.dialog.AboutDialog;
import mt_attacksdb.lib.dialog.AssDialog;
import mt_attacksdb.lib.dialog.CleanUpDialog;
import mt_attacksdb.lib.dialog.FilterDialog;
import mt_attacksdb.lib.dialog.TemplateViewDialog;
import mt_attacksdb.lib.sort.TrierParArcs;
import mt_attacksdb.lib.sort.TrierParAttaques;
import mt_attacksdb.lib.sort.TrierParPersonnages;
import mt_attacksdb.lib.filter.ASSFilter;
import mt_attacksdb.lib.io.*;
import org.xml.sax.SAXException;

/**
 *
 * @author Yves
 */
public class MtdbFrame extends javax.swing.JFrame {

    List<Attaque> attaques = new ArrayList<>();
    List<Arc> arcs = new ArrayList<>();
    List<ImagePerso> images = new ArrayList<>();
    List<Style> styles = new ArrayList<>();
    List<TemplateGen> templates = new ArrayList<>();
    List<String> personnages = new ArrayList<>();
    List<String> equipages = new ArrayList<>();
    
    DefaultTableModel tableModel = null;
    int countEntry = 0;
    CycleSave cyclique = CycleSave.Five;
    String filtrePersonnage = "aucun";
    
    SplashFrame sfx = new SplashFrame();
            
    public enum CycleSave{
        Five(5), Four(4), Three(3), Two(2) ,One(1);
        
        int cycle = 5;

        CycleSave(int cycle) {
            this.cycle = cycle;
        }
        
        public int getCycle(){
            return cycle;
        }
        
        public CycleSave getNext(){
            if(cycle==1){
                return Two;
            }else if(cycle==2){
                return Three;
            }else if(cycle==3){
                return Four;
            }else if(cycle==4){
                return Five;
            }else{//cycle==5
                return One;
            }
        }
        
        public String getText(){
            return "Sauvegarde automatique : " + cycle;
        }
    }
    
    /**
     * Creates new form MtdbFrame
     */
    public MtdbFrame() {
        sfx.setVisible(true); //SplashFrame
        sfx.setState("Initialisations...", 0);
        initComponents();
        init();
        sfx.setState("OK", 100);
        sfx.setVisible(false);
        sfx.dispose();
    }
    
    private void init(){
        sfx.setState("Préparation de l'UI...", 10);
        setLocationRelativeTo(null);
        ImageIcon iiFrame = new ImageIcon(getClass().getResource("71096_104647760324_3539854_q.jpg"));
        setIconImage(iiFrame.getImage());
        setTitle("Mirage-Team - Base de données des attaques");
        
        try {
            javax.swing.UIManager.setLookAndFeel(new NimbusLookAndFeel());
            javax.swing.SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException exc) {
            System.out.println("Nimbus LookAndFeel not loaded : "+exc);
        }
        
        sfx.setState("Création de la base...", 20);
        String[] head = new String[]{"Attaque", "Rômaji", "Attaque US",
            "Personnage", "Equipage", "Style", "Template", "Arc", "Image"};
        
        tableModel = new DefaultTableModel(
                null,
                head
        ){
            Class[] types = new Class [] {
                Attaque.class, String.class, String.class,
                String.class, String.class, Style.class,
                TemplateGen.class, Arc.class, ImagePerso.class};
            boolean[] canEdit = new boolean [] {
                false, false, false,
                false, false, false,
                false, false, false};
            @Override
            public Class getColumnClass(int columnIndex) {return types [columnIndex];}
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {return canEdit [columnIndex];}
        };
        
        table.setModel(tableModel);
        
        TableColumn column;
        for (int i = 0; i < 9; i++) {
            column = table.getColumnModel().getColumn(i);
            switch(i){
                case 0:
                    column.setPreferredWidth(150);
                    break; //Attaque
                case 1:
                    column.setPreferredWidth(100);
                    break; //Attaque FR
                case 2:
                    column.setPreferredWidth(100);
                    break; //Attaque US
                case 3:
                    column.setPreferredWidth(50);
                    break; //Personnage
                case 4:
                    column.setPreferredWidth(50);
                    break; //Equipage
                case 5:
                    column.setPreferredWidth(50);
                    break; //Style
                case 6:
                    column.setPreferredWidth(50);
                    break; //Template
                case 7:
                    column.setPreferredWidth(50);
                    break; //Arc
                case 8:
                    column.setPreferredWidth(50);
                    break; //Image
            }
        }
        
        table.setRowHeight(50);
        table.setDefaultRenderer(ImagePerso.class, new ImagePersoTableRenderer());
        
        sfx.setState("Chargement des bases... (Les arcs)", 50);
        // Chargement des données
        File sf;
        
        sf = new File(getApplicationDirectory() + File.separator + "arcs.dbx");
        if(sf.exists()){
            try {
                XmlArcsHandler x = new XmlArcsHandler(sf.getAbsolutePath());
                arcs = x.getArcs();
            } catch ( ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        sfx.setState("Chargement des bases... (Les styles)", 55);
        
        sf = new File(getApplicationDirectory() + File.separator + "styles.dbx");
        if(sf.exists()){
            try {
                XmlStylesHandler x = new XmlStylesHandler(sf.getAbsolutePath());
                styles = x.getStyles();
            } catch ( ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        sfx.setState("Chargement des bases... (Les images)", 60);
        
        sf = new File(getApplicationDirectory() + File.separator + "images.dbx");
        if(sf.exists()){
            try {
                XmlImagesHandler x = new XmlImagesHandler(sf.getAbsolutePath());
                images = x.getImagePersos();
            } catch ( ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        sfx.setState("Chargement des bases... (Les templates)", 65);
        
        sf = new File(getApplicationDirectory() + File.separator + "templates.dbx");
        if(sf.exists()){
            try {
                XmlTemplatesHandler x = new XmlTemplatesHandler(sf.getAbsolutePath());
                templates = x.getTemplateGens();
            } catch ( ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        sfx.setState("Chargement des bases... (Les personnages)", 70);
        
        sf = new File(getApplicationDirectory() + File.separator + "personnages.dbx");
        if(sf.exists()){
            try {
                XmlPersosHandler x = new XmlPersosHandler(sf.getAbsolutePath());
                personnages = x.getPersos();
            } catch ( ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        sfx.setState("Chargement des bases... (Les équipes)", 75);
        
        sf = new File(getApplicationDirectory() + File.separator + "equipes.dbx");
        if(sf.exists()){
            try {
                XmlEquipesHandler x = new XmlEquipesHandler(sf.getAbsolutePath());
                equipages = x.getEquipes();
            } catch ( ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        sfx.setState("Chargement des bases... (Les attaquess)", 80);
        
        sf = new File(getApplicationDirectory() + File.separator + "attaques.dbx");
        if(sf.exists()){
            try {
                XmlAttaquesHandler x = new XmlAttaquesHandler(sf.getAbsolutePath(), arcs, images, styles, templates);
                attaques = x.getAttaques();
                if(attaques.isEmpty()==false){
                    addAllAttaquesToTable(attaques, false);
                }                
            } catch ( ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }      
    }
    
    private void addAttaque(Attaque att, boolean forced){
        boolean used;
        
        try{
            used = false;
            for(Arc arc : arcs){
                if(arc.getName().equalsIgnoreCase(att.getArc().getName())){
                    used = true;
                }
            }
            if(used == false){
                arcs.add(att.getArc());
            }
        }catch(Exception e){
            
        }
        
        
        try{
            used = false;
            for(ImagePerso img : images){
                if(img.getName().equalsIgnoreCase(att.getImagePerso().getName())){
                    used = true;
                }
            }
            if(used == false){
                images.add(att.getImagePerso());
            }
        }catch(Exception e){
            
        }
        
        
        try{
            used = false;
            for(Style sty : styles){
                if(sty.getName().equalsIgnoreCase(att.getStyle().getName())){
                    used = true;
                }
            }
            if(used == false){
                styles.add(att.getStyle());
            }
        }catch(Exception e){
            
        }
        
        
        try{
            for(int i=0; i<att.getTemplateGens().size(); i++){
                used = false;
                TemplateGen templategen = att.getTemplateGens().get(i);
                for(TemplateGen t : templates){
                    if(t.getName().equalsIgnoreCase(templategen.getName())){
                        used = true;
                    }
                }
                if(used == false){
                    templates.add(templategen);
                }
            }
        }catch(Exception e){
            
        }
        
        
        try{
            used = false;
            for(String s : personnages){
                if(s.equalsIgnoreCase(att.getCharacter())){
                    used = true;
                }
            }
            if(used == false){
                personnages.add(att.getCharacter());
            }
        }catch(Exception e){
            
        }
        
        
        try{
            used = false;
            for(String s : equipages){
                if(s.equalsIgnoreCase(att.getTeam())){
                    used = true;
                }
            }
            if(used == false){
                equipages.add(att.getTeam());
            }
        }catch(Exception e){
            
        }
        
        
        try{
            used = false;
            int index = -1;
            for(Attaque attaque : attaques){
                if(attaque.getName().equalsIgnoreCase(att.getName()) && attaque.getCharacter().equalsIgnoreCase(att.getCharacter())){
                    used = true;
                    index = attaques.indexOf(attaque);
                }
            }
            if(forced == true && index!=-1){
                attaques.set(index, att);
                int pos = jScrollPane1.getVerticalScrollBar().getValue();
                addAllAttaquesToTable(attaques, true);
                jScrollPane1.getVerticalScrollBar().setValue(pos);
                countEntry += 1;
                if(countEntry >= cyclique.getCycle()){
                    saveFiles(true);
                    countEntry = 0;
                }
            }else if(used == false){
                attaques.add(att);
                Object[] array = new Object[]{att, att.getFrenchName(),
                    att.getUSName(), att.getCharacter(), att.getTeam(),
                    att.getStyle(), att.getTemplateGens().get(att.getTemplateGens().size()-1),
                    att.getArc(), att.getImagePerso()
                };
                tableModel.addRow(array);
                countEntry += 1;
                if(countEntry >= cyclique.getCycle()){
                    saveFiles(true);
                    countEntry = 0;
                }
            }else{
                int n = JOptionPane.showConfirmDialog(this,
                        "Une technique du même nom semble déjà exister.\n"
                        + " J'espère que vous savez ce que vous faîtes !" ,
                        "Attention aux doublons !",
                        JOptionPane.WARNING_MESSAGE,
                        JOptionPane.YES_NO_OPTION);
                if(n == JOptionPane.YES_OPTION){
                    attaques.add(att);
                    Object[] array = new Object[]{att, att.getFrenchName(),
                        att.getUSName(), att.getCharacter(), att.getTeam(),
                        att.getStyle(), att.getTemplateGens().get(att.getTemplateGens().size()-1),
                        att.getArc(), att.getImagePerso()
                    };
                    tableModel.addRow(array);
                    countEntry += 1;
                    if(countEntry >= cyclique.getCycle()){
                        saveFiles(true);
                        countEntry = 0;
                    }
                }
            }            
        }catch(HeadlessException e){
            
        }
    }
    
    private void addAllAttaquesToTable(List<Attaque> atts, boolean clearBefore){
        if(clearBefore == true){
            for(int i=tableModel.getRowCount()-1; i>=0; i--){
                tableModel.removeRow(i);
            }
        }
        
        for(Attaque att : atts){
            if(att!=null){
                Object[] array = new Object[]{att, att.getFrenchName(),
                    att.getUSName(), att.getCharacter(), att.getTeam(),
                    att.getStyle(), att.getTemplateGens().get(att.getTemplateGens().size()-1),
                    att.getArc(), att.getImagePerso()
                };
                tableModel.addRow(array);
            }            
        }
    }
    
    private void addAllAttaquesToTable(List<Attaque> atts, String persoFilter){
        for(int i=tableModel.getRowCount()-1; i>=0; i--){
            tableModel.removeRow(i);
        }
        
        for(Attaque att : atts){
            if(att!=null && att.getCharacter().equalsIgnoreCase(persoFilter)){
                Object[] array = new Object[]{att, att.getFrenchName(),
                    att.getUSName(), att.getCharacter(), att.getTeam(),
                    att.getStyle(), att.getTemplateGens().get(att.getTemplateGens().size()-1),
                    att.getArc(), att.getImagePerso()
                };
                tableModel.addRow(array);
            }            
        }
    }
    
    private String getApplicationDirectory(){
        if(System.getProperty("os.name").equalsIgnoreCase("Mac OS X")){
            java.io.File file = new java.io.File("");
            return file.getAbsolutePath();
        }
        String path = System.getProperty("user.dir");
        if(path.toLowerCase().contains("jre")){
            File f = new File(getClass().getProtectionDomain()
                    .getCodeSource().getLocation().toString()
                    .substring(6));
            path = f.getParent();
        }
        return path;
    }
    
    private void saveFiles(boolean zip){
        XmlArcsWriter x1 = new XmlArcsWriter();
        x1.setArcs(arcs);
        x1.createArcs(getApplicationDirectory() + File.separator + "arcs.dbx");
        
        XmlAttaquesWriter x2 = new XmlAttaquesWriter();
        x2.setAttaques(attaques);
        x2.createAttaques(getApplicationDirectory() + File.separator + "attaques.dbx");
        
        XmlEquipesWriter x3 = new XmlEquipesWriter();
        x3.setEquipes(equipages);
        x3.createEquipes(getApplicationDirectory() + File.separator + "equipes.dbx");
        
        XmlImagesWriter x4 = new XmlImagesWriter();
        x4.setImagePersos(images);
        x4.createImages(getApplicationDirectory() + File.separator + "images.dbx");
        
        XmlPersosWriter x5 = new XmlPersosWriter();
        x5.setPersos(personnages);
        x5.createPersos(getApplicationDirectory() + File.separator + "personnages.dbx");
        
        XmlStylesWriter x6 = new XmlStylesWriter();
        x6.setStyles(styles);
        x6.createStyles(getApplicationDirectory() + File.separator + "styles.dbx");
        
        XmlTemplatesWriter x7 = new XmlTemplatesWriter();
        x7.setTemplateGens(templates);
        x7.createTemplates(getApplicationDirectory() + File.separator + "templates.dbx");
        
        if(zip == true){
            // Création du dossier de sauvegarde
            File folder = new File(getApplicationDirectory() + File.separator + "Saves");
            if(folder.exists() == false){
                folder.mkdir();
            }
                
            try {    
                // Copie des fichiers vers le dossier de sauvegarde
                File f1 = new File(getApplicationDirectory() + File.separator + "arcs.dbx");
                Path pathSource = f1.toPath();
                Path pathDest = (new File(folder.getAbsolutePath() + File.separator + f1.getName())).toPath();
                Files.copy(pathSource, pathDest, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {    
                // Copie des fichiers vers le dossier de sauvegarde
                File f1 = new File(getApplicationDirectory() + File.separator + "attaques.dbx");
                Path pathSource = f1.toPath();
                Path pathDest = (new File(folder.getAbsolutePath() + File.separator + f1.getName())).toPath();
                Files.copy(pathSource, pathDest, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {    
                // Copie des fichiers vers le dossier de sauvegarde
                File f1 = new File(getApplicationDirectory() + File.separator + "equipes.dbx");
                Path pathSource = f1.toPath();
                Path pathDest = (new File(folder.getAbsolutePath() + File.separator + f1.getName())).toPath();
                Files.copy(pathSource, pathDest, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {    
                // Copie des fichiers vers le dossier de sauvegarde
                File f1 = new File(getApplicationDirectory() + File.separator + "images.dbx");
                Path pathSource = f1.toPath();
                Path pathDest = (new File(folder.getAbsolutePath() + File.separator + f1.getName())).toPath();
                Files.copy(pathSource, pathDest, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {    
                // Copie des fichiers vers le dossier de sauvegarde
                File f1 = new File(getApplicationDirectory() + File.separator + "personnages.dbx");
                Path pathSource = f1.toPath();
                Path pathDest = (new File(folder.getAbsolutePath() + File.separator + f1.getName())).toPath();
                Files.copy(pathSource, pathDest, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {    
                // Copie des fichiers vers le dossier de sauvegarde
                File f1 = new File(getApplicationDirectory() + File.separator + "styles.dbx");
                Path pathSource = f1.toPath();
                Path pathDest = (new File(folder.getAbsolutePath() + File.separator + f1.getName())).toPath();
                Files.copy(pathSource, pathDest, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {    
                // Copie des fichiers vers le dossier de sauvegarde
                File f1 = new File(getApplicationDirectory() + File.separator + "templates.dbx");
                Path pathSource = f1.toPath();
                Path pathDest = (new File(folder.getAbsolutePath() + File.separator + f1.getName())).toPath();
                Files.copy(pathSource, pathDest, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try{
                zipFolder(folder.getAbsolutePath(), getApplicationDirectory() + File.separator + "saves.zip");
            }catch(Exception e){
                
            }
        }
    }
    
    public static void zipFolder(String srcFolder, String destZipFile) throws Exception {
        ZipOutputStream zip;
        FileOutputStream fileWriter;

        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);

        addFolderToZip("", srcFolder, zip);
        zip.flush();
        zip.close();
  }

    private static void addFileToZip(String path, String srcFile, ZipOutputStream zip)
      throws Exception {

        File folder = new File(srcFile);
        if (folder.isDirectory()) {
          addFolderToZip(path, srcFile, zip);
        } else {
          byte[] buf = new byte[1024];
          int len;
          FileInputStream in = new FileInputStream(srcFile);
          zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
          while ((len = in.read(buf)) > 0) {
            zip.write(buf, 0, len);
          }
        }
    }

    private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
      throws Exception {

        File folder = new File(srcFolder);

        for (String fileName : folder.list()) {
          if (path.equals("")) {
            addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
          } else {
            addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
          }
        }
    }

    private void changeWithFiltersButtons(){
        if(filtrePersonnage.equalsIgnoreCase("aucun")){
            btnAddAttaque.setEnabled(true);
            btnTrierCharacters.setEnabled(true);
            btnEdit.setEnabled(true);
            btnRemove.setEnabled(true);
        }else{
            btnAddAttaque.setEnabled(false);
            btnTrierCharacters.setEnabled(false);
            btnEdit.setEnabled(false);
            btnRemove.setEnabled(false);
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

        popASS = new javax.swing.JPopupMenu();
        popmGenASSWithoutAtts = new javax.swing.JMenuItem();
        popmGenASSWithAtts = new javax.swing.JMenuItem();
        popmModASS = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        popmUpdateTemplate = new javax.swing.JMenuItem();
        popmViewTemplates = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        popmAbout = new javax.swing.JMenuItem();
        fcASS = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        lblMirage = new javax.swing.JLabel();
        lblPropagande = new javax.swing.JLabel();
        lblSeparateur = new javax.swing.JLabel();
        lblSeparateur1 = new javax.swing.JLabel();
        btnAddAttaque = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnTrierAttaques = new javax.swing.JButton();
        btnTrierCharacters = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnTrierArcs = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnAutoSave = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnFilter = new javax.swing.JButton();
        btnNettoyage = new javax.swing.JButton();

        popmGenASSWithoutAtts.setText("Générer un fichier ASS contenant styles et templates mais sans attaques");
        popmGenASSWithoutAtts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmGenASSWithoutAttsActionPerformed(evt);
            }
        });
        popASS.add(popmGenASSWithoutAtts);

        popmGenASSWithAtts.setText("Générer un fichier ASS contenant styles, templates et attaques");
        popmGenASSWithAtts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmGenASSWithAttsActionPerformed(evt);
            }
        });
        popASS.add(popmGenASSWithAtts);

        popmModASS.setText("Modifier un fichier ASS contenant des attaques et y rajouter styles et templates");
        popmModASS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmModASSActionPerformed(evt);
            }
        });
        popASS.add(popmModASS);
        popASS.add(jSeparator1);

        popmUpdateTemplate.setText("Mettre à jour le template...");
        popmUpdateTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmUpdateTemplateActionPerformed(evt);
            }
        });
        popASS.add(popmUpdateTemplate);

        popmViewTemplates.setText("Voir les templates successifs");
        popmViewTemplates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmViewTemplatesActionPerformed(evt);
            }
        });
        popASS.add(popmViewTemplates);
        popASS.add(jSeparator2);

        popmAbout.setText("A propos de...");
        popmAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmAboutActionPerformed(evt);
            }
        });
        popASS.add(popmAbout);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblMirage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mt_attacksdb/71096_104647760324_3539854_q.jpg"))); // NOI18N

        lblPropagande.setText("<html><h1>Mirage-Team</h1><h3>Base de données des attaques</h3>");
        lblPropagande.setToolTipText("");

        lblSeparateur.setBackground(new java.awt.Color(255, 0, 51));
        lblSeparateur.setText("<html><hr />");
        lblSeparateur.setToolTipText("");
        lblSeparateur.setOpaque(true);

        lblSeparateur1.setBackground(new java.awt.Color(255, 0, 51));
        lblSeparateur1.setText("<html><hr />");
        lblSeparateur1.setToolTipText("");
        lblSeparateur1.setOpaque(true);

        btnAddAttaque.setText("Ajouter des attaques");
        btnAddAttaque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAttaqueActionPerformed(evt);
            }
        });

        btnRemove.setText("Enlever des attaques");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnTrierAttaques.setText("Trier par attaques");
        btnTrierAttaques.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrierAttaquesActionPerformed(evt);
            }
        });

        btnTrierCharacters.setText("Trier par personnages");
        btnTrierCharacters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrierCharactersActionPerformed(evt);
            }
        });

        btnEdit.setText("Edtiter");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnTrierArcs.setText("Trier par arcs");
        btnTrierArcs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrierArcsActionPerformed(evt);
            }
        });

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.setComponentPopupMenu(popASS);
        jScrollPane1.setViewportView(table);

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel1.setText("Pour générer ou modifier un fichier ASS, veuillez faire un clic-droit sur le tableau du dessous après avoir sélectionné les attaques figurant dans votre épisode.");

        btnAutoSave.setBackground(new java.awt.Color(255, 255, 255));
        btnAutoSave.setText("Sauvegarde automatique : 5");
        btnAutoSave.setToolTipText("Sauvegarde automatiquement les données de manière cyclique (basé sur le nombre déterminé) lors de l'ajout d'une entrée.");
        btnAutoSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutoSaveActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mt_attacksdb/Luffy_One_Piece_poing-80.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mt_attacksdb/91464786_o-80.png"))); // NOI18N

        btnFilter.setBackground(new java.awt.Color(255, 255, 255));
        btnFilter.setText("Définir le filtrage");
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });

        btnNettoyage.setBackground(new java.awt.Color(255, 255, 255));
        btnNettoyage.setText("Modifier / Nettoyer");
        btnNettoyage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNettoyageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSeparateur1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblMirage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPropagande, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 323, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAutoSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNettoyage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(lblSeparateur)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAddAttaque)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTrierAttaques)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTrierCharacters)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTrierArcs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemove))
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblPropagande, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblMirage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNettoyage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFilter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAutoSave))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSeparateur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddAttaque)
                    .addComponent(btnRemove)
                    .addComponent(btnTrierAttaques)
                    .addComponent(btnTrierCharacters)
                    .addComponent(btnEdit)
                    .addComponent(btnTrierArcs))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSeparateur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddAttaqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAttaqueActionPerformed
        AttaqueDialog ad = new AttaqueDialog(this, true);
        ad.fillArcs(arcs);
        ad.fillCharacters(personnages);
        ad.fillImages(images);
        ad.fillStyles(styles);
        ad.fillTeams(equipages);
        ad.fillTemplates(templates);
        Attaque att = ad.showDialog(null);
        if(att!=null){
            addAttaque(att, false);
        }
    }//GEN-LAST:event_btnAddAttaqueActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // Sauvegarde
        saveFiles(false);
    }//GEN-LAST:event_formWindowClosing

    private void btnTrierAttaquesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrierAttaquesActionPerformed
        List<Attaque> atts = ASS.getClonedList(attaques);
        if(filtrePersonnage.equalsIgnoreCase("aucun")){
            Collections.sort(atts, new TrierParAttaques());
            addAllAttaquesToTable(atts, true);
        }else{
            Collections.sort(atts, new TrierParAttaques());
            addAllAttaquesToTable(atts, filtrePersonnage);
        }
    }//GEN-LAST:event_btnTrierAttaquesActionPerformed

    private void btnTrierCharactersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrierCharactersActionPerformed
        List<Attaque> atts = ASS.getClonedList(attaques);
        Collections.sort(atts, new TrierParPersonnages());
        addAllAttaquesToTable(atts, true);
    }//GEN-LAST:event_btnTrierCharactersActionPerformed

    private void btnTrierArcsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrierArcsActionPerformed
        List<Attaque> atts = ASS.getClonedList(attaques);
        if(filtrePersonnage.equalsIgnoreCase("aucun")){
            Collections.sort(atts, new TrierParArcs());
            addAllAttaquesToTable(atts, true);
        }else{
            Collections.sort(atts, new TrierParArcs());
            addAllAttaquesToTable(atts, filtrePersonnage);
        }
    }//GEN-LAST:event_btnTrierArcsActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if(table.getSelectedRow()!=-1){
            AttaqueDialog ad = new AttaqueDialog(this, true);
            ad.fillArcs(arcs);
            ad.fillCharacters(personnages);
            ad.fillImages(images);
            ad.fillStyles(styles);
            ad.fillTeams(equipages);
            ad.fillTemplates(templates);
            Attaque oldatt = (Attaque)tableModel.getValueAt(table.getSelectedRow(), 0);
            Attaque att = ad.showDialog(oldatt);
            if(att!=null){
                int index = attaques.indexOf(oldatt);
                attaques.set(index, att);
                addAttaque(att, true);
            }
        }        
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        if(table.getSelectedRow()!=-1){
            try{
                int tabtemp[] = table.getSelectedRows();
                for (int i=tabtemp.length-1;i>=0;i--){
                    attaques.remove(tabtemp[i]);
                    tableModel.removeRow(tabtemp[i]);
                }
            }catch(Exception exc){}
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void popmGenASSWithoutAttsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmGenASSWithoutAttsActionPerformed
        if(table.getSelectedRow()!=-1){
            for (FileFilter f : fcASS.getChoosableFileFilters()){
                fcASS.removeChoosableFileFilter(f);
            }
            fcASS.addChoosableFileFilter(new ASSFilter());
            fcASS.setDialogTitle("Choisir le fichier...");
            SwingUtilities.updateComponentTreeUI(fcASS);
            int z = fcASS.showSaveDialog(this);
            if (z == JFileChooser.APPROVE_OPTION){
                List<Attaque> atts = new ArrayList<>();

                int tabtemp[] = table.getSelectedRows();
                for(int n : tabtemp){
                    atts.add((Attaque)tableModel.getValueAt(n, 0));
                }

                try {
                    ASS.createASS(fcASS.getSelectedFile(), atts, false);
                } catch (IOException ex) {
                    Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
        }              
    }//GEN-LAST:event_popmGenASSWithoutAttsActionPerformed

    private void popmGenASSWithAttsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmGenASSWithAttsActionPerformed
        if(table.getSelectedRow()!=-1){
            for (FileFilter f : fcASS.getChoosableFileFilters()){
                fcASS.removeChoosableFileFilter(f);
            }
            fcASS.addChoosableFileFilter(new ASSFilter());
            fcASS.setDialogTitle("Choisir un nom pour le nouveau fichier...");
            SwingUtilities.updateComponentTreeUI(fcASS);
            int z = fcASS.showSaveDialog(this);
            if (z == JFileChooser.APPROVE_OPTION){
                List<Attaque> atts = new ArrayList<>();

                int tabtemp[] = table.getSelectedRows();
                for(int n : tabtemp){
                    atts.add((Attaque)tableModel.getValueAt(n, 0));
                }

                try {
                    ASS.createASS(fcASS.getSelectedFile(), atts, true);
                } catch (IOException ex) {
                    Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_popmGenASSWithAttsActionPerformed

    private void popmModASSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmModASSActionPerformed
        if(table.getSelectedRow()!=-1){
            for (FileFilter f : fcASS.getChoosableFileFilters()){
                fcASS.removeChoosableFileFilter(f);
            }
            fcASS.addChoosableFileFilter(new ASSFilter());
            fcASS.setDialogTitle("Choisir le fichier à modifier...");
            SwingUtilities.updateComponentTreeUI(fcASS);
            int z = fcASS.showOpenDialog(this);
            if (z == JFileChooser.APPROVE_OPTION){
                List<Attaque> atts = new ArrayList<>();

                int tabtemp[] = table.getSelectedRows();
                for(int n : tabtemp){
                    atts.add((Attaque)tableModel.getValueAt(n, 0));
                }

                try {
                    ASS.mixASS(fcASS.getSelectedFile(), atts);                    
                } catch (IOException ex) {
                    Logger.getLogger(MtdbFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }        
    }//GEN-LAST:event_popmModASSActionPerformed

    private void btnAutoSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutoSaveActionPerformed
        cyclique = cyclique.getNext();
        btnAutoSave.setText(cyclique.getText());
    }//GEN-LAST:event_btnAutoSaveActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        FilterDialog fd = new FilterDialog(this, true);
        filtrePersonnage = fd.showDialog(personnages);
        if(filtrePersonnage.equalsIgnoreCase("aucun")){
            addAllAttaquesToTable(attaques, true);
        }else{
            addAllAttaquesToTable(attaques, filtrePersonnage);
        }
        changeWithFiltersButtons();
    }//GEN-LAST:event_btnFilterActionPerformed

    private void btnNettoyageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNettoyageActionPerformed
        CleanUpDialog cud = new CleanUpDialog(this, true);
        cud.init(arcs, images, styles, templates, personnages, equipages, attaques);
        cud.setLocationRelativeTo(null);
        cud.setVisible(true);
    }//GEN-LAST:event_btnNettoyageActionPerformed

    private void popmUpdateTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmUpdateTemplateActionPerformed
        if(table.getSelectedRow()!=-1){
            AssDialog ad = new AssDialog(this, true);
            TemplateGen tpg = ad.showTemplateDialog();
            if(tpg!=null){
                Attaque att = (Attaque)table.getValueAt(table.getSelectedRow(), 0);
                att.addTemplateGen(tpg);
                templates.add(tpg);
                table.updateUI();
            }
        }        
    }//GEN-LAST:event_popmUpdateTemplateActionPerformed

    private void popmViewTemplatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmViewTemplatesActionPerformed
        if(table.getSelectedRow()!=-1){
            Attaque att = (Attaque)table.getValueAt(table.getSelectedRow(), 0);
            TemplateViewDialog tvd = new TemplateViewDialog(this, true);
            tvd.showDialog(att);
        }
    }//GEN-LAST:event_popmViewTemplatesActionPerformed

    private void popmAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmAboutActionPerformed
        AboutDialog ad = new AboutDialog(this, true);
        ad.setVisible(true);
    }//GEN-LAST:event_popmAboutActionPerformed

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
            java.util.logging.Logger.getLogger(MtdbFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MtdbFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAttaque;
    private javax.swing.JButton btnAutoSave;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnNettoyage;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnTrierArcs;
    private javax.swing.JButton btnTrierAttaques;
    private javax.swing.JButton btnTrierCharacters;
    private javax.swing.JFileChooser fcASS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel lblMirage;
    private javax.swing.JLabel lblPropagande;
    private javax.swing.JLabel lblSeparateur;
    private javax.swing.JLabel lblSeparateur1;
    private javax.swing.JPopupMenu popASS;
    private javax.swing.JMenuItem popmAbout;
    private javax.swing.JMenuItem popmGenASSWithAtts;
    private javax.swing.JMenuItem popmGenASSWithoutAtts;
    private javax.swing.JMenuItem popmModASS;
    private javax.swing.JMenuItem popmUpdateTemplate;
    private javax.swing.JMenuItem popmViewTemplates;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
