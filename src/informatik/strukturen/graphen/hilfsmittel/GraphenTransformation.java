package informatik.strukturen.graphen.hilfsmittel;

import informatik.strukturen.Paar;
import informatik.strukturen.graphen.BipartiterGraph;
import informatik.strukturen.graphen.SchlichterGraph;

public class GraphenTransformation {

	
	public static <A extends Object, B extends Object> SchlichterGraph knotenGraph(BipartiterGraph<A,B> bg) {
		SchlichterGraph<Object> graph = new SchlichterGraph<Object>();
		for (A o : bg.alleKnoten1())
			graph.hinzufügen(o);
		for (B o : bg.alleKnoten2())
			graph.hinzufügen(o);
		for (Paar<A,B> p : bg.alleKanten12())
			graph.hinzufügen(p.eins(), p.zwei());
		for (Paar<B,A> p : bg.alleKanten21())
			graph.hinzufügen(p.eins(), p.zwei());
		
		return graph;
	}
}
