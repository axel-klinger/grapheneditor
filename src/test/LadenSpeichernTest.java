/* Erstellt am 09.10.2004 */
package test;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import informatik.strukturen.graphen.Graph;
import informatik.strukturen.graphen.SchlichterGraph;

/**
 * <P><B>LadenSpeichernTest.java</B> <I>(engl. )</I>: Ein LadenSpeichernTest.java ist ... </P>
 *
 * @author Axel
 */
public class LadenSpeichernTest {

    public static void main(String[] args) {
        
        Document d = xmlLaden("Beispiele/test/Graph1.xml");
        SchlichterGraph g = (SchlichterGraph) xmlToGraph(d);
        
        
    }
    
    public static Document xmlLaden(String dateiname) {

        Document doc = null;
        
        DocumentBuilderFactory fabrik = DocumentBuilderFactory.newInstance();
        //factory.setValidating(true);   
        //factory.setNamespaceAware(true);
        try {
           DocumentBuilder builder = fabrik.newDocumentBuilder();
           doc = builder.parse( new File(dateiname) );
        } catch (Exception e) {
           e.printStackTrace();
        }         

        return doc;
    } 
    
    public static Graph xmlToGraph(Document doc) {
        SchlichterGraph graph = null;
        //Node n = doc.
//        System.out.println(doc.getNodeName());
//        System.out.println(doc.getNodeValue());
//        System.out.println(doc.getFirstChild().getNodeName());
//        System.out.println(doc.getNodeType());
        System.out.println(doc.getDocumentElement().getNodeName());
        Element wurzel = doc.getDocumentElement();
        Node k = wurzel.getFirstChild();
        NodeList n = wurzel.getChildNodes();
        NodeList km = wurzel.getElementsByTagName("KnotenMenge").item(0).getChildNodes();
        for (int i=0; i<km.getLength(); i++)
            System.out.println(km.item(i).getNodeName());
                
        return graph;
    }
}
