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
import mt_attacksdb.lib.Arc;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Yves
 */
public class XmlArcsHandler {
    
    /*   Structure à lire :
     * <arcs>
     *      <arc>nom de l'arc</arc>
     * </arcs>
     */
    
    ArcsHandler ah;
    
    public XmlArcsHandler(String path) throws ParserConfigurationException, SAXException, IOException{
        
        SAXParserFactory fabrique = SAXParserFactory.newInstance();
        SAXParser parseur = fabrique.newSAXParser();

        File fichier = new File(path);
        
        ah = new ArcsHandler();
        parseur.parse(fichier, ah);
    }
    
    public List<Arc> getArcs(){
        return ah.getArcs();
    }
    
    public class ArcsHandler extends DefaultHandler{
        
        //résultats de notre parsing
	private List<Arc> ao;
        private Arc arc;
	//flags nous indiquant la position du parseur
	private boolean inArcs, inArc;
	//buffer nous permettant de récupérer les données 
	private StringBuffer buffer;
        
        public ArcsHandler(){
            super();
        }
        
        public List<Arc> getArcs(){
            return ao;
        }
        
        //détection d'ouverture de balise
        @Override
        public void startElement(String uri, String localName,
			String qName, Attributes attributes) throws SAXException{
            if(qName.equals("arcs")){
                ao = new ArrayList<>();
                inArcs = true;
            }else{
                buffer = new StringBuffer();
                if(qName.equals("arc")){
                    arc = new Arc();
                    inArc = true;
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
            if(qName.equals("arcs")){                
                inArcs = false;
            }else{
                if(qName.equals("arc")){
                    arc.setArcName(buffer.toString());
                    ao.add(arc);
                    buffer = null;
                    inArc = false;
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
