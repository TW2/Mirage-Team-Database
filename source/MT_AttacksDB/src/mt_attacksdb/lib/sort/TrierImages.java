/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib.sort;

import java.util.Comparator;
import mt_attacksdb.lib.ImagePerso;

/**
 *
 * @author Yves
 */
public class TrierImages implements Comparator<ImagePerso> {
    
    public TrierImages(){
        
    }

    @Override
    public int compare(ImagePerso o1, ImagePerso o2) {
        return o1.getCharacterName().compareTo(o2.getCharacterName());
    }
}
