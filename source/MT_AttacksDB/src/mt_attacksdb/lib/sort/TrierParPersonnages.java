/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib.sort;

import java.util.Comparator;
import mt_attacksdb.lib.Attaque;

/**
 *
 * @author Yves
 */
public class TrierParPersonnages implements Comparator<Attaque> {
    
    public TrierParPersonnages(){
        
    }

    @Override
    public int compare(Attaque o1, Attaque o2) {
        return o1.getCharacter().compareTo(o2.getCharacter());
    }
    
}
