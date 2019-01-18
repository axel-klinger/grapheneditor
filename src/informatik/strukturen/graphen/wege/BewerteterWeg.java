
package informatik.strukturen.graphen.wege;

import informatik.strukturen.Paar;
import informatik.strukturen.graphen.Gewichtet;

import java.util.HashMap;

/**
 *	<P><B>Bewerteter Weg</B> <I>(engl. weighted path)</I>: Ein Weg besteht aus einer Folge 
 *	von Knoten.</P>
 */
public class BewerteterWeg<K> extends Weg<K> implements Gewichtet<K> /*<KnotenTyp,T extends Bewertung>*/ {		// BT = T extends Bewertung

//	Jedes Element erfassen ...														// -> <T extends Bewertung> T[]
	private HashMap<K,Object[]>			knotenBewertungen;	//das Gleiche für Geweichtete Graphen - Kompatibilität!
	private HashMap<Paar<K,K>,Object[]>	kantenBewertungen;

	private int werteProKnoten;
	private int werteProKante;

	/**
	 *	Erzeugt einen leeren Weg.
	 */
	public BewerteterWeg(int werteProKnoten, int werteProKante) {
		super();
		knotenBewertungen = new HashMap<K,Object[]>();
		kantenBewertungen = new HashMap<Paar<K,K>,Object[]>();
		this.werteProKnoten = werteProKnoten;
		this.werteProKante = werteProKante;
	}

	public boolean hinzufügen(K x) {
		if (!super.hinzufügen(x))
			return false;
		if (knoten.anzahl()>1)
			kantenBewertungen.put(new Paar<K,K>(knoten.get(knoten.anzahl()-2), x), new Object[werteProKante]);
		knotenBewertungen.put(x, new Object[werteProKnoten]);
		return true;
		// -> hier auch Kantenbewertung einfügen !!!
	}

	public boolean hinzufügen(K x, K y) {
		if (!super.hinzufügen(x, y))
			return false;
		kantenBewertungen.put(new Paar<K,K>(x, y), new Object[werteProKante]);
		knotenBewertungen.put(y, new Object[werteProKnoten]);
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
		Paar p = new Paar<K,K>(x, y);
		if (!kantenBewertungen.containsKey(p))
			return false;
		((Object[]) kantenBewertungen.get(p))[index] = wert;
		return true;
	}

	/**
	 *	Liefert ein Gewicht einer Kante.
	 */
	public Object getWert(K x, K y, int index) {
		Paar p = new Paar<K,K>(x, y);
		if (!kantenBewertungen.containsKey(p))
			return null;
		return ((Object[]) knotenBewertungen.get(p))[index];
	}

// public Object getWert(int index);		// Liefert die Bewertung des Weges
											// -> Kenntnis der verwendeten Wegalgebra erforderlich !!!

	public Object getLänge() {
		return null;
	}
	
	// TODO !!!!! ALLE Bewertungen mit übernehmen
	public BewerteterWeg<K> umdrehen() {
		BewerteterWeg<K> w = new BewerteterWeg<K>(1,1);
	    for (int i=knoten.anzahl()-1; i>=0; i--) {
	        w.hinzufügen(knoten.get(i));
	        w.setWert(knoten.get(i), 0, getWert(knoten.get(i),0));
	    }
	    return w;
	}

	// TODO !!!!! Knotenbewertungen mit ausgeben
	public String toString() {
		String s = "<";

		int c = 0;
		for( Paar<K,K> p : this) {
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
		s += ">";
		return s;
	}
}
