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
public class Arc implements ObjectInteface, Cloneable {
    
    String nom_arc = "";
    
    public Arc(){
        
    }
    
    public Arc(String nom_arc){
        this.nom_arc = nom_arc;
    }
    
    public void setArcName(String nom_arc){
        this.nom_arc = nom_arc;
    }
    
    public String getArcName(){
        return nom_arc;
    }

    @Override
    public String getName() {
        return getArcName();
    }
    
    @Override
    public String toString(){
        return getArcName();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Object o = super.clone();
        return o;
    }
}
