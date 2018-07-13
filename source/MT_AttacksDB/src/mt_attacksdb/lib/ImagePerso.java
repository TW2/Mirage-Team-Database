/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Yves
 */
public class ImagePerso implements ObjectInteface, Cloneable {
    
    ImageIcon icon = null;
    String nom_personnage = "";
    
    public ImagePerso(){
        
    }
    
    public ImagePerso(ImageIcon icon, String nom_personnage){
        this.icon = icon;
        this.nom_personnage = nom_personnage;
    }
    
    public void setImage(ImageIcon icon){
        this.icon = icon;
    }
    
    public void setImage(String path){
        this.icon = new ImageIcon(path);
    }
    
    public ImageIcon getImage(){
        return icon;
    }
    
    public String iconToBase64() throws IOException{
        if(icon==null){
            return "";
        }else{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage bi = toBufferedImage(icon);
            ImageIO.write(bi, "png", baos);
            byte[] bytes = baos.toByteArray();
            String value = Base64.encodeBytes(bytes);
            return value;
        }        
    }
    
    public void iconFromBase64(String s) throws IOException{
        if(s.isEmpty()){
            icon = null;
        }else{
            byte[] bytes = Base64.decode(s);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            BufferedImage bi = ImageIO.read(bais);
            icon = new ImageIcon(bi);
        }        
    }
    
    private BufferedImage toBufferedImage(ImageIcon icon) {
        Image image = icon.getImage();

        /** On crée la nouvelle image */
        BufferedImage bufferedImage = new BufferedImage(
                    image.getWidth(null),
                    image.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB );

        Graphics g = bufferedImage.createGraphics();
        g.drawImage(image,0,0,null);
        g.dispose();

        return bufferedImage;
    }
    
    public void setCharacterName(String name){
        nom_personnage = name;
    }
    
    public String getCharacterName(){
        return nom_personnage;
    }

    @Override
    public String getName() {
        return getCharacterName();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Object o = super.clone();
        return o;
    }
    
}
