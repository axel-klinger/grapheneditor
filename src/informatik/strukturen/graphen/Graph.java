/* Erstellt am 11.10.2004 */
package informatik.strukturen.graphen;

import informatik.strukturen.Menge;
import informatik.strukturen.Relation;

/**
 * <P><B>Graph</B> <I>(engl. graph)</I>: 
 * Ein Graph ist ein Gebilde aus einer Menge von Knoten und 
 * einer Menge von Kanten. Die Knoten repr�sentieren Elemente 
 * eines Modells und die Kanten repr�sentieren Beziehungen 
 * zwischen den Elementen. Die Beziehungen zwischen den Elementen 
 * werden durch eine bin�re Relation beschrieben. Die 
 * Schnittstelle f�r einen Graph deklariert Methoden f�r 
 * die Navigation in der [Struktur].</P>
 *
 * @author Axel
 */
public interface Graph<K> {

	/**
	 * Liefert die Anzahl der Knoten im Graph.
	 * @return anzahl der Knoten
	 */
    public int anzahlKnoten();
    
    /**
     * Liefert die Anzahl der Kanten im Graph.
     * @return anzahl der Kanten
     */
    public int anzahlKanten();
    
    /**
     * Liefert die Anzahl der Vorg�nger eines Knotens. 
     * ( eigentlich �berfl�ssig, da = vorg�nger().anzahl() )
     * @param knoten dessen Vorg�ngeranzahl gesucht ist
     * @return anzahl der Vorg�nger
     */
    public int anzahlVorg�nger(K knoten);

    /**
     * Liefert die Anzahl der Nachfolger eines Knotens. 
     * ( eigentlich �berfl�ssig, da = vorg�nger().anzahl() )
     * @param knoten dessen Nachfolgeranzahl gesucht ist
     * @return anzahl der Vorg�nger
     */
    public int anzahlNachfolger(K knoten);
    public boolean enth�lt(K knoten);
    public boolean enth�lt(K knoten1, K knoten2);
    public Menge<K> knoten();
    public Relation<K,K> kanten();
    public Menge<K> vorg�nger(K knoten);
    public Menge<K> nachfolger(K knoten);
    
    public Menge<K> nachfahren(K knoten);
    public Menge<K> vorfahren(K knoten);
    
    // Menge<Paar<K,K>> ausgangsKanten(Object knoten);
    // Menge<Paar<K,K>> eingangsKanten(Object knoten);
    
}
