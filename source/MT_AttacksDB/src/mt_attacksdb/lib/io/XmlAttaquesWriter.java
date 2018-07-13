/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mt_attacksdb.lib.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import mt_attacksdb.lib.Attaque;
import mt_attacksdb.lib.TemplateGen;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.helpers.AttributesImpl;

/**
 *
 * @author The Wingate 2940
 */
public class XmlAttaquesWriter {
    
    // Storage of FxObjects
    List<Attaque> ao = new ArrayList<>();

    /** <p>Create a new XmlPresetWriter.<br />
     * Crée un nouveau XmlPresetWriter.</p> */
    public XmlAttaquesWriter(){

    }
    
    /** *  <p>Container of FxObjects.<br />Conteneur de FxObject.</p> */
    public class AttaquesSource extends org.xml.sax.InputSource{
        
        // Storage of FxObjects
        List<Attaque> ao = new ArrayList<>();

        /** *  <p>Create a new XmlPresetSource.<br />
         * Crée un nouveau XmlPresetSource.</p>
         * @param ao */
        public AttaquesSource(List<Attaque> ao){
            super();
            this.ao = ao;

        }
        
        /** *  <p>Get a list of FxObject.<br />
         * Obtient une liste de FxObject.</p>
         * @return  */
        public List<Attaque> getAttaques(){
            return ao;
        }
    }
    
    /** *  <p>The way to read an XML of XmlPresets (XFX).<br />
     * Comment lire un XML de XmlPresets (XFX).</p> */
    public class AttaquesReader implements org.xml.sax.XMLReader{

        private ContentHandler chandler;
        private final AttributesImpl attributes = new AttributesImpl();
        private final Map<String,Boolean> features = new HashMap<>();
        private final Map<String,Object> properties = new HashMap<>();
        private EntityResolver resolver;
        private DTDHandler dhandler;
        private ErrorHandler ehandler;
        
        @Override
        public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
            return features.get(name).booleanValue();
        }

        @Override
        public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
            try{
                features.put(name, value);
            }catch(Exception ex){
            }            
        }

        @Override
        public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
            return properties.get(name);
        }

        @Override
        public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
            try{
                properties.put(name, value);
            }catch(Exception ex){
            }  
        }

        @Override
        public void setEntityResolver(EntityResolver resolver) {
            this.resolver = resolver;
        }

        @Override
        public EntityResolver getEntityResolver() {
            return resolver;
        }

        @Override
        public void setDTDHandler(DTDHandler handler) {
            this.dhandler = handler;
        }

        @Override
        public DTDHandler getDTDHandler() {
            return dhandler;
        }

        @Override
        public void setContentHandler(ContentHandler handler) {
            this.chandler = handler;
        }

        @Override
        public ContentHandler getContentHandler() {
            return chandler;
        }

        @Override
        public void setErrorHandler(ErrorHandler handler) {
            this.ehandler = handler;
        }

        @Override
        public ErrorHandler getErrorHandler() {
            return ehandler;
        }

        @Override
        public void parse(InputSource input) throws IOException, SAXException {

            if(!(input instanceof AttaquesSource)){
                throw new SAXException("The object isn't a VectorObjectSource");
            }
            if(chandler == null){
                throw new SAXException("ContentHandler not defined");
            }

            AttaquesSource source = (AttaquesSource)input;
            List<Attaque> ao = source.getAttaques();

            // Main element - beginning
            chandler.startDocument();
            chandler.startElement("", "attaques", "attaques", attributes);
            attributes.clear();

            // Attaque element
            for(Attaque att : ao){
                
                // Attaque element - beginning
                attributes.addAttribute("", "", "nom", "nom", att.getAttaqueName());
                attributes.addAttribute("", "", "fr", "fr", att.getFrenchName());
                attributes.addAttribute("", "", "us", "us", att.getUSName());
                attributes.addAttribute("", "", "perso", "perso", att.getCharacter());
                attributes.addAttribute("", "", "equipe", "equipe", att.getTeam());
                attributes.addAttribute("", "", "style", "style", att.getStyle().getName());
                attributes.addAttribute("", "", "arc", "arc", att.getArc().getName());
                attributes.addAttribute("", "", "image", "image", att.getImagePerso().getName());
                chandler.startElement("", "attaque", "attaque", attributes);
                attributes.clear();
                
                // TemplateGen element
                for(TemplateGen t : att.getTemplateGens()){
                    
                    // TemplateGen element - beginning
                    chandler.startElement("", "template", "template", attributes);
                    attributes.clear();
                    
                    char[] template = t.getName().toCharArray();
                    chandler.characters(template,0,template.length);
                    
                    // Attaque element - end
                    chandler.endElement("", "template", "template");
                    attributes.clear();
                }
                
                // Attaque element - end
                chandler.endElement("", "attaque", "attaque");
                attributes.clear();
            }

            // Main element - end
            chandler.endElement("", "attaques", "attaques");
            chandler.endDocument();

        }

        @Override
        public void parse(String systemId) throws IOException, SAXException {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
    
    /** *  <p>Create a new XML file.<br />Crée un nouveau fichier XML.</p>
     * @param path
     * @return  */
    public boolean createAttaques(String path){
        org.xml.sax.XMLReader pread = new AttaquesReader();
        InputSource psource = new AttaquesSource(ao);
        Source source = new SAXSource(pread, psource);

        File file = new File(path);
        Result resultat = new StreamResult(file);
        
        try {
            TransformerFactory fabrique = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = fabrique.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(source, resultat);
        } catch (TransformerConfigurationException ex) {
            return false;
        } catch (TransformerException ex) {
            return false;
        }
        return true;
    }
    
    /** *  <p>Set a new list of FxObject.<br />
     * Définit une nouvelle liste FxObject.</p>
     * @param ao */
    public void setAttaques(List<Attaque> ao){
        this.ao = ao;
    }
    
}
