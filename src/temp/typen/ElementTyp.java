package temp.typen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ElementTyp {

	private Document definition;
	private Class	 klasse;
	private String   klassenPfad;
	
	private Vector<Attribut> attribs;
	
	public ElementTyp(File xmlDatei, String klassenPfad) {
		definition = laden(xmlDatei);
		this.klassenPfad = klassenPfad;
		attribs = new Vector<Attribut>();
	}
	
	public void übersetzen() {

		// Java-Code generieren ...
		// ===================
		String code = "";
		String klassenName = definition.getDocumentElement()
							.getChildNodes().item(1).getTextContent();
System.out.println( klassenName );		

		// import
		// - selbstdefinierte Typen sind im gleichen Verzeichnis
		// - package-Bildung später
		// - z.B. Date mit voller Bezeichnung java.util.Date
		code += "import java.io.Serializable;\n";

		// Klasse
		code += "\npublic class " + klassenName + " implements Serializable{\n";

		// Attribute einfügen
		// - Attribut mit Datentyp
		// - getAttribut()
		// - setAttribut()
		NodeList attr = definition.getElementsByTagName("Attribut");
	    for (int i = 0; i<attr.getLength(); i++) {
	    	Node a = attr.item(i);
	    	String name = a.getAttributes().getNamedItem("name").getNodeValue(); //a.getChildNodes().item(1).getTextContent();
	    	String typ  = a.getAttributes().getNamedItem("typ").getNodeValue(); //a.getChildNodes().item(3).getTextContent();
	    	attribs.add(new Attribut(name, typ));
	    }

	    for (Attribut a : attribs)
	    	code += createAttribute(a.name, a.typ);

		
		// toString
	    code += "\n\tpublic String toString() {\n"
	    		+ "\t\tString s = \"(\";\n";
	    		// hier: Attribute einfügen
	    code += "\t\treturn s;\n"
	    		+ "\t}\n";
		
		// equals
	    code += "\n\tpublic boolean equals(Object o) {\n";
    			// hier: Attribute einfügen
	    code += "\t\treturn false;\n"
    		+ "\t}\n";
		
		// hashCode
		
		code += "}\n";

System.out.println( code );		

		// ... speichern ...
		// ===================
		try {
			BufferedWriter ausgabe = new BufferedWriter(new FileWriter(klassenPfad + klassenName + ".java"));
			ausgabe.write(code);
			ausgabe.close();
		} catch (Exception e) { e.printStackTrace(); }
		
		// ... und kompilieren
		// ===================
		Runtime R = Runtime.getRuntime();
		try {
			R.exec("javac " + klassenPfad + klassenName + ".java");
//			Thread.sleep(1000);
		} catch (Exception e) { e.printStackTrace(); }


	}
	
	private String createAttribute(String name, String typ) {
		String attr = "";
		attr += "\n\tprivate " + typ + " " + name + ";\n";
		attr += "\tpublic void set" + name + "(" + typ + " " + name + ") {"
				+ " this." + name + " = " + name + "; "
				+ "}\n";
		attr += "\tpublic " + typ + " get" + name + "() {"
		+ " return " + name + "; "
		+ "}\n";
		return attr;
	}
	
	public Object instanzieren() {
		Object o = new Object();
		
		return o;
	}
	
	// ===== Hilfsfunktionen ===== //
	
	private Document laden(File datei) {
			
	    Document doc = null;
        // file -> document
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder builder = factory.newDocumentBuilder();
	    	if (datei != null)
	    		doc = builder.parse(datei);
	    	else
	    		doc = builder.newDocument();
	    } catch(Exception e) { 
	        e.printStackTrace(); 
	    }
    	return doc;
	}
	
}

class Klasse {
	Vector<Attribut> attr; 
	Vector<Methode> meth;
}

class Attribut {
	String name;
	String typ;
	Attribut(String name, String typ) {
		this.name = name;
		this.typ = typ;
	}
}

class Methode {
	String name;
	String rückgabeTyp;
	String[] parameterTyp;
	
}