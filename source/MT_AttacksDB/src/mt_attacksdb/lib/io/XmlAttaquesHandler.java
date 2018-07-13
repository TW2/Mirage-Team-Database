/*
 * To change this template, choose Tools | Templates
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
import mt_attacksdb.lib.Attaque;
import mt_attacksdb.lib.ImagePerso;
import mt_attacksdb.lib.Style;
import mt_attacksdb.lib.TemplateGen;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author The Wingate 2940
 */
public class XmlAttaquesHandler {
    /*   Structure à lire :
     * <attaques>
     *      <attaque nom="" fr="" us="" perso="" equipe="" style="" arc="" image="">
     *              <template>nom du templategen</template>
     *              <template>nom du templategen</template>
     *              <template>nom du templategen</template>
     *              ...
     *      </attaque>
     * </attaques>
     */
    
    AttaquesHandler ah;
    List<Arc> arcs = new ArrayList<>();
    List<ImagePerso> images = new ArrayList<>();
    List<Style> styles = new ArrayList<>();
    List<TemplateGen> templates = new ArrayList<>();
    
    public XmlAttaquesHandler(String path, List<Arc> arcs, 
            List<ImagePerso> images, List<Style> styles, List<TemplateGen> templates) throws ParserConfigurationException, SAXException, IOException{
        this.arcs = arcs;
        this.images = images;
        this.styles = styles;
        this.templates = templates;        
        
        SAXParserFactory fabrique = SAXParserFactory.newInstance();
        SAXParser parseur = fabrique.newSAXParser();

        File fichier = new File(path);
        
        ah = new AttaquesHandler();
        parseur.parse(fichier, ah);
    }
    
    public List<Attaque> getAttaques(){
        return ah.getAttaques();
    }
    
    public class AttaquesHandler extends DefaultHandler{
        
        //résultats de notre parsing
	private List<Attaque> ao;
        private Attaque att;
        private String currentAtt, currentAttFR, currentAttUS, currentPerso, 
                currentTeam, currentStyle, currentTemplate, currentArc, currentImage;
	//flags nous indiquant la position du parseur
	private boolean inAttaques, inAttaque, inTemplate;
	//buffer nous permettant de récupérer les données 
	private StringBuffer buffer;
        
        public AttaquesHandler(){
            super();
        }
        
        public List<Attaque> getAttaques(){
            return ao;
        }
        
        //détection d'ouverture de balise
        @Override
        public void startElement(String uri, String localName,
			String qName, Attributes attributes) throws SAXException{
            if(qName.equals("attaques")){
                ao = new ArrayList<>();
                inAttaques = true;
            }else if(qName.equals("attaque")){
                att = new Attaque();
                currentAtt = attributes.getValue("nom"); //
                currentAttFR = attributes.getValue("fr"); //
                currentAttUS = attributes.getValue("us"); //
                currentPerso = attributes.getValue("perso"); //
                currentTeam = attributes.getValue("equipe"); //
                currentStyle = attributes.getValue("style"); //
                currentArc = attributes.getValue("arc"); //
                currentImage = attributes.getValue("image"); //
                inAttaque = true;
            }else{
                buffer = new StringBuffer();
                if(qName.equals("template")){
                    inTemplate = true;
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
            if(qName.equals("attaques")){                
                inAttaques = false;
            }else if(qName.equals("attaque")){                
                att.setAttaqueName(currentAtt);
                att.setFrenchName(currentAttFR);
                att.setUSName(currentAttUS);
                att.setCharacter(currentPerso);
                att.setTeam(currentTeam);
                att.setStyle(selectStyle(currentStyle));
                att.setArc(selectArc(currentArc));
                att.setImagePerso(selectImage(currentImage));
                ao.add(att);
                inAttaque = false;
            }else{
                if(qName.equals("template")){
                    att.addTemplateGen(selectTemplate(buffer.toString()));
                    buffer = null;
                    inTemplate = false;
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
    
    private Arc selectArc(String arc){
        for(Arc a : arcs){
            if(a.getName().equalsIgnoreCase(arc)){
                return a;
            }
        }
        return null;
    }
    
    private Style selectStyle(String style){
        for(Style sty : styles){
            if(sty.getName().equalsIgnoreCase(style)){
                return sty;
            }
        }
        return null;
    }
    
    private TemplateGen selectTemplate(String template){
        for(TemplateGen t : templates){
            if(t.getName().equalsIgnoreCase(template)){
                return t;
            }
        }
        return null;
    }
    
    private ImagePerso selectImage(String image){
        for(ImagePerso img : images){
            if(img.getName().equalsIgnoreCase(image)){
                return img;
            }
        }
        return null;
    }
    
}
