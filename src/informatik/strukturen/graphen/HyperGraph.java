package informatik.strukturen.graphen;

import informatik.strukturen.Menge;

/**
 * <P><B>Hypergraph</B> <I>(engl. hyper graph)</I>: Ein 
 * Hypergraph ist ein spezieller bipartiter Graph, dessen 
 * zweite Knotenmenge die Beziehungen
 * zwischen je mehreren Knoten der ersten Menge beschreibt. Eine Kante im Hypergraph
 * ist ungerichtet und verbindet mehrere Knoten miteinander.
 *
 * @author klinger
 */
public class HyperGraph<K> extends BipartiterGraph<K,HyperKante<K>> {

}

class HyperKante<E> {
	Menge<E> teilnehmer;
}