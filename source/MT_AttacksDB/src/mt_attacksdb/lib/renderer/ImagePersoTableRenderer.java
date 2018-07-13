/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib.renderer;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import mt_attacksdb.lib.ImagePerso;

/**
 *
 * @author Yves
 */
public class ImagePersoTableRenderer extends JLabel implements TableCellRenderer {

    public ImagePersoTableRenderer(){
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        
        if(value instanceof ImagePerso){
            ImagePerso img = (ImagePerso)value;
            setIcon(img.getImage());
        }        
        
        return this;
    }
    
}
