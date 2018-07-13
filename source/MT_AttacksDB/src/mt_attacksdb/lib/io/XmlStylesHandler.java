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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mt_attacksdb.lib.Style;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Yves
 */
public class XmlStylesHandler {
    
    /*   Structure à lire :
     * <styles>
     *      <style nom="">paramètres du style</style>
     * </styles>
     */
    
    StylesHandler sh;
    
    public XmlStylesHandler(String path) throws ParserConfigurationException, SAXException, IOException{
        
        SAXParserFactory fabrique = SAXParserFactory.newInstance();
        SAXParser parseur = fabrique.newSAXParser();

        File fichier = new File(path);
        
        sh = new StylesHandler();
        parseur.parse(fichier, sh);
    }
    
    public List<Style> getStyles(){
        return sh.getStyles();
    }
    
    public class StylesHandler extends DefaultHandler{
        
        //résultats de notre parsing
	private List<Style> ao;
        private Style style;
        private String currentName;
	//flags nous indiquant la position du parseur
	private boolean inStyles, inStyle;
	//buffer nous permettant de récupérer les données 
	private StringBuffer buffer;
        
        public StylesHandler(){
            super();
        }
        
        public List<Style> getStyles(){
            return ao;
        }
        
        //détection d'ouverture de balise
        @Override
        public void startElement(String uri, String localName,
			String qName, Attributes attributes) throws SAXException{
            if(qName.equals("styles")){
                ao = new ArrayList<>();
                inStyles = true;
            }else{
                buffer = new StringBuffer();
                if(qName.equals("style")){
                    currentName = attributes.getValue("nom");
                    style = new Style();
                    inStyle = true;
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
            if(qName.equals("styles")){                
                inStyles = false;
            }else{
                if(qName.equals("style")){
                    style.setStyleName(currentName);
                    style.setStyle(buffer.toString());
                    ao.add(style);
                    buffer = null;
                    inStyle = false;
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
