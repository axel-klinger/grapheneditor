package informatik.strukturen.graphen;

import informatik.strukturen.Menge;

/**
 * <P><B>Hierarchisch</B> <I>(engl. hierarchical)</I>: Eine
 * Hierarchische Struktur bietet unterschiedlich grobe/feine
 * Sichten der Struktur.</P>
 * 
 * @author klinger
 */
public interface Hierarchisch<K> {

	/**
	 * Liefert den Elternknoten aus einer hierarchischen Struktur.
	 */
	public K getEltern(K knoten);

	/**
	 * Liefert die Menge der Kindknoten
	 */
	public Menge<K> getKinder(K knoten);
	
	public Graph<K> getEbene(int ebene);
}
