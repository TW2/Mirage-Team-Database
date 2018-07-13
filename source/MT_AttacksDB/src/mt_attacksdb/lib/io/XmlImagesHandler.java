/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mt_attacksdb.lib.ImagePerso;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Yves
 */
public class XmlImagesHandler {
    
    /*   Structure à lire :
     * <images>
     *      <image nom="">base64</image>
     * </images>
     */
    
    ImagePersosHandler sh;
    
    public XmlImagesHandler(String path) throws ParserConfigurationException, SAXException, IOException{
        
        SAXParserFactory fabrique = SAXParserFactory.newInstance();
        SAXParser parseur = fabrique.newSAXParser();

        File fichier = new File(path);
        
        sh = new ImagePersosHandler();
        parseur.parse(fichier, sh);
    }
    
    public List<ImagePerso> getImagePersos(){
        return sh.getImagePersos();
    }
    
    public class ImagePersosHandler extends DefaultHandler{
        
        //résultats de notre parsing
	private List<ImagePerso> ao;
        private ImagePerso img;
        private String currentName;
	//flags nous indiquant la position du parseur
	private boolean inImagePersos, inImagePerso;
	//buffer nous permettant de récupérer les données 
	private StringBuffer buffer;
        
        public ImagePersosHandler(){
            super();
        }
        
        public List<ImagePerso> getImagePersos(){
            return ao;
        }
        
        //détection d'ouverture de balise
        @Override
        public void startElement(String uri, String localName,
			String qName, Attributes attributes) throws SAXException{
            if(qName.equals("images")){
                ao = new ArrayList<>();
                inImagePersos = true;
            }else{
                buffer = new StringBuffer();
                if(qName.equals("image")){
                    currentName = attributes.getValue("nom");
                    img = new ImagePerso();
                    inImagePerso = true;
                }else{
                    //erreur, on peut lever une exception
//                        throw new SAXException("Balise "+qName+" inconnue.");
                }
            }
        }
        
        //détection fin de balise
        @Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException{
            if(qName.equals("images")){                
                inImagePersos = false;
            }else{
                if(qName.equals("image")){
                    img.setCharacterName(currentName);
                    try {
                        img.iconFromBase64(buffer.toString());
                    } catch (IOException ex) {
                        Logger.getLogger(XmlImagesHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ao.add(img);
                    buffer = null;
                    inImagePerso = false;
                }else{
                    //erreur, on peut lever une exception
//                        throw new SAXException("Balise "+qName+" inconnue.");
                }
            }
        }
        
        //détection de caractères
        @Override
	public void characters(char[] ch,int start, int length)
			throws SAXException{
            String lecture = new String(ch,start,length);
            if(buffer != null) {
                buffer.append(lecture);
            }       
	}
        
	//début du parsing
        @Override
	public void startDocument() throws SAXException {
//            System.out.println("Début du parsing");
	}
        
	//fin du parsing
        @Override
	public void endDocument() throws SAXException {
//            System.out.println("Fin du parsing");
//            System.out.println("Resultats du parsing");
//            for(ParticleObject p : lpo){
//                    System.out.println(p);
//            }
	}
    }
    
}
