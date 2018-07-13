/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib;

/**
 *
 * @author Yves
 */
public class TemplateGen implements ObjectInteface, Cloneable {
    
    String nom_template = "";
    String version = "";
    String template = "";
    
    public TemplateGen(){
        
    }
    
    public TemplateGen(String nom_template, String version, String template){
        this.nom_template = nom_template;
        this.version = version;
        this.template = template;
    }
    
    public void setTemplateGenName(String nom_template){
        this.nom_template = nom_template;
    }
    
    public String getTemplateGenName(){
        return nom_template;
    }
    
    public void setTemplateGenVersion(String version){
        this.version = version;
    }
    
    public String getTemplateGenVersion(){
        return version;
    }
    
    public void setTemplateGen(String template){
        this.template = template;
    }
    
    public String getTemplateGen(){
        return template;
    }

    @Override
    public String getName() {
        return getTemplateGenName();
    }
    
    @Override
    public String toString(){
        return getTemplateGenName();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Object o = super.clone();
        return o;
    }
}
