package informatik.strukturen.graphen;

import informatik.strukturen.Paar;

import java.util.HashMap;

	/**
	 *	<P><B>Gewichteter Graph</B> <I>(engl. weighted graph)</I>: 
	 *	Ein gewichteter Graph erweitert einen schlichten Grahp um 
	 *	Gewichte der Kanten und Knoten.</P>
	 *
	 *	<P>Überarbeiten: Die Bewertung des Graphen (Interface) setzt die Definition 
	 *	gewisser Rechenregeln vorraus. Es müssen die Kettung und die 
	 *	Vereinigung definiert werden. Außerdem muss (zumindest intern!?)
	 *	eine Bewertungsmatrix ausgegeben werden können.</P>
	 *
	 *	<CENTER><IMG SRC="imagesGraph/Gewichtet.png" BORDER=0 ALT="gewichteter Graph"></CENTER>
	 *
	 *	<I>Anwendungsbeispiele</I>:
	 *		<UL>
	 *			<LI>Kürzeste Wege Suche
	 *			<LI>Maximale Flüsse
	 *		</UL>
	 *
	 *  <p><strong>Version:</strong>
	 *  <br><dd>1.0, April 2004
	 *  <p><strong>Autor:</strong>
	 *  <br><dd>Institut für Bauinformatik
	 *  <br><dd>Universität Hannover
	 *  <br><dd>Dipl.-Ing. Axel Klinger
	 */
public class BewerteterGraph<K, W> extends SchlichterGraph<K> {

	private HashMap<K,W>			knotenBewertungen;	//das Gleiche für Geweichtete Graphen - Kompatibilität!
	private HashMap<Paar<K,K>,W>	kantenBewertungen;


	/**	Erzeugt einen Nullgraph. Ein Nullgraph ist ein Graph ohne Knoten.	 */
	public BewerteterGraph() { 
		super();
		knotenBewertungen = new HashMap<K,W>();
		kantenBewertungen = new HashMap<Paar<K,K>,W>();
	}

	/**	Erzeugt einen leeren Graph zu einer gegebenen Menge von Knoten. 
	 *	Ein Leerer Graph ist ein Graph ohne Kanten.
	 */
	public BewerteterGraph(SchlichterGraph<K> g) { 
		super(g.knoten(), g.kanten());
		
		knotenBewertungen = new HashMap<K,W>();
		kantenBewertungen = new HashMap<Paar<K,K>,W>();

		for (K k : knoten())
			knotenBewertungen.put(k, null);
		
		for (Paar<K,K> p : kanten())
			kantenBewertungen.put(p, null);
	}

	// TODO Übertragung der Attribute auf die Gewichte

  	// ACHTUNG: wenn knoten ausserhalb in den graph eingefügt wird ... ?
	public boolean hinzufügen(K knoten) {
		if (!super.hinzufügen(knoten))
			return false;
		knotenBewertungen.put(knoten, null);
		return true;
	}

	public boolean hinzufügen(K knoten1, K knoten2) {
		if (!super.hinzufügen(knoten1, knoten2))
			return false;
		kantenBewertungen.put(new Paar<K,K>(knoten1, knoten2), null);
		return true;
	}

	public boolean entfernen(K knoten) {
		if (!super.entfernen(knoten))
			return false;
		knotenBewertungen.remove(knoten);
		return true;
	}

	public boolean entfernen(K knoten1, K knoten2) {
		if (!super.entfernen(knoten1, knoten2))
			return false;
		knotenBewertungen.remove(new Paar<K,K>(knoten1, knoten2));
		return true;
	}
	
	/**
	 *	Setzt ein Gewicht eines Knotens auf einen neuen Wert.
	 */
	public void setWert(K knoten, W wert) {
		knotenBewertungen.put(knoten, wert);
	}

	/**
	 *	Liefert ein Gewicht eines Knotens.
	 */
	public W getWert(K knoten) {
		return knotenBewertungen.get(knoten);
	}

	/**
	 *	Setzt ein Gewicht einer Kante auf einen neuen Wert.
	 */
	public void setWert(K knoten1, K knoten2, W wert) {
		kantenBewertungen.put(new Paar<K,K>(knoten1, knoten2), wert);
	}

	/**
	 *	Liefert ein Gewicht einer Kante.
	 */
	public W getWert(K x, K y) {
		return kantenBewertungen.get(new Paar<K,K>(x, y));
	}

	public String toString() {
		String s = "({";
		int c = 0;
		for (K k : knoten())
			s += (c>0 ? "," : "") + "(" + k + "," + knotenBewertungen.get(k) + ")";
		s += "};{";
		c = 0;
		for (Paar<K,K> p : kanten())
			s += (c>0 ? "," : "") + "(" + p + "," + kantenBewertungen.get(p) + ")";
		s += "})";
		return s;
	}
}	