/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mt_attacksdb.lib.Attaque;
import mt_attacksdb.lib.Style;
import mt_attacksdb.lib.TemplateGen;

/**
 *
 * @author Yves
 */
public class ASS {
    
    public ASS(){
        
    }
    
    public static void createASS(File fileToCreate, List<Attaque> attaques, boolean showAttacks) throws IOException{
        try (FileOutputStream fos = new FileOutputStream(fileToCreate); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8")); PrintWriter pw = new PrintWriter(bw)) {
            
            //On prépare les styles pour ne pas avoir de doublons
            List<String> styles = new ArrayList<>();
            //On prépare les templates pour ne pas avoir de doublons
            List<String> templates = new ArrayList<>();
            List<Attaque> clonedAttaques = getValuesOf(styles, templates, attaques);
            
            // Entête
            pw.println("[Script Info]");
            pw.println("; Ceci est un script Advanced Sub Station ou ASS."
                + " Aucune référence avec votre derrière.");
            pw.println("; Crée avec MTDB qui n'a pas de site ni de support."
                + " Message du créateur : \"Si vous êtes dans la merde, cherchez dans ceux qui aiment One Piece.\" ");
            pw.println("; MTDB Version 1.0 (alpha)");
            
            pw.println("ScriptType: v4.00+");
            pw.println("WrapStyle: 0");
            pw.println("PlayResX: 1280");
            pw.println("PlayResY: 720");
            pw.println("Collisions: Normal");
            
            pw.println("");
            
            
            //STYLE
            pw.println("[V4+ Styles]");
            pw.println("Format: Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, OutlineColour, BackColour, Bold, Italic" +
                    ", Underline, StrikeOut, ScaleX, ScaleY, Spacing, Angle" +
                    ", BorderStyle, Outline, Shadow, Alignment, MarginL, MarginR, MarginV, Encoding");
            
            for(String s : styles){
                pw.println(s);
            }
            
            
            pw.println("");
            

            //BODY
            pw.println("[Events]");
            pw.println("Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text");
            
            for(String s : templates){
                pw.println(s);
            }
            
            if(showAttacks==true){
                for(Attaque att : clonedAttaques){
                    String s = "Dialogue: 0,0:00:00.00,0:00:00.00,"
                            + att.getStyle().getStyleName()
                            + ",,0000,0000,0000,," 
                            + att.getAttaqueName();
                    pw.println(s);
                }
            }
            
        }
    }
    
    public static void mixASS(File fileToOverride, List<Attaque> attaques) throws IOException, UnsupportedEncodingException, FileNotFoundException{
        // Objets à récupérer
        String ScriptType = "V4.00+";
        String WrapStyle = "0";
        String PlayResX = "1280";
        String PlayResY = "720";
        String Collisions = "Normal";
        List<EventLine> lines = new ArrayList<>();        
        //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        
        
        // Lecture du fichier
        String newline;

        
        // Encoding detection
        FileReader fr = new FileReader(fileToOverride);
        String charset = detectCharset(fr);
        fr.close();

        // Start process to read file...
        FileInputStream fis = new FileInputStream(fileToOverride);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(fis, charset));

        //On lit le fichier
        while((newline=br.readLine())!=null){
            try{
                if(newline.startsWith("ScriptType") && !newline.substring(12).isEmpty()){
                    ScriptType = newline.substring(12);
                }else if(newline.startsWith("WrapStyle") && !newline.substring("WrapStyle".length()+2).isEmpty()){
                    WrapStyle = newline.substring("WrapStyle".length()+2);
                }else if(newline.startsWith("PlayResX") && !newline.substring("PlayResX".length()+2).isEmpty()){
                    PlayResX = newline.substring("PlayResX".length()+2);
                }else if(newline.startsWith("PlayResY") && !newline.substring("PlayResY".length()+2).isEmpty()){
                    PlayResY = newline.substring("PlayResY".length()+2);
                }else if(newline.startsWith("Collisions") && !newline.substring("Collisions".length()+2).isEmpty()){
                    Collisions = newline.substring("Collisions".length()+2);
                }

                if(newline.startsWith("Style:")){

                }

                if(newline.startsWith("Dialogue:") | newline.startsWith("Comment")){
                    EventLine evline = new EventLine();
                    evline.create(newline);
                    lines.add(evline);
                }
            }catch(IndexOutOfBoundsException ioobe){
                //erreurs = ioobe.getMessage();
            }
        }

        //On ferme le flux puis le fichier
        br.close();
        fis.close();
        //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        
        
        
        // Ecriture du fichier
        try (FileOutputStream fos = new FileOutputStream(fileToOverride); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8")); PrintWriter pw = new PrintWriter(bw)) {
            
            //On prépare les styles pour ne pas avoir de doublons
            List<String> styles = new ArrayList<>();
            //On prépare les templates pour ne pas avoir de doublons
            List<String> templates = new ArrayList<>();
            List<Attaque> clonedAttaques = getValuesOf(styles, templates, attaques);
            
            // Entête
            pw.println("[Script Info]");
            pw.println("; Ceci est un script Advanced Sub Station ou ASS."
                + " Aucune référence avec votre derrière.");
            pw.println("; Crée avec MTDB qui n'a pas de site ni de support."
                + " Message du créateur : \"Si vous êtes dans la merde, cherchez dans ceux qui aiment One Piece.\" ");
            pw.println("; MTDB Version 1.0 (alpha)");
            
            pw.println("ScriptType: v4.00+");
            pw.println("WrapStyle: "+WrapStyle);
            pw.println("PlayResX: "+PlayResX);
            pw.println("PlayResY: "+PlayResY);
            pw.println("Collisions: "+Collisions);
            
            
            pw.println("");
            
            
            //STYLE
            pw.println("[V4+ Styles]");
            pw.println("Format: Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, OutlineColour, BackColour, Bold, Italic" +
                    ", Underline, StrikeOut, ScaleX, ScaleY, Spacing, Angle" +
                    ", BorderStyle, Outline, Shadow, Alignment, MarginL, MarginR, MarginV, Encoding");
            
            for(String s : styles){
                pw.println(s);
            }
            
            
            pw.println("");
            

            //BODY
            pw.println("[Events]");
            pw.println("Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text");
            
            for(String s : templates){
                pw.println(s);
            }
            
            for(Attaque att : clonedAttaques){
                EventLine goodEV = null;
                for(EventLine ev : lines){
                    if(att.getAttaqueName().equalsIgnoreCase(getStrippedElement(ev.getText()))){
                        goodEV = ev;
                    }
                }
                
                if(goodEV!=null){
                    String s = goodEV.getType() 
                            + goodEV.getLayer() + "," 
                            + goodEV.getStart() + "," 
                            + goodEV.getEnd() + ","
                            + att.getStyle().getStyleName() + ","
                            + goodEV.getName() + ","
                            + goodEV.getMarginL() + ","
                            + goodEV.getMarginR() + ","
                            + goodEV.getMarginV() + ","
                            + goodEV.getEffect() + ","
                            + goodEV.getText();
                    pw.println(s);
                }else{
                    String s = "Dialogue: 0,0:00:00.00,0:00:00.00,"
                            + att.getStyle().getStyleName()
                            + ",,0000,0000,0000,," 
                            + att.getAttaqueName();
                    pw.println(s);
                }
            }
            
        }
    }
    
    /** <p>Try to get a correct charset<br />
    * Essaie d'obtenir le bon encodage des caractères.</p>
    * <table><tr><td colspan="2">Byte Order mark :</td><td></td></tr>
    * <tr><td width="100">Bytes</td><td>Encoding Form</td></tr>
    * <tr><td>00 00 FE FF</td><td>UTF-32, big-endian</td></tr>
    * <tr><td>FF FE 00 00</td><td>UTF-32, little-endian</td></tr>
    * <tr><td>FE FF</td><td>UTF-16, big-endian</td></tr>
    * <tr><td>FF FE</td><td>UTF-16, little-endian</td></tr>
    * <tr><td>EF BB BF</td><td>UTF-8</td></tr></table>
     * @param fr
     * @return  */
    private static String detectCharset(FileReader fr){
        String charset = ""; String newline;
        
        try {
            try (BufferedReader br = new BufferedReader(fr)) {
                while ((newline = br.readLine()) != null) {
                    
                    if(newline.startsWith("[\u0000\u0000") |
                            newline.startsWith("\u00FF\u00FE\u0000\u0000")){
                        charset = "UTF-32LE";
                    }else if(newline.startsWith("\u0000\u0000[") |
                            newline.startsWith("\u0000\u0000\u00FE\u00FF")){
                        charset = "UTF-32BE";
                    }else if(newline.startsWith("[\u0000") |
                            newline.startsWith("\u00FF\u00FE")){
                        charset = "UTF-16LE";
                    }else if(newline.startsWith("\u0000[") |
                            newline.startsWith("\u00FE\u00FF")){
                        charset = "UTF-16BE";
                    }else if(newline.startsWith("\u00EF\u00BB\u00BF")){
                        charset = "UTF-8";
                    }
                    
                    // If a charset was found then close the stream
                    // and the return charset encoding.
                    if (charset.length()!=0){
                        br.close();
                        return charset;
                    }
                }
                
                // If nothing was found then set the encoding to system default.
                if (charset.length()==0){
                    charset = fr.getEncoding();
                }
            }
            
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        
        return charset;
    }
    
    private static String getStrippedElement(String s){
        String str;
        if(s.contains("{\\")){
            try{
                str = s.replaceAll("\\{[^\\}]+\\}", "");
            }catch(Exception e){
                str = s;
            }
        }else{
            str = s;
        }
        return str;
    }
    
    private static List<Attaque> getValuesOf(List<String> styles, List<String> templates, List<Attaque> attaques){
        List<StyleAndTemplate> normal = new ArrayList<>();
        List<Attaque> clonedAttaques = getClonedList(attaques);
        
        int counter = 0;
        boolean satFound = false, satConflit = false;
        for(Attaque att : clonedAttaques){
            StyleAndTemplate sat = new StyleAndTemplate(att.getStyle(), att.getTemplateGens().get(att.getTemplateGens().size()-1));
            for(StyleAndTemplate s : normal){
                if(s.isSame(sat)==true){
                    satFound = true;
                    break;
                }else if(s.isConflict(sat)==true){
                    counter += 1;
                    satConflit = true;
                    break;
                }
            }
            
            if(satConflit == true){
                try {
                    String oldStyleName = sat.getStyle().getStyleName();
                    String newStyleName = sat.getStyle().getStyleName()+"["+counter+"]";
                    Style sty = (Style)sat.getStyle().clone();
                    TemplateGen t = (TemplateGen)sat.getTemplate();
                    String style = sty.getStyle().replaceFirst(oldStyleName, newStyleName);
                    String template = t.getTemplateGen().replaceAll(oldStyleName, newStyleName);
                    sty.setStyleName(newStyleName);
                    sty.setStyle(style);
                    att.setStyle(sty);
                    styles.add(style);
                    templates.add(template);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(ASS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if(satFound == false){
                normal.add(sat);
                styles.add(sat.getStyle().getStyle());
                templates.add(sat.getTemplate().getTemplateGen());
            }
            
            satFound = false;
            satConflit = false;
        }
        
        return clonedAttaques;
    }
    
    public static List<Attaque> getClonedList(List<Attaque> attaques){
        List<Attaque> clone = new ArrayList<>(attaques.size());
        
        for(Attaque item : attaques){
            try {
                clone.add((Attaque)item.clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ASS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return clone;
    }
    
    public static class EventLine{
        
        String Type = "Dialogue";
        String Layer = "0";
        String Start = "0:00:00.00";
        String End = "0:00:00.00";
        String Style = "Default";
        String Name = "";
        String MarginL = "0000";
        String MarginR = "0000";
        String MarginV = "0000";
        String Effect = "";
        String Text = "";
        
        public EventLine(){
            
        }
        
        public void create(String rawline){
            if(rawline.startsWith("Comment")){
                Type = "Comment";
            }
            
            String newline = rawline.substring(rawline.indexOf(":")+1);
            String[] table = newline.split(",");
            
            Layer = table[0];
            Start = table[1];
            End = table[2];
            Style = table[3];
            Name = table[4];
            MarginL = table[5];
            MarginR = table[6];
            MarginV = table[7];
            Effect = table[8];
            Text = table[9];
        }
        
        public String getType(){
            return Type + ": ";
        }
        
        public String getLayer(){
            return Layer;
        }
        
        public String getStart(){
            return Start;
        }
        
        public String getEnd(){
            return End;
        }
        
        public String getStyle(){
            return Style;
        }
        
        public String getName(){
            return Name;
        }
        
        public String getMarginL(){
            return MarginL;
        }
        
        public String getMarginR(){
            return MarginR;
        }
        
        public String getMarginV(){
            return MarginV;
        }
        
        public String getEffect(){
            return Effect;
        }
        
        public String getText(){
            return Text;
        }
    }
    
    public static class StyleAndTemplate{
        
        private Style style = null;
        private TemplateGen template = null;
        
        public StyleAndTemplate(){
            
        }
        
        public StyleAndTemplate(Style style, TemplateGen template){
            this.style = style;
            this.template = template;
        }
        
        public Style getStyle(){
            return style;
        }
        
        public TemplateGen getTemplate(){
            return template;
        }
        
        public boolean isSame(StyleAndTemplate sat){
            if(style.getStyle().equalsIgnoreCase(sat.getStyle().getStyle()) 
                    && template.getTemplateGen().equalsIgnoreCase(sat.getTemplate().getTemplateGen())){
                return true;
            }
            return false;
        }
        
        public boolean isConflict(StyleAndTemplate sat){
            if(style.getStyle().equalsIgnoreCase(sat.getStyle().getStyle()) 
                    && template.getTemplateGen().equalsIgnoreCase(sat.getTemplate().getTemplateGen())==false){
                return true;
            }
            return false;
        }
    }
    
}
