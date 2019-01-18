
package informatik.hilfsmittel;

import java.io.FileOutputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Tool zur einfachen Dateiein-/-ausgabe von XML-Documents 
 * nach org.w3c.dom.
 */
public class XML {

	private XML() {}
	
	/**
	 * Parst eine Zeichenkette und liefert ein Document.
	 */
	public static Document parseDocument(String text) {
	    Document doc = null;
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder builder = factory.newDocumentBuilder();
		    doc = builder.parse(new InputSource(new StringReader(text)));  //.newDocument();
	    } catch(Exception e) { e.printStackTrace(); }
	    return doc;
	} 
	
	/**
	 * Erzeugt ein Document.
	 */
    public static Document createDocument() {
	    Document doc = null;
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder builder = factory.newDocumentBuilder();
		    doc = builder.newDocument();
	    } catch(Exception e) { e.printStackTrace(); }
	    return doc;
    }

	/**
	 * Liest ein Document aus einer Datei ein.
	 */
    public  static Document loadDocument(String dateiname) {
	    Document doc = null;
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        doc = builder.parse(dateiname);
	    } catch(Exception e) { e.printStackTrace(); }
	    return doc;
    }
	
	/**
	 * Schreibt ein Document in eine Datei.
	 */
    public static void saveDocument(Document doc, String dateiname) {
		try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
 
            DOMSource source = new DOMSource(doc);
            FileOutputStream ausgabe = new FileOutputStream(dateiname);
            StreamResult result = new StreamResult(ausgabe);
            transformer.transform(source, result);
           
        } catch (Exception e) {	e.printStackTrace();	}
    }
}