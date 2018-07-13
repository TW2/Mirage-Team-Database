/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

/**
 *
 * @author Yves
 */
public class Clipboard {
    
    /** <p>Creates a new Clipboard.<br />
* Crée un nouveau Clipboard.</p> */
    public Clipboard() {
    }
    
    /** <p>Copy an element in the clipboard.<br />
* Copie un élément texte dans le presse papier.</p>
* @param s The text to copy. */
     public void CCopy(String s){
         try{
             StringSelection ss = new StringSelection(s);
             Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);
         }catch(IllegalStateException ise){
             /** Le presse-papier n'est pas disponible */
         }
     }
     
     /** <p>Paste an element incoming from clipboard.<br />
* Colle un élément texte venant du presse papier.</p>
     * @return  */
     public String CPaste(){
         String s = "";
         Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
         try {
             /** V�rification que le contenu est de type texte. */
             if( t!=null && t.isDataFlavorSupported(DataFlavor.stringFlavor) ) {
                 s = (String)t.getTransferData(DataFlavor.stringFlavor);
             }
         }catch(  UnsupportedFlavorException | java.io.IOException ufe){
         }
         return s;
     }
}
