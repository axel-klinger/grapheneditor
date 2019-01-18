/* Erstellt am 11.10.2004 */
package informatik.strukturen.graphen;

import informatik.strukturen.Menge;
import informatik.strukturen.Relation;

/**
 * <P><B>Graph</B> <I>(engl. graph)</I>: 
 * Ein Graph ist ein Gebilde aus einer Menge von Knoten und 
 * einer Menge von Kanten. Die Knoten repräsentieren Elemente 
 * eines Modells und die Kanten repräsentieren Beziehungen 
 * zwischen den Elementen. Die Beziehungen zwischen den Elementen 
 * werden durch eine binäre Relation beschrieben. Die 
 * Schnittstelle für einen Graph deklariert Methoden für 
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
     * Liefert die Anzahl der Vorgänger eines Knotens. 
     * ( eigentlich überflüssig, da = vorgänger().anzahl() )
     * @param knoten dessen Vorgängeranzahl gesucht ist
     * @return anzahl der Vorgänger
     */
    public int anzahlVorgänger(K knoten);

    /**
     * Liefert die Anzahl der Nachfolger eines Knotens. 
     * ( eigentlich überflüssig, da = vorgänger().anzahl() )
     * @param knoten dessen Nachfolgeranzahl gesucht ist
     * @return anzahl der Vorgänger
     */
    public int anzahlNachfolger(K knoten);
    public boolean enthält(K knoten);
    public boolean enthält(K knoten1, K knoten2);
    public Menge<K> knoten();
    public Relation<K,K> kanten();
    public Menge<K> vorgänger(K knoten);
    public Menge<K> nachfolger(K knoten);
    
    public Menge<K> nachfahren(K knoten);
    public Menge<K> vorfahren(K knoten);
    
    // Menge<Paar<K,K>> ausgangsKanten(Object knoten);
    // Menge<Paar<K,K>> eingangsKanten(Object knoten);
    
}
