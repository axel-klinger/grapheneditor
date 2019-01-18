package informatik.strukturen.graphen;

import informatik.strukturen.Folge;
import informatik.strukturen.Menge;
import informatik.strukturen.Relation;
import informatik.strukturen.graphen.hilfsmittel.GraphenAufgaben;

/**
 * <P><B>Baum</B> <I>(engl. tree)</I>: Ein Baum ist ein Graph
 * mit einer hierarchischen Struktur. Ein Baum enth�lt genau 
 * ein Element ohne Vorg�nger; alle anderen Elemente besitzen 
 * genau einen Vorg�nger.</P>
 *
 *  <p><strong>Version:</strong>
 *  <br><dd>1.0, April 2004
 *  <p><strong>Autor:</strong>
 *  <br><dd>Institut f�r Bauinformatik
 *  <br><dd>Universit�t Hannover
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
		struktur.hinzuf�gen(wurzel);
		this.wurzel = wurzel;
	}

	public Baum (Menge<K> knoten, Relation<K,K> kanten) {
		struktur = new SchlichterGraph<K>(knoten, kanten);

		int i = 0;
		for (K k : struktur.knoten()) {
			if (struktur.vorg�nger(k).anzahl() == 0) {
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
	 *	�berpr�ft, ob zwei B�ume gleich sind. Zwei B�ume sind gleich, wenn sie
	 *	die gleichen Knoten in der gleichen Anordnung besitzen.
	 */
	public boolean equals (Object objekt) {
		if (!objekt.getClass().equals(getClass())) 
			return false;
		Baum b = (Baum) objekt;
		return struktur.equals(b.struktur);
	}

	/**
	 *	�berpr�ft, ob ein Knoten im Baum enthalten ist.
	 */
	public boolean enth�lt (K knoten) { 
		return struktur.enth�lt(knoten);
	}

	/**
	 *	Liefert den Elternknoten eines Knoten zur�ck.
	 */
	public K getVorg�nger (K knoten) {
		if (!enth�lt(knoten))
			return null;
		if (knoten.equals(wurzel))
			return null;
		return struktur.vorg�nger(knoten).iterator().next();
	}

	/**
	 *	Liefert die Wurzel des Baums zur�ck.
	 */
	public K getWurzel () {
		 return wurzel;
	}

	/**
	 *	F�gt einen Kindknoten in den Baum ein. Ein Kindknoten kann 
	 *	in den Baum eingef�gt werden, wenn der Vorg�nger bereits 
	 *	im Baum enthalten ist und der Kindknoten nicht.
	 */
	public boolean hinzuf�gen (K vorg�nger,K knoten) {
		// wenn der erste Knoten noch nicht im Baum enthalten ist
		// - den ersten an die Wurzel einh�ngen
		// wenn der zweite Knoten schon im Baum enthalten ist
		// - seinen Ast entnehmen und austragen
//		if (!enth�lt(vorg�nger) || enth�lt(knoten))
//			return false;
		if (!enth�lt(vorg�nger) && !vorg�nger.equals(wurzel)) {
			struktur.hinzuf�gen(vorg�nger);
			struktur.hinzuf�gen(wurzel, vorg�nger);
		}
		if (enth�lt(knoten)) {
			Baum<K> baum = entfernen(knoten);
			hinzuf�gen(vorg�nger,baum);
		} else {
			struktur.hinzuf�gen(knoten);
			struktur.hinzuf�gen(vorg�nger, knoten);
		}
			
		return true;
	}

	/**
	 *	F�gt einen Baum in den Baum ein. Ein Baum kann 
	 *	in den Baum eingef�gt werden, wenn der Vorg�nger bereits 
	 *	im Baum enthalten ist und keiner der neuen Knoten bereits 
	 *	im Baum enthalten ist.
	 */
	public boolean hinzuf�gen (K vorg�nger, Baum<K> baum) {
		for (K k : baum.knoten())
			if(enth�lt(k))
				return false;
		if (!enth�lt(vorg�nger))
			return false;

		// Wurzel hinzuf�gen und  Graphen vereinigen !!!
		hinzuf�gen(vorg�nger, baum.getWurzel());
		Folge<Menge<K>> topSorted = GraphenAufgaben.topologischSortieren(baum, true);
		for (int i = 1; i<topSorted.anzahl(); i++) {
			Menge<K> m = topSorted.get(i);
			for (K k : m)
				hinzuf�gen(baum.getVorg�nger(k), k);
		}
		
		return true; //struktur.hinzuf�gen(knoten) && struktur.hinzuf�gen(vorg�nger, knoten);
	}

	/**
	 *	Entfernt einen Knoten aus dem Baum. Alle Nachfahren des knoten 
	 *	werden ebenfalls aus dem Baum entfernt und der Teilbaum wird 
	 *  zur�ckgeliefert.
	 */
	public Baum<K> entfernen(K knoten) {
		Baum<K> teilbaum = GraphenAufgaben.breitenSuchbaum(this, knoten, true);
		for (K k : teilbaum.knoten())
			struktur.entfernen(k);
		return teilbaum;
	}

	/**
	 *	Liefert einen Knoten mit allen Nachfahren in Form eines Baums zur�ck.
	 */
	public Baum<K> getAst(K knoten) {
		return GraphenAufgaben.breitenSuchbaum(this, knoten, true);
	}

	/**
	 *	Liefert die Ebene eines Knoten als Ganzzahl zur�ck.
	 */
	public int getAbstandWurzel(K knoten) {
		int abstand = 0;		
		while (!wurzel.equals(knoten)) {
			knoten = getVorg�nger(knoten);
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
				ebene.hinzuf�gen(k);
		return ebene;
	}
	
	public Folge<Menge<K>> getEbenen() {
		Folge<Menge<K>> ebenen = new Folge<Menge<K>>();
		Menge<K> m = null;
		int i = 1;
		while ((m = getEbene(i++)).anzahl()>0)
			ebenen.hinzuf�gen(m);
		return ebenen;
	} 
	
	/**
	 * Liefert die Menge aller Knoten, die keinen Nachfolger haben.
	 * @return bl�tter
	 */
	public Menge<K> getBl�tter() {
		Menge<K> bl�tter = new Menge<K>();
		for (K k : knoten())
			if (nachfolger(k).anzahl() == 0)
				bl�tter.hinzuf�gen(k);
		return bl�tter;
	}

	// Graph:
	public String toString () {
		return struktur.toString();
	}
	public int anzahlKanten() {
		return struktur.anzahlKanten();
	}
	public int anzahlVorg�nger(K knoten) {
		return struktur.anzahlVorg�nger(knoten);
	}
	public int anzahlNachfolger(K knoten) {
		return struktur.anzahlNachfolger(knoten);
	}
	public boolean enth�lt(K knoten1, K knoten2) {
		return struktur.enth�lt(knoten1, knoten2);
	}
	public Menge<K> knoten() {
		return struktur.knoten();
	}
	public Relation<K,K> kanten() {
		return struktur.kanten();
	}
	public Menge<K> vorg�nger(K knoten) {
		return struktur.vorg�nger(knoten);
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