/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib.sort;

import java.util.Comparator;
import mt_attacksdb.lib.Style;

/**
 *
 * @author Yves
 */
public class TrierStyles implements Comparator<Style> {
    
    public TrierStyles(){
        
    }

    @Override
    public int compare(Style o1, Style o2) {
        return o1.getStyleName().compareTo(o2.getStyleName());
    }
}
