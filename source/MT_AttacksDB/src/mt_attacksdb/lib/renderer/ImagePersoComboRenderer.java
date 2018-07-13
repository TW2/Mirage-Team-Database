/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib.renderer;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import mt_attacksdb.lib.ImagePerso;

/**
 *
 * @author Yves
 */
public class ImagePersoComboRenderer extends JLabel implements ListCellRenderer {

    public ImagePersoComboRenderer(){
        setOpaque(true);
//        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        if(value instanceof ImagePerso){
            ImagePerso img = (ImagePerso)value;
        
            ImageIcon icon = img.getImage();
            String perso = img.getCharacterName();

            setIcon(icon);

            if (icon != null) {
                setText(perso);
                setFont(list.getFont());
            } else {
                setText(perso + " (pas d'aperçu disponible)");
                setFont(list.getFont());
            }
        }        
        
        return this;
    }
    
}
