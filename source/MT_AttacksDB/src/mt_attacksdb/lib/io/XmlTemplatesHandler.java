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
import mt_attacksdb.lib.TemplateGen;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Yves
 */
public class XmlTemplatesHandler {
    
    /*   Structure à lire :
     * <templates>
     *      <template nom="" version="">paramètres du template</template>
     * </templates>
     */
    
    TemplateGensHandler tgh;
    
    public XmlTemplatesHandler(String path) throws ParserConfigurationException, SAXException, IOException{
        
        SAXParserFactory fabrique = SAXParserFactory.newInstance();
        SAXParser parseur = fabrique.newSAXParser();

        File fichier = new File(path);
        
        tgh = new TemplateGensHandler();
        parseur.parse(fichier, tgh);
    }
    
    public List<TemplateGen> getTemplateGens(){
        return tgh.getTemplateGens();
    }
    
    public class TemplateGensHandler extends DefaultHandler{
        
        //résultats de notre parsing
	private List<TemplateGen> ao;
        private TemplateGen t;
        private String currentName, currentVersion;
	//flags nous indiquant la position du parseur
	private boolean inTemplateGens, inTemplateGen;
	//buffer nous permettant de récupérer les données 
	private StringBuffer buffer;
        
        public TemplateGensHandler(){
            super();
        }
        
        public List<TemplateGen> getTemplateGens(){
            return ao;
        }
        
        //détection d'ouverture de balise
        @Override
        public void startElement(String uri, String localName,
			String qName, Attributes attributes) throws SAXException{
            if(qName.equals("templates")){
                ao = new ArrayList<>();
                inTemplateGens = true;
            }else{
                buffer = new StringBuffer();
                if(qName.equals("template")){
                    currentName = attributes.getValue("nom");
                    currentVersion = attributes.getValue("version");
                    t = new TemplateGen();
                    inTemplateGen = true;
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
            if(qName.equals("templates")){                
                inTemplateGens = false;
            }else{
                if(qName.equals("template")){
                    t.setTemplateGenName(currentName);
                    t.setTemplateGenVersion(currentVersion);
                    t.setTemplateGen(buffer.toString());
                    ao.add(t);
                    buffer = null;
                    inTemplateGen = false;
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
