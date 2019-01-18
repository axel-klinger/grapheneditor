package temp;

//import grapheneditor.darstellung.figuren.KnotenForm;
//import grapheneditor.darstellung.figuren.Pfeil;
//
//import java.awt.Color;
//import java.io.FileOutputStream;
//import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
//import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GraphenVorlageEinAusgabe {

	
	private GraphenVorlageEinAusgabe() {}
	
	/**
	 * Erstellt eine neue Instanz von einer ModellVorlage.
	 * 
	 * @param dateiname
	 */
//	public AllgemeinesGraphenModell neueInstanz(String dateiname) {
//		AllgemeinesGraphenModell gm = new AllgemeinesGraphenModell();
//		
//		// Einlesen der xml-VorlagenDatei
//		Document doc = file2doc(dateiname);
//	    if (doc == null) return gm;
//		
//	    // Selektion der Mengen und Relationen (später auch Eigenschaften, ...)
//	    NodeList mengen = doc.getElementsByTagName("Menge");
//	    NodeList relationen = doc.getElementsByTagName("Relation");
//
//	    // Erzeugen der Mengen im allgemeinen GraphenModell
//	    for (int i = 0; i<mengen.getLength(); i++) {
//	        NodeList menge = mengen.item(i).getChildNodes();
//	        
//	    }
//	    
//		return gm;
//	}
//	
	
	
	private static void sop(Object o) {System.out.println(o);}
	
	public static GraphenVorlage loadXML(String dateiname) {
		
		GraphenVorlage vorlage = new GraphenVorlage(dateiname.replace(".gvl",""));

		Document doc = file2doc(dateiname);
	    if (doc == null) return vorlage;
	    
        // document -> modell
	    // - Mengen	(MengenModelle, ggf. bereits Observable)
	    // - Relationen
	    // - Eigenschaften
	    // - Methoden
	    
	    // Alle Knotenmengen
	    NodeList mengen = doc.getElementsByTagName("Menge");
	    for (int i = 0; i<mengen.getLength(); i++) {
	    	// für jede Knotenmenge
	        NodeList menge = mengen.item(i).getChildNodes(); 
//sop(menge.item(0).getNodeName());	        
sop(menge.item(1).getNodeName() + ":\t" + menge.item(1).getChildNodes().item(0).getNodeValue());	        
//sop(menge.item(2).getNodeName());	        
sop(menge.item(3).getNodeName() + ":\t" + menge.item(3).getChildNodes().item(0).getNodeValue());	        
//sop(menge.item(4).getNodeName());	        
sop(menge.item(5).getNodeName() + ":\t" + menge.item(5).getChildNodes().item(0).getNodeValue());	        
/*	    	for (int j = 0; j<knotenMenge.getLength(); j++) {
	    		// für jeden Knoten
	    		Element knoten = (Element) knotenMenge.item(j);

	    		
// ->	// ->	// -> hier: Typ Variabel halten !!!!!!!!!!!!!!!!!
	    		
// 		wie: 	Object id = Class.forName(knotenMenge.getAttribute("typ")).newInstance();
//	...	und		id = getObject((Element) knoten);
	    		Integer id = new Integer(Integer.parseInt(knoten.getAttribute("id")));
	    		
	    		
		    	Element obj = (Element) knoten.getElementsByTagName("Object").item(0);
		    	Element titel = (Element) obj.getElementsByTagName("Titel").item(0);
		    	Element inhalt = (Element) obj.getElementsByTagName("Inhalt").item(0);
		    	
		    	Element pos = (Element) knoten.getElementsByTagName("Position").item(0);
		    	double x = Double.parseDouble(pos.getAttribute("x"));
		    	double y = Double.parseDouble(pos.getAttribute("y"));
		    	
		    	Element form = (Element) knoten.getElementsByTagName("Form").item(0);
		    	int b = Integer.parseInt(form.getAttribute("b"));
		    	int h = Integer.parseInt(form.getAttribute("h"));
		    	String typ = form.getAttribute("typ");
		    	
		    	KnotenForm kf = string2form(typ, x, y, b, h);
		    	Knoten k = new Knoten(inhalt.getTextContent(), titel.getTextContent(), kf);
		    	knotenIndizes.put(id, k);
		    	modell.addNode(k);

                Element farbe = (Element) knoten.getElementsByTagName("Farbe").item(0);
                if (farbe != null) {
                    k.linienFarbe = new Color(Integer.parseInt(farbe.getAttribute("linie")));
                    if (!farbe.getAttribute("füllung").equals("null"))
                        k.füllFarbe = new Color(Integer.parseInt(farbe.getAttribute("füllung")));
                    else
                        k.füllFarbe = null;
                }
	    	}	*/
	    }	
	    
	    // Alle KantenMengen
/*	    NodeList kantenMengen = doc.getElementsByTagName("KantenMenge");
	    for (int i = 0; i<kantenMengen.getLength(); i++) {
	    	// für jede Knotenmenge
	        NodeList kantenMenge = kantenMengen.item(i).getChildNodes(); 
	    	for (int j = 0; j<kantenMenge.getLength(); j++) {
	    		// für jeden Knoten
	    		Element kante = (Element) kantenMenge.item(j);
	    		
	    		Knoten k1 = knotenIndizes.get( new Integer(Integer.parseInt(kante.getAttribute("k1"))) ); 	//modell.getNode( new Integer(Integer.parseInt(kante.getAttribute("k1"))) );
	    		Knoten k2 = knotenIndizes.get( new Integer(Integer.parseInt(kante.getAttribute("k2"))) );	//modell.getNode( new Integer(Integer.parseInt(kante.getAttribute("k2"))) );
	    		
	    		Element form = (Element) kante.getElementsByTagName("Form").item(0); 
	    		int b = Integer.parseInt(form.getAttribute("b"));
	    		int h = Integer.parseInt(form.getAttribute("h"));
	    		int typ = Integer.parseInt(form.getAttribute("typ"));
	    		
	    		Pfeil pf = new Pfeil(k1.form.x, k1.form.y, k2.form.x, k2.form.y, h, b, typ);
	    		Kante k = new Kante(k1, k2, pf);
                modell.addEdge(k);
                
                Element farbe = (Element) kante.getElementsByTagName("Farbe").item(0);
                if (farbe != null) {
//                    k.linienFarbe = new Color(Integer.parseInt(farbe.getAttribute("linie")));
                    if (!farbe.getAttribute("linie").equals("null"))
                        k.linienFarbe = new Color(Integer.parseInt(farbe.getAttribute("linie")));
                    else
                        k.linienFarbe = null;
                }

	    	}
	    }	*/
	    
	    // die Pfeile richtig rücken
/*	    for (Knoten k : modell.alleKnoten())
	    	modell.knotenVerschieben(k, (int)k.form.x, (int)k.form.y);
	    return modell;	*/
		
		return vorlage;
	}
	
	private static Document file2doc(String dateiname) {
        // file -> document
	    Document doc = null;
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        doc = builder.parse(dateiname);
	    } catch(Exception e) { 
	        e.printStackTrace(); 
	    }
		return doc;
	}
	
	private static GraphenVorlage doc2vorlage(Document doc) {
		GraphenVorlage gv = new GraphenVorlage("");
		// ...
		return gv;
	}
	
	private static Document vorlage2doc(GraphenVorlage vorlage) {
		// ...
		return null;
	}
	
	private static void doc2file(Document doc, String dateiname) {
		
	}
	
	public static void saveXML(GraphenVorlage vorlage, String dateiname) {
/*        int knotenIndex = 0;
        HashMap<Knoten,Integer> knotenIndizes = new HashMap<Knoten,Integer>();
        
        // modell -> document
	    Document doc = null;
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder builder = factory.newDocumentBuilder();
		    doc = builder.newDocument();
	    } catch(Exception e) { e.printStackTrace(); }
	    

	    Element root = (Element) doc.createElement("GraphenModell"); 
	    doc.appendChild(root);
	    
	    Element km  = doc.createElement("KnotenMenge");
	    km.setAttribute("id", "km01");
	    root.appendChild(km);
	    
	    for (Knoten k : modell.alleKnoten()) {
	    	// Ein Knoten
	    	Element kno = doc.createElement("Knoten");
	    	
	    	
	    	//kno.setAttribute("id", "" + k.getID());
	    	kno.setAttribute("id", "" + (++knotenIndex));
	    	//knotenIndizes.put(k, k.getID());
	    	knotenIndizes.put(k, new Integer(knotenIndex));
	    	
	    	// Inhalt
	    	Element obj = doc.createElement("Object");
	    	Element inhalt = doc.createElement("Inhalt");
	    	inhalt.setTextContent(k.getObjekt().toString());
	    	obj.appendChild(doc.createElement("Titel"));
	    	obj.getFirstChild().setTextContent(k.titel);
	    	obj.appendChild(inhalt);
	    	kno.appendChild(obj);
	    	
	    	// Position
	    	Element pos = doc.createElement("Position");
	    	pos.setAttribute("x", "" + k.form.x);
	    	pos.setAttribute("y", "" + k.form.y);
	    	kno.appendChild(pos);
	    	
	    	// Form
	    	Element form = doc.createElement("Form");
	    	form.setAttribute("b", "" + (int) k.form.getBounds().getWidth());
	    	form.setAttribute("h", "" + (int) k.form.getBounds().getHeight());
	    	form.setAttribute("typ", form2string(k.form));
	    	kno.appendChild(form);
            
            // Farbe
	    	Element farbe = doc.createElement("Farbe");
            farbe.setAttribute("linie",   "" + k.linienFarbe.getRGB());
            if (k.füllFarbe != null)
                farbe.setAttribute("füllung", "" + k.füllFarbe.getRGB());
            else
                farbe.setAttribute("füllung", "null");
            kno.appendChild(farbe);
            
	    	km.appendChild(kno);
	    }
	    
	    Element kam = doc.createElement("KantenMenge");
	    kam.setAttribute("id1", "km01");
	    kam.setAttribute("id2", "km01");
	    root.appendChild(kam);
	    
	    for (Kante k : modell.alleKanten()) {
	    	// Relation
	    	Element kan = doc.createElement("Kante");
	    	kan.setAttribute("k1", "" + knotenIndizes.get(k.start));	//k.start.getID());
	    	kan.setAttribute("k2", "" + knotenIndizes.get(k.ziel));		//.ziel.getID());

	    	// Form
	    	Element form = doc.createElement("Form");
	    	form.setAttribute("b", "" + ((Pfeil)k.form).getBreiteSpitze());
	    	form.setAttribute("h", "" + ((Pfeil)k.form).getLängeSpitze());
	    	form.setAttribute("typ", "" + ((Pfeil)k.form).getTyp());
	    	kan.appendChild(form);

            // Farbe
            Element farbe = doc.createElement("Farbe");
            farbe.setAttribute("linie",   "" + k.linienFarbe.getRGB());
            if (k.linienFarbe != null)
                farbe.setAttribute("füllung", "" + k.linienFarbe.getRGB());
            else
                farbe.setAttribute("füllung", "null");
            kan.appendChild(farbe);

	    	kam.appendChild(kan);
	    }
	    
        // document -> file
		try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
 
            DOMSource source = new DOMSource(doc);
            FileOutputStream ausgabe = new FileOutputStream(dateiname);
            StreamResult result = new StreamResult(ausgabe);
            transformer.transform(source, result);
           
        } catch (Exception e) {
		    e.printStackTrace();
		}	*/
	}
}
