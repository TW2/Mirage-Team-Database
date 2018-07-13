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
public class Style implements ObjectInteface, Cloneable {
    
    String nom_style = "";
    String style = "";
    
    public Style(){
        
    }
    
    public Style(String nom_style, String style){
        this.nom_style = nom_style;
        this.style = style;
    }
    
    public void setStyleName(String nom_style){
        this.nom_style = nom_style;
    }
    
    public String getStyleName(){
        return nom_style;
    }
    
    public void setStyle(String style){
        this.style = style;
    }
    
    public String getStyle(){
        return style;
    }

    @Override
    public String getName() {
        return getStyleName();
    }
    
    @Override
    public String toString(){
        return getStyleName();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Object o = super.clone();
        return o;
    }    
}
