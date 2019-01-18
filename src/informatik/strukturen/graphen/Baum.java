package informatik.strukturen.graphen;

import informatik.strukturen.Folge;
import informatik.strukturen.Menge;
import informatik.strukturen.Relation;
import informatik.strukturen.graphen.hilfsmittel.GraphenAufgaben;

/**
 * <P><B>Baum</B> <I>(engl. tree)</I>: Ein Baum ist ein Graph
 * mit einer hierarchischen Struktur. Ein Baum enthält genau 
 * ein Element ohne Vorgänger; alle anderen Elemente besitzen 
 * genau einen Vorgänger.</P>
 *
 *  <p><strong>Version:</strong>
 *  <br><dd>1.0, April 2004
 *  <p><strong>Autor:</strong>
 *  <br><dd>Institut für Bauinformatik
 *  <br><dd>Universität Hannover
 *  <br><dd>Dipl.-Ing. Axel Klinger
 */
public class Baum<K> implements Graph<K> {		//	extends SchlichterGraph{	<- geht nicht, weil hinz(obj) beim Baum nicht erlaubt ist
 
	private K					wurzel;
	private SchlichterGraph<K> 	struktur;

	/**
	 *	Erstellt einen Baum mit einem Wurzelelement.
	 */
	public Baum (K wurzel) {
		struktur = new SchlichterGraph<K>();
		struktur.hinzufügen(wurzel);
		this.wurzel = wurzel;
	}

	public Baum (Menge<K> knoten, Relation<K,K> kanten) {
		struktur = new SchlichterGraph<K>(knoten, kanten);

		int i = 0;
		for (K k : struktur.knoten()) {
			if (struktur.vorgänger(k).anzahl() == 0) {
				i++;
				wurzel = k;
			}
		}
		if (i>1) {
			struktur = new SchlichterGraph<K>();
		}
	}
	
	public int anzahlKnoten() {
	    return struktur.anzahlKnoten();
	}

	/**
	 *	Überprüft, ob zwei Bäume gleich sind. Zwei Bäume sind gleich, wenn sie
	 *	die gleichen Knoten in der gleichen Anordnung besitzen.
	 */
	public boolean equals (Object objekt) {
		if (!objekt.getClass().equals(getClass())) 
			return false;
		Baum b = (Baum) objekt;
		return struktur.equals(b.struktur);
	}

	/**
	 *	Überprüft, ob ein Knoten im Baum enthalten ist.
	 */
	public boolean enthält (K knoten) { 
		return struktur.enthält(knoten);
	}

	/**
	 *	Liefert den Elternknoten eines Knoten zurück.
	 */
	public K getVorgänger (K knoten) {
		if (!enthält(knoten))
			return null;
		if (knoten.equals(wurzel))
			return null;
		return struktur.vorgänger(knoten).iterator().next();
	}

	/**
	 *	Liefert die Wurzel des Baums zurück.
	 */
	public K getWurzel () {
		 return wurzel;
	}

	/**
	 *	Fügt einen Kindknoten in den Baum ein. Ein Kindknoten kann 
	 *	in den Baum eingefügt werden, wenn der Vorgänger bereits 
	 *	im Baum enthalten ist und der Kindknoten nicht.
	 */
	public boolean hinzufügen (K vorgänger,K knoten) {
		// wenn der erste Knoten noch nicht im Baum enthalten ist
		// - den ersten an die Wurzel einhängen
		// wenn der zweite Knoten schon im Baum enthalten ist
		// - seinen Ast entnehmen und austragen
//		if (!enthält(vorgänger) || enthält(knoten))
//			return false;
		if (!enthält(vorgänger) && !vorgänger.equals(wurzel)) {
			struktur.hinzufügen(vorgänger);
			struktur.hinzufügen(wurzel, vorgänger);
		}
		if (enthält(knoten)) {
			Baum<K> baum = entfernen(knoten);
			hinzufügen(vorgänger,baum);
		} else {
			struktur.hinzufügen(knoten);
			struktur.hinzufügen(vorgänger, knoten);
		}
			
		return true;
	}

	/**
	 *	Fügt einen Baum in den Baum ein. Ein Baum kann 
	 *	in den Baum eingefügt werden, wenn der Vorgänger bereits 
	 *	im Baum enthalten ist und keiner der neuen Knoten bereits 
	 *	im Baum enthalten ist.
	 */
	public boolean hinzufügen (K vorgänger, Baum<K> baum) {
		for (K k : baum.knoten())
			if(enthält(k))
				return false;
		if (!enthält(vorgänger))
			return false;

		// Wurzel hinzufügen und  Graphen vereinigen !!!
		hinzufügen(vorgänger, baum.getWurzel());
		Folge<Menge<K>> topSorted = GraphenAufgaben.topologischSortieren(baum, true);
		for (int i = 1; i<topSorted.anzahl(); i++) {
			Menge<K> m = topSorted.get(i);
			for (K k : m)
				hinzufügen(baum.getVorgänger(k), k);
		}
		
		return true; //struktur.hinzufügen(knoten) && struktur.hinzufügen(vorgänger, knoten);
	}

	/**
	 *	Entfernt einen Knoten aus dem Baum. Alle Nachfahren des knoten 
	 *	werden ebenfalls aus dem Baum entfernt und der Teilbaum wird 
	 *  zurückgeliefert.
	 */
	public Baum<K> entfernen(K knoten) {
		Baum<K> teilbaum = GraphenAufgaben.breitenSuchbaum(this, knoten, true);
		for (K k : teilbaum.knoten())
			struktur.entfernen(k);
		return teilbaum;
	}

	/**
	 *	Liefert einen Knoten mit allen Nachfahren in Form eines Baums zurück.
	 */
	public Baum<K> getAst(K knoten) {
		return GraphenAufgaben.breitenSuchbaum(this, knoten, true);
	}

	/**
	 *	Liefert die Ebene eines Knoten als Ganzzahl zurück.
	 */
	public int getAbstandWurzel(K knoten) {
		int abstand = 0;		
		while (!wurzel.equals(knoten)) {
			knoten = getVorgänger(knoten);
			abstand++;
		}
		return abstand;
	}
	
	/**
	 * Liefert die Menge aller Knoten einer gegebenen tiefe.
	 * 
	 * @param tiefe
	 */
	public Menge<K> getEbene(int tiefe) {
		Menge<K> ebene = new Menge<K>();
		for (K k : knoten())
			if (getAbstandWurzel(k) == tiefe)
				ebene.hinzufügen(k);
		return ebene;
	}
	
	public Folge<Menge<K>> getEbenen() {
		Folge<Menge<K>> ebenen = new Folge<Menge<K>>();
		Menge<K> m = null;
		int i = 1;
		while ((m = getEbene(i++)).anzahl()>0)
			ebenen.hinzufügen(m);
		return ebenen;
	} 
	
	/**
	 * Liefert die Menge aller Knoten, die keinen Nachfolger haben.
	 * @return blätter
	 */
	public Menge<K> getBlätter() {
		Menge<K> blätter = new Menge<K>();
		for (K k : knoten())
			if (nachfolger(k).anzahl() == 0)
				blätter.hinzufügen(k);
		return blätter;
	}

	// Graph:
	public String toString () {
		return struktur.toString();
	}
	public int anzahlKanten() {
		return struktur.anzahlKanten();
	}
	public int anzahlVorgänger(K knoten) {
		return struktur.anzahlVorgänger(knoten);
	}
	public int anzahlNachfolger(K knoten) {
		return struktur.anzahlNachfolger(knoten);
	}
	public boolean enthält(K knoten1, K knoten2) {
		return struktur.enthält(knoten1, knoten2);
	}
	public Menge<K> knoten() {
		return struktur.knoten();
	}
	public Relation<K,K> kanten() {
		return struktur.kanten();
	}
	public Menge<K> vorgänger(K knoten) {
		return struktur.vorgänger(knoten);
	}
	public Menge<K> nachfolger(K knoten) {
		return struktur.nachfolger(knoten);
	}
	public Menge<K> nachfahren(K knoten) {
		return struktur.nachfahren(knoten);
	}
	public Menge<K> vorfahren(K knoten) {
		return struktur.vorfahren(knoten);
	}
}