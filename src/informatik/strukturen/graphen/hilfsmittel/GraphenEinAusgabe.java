
package informatik.strukturen.graphen.hilfsmittel;

import informatik.strukturen.graphen.GewichteterGraph;
import informatik.strukturen.graphen.SchlichterGraph;
//import informatik.hilfsmittel.XML;
import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
//import org.w3c.dom.Node;

public class GraphenEinAusgabe {
	
/*
 * 	Ein-/Ausgabe Datenbank, Textdatei, XML-Datei
 * 
 *  Für Schlichte, Bewertete, N-partite und erweiterte (z.B. Petri-Netze) Graphen
 * 
 *  Gemäß Vorlagen für Verkehrsnetze, Ablaufplände, Raumpläne, ...
 *  
 *  
 * 	SG->XML
 * 	XML->FILE
 * 
 * SG->TXT
 * TXT->FILE
 * 
 * FILE->TXT
 * TXT->SG
 * 
 * FILE->XML
 * XML->SG
 * 
 * DB->SG
 * 
 * SG->DB
 * 
 *  
 * 
 * */	
	

	public static String   toTXT(SchlichterGraph g) {
		
		return g.toString();
	}
	
	public static Document toXML(SchlichterGraph g) {
	    Document doc = null;
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder builder = factory.newDocumentBuilder();
		    doc = builder.newDocument();

		    Element root = (Element) doc.createElement("GraphenModell"); 
		    doc.appendChild(root);
		    
		    
		    root.appendChild( doc.createTextNode("Some") );
		    root.appendChild( doc.createTextNode(" ")    );
		    root.appendChild( doc.createTextNode("text") );
	    } catch(Exception e) { e.printStackTrace(); }

	    return doc;
	}

	
	
	/**
	 * Schreibt einen Schlichten Graphen in eine Binärdatei.
	 * @param graph
	 * @param dateiname
	 */
	public static void writeBIN (SchlichterGraph graph, String dateiname) {
	    try {
	        FileOutputStream   dateiAusgabeStrom  = new FileOutputStream(dateiname); 
	        ObjectOutputStream objektAusgabeStrom = new ObjectOutputStream(dateiAusgabeStrom);
	        
	        objektAusgabeStrom.writeObject(graph);
	        
	        objektAusgabeStrom.flush();
	    } catch (IOException ioe) { 
	        System.out.println("Fehler bei der Serialisierung!");
	        ioe.printStackTrace();
	    }  
	}

	/**
	 * Schreibt einen Schlichten Graphen in eine Textdatei.
	 * @param graph
	 * @param dateiname
	 * @throws IOException
	 */
	public static void writeTXT (SchlichterGraph graph, String dateiname) throws IOException {
		String s = toTXT(graph);
		BufferedWriter ausgabe = new BufferedWriter(new FileWriter(dateiname));
		ausgabe.write(s);
		ausgabe.flush();
	}

	/**
	 * Schreibt einen Schlichten Graphen in eine XML-Datei.
	 * @param graph
	 * @param dateiname
	 */
	public static void writeXML (SchlichterGraph graph, String dateiname) {
		Document doc = toXML(graph);

		try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
 
            DOMSource source = new DOMSource(doc);
            FileOutputStream ausgabe = new FileOutputStream(dateiname);
            StreamResult result = new StreamResult(ausgabe);
            transformer.transform(source, result);
           
        } catch (Exception e) {
		    e.printStackTrace();
		}
	}
		

	public static SchlichterGraph fromTXT(String s) {
		return new SchlichterGraph();
	}
	
	public static SchlichterGraph fromXML(Document d) {
	    SchlichterGraph g = new SchlichterGraph();
	    
	    return g;
	}

	
	/**
	 * Liest einen Schlichten Graphen aus einer Binärdatei ein.
	 * @param dateiname
	 * @return graph
	 */
	public static SchlichterGraph readBIN(String dateiname) {
	    SchlichterGraph g = new SchlichterGraph();
	    try {
	        FileInputStream   dateiEingabeStrom = new FileInputStream(dateiname); 
	        ObjectInputStream objektEingabeStrom = new ObjectInputStream(dateiEingabeStrom);

	        g = (SchlichterGraph) objektEingabeStrom.readObject();

	    } catch (IOException ioe) {
		    System.out.println("Fehler bei der Serialisierung!");
		} catch (ClassNotFoundException ioe) {	
		    System.out.println("Fehler bei der Serialisierung!");
		}  
		return g;
	}

	/**
	 * Liest einen Schlichten Graphen aus einer Textdatei ein.
	 * @param dateiname
	 * @return graph
	 * @throws IOException
	 */
	public static SchlichterGraph readTXT(String dateiname) throws IOException {
	    String s = "";
	    String zeile = "";
	    BufferedReader eingabe = new BufferedReader(new FileReader(dateiname));
	    while ((zeile = eingabe.readLine()) != null)
	        s += zeile;
		return fromTXT(s);
	}

	/**
	 * Liest einen Schlichten Graphen aus einer XML-Datei ein.
	 * @param dateiname
	 * @return graph
	 */
	public static SchlichterGraph readXML(String dateiname) {
	    Document doc = null;
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder builder = factory.newDocumentBuilder();
		    doc = builder.parse(dateiname);
	    } catch(Exception e) { 
	        e.printStackTrace(); 
	    }
	    if (doc == null)
	        return new SchlichterGraph();
	    return fromXML(doc);
	}
		
	
	
	

	// Rest löschen !!!
	/**
	 *	Liest einen schlichten Graph aus einer Datei ein. Die Knoten des Graphen  
	 *	bestehen nur aus Zeichenketten. Die Datei hat folgende Struktur:<BR><BR>
	 *
	 *	# Kommentar<BR>
	 *	# ...<BR>
	 *	k1<BR>
	 *	k2<BR>
	 *	...<BR>
	 *
	 *	# Kommentar<BR>
	 *	# ...<BR>
	 *	k1,k2<BR>
	 *	...<BR>
	 */
	public static SchlichterGraph<String> read (String dateiname) {
		SchlichterGraph<String> g = new SchlichterGraph<String>();
		try {
			BufferedReader eingabe = new BufferedReader(new FileReader(dateiname));
			String zeile = null;
			while ( !(zeile = eingabe.readLine()).equals("")) {
				if (!zeile.startsWith("#")) {
					g.hinzufügen(zeile.trim());
				}
			}

			while ( !(zeile = eingabe.readLine()).equals("")) {
				if (!zeile.startsWith("#")) {
					String[] token = zeile.split(",");
					g.hinzufügen(token[0].trim(), token[1].trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return g;
	}

	public static GewichteterGraph<String> read (String dateiname, int knotenGewichte, int kantenGewichte) {
	    GewichteterGraph<String> g = new GewichteterGraph<String>(knotenGewichte, kantenGewichte);
		try {
			BufferedReader eingabe = new BufferedReader(new FileReader(dateiname));
			String zeile = null;
			// Knoten einlesen ...
			while ( !(zeile = eingabe.readLine()).equals("")) {
			    // ... nach dem ersten Kommentar
				if (!zeile.startsWith("#")) {
				    String[] knoten_wert = zeile.split(";");
				    String knoten = knoten_wert[0].trim();
					g.hinzufügen(knoten);
					if(knotenGewichte>0) {
						String[] werte = knoten_wert[1].split(",");
						for (int i = 0; i<werte.length; i++) {
						    g.setWert(knoten, i, new Double(werte[i]));
						}
					}
				}
			}

			while ( !(zeile = eingabe.readLine()).equals("")) {
				if (!zeile.startsWith("#")) {
				    String[] kante_wert = zeile.split(";");
					String[] knoten = kante_wert[0].split(",");
					knoten[0] = knoten[0].trim();
					knoten[1] = knoten[1].trim();
					g.hinzufügen(knoten[0], knoten[1]);
					if (kantenGewichte>0) {
						String[] werte = kante_wert[1].split(",");
						for (int i = 0; i<werte.length; i++) {
						    g.setWert(knoten[0], knoten[1], i, new Double(werte[i]));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return g;
	}

}
