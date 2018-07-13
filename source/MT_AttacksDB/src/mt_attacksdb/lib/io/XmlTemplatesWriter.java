/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author Yves
 */
public class XmlTemplatesWriter {
    
    // Storage of FxObjects
    List<TemplateGen> ao = new ArrayList<>();

    /** <p>Create a new XmlPresetWriter.<br />
     * Crée un nouveau XmlPresetWriter.</p> */
    public XmlTemplatesWriter(){

    }
    
    /** *  <p>Container of FxObjects.<br />Conteneur de FxObject.</p> */
    public class TemplateGensSource extends org.xml.sax.InputSource{
        
        // Storage of FxObjects
        List<TemplateGen> ao = new ArrayList<>();

        /** *  <p>Create a new XmlPresetSource.<br />
         * Crée un nouveau XmlPresetSource.</p>
         * @param ao */
        public TemplateGensSource(List<TemplateGen> ao){
            super();
            this.ao = ao;

        }
        
        /** *  <p>Get a list of FxObject.<br />
         * Obtient une liste de FxObject.</p>
         * @return  */
        public List<TemplateGen> getTemplateGens(){
            return ao;
        }
    }
    
    /** *  <p>The way to read an XML of XmlPresets (XFX).<br />
     * Comment lire un XML de XmlPresets (XFX).</p> */
    public class TemplateGensReader implements org.xml.sax.XMLReader{

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

            if(!(input instanceof TemplateGensSource)){
                throw new SAXException("The object isn't a VectorObjectSource");
            }
            if(chandler == null){
                throw new SAXException("ContentHandler not defined");
            }

            TemplateGensSource source = (TemplateGensSource)input;
            List<TemplateGen> ao = source.getTemplateGens();

            // Main element - beginning
            chandler.startDocument();
            chandler.startElement("", "templates", "templates", attributes);
            attributes.clear();

            // TemplateGen element
            for(TemplateGen t : ao){

                // TemplateGen element - beginning
                attributes.addAttribute("", "", "nom", "nom", t.getTemplateGenName());
                attributes.addAttribute("", "", "version", "version", t.getTemplateGenVersion());
                chandler.startElement("", "template", "template", attributes);
                attributes.clear();

                char[] template = t.getTemplateGen().toCharArray();
                chandler.characters(template,0,template.length);

                // TemplateGen element - end
                chandler.endElement("", "template", "template");
                attributes.clear();
            }

            // Main element - end
            chandler.endElement("", "templates", "templates");
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
    public boolean createTemplates(String path){
        org.xml.sax.XMLReader pread = new TemplateGensReader();
        InputSource psource = new TemplateGensSource(ao);
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
    public void setTemplateGens(List<TemplateGen> ao){
        this.ao = ao;
    }
    
}
