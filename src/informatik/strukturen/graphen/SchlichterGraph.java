
package informatik.strukturen.graphen;

import informatik.strukturen.Relation;
import informatik.strukturen.Menge;
import informatik.strukturen.Paar;
import informatik.strukturen.graphen.hilfsmittel.GraphenAufgaben;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.HashMap;

/**
 *	<P><B>Schlichter Graph</B> <I>(engl. simple graph)</I>: 
 *	Ein SchlichterGraph beschreibt die Beziehungen zwischen 
 *	den Elementen einer Menge. Er dient als grafische 
 *	Repräsentation einer homogenen binären Relation. 
 *	Die Elemente der Menge werden als Knoten bezeichnet und die 
 *	Beziehungen	als Kanten. Grafisch werden die Knoten durch 
 *	gleichartige Symbole dargestellt und die Kanten als Linien oder Pfeile.
 *
 *	Beispiel: (Bild)
 *
 *	<CENTER><IMG SRC="imagesGraph/Graph.png" BORDER=0 ALT="schlichter Graph"></CENTER>
 *
 *  <p><strong>Version:</strong>
 *  <br><dd>1.1, Juni 2004
 *  <p><strong>Autor:</strong>
 *  <br><dd>Axel Klinger
 */
public class SchlichterGraph<K> implements Graph<K>, Serializable { 

    private HashMap<K,Knoten<K>> knoten;	// Menge mit den Knoten

	/**	
	 *	Erzeugt einen Nullgraph. Ein Nullgraph ist ein Graph ohne Knoten und Kanten.	 
	 */
	public SchlichterGraph() { 
		knoten = new HashMap<K,Knoten<K>>(); 
	}

	/**	
	 *	Erzeugt einen leeren Graph zu einer gegebenen Menge von Knoten. 
	 *	Ein Leerer Graph ist ein Graph ohne Kanten.
	 */
	public SchlichterGraph(Menge<K> knoten) { 
		this.knoten = new HashMap<K,Knoten<K>>();
		for (K m : knoten)
			hinzufügen(m);
	}

	/**	
	 *	Erzeugt einen neuen Graph aus einer Menge von Knoten und einer Relation von Kanten.	 
	 */
	public SchlichterGraph(Menge<K> knoten, Relation<K,K> kanten) {
		this.knoten = new HashMap<K,Knoten<K>>();
		for (K m : knoten)
			hinzufügen(m);
		for (Paar<K,K> p : kanten)
			hinzufügen(p.eins(), p.zwei());
	}	

	/**	
	 *	Erzeugt eine Kopie des Graphen. Es werden nur die Verweise auf die Elemente 
	 *	des Graphen kopiert und nicht die Inhalte.
	 */
	public Object clone() {
		return new SchlichterGraph<K>(knoten(), kanten());
	}

	//// Liefert einen Knoten zu einem gegebenen Objekt.
	private Knoten<K> getKnoten(Object wert) {												// item
		return (Knoten<K>) knoten.get(wert);
	}

	/**	
	 *	Liefert die Anzahl der Knoten.	 
	 */
	public int anzahlKnoten() {																		// nodesize
		return knoten.size();
	}

	/**	
	 *	Liefert die Anzahl der Kanten.	 
	 */
	public int anzahlKanten() {																		// edgesize
		return kanten().anzahl();
	}

	/**	
	 *	Liefert die Anzahl der Vorgänger eines Knoten.	 
	 */
	public int anzahlVorgänger(K knoten) {	// indegree
		if (!enthält(knoten))	
			throw new NoSuchElementException ("Der Knoten " + knoten + " existiert nicht im Graphen!");
		return getKnoten(knoten).vorgänger.anzahl();
	}

	/**	
	 *	Liefert die Anzahl der Nachfolger eines Knoten.	 
	 */
	public int anzahlNachfolger(K knoten) {	// outdegree
		if (!enthält(knoten))	
			throw new NoSuchElementException ("Der Knoten " + knoten + " existiert nicht im Graphen!");
		return getKnoten(knoten).nachfolger.anzahl();
	}

	/**	
	 *	Liefert TRUE wenn der gegebene Knoten im Graph enthalten ist, sonst FALSE.	 
	 */
	public boolean enthält (K knoten) {												// nodemember
		return this.knoten.containsKey(knoten);
	}

	/**	
	 *	Liefert TRUE wenn die gegebene Kante im Graph enthalten ist, sonst FALSE.	 
	 */
	public boolean enthält (K knoten1, K knoten2) {			// edgemember
		Knoten k1 = getKnoten(knoten1);
		Knoten k2 = getKnoten(knoten2);
		return (k1 != null) && (k2 != null) && (k1.nachfolger.enthält(k2));
	}

	/**	
	 *	Liefert TRUE wenn der gegebene Graph in diesem Graph enthalten ist, sonst FALSE.	 
	 */
	public boolean enthält (SchlichterGraph<K> graph) {
		return (this.knoten().enthält(graph.knoten()))
			&& (this.kanten().enthaelt(graph.kanten()));
	}

	/**	
	 *	Fügt einen Knoten zum Graph hinzu. 
	 *	Die Funktion lieferrt TRUE wenn der Knoten in den Graph eingefgt werden konnte, 
	 *	sonst FALSE. Ein Knoten kann nicht in den Graph eingefgt werden, 
	 *	wenn er bereits im Graph enthalten ist.
	 */
	public boolean hinzufügen(K knoten) {	// addNode
		if (enthält((K) knoten))
			return false;
		this.knoten.put(knoten, new Knoten<K>(knoten));
		return true;
	}

	/**	
	 *	Fügt eine Kante zum Graph hinzu. Die Funktion lieferrt TRUE wenn die Kante 
	 *	in den Graph eingefgt werden konnte, sonst FALSE. Eine Kante kann nicht in den 
	 *	Graph eingefügt werden, wenn sie bereits im Graph enthalten ist.
	 */
	public boolean hinzufügen(K knoten1, K knoten2) {		// addEdge
		Knoten<K> k1 = getKnoten(knoten1);
		Knoten<K> k2 = getKnoten(knoten2);
		if (k1!=null && k2!=null) {
			if (k1.nachfolger.enthält(k2))
				return false;
			k1.nachfolger.hinzufügen(k2);
			k2.vorgänger.hinzufügen(k1);
			return true;
		}
		return false;
	}

	/**
	 *	Entfernt einen Knoten aus dem Graph. Die Funktion liefert TRUE wenn der Knoten 
	 *	aus dem Graph entfernt werden konnte, sonst FALSE. Ein Knoten kann nicht aus dem 
	 *	Graph entfernt werden, wenn er nicht im Graph enthalten ist.
	 */
	public boolean entfernen(K knoten) {
		Knoten<K> k = getKnoten(knoten);
		if (k != null) {
			for (Knoten n : k.nachfolger)
				n.vorgänger.entfernen(k);
			for (Knoten n : k.vorgänger)
				n.nachfolger.entfernen(k);
			this.knoten.remove(knoten);
			return true;
		}
		return false;
	}

	/**
	 *	Entfernt eine Kante aus dem Graph. Die Funktion liefert TRUE wenn die Kante 
	 *	aus dem Graph entfernt werden konnte, sonst FALSE. Eine Kante kann nicht aus dem 
	 *	Graph entfernt werden, wenn sie nicht im Graph enthalten ist.
	 */
	public boolean entfernen(K knoten1, K knoten2) {
		Knoten<K> k1 = getKnoten(knoten1);
		Knoten<K> k2 = getKnoten(knoten2);
		if ((k1 != null) && (k2 != null))
			for (Knoten k : k1.nachfolger)
				if (knoten2.equals(k.wert)) {
					k1.nachfolger.entfernen(k);
					k.vorgänger.entfernen(k1);
					return true;
				}
		return false;
	}

	////
	//	Liefert alle Knoten als Menge zurück.
	//
	public Menge<K> knoten() {			// liefert: Menge <Wert>
		Menge<K> m = new Menge<K>();
		for (Knoten<K> k : knoten.values())
			m.hinzufügen(k.wert);
		return m;
	}
	
	////
	//	Liefert alle Kanten als Relation zurück.
	//
	public Relation<K,K> kanten() {		// liefert: Menge <Paar <Wert>>
		Relation<K,K> r = new Relation<K,K>();
		for (Knoten<K> k : knoten.values())
			for (Knoten<K> n : k.nachfolger)
				r.hinzufügen((K) k.wert, (K) n.wert);
		return r;
	}

	public Menge<K> vorgänger(K knoten) {
		Menge<K> m = new Menge<K>();
		if (getKnoten(knoten) == null) 
			return m;
		for (Knoten<K> k : getKnoten(knoten).vorgänger)
			m.hinzufügen((K) k.wert);
		return m;
	}

	public Menge<K> nachfolger(K knoten) {
		Menge<K> m = new Menge<K>();
        if (getKnoten(knoten) == null)
            return m;
		for (Knoten<K> k : getKnoten(knoten).nachfolger)
			m.hinzufügen((K) k.wert);
		return m;
	}
	
	public Menge<K> startKnoten() {
		Menge<K> m = new Menge<K>();
		for (Knoten<K> k : knoten.values()) 
			if (k.vorgänger.anzahl() == 0)
				m.hinzufügen(k.wert);
		return m;
	}

	public Menge<K> endKnoten() {
		Menge<K> m = new Menge<K>();
		for (Knoten<K> k : knoten.values()) 
			if (k.nachfolger.anzahl() == 0)
				m.hinzufügen(k.wert);
		return m;
	}

	/**
	 *	Überprüft ob zwei Graphen gleich sind. Zwei Graphen sind gleich, 
	 *	wenn sie jeweils im anderen Graph enthalten sind.
	 */
	public boolean equals(Object objekt) {
		if (objekt == this)
		    return true;

		if (!(objekt instanceof SchlichterGraph))	
			return false;

		SchlichterGraph g = (SchlichterGraph) objekt;
		return this.enthält(g) && g.enthält(this);
	}

	/**
	 *	Liefert den transponierten Graph dieses Graphen. Der transponierte 
	 *	Graph enthält die gleichen Knoten und nur die umgekehrten Kanten des 
	 *	ursprünglichen Graphen.
	 *
	 *	G^K = {(x,y) | (y,x) IN R}
	 */
	public SchlichterGraph<K> transposition() {
		return new SchlichterGraph<K>(knoten(), kanten().transposition());
	}

	/**
	 *	Liefert den Durchschnitt von zwei Graphen.
	 *
	 *	G3 = G1 oder G2 = (V1 oder V2; R1 oder R2)
	 */
	public SchlichterGraph<K> durchschnitt(SchlichterGraph<K> graph) {
		Menge<K>	  M = (Menge<K>)	  this.knoten().durchschnitt(graph.knoten());
		Relation<K,K> R = (Relation<K,K>) this.kanten().durchschnitt(graph.kanten());
		return new SchlichterGraph<K>(M, R);
	}

	/**
	 *	Liefert die Vereinigung dieses Graphen mit einem weiteren Graph.
	 *
	 *	G3 = G1 und G2 = (V1 und V2; R1 und R2)
	 */
	public SchlichterGraph<K> vereinigung(SchlichterGraph<K> graph) {
		Menge<K>	  M = (Menge<K>)	  this.knoten().vereinigung(graph.knoten());
		Relation<K,K> R = (Relation<K,K>) this.kanten().vereinigung(graph.kanten());
		return new SchlichterGraph<K>(M, R);
	}	

	public SchlichterGraph<K> differenz(SchlichterGraph<K> graph) {
		Menge<K>	  M = (Menge<K>)	  this.knoten().differenz(graph.knoten());
		Relation<K,K> R = (Relation<K,K>) this.kanten().differenz(graph.kanten());
		return new SchlichterGraph<K>(M, R);
	}

	public SchlichterGraph<K> kantenDifferenz(SchlichterGraph<K> graph) {
	    ///// noch FALSCH //////
		Menge<K>	  M = (Menge<K>)	  this.knoten().differenz(graph.knoten());
		Relation<K,K> R = (Relation<K,K>) this.kanten().differenz(graph.kanten());
		return new SchlichterGraph<K>(M, R);
	}

	// kettung -> kettung
	public SchlichterGraph<K> kettung(SchlichterGraph<K> graph) {
		if (this.knoten.equals(graph.knoten)) {
			Relation<K,K> R = (Relation<K,K>) this.kanten().kettung(graph.kanten());	//	???
			return new SchlichterGraph<K>(this.knoten(), R);
		}
		return new SchlichterGraph<K>();
	}	

	public SchlichterGraph<K> transitiveHülle() {
		SchlichterGraph<K> G = new SchlichterGraph<K>(knoten(), kanten());
		for (K y : knoten())
			for (K x : knoten())
				for (K z : knoten())
					if (!G.enthält(x,z) && G.enthält(x,y) && G.enthält(y,z))
						G.hinzufügen(x,z);
		return G;
	}
	
	public SchlichterGraph<K> reflexivTransitiveHülle() {
		SchlichterGraph<K> G = transitiveHülle();
		for (K x : knoten())
			G.hinzufügen(x,x);
		return G;
	}

	public String toString() {
		return "{" + knoten() + ";" + kanten() + "}";
	}

	/**
	 * Liefert die Nachfahren eines Knotens in Form einer Menge
	 * von Knoten.
	 * 
	 * @return menge der Nachfahren
	 */
	public Menge<K> nachfahren(K knoten) {
        if (getKnoten(knoten) == null)
            return new Menge<K>();
		return GraphenAufgaben.tiefenSuchbaum(this, (K) knoten, true).knoten();
	}

	public Menge<K> vorfahren(K knoten) {
        if (getKnoten(knoten) == null)
            return new Menge<K>();
		return GraphenAufgaben.tiefenSuchbaum(this, (K) knoten, false).knoten();
	}

	public SchlichterGraph<K> getTeilgraph(Menge<K> knotenMenge) {
		// TODO Auto-generated method stub
		SchlichterGraph<K> teilGraph = new SchlichterGraph<K>();
		for (K k : knotenMenge) {
			teilGraph.hinzufügen(k);
		}
		for (Paar<K,K> p : kanten()) {
			if (this.enthält(p.eins()) && this.enthält(p.zwei()))
				teilGraph.hinzufügen(p.eins(), p.zwei());
		}
		return teilGraph;
	}

////////////////////////////////////////////////////////////////////////////
//  KLASSE Knoten																													//
////////////////////////////////////////////////////////////////////////////
} // statt 490 ohne Generics

class Knoten<K> implements Serializable {
    /** Comment for <code>serialVersionUID</code>     */
    private static final long serialVersionUID = 1L;
    
    K wert;
	public Menge<Knoten<K>>  vorgänger;		// Menge<Knoten>
	public Menge<Knoten<K>>	nachfolger;	// Menge<Knoten>

	public Knoten(K knoten) {
		wert	   = knoten;
		vorgänger  = new Menge<Knoten<K>>();
		nachfolger = new Menge<Knoten<K>>();
	}

	public boolean equals(Object objekt) {
		if (!(objekt instanceof Knoten))
			return false;
		Knoten k = (Knoten) objekt;
		return wert.equals(k.wert);
	}

	public String toString() {
		return wert.toString();
	}

	public int hashCode() {
	    int hash = 7;
	    hash = hash * 31 + ((wert == null) ? 0 : wert.hashCode());
	    return hash;
	}
}
