package informatik.strukturen.graphen;

import informatik.strukturen.Menge;

/**
 * <P><B>Multigraph</B> <I>(engl. multi graph)</I>: Ein Multigraph ist 
 * ein bipartiter Graph, dessen zweite Knotenmenge die Beziehungen
 * zwischen je zwei Knoten der ersten Menge beschreibt. Eine Kante im Multigraph
 * wird durch ein Objekt beschrieben. Im Multigraph können parallele oder
 * partielle Kanten existieren.
 * 
 * @author klinger
 */
public class MultiGraph<K> extends BipartiterGraph<K,MultiKante<K>> {

}

class MultiKante<E> {
	Menge<E> teilnehmer;
}