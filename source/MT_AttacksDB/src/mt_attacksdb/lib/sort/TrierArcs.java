/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib.sort;

import java.util.Comparator;
import mt_attacksdb.lib.Arc;

/**
 *
 * @author Yves
 */
public class TrierArcs implements Comparator<Arc> {
    
    public TrierArcs(){
        
    }

    @Override
    public int compare(Arc o1, Arc o2) {
        return o1.getArcName().compareTo(o2.getArcName());
    }
}
