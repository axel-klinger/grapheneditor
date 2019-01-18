
package informatik.strukturen.graphen;

/**
 * <P><B>Gewichtet</B> <I>(engl. weighted)</I>: Eine gewichtete
 * Struktur kann zu den Elementen und/oder Beziehungen mit einer 
 * Menge von Gewichten versehen werden. Die Gewichte können über 
 * einheitliche Indizes von jedem Element gesetzt und erfragt 
 * werden.</P>
 * 
 * @author klinger
 *
 * @param <K>
 */
public interface Gewichtet<K> {

	/**
	 *	Setzt ein Gewicht eines Knotens auf einen neuen Wert.
	 */
	public boolean setWert(K knoten, int index, Object wert);

	/**
	 *	Liefert ein Gewicht eines Knotens.
	 */
	public Object getWert(K knoten, int index);

	/**
	 *	Setzt ein Gewicht einer Kante auf einen neuen Wert.
	 */
	public boolean setWert(K knoten1, K knoten2, int index, Object wert);

	/**
	 *	Liefert ein Gewicht einer Kante.
	 */
	public Object getWert(K knoten1, K knoten2, int index);

}
