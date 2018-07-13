/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib.sort;

import java.util.Comparator;
import mt_attacksdb.lib.TemplateGen;

/**
 *
 * @author Yves
 */
public class TrierTemplates implements Comparator<TemplateGen> {
    
    public TrierTemplates(){
        
    }

    @Override
    public int compare(TemplateGen o1, TemplateGen o2) {
        return o1.getTemplateGenName().compareTo(o2.getTemplateGenName());
    }
}
