/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib.sort;

import java.util.Comparator;

/**
 *
 * @author Yves
 */
public class TrierPersonnages implements Comparator<String> {
    
    public TrierPersonnages(){
        
    }

    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
}
