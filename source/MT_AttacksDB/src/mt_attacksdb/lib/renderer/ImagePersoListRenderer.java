/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib.renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import mt_attacksdb.lib.ImagePerso;

/**
 *
 * @author Yves
 */
public class ImagePersoListRenderer extends JPanel implements ListCellRenderer {
    
    JLabel lblImage = new JLabel();
    JLabel lblText = new JLabel();
    
    public ImagePersoListRenderer(){
        setOpaque(true);
        setLayout(new BorderLayout());
        setSize(100, 100);
        add(lblImage, BorderLayout.CENTER);
        add(lblText, BorderLayout.SOUTH);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        
        if (isSelected) {
            setBackground(list.getSelectionBackground());
        } else {
            setBackground(Color.white);
        }
        
        if(value instanceof ImagePerso){
            ImagePerso img = (ImagePerso)value;
            
            lblImage.setText("");
            lblImage.setIcon(img.getImage());
            lblImage.setHorizontalAlignment(JLabel.CENTER);
            
            lblText.setText(img.getCharacterName());
            lblText.setHorizontalAlignment(JLabel.CENTER);
            
            if (isSelected) {
                lblText.setForeground(Color.white);
            } else {
                lblText.setForeground(Color.black);
            }
        }
        
        return this;
    }
    
}
