
package informatik.strukturen.graphen;

import informatik.strukturen.Menge;
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
public class GewichteterGraph<K> extends SchlichterGraph<K> implements Gewichtet<K> { //implements gewichtet{ 

    //	private HashMap knoten;												// Menge mit den Knoten
	private HashMap<K,Object[]>			knotenBewertungen;	//das Gleiche für Geweichtete Graphen - Kompatibilität!
	private HashMap<Paar<K,K>,Object[]>	kantenBewertungen;
	private int werteProKnoten;
	private int werteProKante;

	// ODER DOCH NUR EIN GEWICHT JE KANTE BZW. KNOTEN ???????????
	// =======================================================


	/**	Erzeugt einen Nullgraph. Ein Nullgraph ist ein Graph ohne Knoten.	 */
  public GewichteterGraph(int werteProKnoten, int werteProKante) { 
		super();
		knotenBewertungen = new HashMap<K,Object[]>();
		kantenBewertungen = new HashMap<Paar<K,K>,Object[]>();
		this.werteProKnoten = werteProKnoten;
		this.werteProKante = werteProKante;
	}

	/**	Erzeugt einen leeren Graph zu einer gegebenen Menge von Knoten. 
	 *	Ein Leerer Graph ist ein Graph ohne Kanten.
	 */
  public GewichteterGraph(Menge<K> knoten) { 
		super(knoten);
//		this.knoten = new HashMap();
//		for (Iterator i = knoten.alleElemente(); i.hasNext(); )
//			hinzufügen(i.next());
	}

  // TODO Übertragung der Attribute auf die Gewichte
  public GewichteterGraph(SchlichterGraph<K> g, int werteProKnoten, int werteProKante) { 
		super(g.knoten(), g.kanten());
		knotenBewertungen = new HashMap<K,Object[]>();
		kantenBewertungen = new HashMap<Paar<K,K>,Object[]>();
		this.werteProKnoten = werteProKnoten;
		this.werteProKante = werteProKante;

		if (werteProKnoten > 0)
			for (K k : knoten())
				knotenBewertungen.put(k, new Object[werteProKnoten]);
		
		if (werteProKante > 0)
			for (Paar<K,K> p : kanten())
				kantenBewertungen.put(p, new Object[werteProKante]);
	}


  	// ACHTUNG: wenn knoten ausserhalb in den graph eingefügt wird ... ?
	public boolean hinzufügen(K x) {
		if (!super.hinzufügen(x))
			return false;
		knotenBewertungen.put(x, new Object[werteProKnoten]);
		return true;
	}

	public boolean hinzufügen(K x, K y) {
		if (!super.hinzufügen(x, y))
			return false;
		kantenBewertungen.put(new Paar<K,K>(x, y), new Object[werteProKante]);
		return true;
	}

	/**
	 *	Setzt ein Gewicht eines Knotens auf einen neuen Wert.
	 */
	public boolean setWert(K x, int index, Object wert) {
		if (!knotenBewertungen.containsKey(x))
			return false;
		((Object[]) knotenBewertungen.get(x))[index] = wert;
		return true;
	}

	/**
	 *	Liefert ein Gewicht eines Knotens.
	 */
	public Object getWert(K x, int index) {
		if (!knotenBewertungen.containsKey(x))
			return null;
		return ((Object[]) knotenBewertungen.get(x))[index];
	}

	/**
	 *	Setzt ein Gewicht einer Kante auf einen neuen Wert.
	 */
	public boolean setWert(K x, K y, int index, Object wert) {
		Paar<K,K> p = new Paar<K,K>(x, y);
		if (!kantenBewertungen.containsKey(p))
			return false;
		((Object[]) kantenBewertungen.get(p))[index] = wert;
		return true;
	}

	/**
	 *	Liefert ein Gewicht einer Kante.
	 */
	public Object getWert(K x, K y, int index) {
		Paar<K,K> p = new Paar<K,K>(x, y);
		if (!kantenBewertungen.containsKey(p))
			return null;
		return ((Object[]) kantenBewertungen.get(p))[index];
	}

	public String toString() {
		String s = "({";
		int c = 0;

		for (K k : knoten()) {
			if (c>0)
				s += ",";
			s += "(" + k + ",[";
			for (int j=0; j<werteProKnoten; j++) {
				if (j>0)
					s += ",";
				s += ((Object[]) knotenBewertungen.get(k))[j];
			}
			s += "])";
		}

		s += "};{";

		c = 0;
		for (Paar<K,K> p : kanten()) {
			if (c>0)
				s += ",";
			s += "(" + p + ",[";
			for (int j=0; j<werteProKante; j++) {
				if (j>0)
					s += ",";
				s += ((Object[]) kantenBewertungen.get(p))[j];
			}
			s += "])";
		}
		s += "})";
		return s;
	}
}	