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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Yves
 */
public class XmlEquipesHandler {
    
    /*   Structure à lire :
     * <arcs>
     *      <arc>nom de l'arc</arc>
     * </arcs>
     */
    
    EquipesHandler ah;
    
    public XmlEquipesHandler(String path) throws ParserConfigurationException, SAXException, IOException{
        
        SAXParserFactory fabrique = SAXParserFactory.newInstance();
        SAXParser parseur = fabrique.newSAXParser();

        File fichier = new File(path);
        
        ah = new EquipesHandler();
        parseur.parse(fichier, ah);
    }
    
    public List<String> getEquipes(){
        return ah.getEquipes();
    }
    
    public class EquipesHandler extends DefaultHandler{
        
        //résultats de notre parsing
	private List<String> ao;
        private String p;
	//flags nous indiquant la position du parseur
	private boolean inEquipes, inEquipe;
	//buffer nous permettant de récupérer les données 
	private StringBuffer buffer;
        
        public EquipesHandler(){
            super();
        }
        
        public List<String> getEquipes(){
            return ao;
        }
        
        //détection d'ouverture de balise
        @Override
        public void startElement(String uri, String localName,
			String qName, Attributes attributes) throws SAXException{
            if(qName.equals("equipes")){
                ao = new ArrayList<>();
                inEquipes = true;
            }else{
                buffer = new StringBuffer();
                if(qName.equals("equipe")){
                    p = "";
                    inEquipe = true;
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
            if(qName.equals("equipes")){                
                inEquipes = false;
            }else{
                if(qName.equals("equipe")){
                    p = buffer.toString();
                    ao.add(p);
                    buffer = null;
                    inEquipe = false;
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
