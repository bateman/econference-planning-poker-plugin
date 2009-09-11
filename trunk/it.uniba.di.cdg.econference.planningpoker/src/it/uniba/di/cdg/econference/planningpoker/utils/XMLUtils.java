package it.uniba.di.cdg.econference.planningpoker.utils;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class XMLUtils {
	
    /**
     * XML DOM helper.
     * 
     * @param is
     * @return the XML DOM document object
     * @throws Exception
     */
    public static Document loadDocument( InputStream is ) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse( is );
        return doc;
    }

}
