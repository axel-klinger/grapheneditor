
package informatik.strukturen.graphen.wege;

import informatik.strukturen.Folge;
import informatik.strukturen.Paar;
import informatik.strukturen.graphen.SchlichterGraph;

import java.util.Iterator;

// ===> Die Bewertung eines Weges ist ein Wert, der sich nach den Regeln der 
//			entsprechenden (Wegalgebra) Wegaufgabe aus der (Kettung oder Vereinigung?) 
//			der Kanten ergibt.

/**
 *	<P><B>Weg</B> <I>(engl. path)</I>: Ein Weg besteht aus einer Folge 
 *	von Knoten.</P>
 */
public class Weg<K> implements Iterable<Paar<K,K>> /*<KnotenTyp,T extends Bewertung>*/ {		// BT = T extends Bewertung

	protected Folge<K>			knoten;
	protected Folge<Paar<K,K>>	kanten;

	/**
	 *	Erzeugt einen leeren Weg.
	 */
	public Weg() {
		knoten = new Folge<K>();
		kanten = new Folge<Paar<K,K>>();
	}

	public Weg(K a) {
		knoten = new Folge<K>();
		kanten = new Folge<Paar<K,K>>();
		hinzufügen(a);
	}

	public Weg(K a, K b) {
		knoten = new Folge<K>();
		kanten = new Folge<Paar<K,K>>();
		hinzufügen(a, b);
	}

	/**
	 * Liefert den Weg als schlichten Graph zurück. 
	 * Vorsicht: Hier können Informationen verloren 
	 * gehen, da doppelte Kanten im Weg beim Graph 
	 * verschwinden. 
	 * @return graph der Weg in Form eines schlichten Graphen
	 */
    public SchlichterGraph<K> getGraph() {
        SchlichterGraph<K> graph = new SchlichterGraph<K>();
        for (K k : knoten)
            graph.hinzufügen(k);
        for (Paar<K,K> p : kanten)
            graph.hinzufügen(p.eins(), p.zwei());
        return graph;
    }
    
    /**
     * Liefert die Folge der Kanten des Weges.
     * @return kanten Folge der Kanten
     */
	public Folge<Paar<K,K>> kantenFolge() {
		return kanten;
	}

	/**
	 * Liefert die Folge der Knoten des Weges.
	 * @return knoten Folge der Knoten des Weges
	 */
	public Folge<K> knotenFolge() {
		return knoten;
	}

	/**
	 * Liefert true wenn der Knoten im Weg enthalten ist, sonst false.
	 * @param knoten Knoten, dessen Existenz im Weg geprüft werden soll
	 * @return true/false ob der Knoten im Weg entahlten ist
	 */
	public boolean enthält(K knoten) {
		return this.knoten.enthält(knoten);
	}

	/**
	 * Liefert true wenn die Kante im Weg enthalten ist, sonst false.
	 * @return true/false ob die Kante im Weg entahlten ist
	 */
	public boolean enthält(K knoten1, K knoten2) {
	    return kanten.enthält(new Paar<K,K>(knoten1, knoten2));
	}
	
	/**
	 * Ein Weg ist einfach, wenn er keine Kante mehrfach enthält. 
	 * @return Liefert true wenn ein Weg einfach ist, sonst false 
	 */
	public boolean istEinfach() {
		for (Paar<K,K> kante : this) {
			int zähler = 0;
			for (Paar<K,K> kante2 : this) {
				if (kante.equals(kante2))
					zähler++;
			}
			if (zähler > 0)
				return false;
		}
		return true;
	}
	
	/**
	 * Ein Weg ist elementar, wenn er keinen Knoten mehrfach enthält. 
	 * @return Liefert true wenn ein Weg elementar ist, sonst false 
	 */
	public boolean istElementar() {
		for (K knoten : this.knoten) {
			int zähler = 0;
			for (K knoten2 : this.knoten) {
				if (knoten.equals(knoten2))
					zähler++;
			}
			if (zähler > 0)
				return false;
		}
		return true;
	}
	
	/**
	 * Liefert den Anfangsknoten des Weges.
	 * @return knotenA Anfangsknoten des Weges
	 */
	public K start() {
	    return knoten.get(0);
	}
	
	/**
	 * Liefert den Endknoten eines Weges.
	 * @return knotenE Endknoten des Weges
	 */
	public K ziel() {
	    return knoten.get(knoten.anzahl()-1);
	}
	
	/**
	 * Fügt einen Knoten dem Weg hinzu. Hier kann 
	 * dem Weg jeder beliebige Knoten hinzugefügt werden.
	 * @param k Knoten der hinzugefügt werden soll
	 * @return true/false ob der Knoten dem Weg hinzugefügt werden konnte
	 */
	public boolean hinzufügen(K k) {
		if (knoten.anzahl()>0)
			kanten.hinzufügen(new Paar<K,K>(knoten.get(knoten.anzahl()-1), k));
		knoten.hinzufügen(k);
		return true;
	}

	/**
	 * Fügt eine Kante dem Weg hinzu. Eine Kante kann 
	 * dem Weg hinzugefügt werden, wenn der Anfangsknoten 
	 * der Kante gleich dem Endknoten des Weges ist.
	 * @param knoten1 Anfangsknoten der Kante die hinzugefügt werden soll
	 * @param knoten2 Endknoten der Kante die hinzugefügt werden soll
	 * @return true/false ob die Kante dem Weg hinzugefügt werden konnte
	 */
	public boolean hinzufügen(K knoten1, K knoten2) {
		if (knoten.anzahl() == 0)
			knoten.hinzufügen(knoten1);
		if (!knoten.get(knoten.anzahl()-1).equals(knoten1))
			return false;
		knoten.hinzufügen(knoten2);
		kanten.hinzufügen(new Paar<K,K>(knoten1, knoten2));
		return true;
	}

/*	s. kantenFolge().iterator()
 * 	public Iterator<K> alleKnoten() {		// Menge oder Folge ???
		return knoten.iterator();
	}*/
	
/*	s. knotenFolge()
 	public Folge<K> knoten() {
	    return knoten;	    
	}*/

/*	s. iteraotr()
 	public Iterator<Paar<K,K>> alleKanten() {		// ...
		return kanten.iterator();
	}*/
	
/*	s. kantenFolge()
	public Folge<Paar<K,K>> kanten() {
	    return kanten;
	}*/

	/**
	 * Liefert den umgekehrten Weg dieses Weges.
	 * @return weg umgekehrter Weg
	 */
	public Weg<K> umdrehen() {
	    Weg<K> w = new Weg<K>();
	    for (int i=knoten.anzahl()-1; i>=0; i--) {
	        w.hinzufügen(knoten.get(i));
	    }
	    return w;
	}
	
	/**
	 * Liefert die Verkettung zweier Wege. Zwei nichtleere 
	 * Wege können aneinander gehängt werden, wenn der Anfangsknoten 
	 * des zweiten Weges gleich dem Endknoten des ersten Weges ist.
	 * @param b der zweite Weg
	 * @return c ein neuer Weg aus der Aneinanderreihung der beiden Wege
	 */
	public Weg<K> ketten(Weg<K> b) {
	    Weg<K> c = new Weg<K>();
	    if (this.knoten.anzahl()==0 || b.knoten.anzahl()==0)
	        return c;
	    if (!this.ziel().equals(b.start()))
	        return c;
	    for (Paar<K,K> p : kanten)
	        c.hinzufügen(p.eins(), p.zwei());
		for (Paar<K,K> p : b.kanten)
	        c.hinzufügen(p.eins(), p.zwei());
	    return c;
	}
	
	/**
	 * Zwei Wege sind gleich, wenn sie aus den gleichen Knoten 
	 * in der gleichen Reihenfolge bestehen. 
	 */
	public boolean equals(Object o) { 
	    if (!(o instanceof Weg))
	        return false;
	    Weg w = (Weg) o;
	    return this.knoten.equals(w.knoten); 
	}	
	
	/**
	 * Liefert den HashCode eines Weges.
	 */
	public int hashCode() {
	    int hash = 7;
	    for (Object o : knoten)
	        hash = hash * 31 + ((o == null) ? 0 : o.hashCode());
	    return hash;
	}
	
	/**
	 * Liefert die Zeichenkettenrepräsentation des Weges.
	 */
	public String toString() {
		return knoten.toString();
	}

/*	s. kantenFolge().anzahl()
	public int anzahlKanten() {
		return kanten.anzahl();
	}*/

/*	s. knotenFolge().anzahl()	
	public int anzahlKnoten() {
		return knoten.anzahl();
	}*/

	/**
	 * Liefert eine Aufzählung der Kanten des Weges zurück.
	 * @return iterator Aufzählung der Kanten des Weges
	 */
	public Iterator<Paar<K,K>> iterator() {
		return kanten.iterator();
	}
}
