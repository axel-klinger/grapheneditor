
package informatik.strukturen.graphen.wege;

import informatik.strukturen.Menge;

import java.util.Iterator;

/**
 *	Eine Wegmenge enth�lt Wege mit den gleichen Startknoten und den gleichen Endknoten.
 */
public class WegMenge<K> extends Menge<Weg<K>> {		// extends Menge !?!?

    private K start;
    private K ziel;
    
//	private Menge<Weg<K>> wege;

	public WegMenge() {
	    super();
	    start = null;
	    ziel  = null;
//	    wege  = new Menge<Weg<K>>();
	}
	
	public WegMenge(Weg<K> w) {
	    super();
	    start = null;
	    ziel  = null;
//	    wege  = new Menge<Weg<K>>();
	    hinzuf�gen(w);
	}
	
	public K getStart() {
	    return start;
	}
	
	public K getZiel() {
	    return ziel;
	}
	
	public boolean hinzuf�gen(Weg<K> w) {
	    if (this.anzahl()==0 && w.knotenFolge().anzahl()==1) {
	        super.hinzuf�gen(w);
	        return true;
	    }
	    if (w.kantenFolge().anzahl() == 0)
	        return false;
	    if (anzahl() == 0) {
	        start = w.start();
	        ziel  = w.ziel();
	        super.hinzuf�gen(w);
	        return true;
	    } else if (start.equals(w.start()) 	// LEEREN WEG hinzuf�gen k�nnen !!!
	            && ziel.equals(w.ziel())
	            && !super.enth�lt(w)) {		// ben�tigt weg.equals !!!
	        super.hinzuf�gen(w);
	        return true;
	    }
		return false;
	}

//	public boolean hinzuf�gen(WegMenge wm) { return false; }	// s. Vereinigung

	public boolean entfernen(Weg w) {
		if (super.enth�lt(w)) {
		    super.entfernen(w);
		    return true;
		}
		return false;
	}

	public Iterator<Weg<K>> alleWege() {
		return super.iterator();
	}
	
	public Iterator<Weg<K>> iterator() {
		return super.iterator();
	}

	public int anzahl() {
	    return super.anzahl();
	}

	/**
	 *	Liefert die Kettung dieser Wegmenge mit einer weiteren Wegmenge.
	 */
	public WegMenge kettung(WegMenge w) {
		WegMenge ergebnis = new WegMenge();
		// Zwei Wegmengen k�nnen gekettet werden, wenn der Zielknoten der ersten Menge gleich dem 
		// Startknoten der zweiten Menge ist
		
		return ergebnis;
	}

	/**
	 *	Liefert die Vereinigung dieser Wegmenge mit einer weiteren Wegmenge.
	 */
	public WegMenge vereinigung(WegMenge w) {
		WegMenge ergebnis = new WegMenge();
		// Zwei Wegmengen k�nnen vereinigt werden, wenn sie die gleichen Start- und Zielknoten besitzen

		return ergebnis;
	}
	
	public boolean equals(Object o) { 
	    if (!(o instanceof WegMenge))
	        return false;
	    WegMenge wm = (WegMenge) o;
	    return super.equals(wm); 
	}
	
	public String toString() { 
	    return super.toString(); 
	}	
}
