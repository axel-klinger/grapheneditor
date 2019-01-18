/* Created on 28.11.2004 */
package grapheneditor;

import grapheneditor.darstellung.figuren.Ellipse;
import grapheneditor.darstellung.figuren.KnotenForm;
import grapheneditor.darstellung.figuren.NEck;
import grapheneditor.darstellung.figuren.Pfeil;
import grapheneditor.darstellung.figuren.Rechteck;

import informatik.hilfsmittel.XML;
import informatik.strukturen.Paar;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Die GrapgenModellEinAsgabe bietet statische Methoden zum Laden und Speichern
 * eines GraphenModells als XML, sowie eine ExportMethode als SVG.
 * 
 * @author Axel
 */
public class GraphenModellEinAusgabe {	// ...Steuerung

    private GraphenModellEinAusgabe() {    }
    
    public static void saveSVG(GraphenModell modell, String dateiname) {
    	// Document erzeugen
	    Document doc = XML.createDocument();

	    // Wurzelelement
	    Element root = doc.createElement("svg");
	    root.setAttribute("xmlns", "http://www.w3.org/2000/svg");
	    doc.appendChild(root);
	    
	    // SVG-Grafik
	    Element grafik = doc.createElement("g");
	    grafik.setAttribute("fill-opacity", "0.7");
	    grafik.setAttribute("stroke", "black");
	    grafik.setAttribute("stoke-width", "0.3cm");
	    root.appendChild(grafik);
/*	    
	    // Alle Kanten
	    for (Kante k : modell.alleKanten()) {
	    	
		    // Pfeile oder Linien...
	    	Element kante = pfeil2element(k.form,doc);
    	    kante.setAttribute("fill-opacity", "1.0");
	    	
	    	// Linienfarbe
	    	Color lf = k.linienFarbe;
	    	kante.setAttribute("stroke", "rgb(" + lf.getRed() + "," + lf.getGreen() + "," + lf.getBlue() + ")");
	    
	    	// Linienstärke
	    	kante.setAttribute("stroke-width", "2");
	    
	    	// Linienart
	    
	    	// Text
	    
	    	// Bewertung
	    	
	    	
	    	grafik.appendChild(kante);
	    }
	    
	    // Alle Knoten
	    for (Knoten k : modell.alleKnoten()) {
	    	
	    	// KnotenForm
	    	Element knoten =  shape2element(k.form, doc);
	    	
	    	// Linienfarbe
	    	Color lf = k.linienFarbe;
	    	knoten.setAttribute("stroke", "rgb(" + lf.getRed() + "," + lf.getGreen() + "," + lf.getBlue() + ")");
	    	
	    	// Linienstärke
	    	knoten.setAttribute("stroke-width", "2");
	    
	    	// Linienart
	    	
	    	// Füllfarbe
	    	Color ff = k.füllFarbe;
	    	if (ff != null)
	    		knoten.setAttribute("fill", "rgb(" + ff.getRed() + "," + ff.getGreen() + "," + ff.getBlue() + ")");
	    	else
	    		knoten.setAttribute("fill", "white");
	    	
	    	// Text
	    	Element name = doc.createElement("text");
	    	name.setTextContent(k.titel);
            name.setAttribute("x", "" + (int)k.form.x); //-g2.getFontMetrics().stringWidth(k.titel)/2); 
            name.setAttribute("y", "" + (int)(k.form.y-k.form.getBounds().getWidth()*0.8));
            name.setAttribute("font-family", "sans-serif");
            name.setAttribute("font-size", "14");
            name.setAttribute("font-width", "bold");
            name.setAttribute("fill", "rgb(0,0,0)");
    	    name.setAttribute("fill-opacity", "1.0");
    	    grafik.appendChild(name);
	    	
	    	// Bewertung
	    	
	    	grafik.appendChild(knoten);
	    }
	    	*/
	    // Document speichern
	    XML.saveDocument(doc, dateiname);
    }
    
    
    public static void saveXML(GraphenModell modell, String dateiname) {
    	
        int knotenIndex = 0;
        HashMap<KnotenAnsicht,String> knotenIndizes = new HashMap<KnotenAnsicht,String>();
        
        // Ein leeres Document
	    Document doc = XML.createDocument();

	    // Die Wurzel des Document
	    Element root = (Element) doc.createElement("GraphenModell"); 
	    root.setAttribute("id", modell.getHierarchie().getWurzel().getID());
	    root.setAttribute("name", modell.getHierarchie().getWurzel().getName());
	    doc.appendChild(root);
	    
	    Element km  = doc.createElement("KnotenMenge");
	    km.setAttribute("id", "km01");
	    root.appendChild(km);
	    
	    // Die Wurzel ist ein Knoten ohne Ansicht, aber mit eigener ID

	    // Alle Knoten -> XML
	    for (KnotenAnsicht k : modell.knotenMenge.values()) {
//	    	Element kno = knotenToXML(k, doc);

	    	// Ein Knoten
	    	Element kno = doc.createElement("Knoten");
	    	
	    	kno.setAttribute("id", "" + k.getObjekt().getID());
//	    	kno.setAttribute("id", "" + (++knotenIndex));
	    	knotenIndizes.put(k, k.getObjekt().getID());
//	    	knotenIndizes.put(k, new Integer(knotenIndex));
	    	
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
	    
	    // Alle Kanten -> XML 
//System.out.println(modell.kantenMenge.size());
	    for (KantenAnsicht k : modell.kantenMenge.values()) {

	    	// Relation
	    	Element kan = doc.createElement("Kante");
//System.out.println( knotenIndizes.get(k.start) + " " + knotenIndizes.get(k.ziel));	    	
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
	    
	    // Hierarchie speichern
	    Element hier = doc.createElement("Hierarchie");
	    hier.setAttribute("id1", "km01");
	    hier.setAttribute("id2", "km01");
	    root.appendChild(hier);

	    // Alle Verfeinerungen -> XML
	    for (Paar<KnotenElement,KnotenElement> p : modell.getHierarchie().kanten()) {
	    	
	    	// Relation
	    	Element hi = doc.createElement("Verfeinerung");
	    	String str1 = "" + knotenIndizes.get(modell.knotenMenge.get(p.eins()));
	    	String str2 = "" + knotenIndizes.get(modell.knotenMenge.get(p.zwei()));
	    	hi.setAttribute("k1", "" + (str1.equals("null") ? "WURZEL" : str1));	//k.start.getID());
	    	hi.setAttribute("k2", "" + str2);		//.ziel.getID());
	    	hier.appendChild(hi);
	    }

	    // Document -> Datei 
	    XML.saveDocument(doc, dateiname);
    }
    
    // FIXME
    public static GraphenModell loadXML(String dateiname) {
        int knotenIndex = 0;
        HashMap<String,KnotenAnsicht> knotenIndizes = new HashMap<String,KnotenAnsicht>();
    	
    	GraphenModell modell = null;
    	if (dateiname.equals(""))
    		return modell;
        // file -> document
	    Document doc = XML.loadDocument(dateiname);
	    if (doc == null)
	    	return modell;
	    
        // document -> modell
	    // Die Wurzel
	    String wID   = doc.getDocumentElement().getAttribute("id");
	    String wName = doc.getDocumentElement().getAttribute("name");
	    KnotenElement wurzel = new KnotenElement(wID,wName);
	    
	    modell = new GraphenModell( wurzel );
	    
	    // Alle Knotenmengen
	    NodeList knotenMengen = doc.getElementsByTagName("KnotenMenge");
	    for (int i = 0; i<knotenMengen.getLength(); i++) {
	    	// für jede Knotenmenge
	        NodeList knotenMenge = knotenMengen.item(i).getChildNodes(); 
	    	for (int j = 0; j<knotenMenge.getLength(); j++) {
	    		// für jeden Knoten
	    		Element knoten = (Element) knotenMenge.item(j);

	    		
// ->	// ->	// -> hier: Typ Variabel halten !!!!!!!!!!!!!!!!!
	    		
// 		wie: 	Object id = Class.forName(knotenMenge.getAttribute("typ")).newInstance();
//	...	und		id = getObject((Element) knoten);
//	    		Integer id = new Integer(Integer.parseInt(knoten.getAttribute("id")));
	    		String id = knoten.getAttribute("id");
	    		
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
		    	KnotenElement e = new KnotenElement( id, inhalt.getTextContent() );
		    	KnotenAnsicht k = new KnotenAnsicht(e, /*id, */e.toString(), kf);
		    	knotenIndizes.put(id, k);
		    	modell.hinzufügen(k);

                Element farbe = (Element) knoten.getElementsByTagName("Farbe").item(0);
                if (farbe != null) {
                    k.linienFarbe = new Color(Integer.parseInt(farbe.getAttribute("linie")));
                    if (!farbe.getAttribute("füllung").equals("null"))
                        k.füllFarbe = new Color(Integer.parseInt(farbe.getAttribute("füllung")));
                    else
                        k.füllFarbe = null;
                }
	    	}
	    }	
	    
	    // Alle KantenMengen
	    NodeList kantenMengen = doc.getElementsByTagName("KantenMenge");
	    for (int i = 0; i<kantenMengen.getLength(); i++) {
	    	// für jede Knotenmenge
	        NodeList kantenMenge = kantenMengen.item(i).getChildNodes(); 
	    	for (int j = 0; j<kantenMenge.getLength(); j++) {
	    		// für jeden Knoten
	    		Element kante = (Element) kantenMenge.item(j);
	    		
	    		KnotenAnsicht k1 = knotenIndizes.get( kante.getAttribute("k1") ); 	//modell.getNode( new Integer(Integer.parseInt(kante.getAttribute("k1"))) );
	    		KnotenAnsicht k2 = knotenIndizes.get( kante.getAttribute("k2") );	//modell.getNode( new Integer(Integer.parseInt(kante.getAttribute("k2"))) );
	    		
	    		Element form = (Element) kante.getElementsByTagName("Form").item(0); 
	    		int b = Integer.parseInt(form.getAttribute("b"));
	    		int h = Integer.parseInt(form.getAttribute("h"));
	    		int typ = Integer.parseInt(form.getAttribute("typ"));
	    		
	    		Pfeil pf = new Pfeil(k1.form.x, k1.form.y, k2.form.x, k2.form.y, h, b, typ);
	    		KantenAnsicht k = new KantenAnsicht(k1, k2, pf);
                modell.hinzufügen(k);
                
                Element farbe = (Element) kante.getElementsByTagName("Farbe").item(0);
                if (farbe != null) {
//                    k.linienFarbe = new Color(Integer.parseInt(farbe.getAttribute("linie")));
                    if (!farbe.getAttribute("linie").equals("null"))
                        k.linienFarbe = new Color(Integer.parseInt(farbe.getAttribute("linie")));
                    else
                        k.linienFarbe = null;
                }

	    	}
	    }
	    
	    // Hierarchie einlesen
	    NodeList verfeinerungen = doc.getElementsByTagName("Hierarchie");

		// Für alle Hierarchien
	    for (int i = 0; i<verfeinerungen.getLength(); i++) {
	    	
	    	// für jede Hierarchie
	    	NodeList verfeinerung = verfeinerungen.item(i).getChildNodes();
	    	for (int j = 0; j<verfeinerung.getLength(); j++) {
		    	Element verfeinerungsElement = (Element) verfeinerung.item(j);
		    	String eltern = verfeinerungsElement.getAttribute("k1");
		    	String kind = verfeinerungsElement.getAttribute("k2");
		    	
		    	KnotenElement elternKnotenElement;
		    	KnotenElement kindKnotenElement;
		    	
		    	if (eltern.equals("WURZEL")) {
		    		elternKnotenElement = modell.getHierarchie().getWurzel();
		    	} else {
		    		elternKnotenElement = knotenIndizes.get( eltern ).getObjekt();
		    	}
		    	kindKnotenElement = knotenIndizes.get( kind ).getObjekt();
		    	modell.hierarchie.hinzufügen(elternKnotenElement, kindKnotenElement);
	    	}
	    }
	    
	    // die Pfeile richtig rücken
	    for (KnotenAnsicht k : modell.knotenMenge.values())
	    	modell.knotenVerschieben(k, k.form.x, k.form.y);	
	    
	    modell.aktualisieren();
	    return modell;
    }

    private static Element knotenToXML(KnotenAnsicht k, Document doc) {
    	// Ein Knoten
    	Element kno = doc.createElement("Knoten");
    	kno.setAttribute("id", "" + k.getObjekt());
    	
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
    	return kno;
    }

    private static Element kanteToXML(KantenAnsicht k, Document doc) {
    	// Relation
    	Element kan = doc.createElement("Kante");
    	kan.setAttribute("k1", "" + k.start.getObjekt());	//k.start.getID());
    	kan.setAttribute("k2", "" + k.ziel.getObjekt());	//k.ziel.getID());

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

        return kan;
    }
    
    private static String form2string(KnotenForm kf) {
    	String s = "";
    	if (kf instanceof Ellipse)	return "Ellipse";
    	if (kf instanceof Rechteck)	return "Rechteck";
    	if (kf instanceof NEck) {
    		NEck kfn = (NEck) kf;
    		switch (kfn.anzahlEcken()) {
    			case 3:	return "Dreieck";
    			case 4:	return "Viereck";
    			case 5:	return "Fünfeck";
    			case 6:	return "Sechseck";
    			case 7:	return "Siebeneck";
    			case 8:	return "Achteck";
    		}
    	}
    	return s;
    }
    
    private static Element pfeil2element(Pfeil pf, Document doc) {
    	Element kante = null;
    	
    	kante =  doc.createElement("polygon");
		String s = "";
		double[] f = new double[6];
		PathIterator pi = pf.getPathIterator(new AffineTransform());
		while(!pi.isDone()) {
			int i = pi.currentSegment(f);
			if (i == 0 || i == 1) {
				s += f[0] + " " + f[1];
			}
			pi.next();
			if (!pi.isDone())
				s += ", ";
		}
    	kante.setAttribute("points",  s);

    	return kante;
    }
    
    private static Element shape2element(KnotenForm kf, Document doc) {
    	Element knoten = null;
    	double x = kf.getPosition().getX();
    	double y = kf.getPosition().getY();
    	double b = kf.getBounds().getWidth();
    	double h = kf.getBounds().getHeight();
    	if (kf instanceof Ellipse) {	
	    	knoten =  doc.createElement("circle");
	    	knoten.setAttribute("cx", "" + x);
	    	knoten.setAttribute("cy", "" + y);
	    	knoten.setAttribute("r",  "" + b/2);
    	} else if (kf instanceof Rechteck) {	
	    	knoten =  doc.createElement("rect");
	    	knoten.setAttribute("x", "" + (x-b/2));
	    	knoten.setAttribute("y", "" + (y-h/2));
	    	knoten.setAttribute("width",  "" + b);
	    	knoten.setAttribute("height",  "" + h);
    	} else if (kf instanceof NEck) {
	    	knoten =  doc.createElement("polygon");
			String s = "";
			double[] f = new double[6];
			PathIterator pi = kf.getPathIterator(new AffineTransform());
			while(!pi.isDone()) {
				int i = pi.currentSegment(f);
				if (i == 0 || i == 1) {
					s += f[0] + " " + f[1];
				}
				pi.next();
				if (!pi.isDone())
					s += ", ";
			}
	    	knoten.setAttribute("points",  s);
    	}
    	return knoten;
    }
    
    private static KnotenForm string2form(String s, double x, double y, int b, int h) {
    	KnotenForm kf = null;
    	if (s.equals("Ellipse"))	return new Ellipse(x, y, b, h);
    	if (s.equals("Rechteck"))	return new Rechteck(x, y, b, h);
    	if (s.equals("Dreieck"))	return new NEck(x, y, b, h, 3);
    	if (s.equals("Viereck"))	return new NEck(x, y, b, h, 4);
    	if (s.equals("Fünfeck"))	return new NEck(x, y, b, h, 5);
    	if (s.equals("Sechseck"))	return new NEck(x, y, b, h, 6);
    	if (s.equals("Siebeneck"))	return new NEck(x, y, b, h, 7);
    	if (s.equals("Achteck"))	return new NEck(x, y, b, h, 8);
    	return kf;
    }
}
// 387