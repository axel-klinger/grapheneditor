package informatik.hilfsmittel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

/**
 * Tool zur einfachen Dateiein-/-ausgabe von Zeichenketten.
 * @author klinger
 *
 */
public class TXT {

	/**
	 * Liest den Inhalt einer gegebenen Textdatei ein.
	 */
	public static String lesen(String datei) {
		String text = "", zeile = null;
		try {
			BufferedReader eingabe = new BufferedReader(new FileReader(datei)); 
			while ((zeile = eingabe.readLine()) != null) {text += zeile;}
			eingabe.close();
		} catch (Exception e) {	System.out.println("Textdatei " + datei + " geht nicht auf!");	}
		return text;
	}

	/**
	 * Liest den Inhalt einer gegebenen Textdatei zeilenweise ein.
	 */
	public static String[] lesenZeilen(String datei) {
		String zeile = null;
		Vector<String> zeilen = new Vector<String>(); 
		try {
			BufferedReader eingabe = new BufferedReader(new FileReader(datei)); 
			while ((zeile = eingabe.readLine()) != null) { zeilen.add(zeile); }
			eingabe.close();
		} catch (Exception e) {	System.out.println("Textdatei " + datei + " geht nicht auf!");	}
		String[] text = new String[zeilen.size()];
		text = zeilen.toArray(text);
		return text;
	}
	
	/**
	 * Schreibt eine Zeichenkette in eine Textdatei.
	 */
	public static void schreiben(String text, String datei) {
		try {
			BufferedWriter ausgabe = new BufferedWriter(new FileWriter(datei));
			ausgabe.write(text);
			ausgabe.close();
		} catch (Exception e) {	System.out.println("Textdatei " + datei + " geht nicht auf!");	}
	}
	
}
