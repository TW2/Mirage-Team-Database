/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Yves
 */
public class Attaque implements ObjectInteface, Cloneable {
    
    String attaque = "";
    String attaque_french = "";
    String attaque_us = "";
    String personnage = "";
    String equipage = "";
    Style style = null;
    List<TemplateGen> templates = new ArrayList<>();
    Arc arc = null;
    ImagePerso image = null;
    
    public Attaque(){
        
    }
    
    public Attaque(String attaque, String attaque_french, String attaque_us,
            String personnage, String equipage, Style style, TemplateGen template,
            Arc arc, ImagePerso image){
        this.attaque = attaque;
        this.attaque_french = attaque_french;
        this.attaque_us = attaque_us;
        this.personnage = personnage;
        this.equipage = equipage;
        this.style = style;
        templates.add(template);
        this.arc = arc;
        this.image = image;
    }
    
    public void setAttaqueName(String attaque){
        this.attaque = attaque;
    }
    
    public String getAttaqueName(){
        return attaque;
    }
    
    public void setFrenchName(String attaque_french){
        this.attaque_french = attaque_french;
    }
    
    public String getFrenchName(){
        return attaque_french;
    }
    
    public void setUSName(String attaque_us){
        this.attaque_us = attaque_us;
    }
    
    public String getUSName(){
        return attaque_us;
    }
    
    public void setCharacter(String personnage){
        this.personnage = personnage;
    }
    
    public String getCharacter(){
        return personnage;
    }
    
    public void setTeam(String equipage){
        this.equipage = equipage;
    }
    
    public String getTeam(){
        return equipage;
    }
    
    public void setStyle(Style style){
        this.style = style;
    }
    
    public Style getStyle(){
        return style;
    }
    
    public void addTemplateGen(TemplateGen t){
        templates.add(t);
    }
    
    public void removeTemplateGen(TemplateGen t){
        templates.remove(t);
    }
    
    public void clearTemplateGens(){
        templates.clear();
    }
    
    public List<TemplateGen> getTemplateGens(){
        return templates;
    }
    
    public void setArc(Arc arc){
        this.arc = arc;
    }
    
    public Arc getArc(){
        return arc;
    }
    
    public void setImagePerso(ImagePerso image){
        this.image = image;
    }
    
    public ImagePerso getImagePerso(){
        return image;
    }

    @Override
    public String getName() {
        return getAttaqueName();
    }
    
    @Override
    public String toString(){
        return getAttaqueName();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Object o = super.clone();
        return o;
    }
}
