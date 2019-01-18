
package informatik.strukturen.graphen.hilfsmittel;

import informatik.strukturen.Paar;
import informatik.strukturen.graphen.SchlichterGraph;

//import java.util.Iterator;
import java.util.Vector;

/**
 * <P><B>GraphenEigenschaften.java</B> <I>(engl. )</I>: Ein GraphenEigenschaften.java ist ... </P>
 * Hier: in Mengenschreibweise. Matrizen s. Skript S. 16
 *
 * @author klinger
 */
public class GraphenEigenschaften<K> {

	private SchlichterGraph<K> graph;
	private SchlichterGraph<K> h�lle;
//	private KnotenTyp[] knotenFeld;
	private Vector<K> knotenFeld;
	private BoolscheMatrix kanten;

	
	public GraphenEigenschaften(SchlichterGraph<K> graph) {
		this.graph = graph; //(SchlichterGraph) graph.clone();
		knotenFeld = knotenVektor();
		kanten = kantenRelation(graph, knotenFeld);
		h�lle  = getTransitiveH�lle();
	}
	
	public void setGraph(SchlichterGraph<K> graph) {
		this.graph = graph; //(SchlichterGraph) graph.clone();
		knotenFeld = knotenVektor();
		kanten = kantenRelation(graph, knotenFeld);
		h�lle  = getTransitiveH�lle();
	}

	//=======================================================================//
	// EIGENSCHAFTEN														 //
	//=======================================================================//
	// ToDo: Herleitung einer Eigenschaft speichern, um sagen zu k�nnen,	 // 
	//		 wenn ein Graph eine Eigenschaft nicht besitzt, WARUM er sie 	 //
	//		 nicht besitzt.													 //
	//=======================================================================//

	/**
	 * Pr�ft ob der Graph refelxiv ist. Ein Graph ist reflexiv, wenn
	 * von <u>jedem</u> Knoten eine Kante zu sich selbst f�hrt. 
	 * (zwei Formeln und ein Bild!)
	 */
	public boolean istReflexiv () {
	    // ALLE x: (x, x) ELEMENT R
	    for (K x : graph.knoten())
			if (!graph.enth�lt(x, x))
				return false;
		return true;	
		
		// I IN R
	    // return getI().in(R);
	}

	/**
	 * Pr�ft ob der Graph antirefelxiv ist. Ein Graph ist antireflexiv, 
	 * wenn von <u>keinem</u> Knoten eine Kante zu sich selbst f�hrt. 
	 * (zwei Formeln und ein Bild!)
	 */
	public boolean istAntiReflexiv () {
	    // ALLE x: (x,x) NOT ELEMENT R  
	    for (K x : graph.knoten())
			if (graph.enth�lt(x, x))
				return false;
		return true;	
		
		// I IN KOMPLEMENT R
		// return getI().in(R.komplement());
	}

	/**
	 * Pr�ft ob der Graph symmetrisch ist. Ein Graph ist symmetrisch, 
	 * wenn er zu <u>jeder</u> Kante (x,y) eine Gegenkante (y,x) besitzt. 
	 * (zwei Formeln und ein Bild!)
	 */
	public boolean istSymmetrisch () {
	    // ALLE x ALLE y: (x,y) ELEMENT R -> (y,x) ELEMENT R
		// ...
		
		// R = R TRANSPONIERT 
	    return graph.equals(graph.transposition());
	}

	/**
	 * Pr�ft ob der Graph asymmetrisch ist. Ein Graph ist asymmetrisch, 
	 * wenn er zu <u>keiner</u> Kante (x,y) eine Gegenkante (y,x) besitzt. 
	 * (zwei Formeln und ein Bild!)
	 */
	public boolean istAsymmetrisch () {
	    // ALLE x ALLE y: (x,y) ELEMENT R -> (y,x) NICHT ELEMENT R
		// ...
		
		// R GESCHNITTEN MIT R TRANSPONIERT = {}
		return graph.durchschnitt(graph.transposition()).anzahlKanten() == 0;
	}

	/**
	 * Pr�ft ob der Graph identitiv ist. Ein Graph ist identitiv, 
	 * wenn die einzigen Doppelkanten, die er besitzt, Schlingen sind. 
	 * (zwei Formeln und ein Bild!)
	 */
	public boolean istIdentitiv () {
	    // ALLE x ALLE y: (x,y) ELEMENT R UND (y,x) ELEMENT R -> x = y
		// ...
		
		// R GESCHNITTEN MIT R TRANSPONIERT IN I
	    return getI().enth�lt(graph.durchschnitt(graph.transposition()));
	}
	
	/**
	 * Pr�ft ob der Graph linear ist. Ein Graph ist linear, 
	 * wenn er zwischen je zwei Knoten mindestens eine 
	 * Kante besitzt. 
	 * (zwei Formeln und ein Bild!)
	 */
	public boolean istLinear () {
	    // ALLE x ALLE y: (x,y) ELEMENT R ODER (y,x) ELEMENT R
	    for (K x : graph.knoten())
	        for (K y : graph.knoten())
	            if (!(graph.enth�lt(x,y) || graph.enth�lt(y,x)))
	                return false;
	    return true;
	    
	    // R VEREINIGT MIT R TRANSPONIERT = E
	    // return R.vereinigung(R).transposition().equals(getE());
	}

	
	/**
	 * Pr�ft ob der Graph konnex ist. Ein Graph ist konnex, 
	 * wenn er zwischen je zwei <u>verschiedenen</u> Knoten mindestens eine 
	 * Kante besitzt. 
	 * (zwei Formeln und ein Bild!)
	 */
	public boolean istKonnex () {
		// ALLE x ALLE y: x != y  ->  (x,y) ELEMENT R ODER (y,x) ELEMENT R
	    for (K x : graph.knoten())
	        for (K y : graph.knoten())
		        if (!x.equals(y))
		            if (!(graph.enth�lt(x,y) || graph.enth�lt(y,x)))
		                return false;
	    return true;
	    
	    // KOMPLEMENT I IN R VEREINIGT MIT R TRANSPONIERT
	    // return getI().komplement().in(R.vereinigung(R).transposition())
	}

	/**
	 * Pr�ft ob der Graph transitiv ist. Ein Graph ist transitiv, 
	 * wenn er zu einem Kantenpaar (x,y) und (y,z) eine Kante (x,z) besitzt. 
	 * (zwei Formeln und ein Bild!)
	 */
	public boolean istTransitiv () {
	    // ALLE x ALLE y ALLE z: (x,y) ELEMENT R UND (y,z) ELEMENT R -> (x,z) ELEMENT R
		// ...
		
		// R GEKETTET MIT R IN R
		// return R.kettung(R).in(R);
		return graph.enth�lt(graph.kettung(graph));
	}	
	
	
	//=======================================================================//
	// WEGE UND ZYKLEN														 //
	//=======================================================================//

	public boolean enth�ltWeg(K knoten1, K knoten2) {
		return h�lle.enth�lt(knoten1, knoten2);
	}

	// -> statt �ber die H�lle: praktisch schneller durch Tiefendurchlauf !!!
	public boolean hatZyklen() {
	    for (K x : graph.knoten())
	        for (K y : graph.vorg�nger(x))
		        if (GraphenAufgaben.tiefenSuchbaum(graph, x, true).enth�lt(y))
		            return true;
		return false;	
	}
	
	public boolean istAzyklisch() {
	    return !hatZyklen();
	}

	public boolean istZyklisch() {
	    for (K x : graph.knoten())
	        for (K y : graph.vorg�nger(x))
				if (!GraphenAufgaben.tiefenSuchbaum(graph, x, true).enth�lt(y))
					return false;
		return true;	
	}
	
	// -> s.o.
//	public boolean hatZyklen(KnotenTyp knoten) {
		// return h�lle.enth�lt(knoten, knoten);
//	    return GraphenAufgaben.tiefenSuchbaum(graph, knoten).enth�lt(knoten);
//	}
	
	//=======================================================================//
	// ZUSAMMENHANG 														 //
	//=======================================================================//

	// (R ODER R^KnotenTyp)* == E
	public boolean istSchwachZusammenh�ngend() {
	    SchlichterGraph<K> g2 = new SchlichterGraph<K>();
	    for (K x : graph.knoten())
	        g2.hinzuf�gen(x);
	    for (Paar<K,K> p : graph.kanten()) {
	        g2.hinzuf�gen(p.eins(), p.zwei());
	        g2.hinzuf�gen(p.zwei(), p.eins());
	    }
	    K knoten = null;
	    if (g2.anzahlKnoten() > 0)
	        knoten = g2.knoten().iterator().next();
	    else
	        return false;
		if (GraphenAufgaben.breitenSuchbaum(g2, knoten, true).anzahlKnoten() == g2.anzahlKnoten()
		 && GraphenAufgaben.breitenSuchbaum(g2, knoten, false).anzahlKnoten() == g2.anzahlKnoten())
		    return true;
		return false;
	}

	/** noch nicht implementiert */
	public boolean istPseudoStrengZusammenh�ngend() {
		// ...	
		return false;
	}

	/** ... */
	public boolean istStrengZusammenh�ngend() {
	    K knoten = null;
	    if (graph.anzahlKnoten() > 0)
	        knoten = graph.knoten().iterator().next();
	    else
	        return false;
		if (GraphenAufgaben.breitenSuchbaum(graph, knoten, true).anzahlKnoten() == graph.anzahlKnoten()
		 && GraphenAufgaben.breitenSuchbaum(graph, knoten, false).anzahlKnoten() == graph.anzahlKnoten())
		    return true;
		return false;
	}

	///////////////////////////////////////////////////////////////////////////
	// HILFSRELATIONEN 														 //
	///////////////////////////////////////////////////////////////////////////
	
	// <R>r = R ODER I
	public SchlichterGraph reflexiveH�lle() {
		BoolscheMatrix k = kanten.oder(I(knotenFeld.size()));			///// knoten raus -> graph.anzahlKnoten()
		return graph(knotenFeld, k);
	}

	// <R>s = R ODER R^KnotenTyp
	public SchlichterGraph<K> symmetrischeH�lle() {
		BoolscheMatrix k = kanten.oder(kanten.transponierte());
		return graph(knotenFeld, k);
	}

	// <R>t = R ODER R^2 ODER R^3 ... R^s			= R+
	public SchlichterGraph<K> transitiveH�lle() {
		return h�lle;
	}

	private SchlichterGraph<K> getTransitiveH�lle() {
		BoolscheMatrix k = kanten.transitiveH�lle();
		return graph(knotenFeld, k);
	}

	// <R>rt = <<R>r>t			= R*
	public SchlichterGraph reflexivTransitiveH�lle() {
		return h�lle.vereinigung(getI());
	}

	// EIGENSCHAFTEN von homogenen Relationen
	private SchlichterGraph<K> getI() {
		SchlichterGraph<K> g = new SchlichterGraph<K>();
		for (K x : graph.knoten()) {
			g.hinzuf�gen(x);
			g.hinzuf�gen(x, x);
		}
		return g;
	}

	private BoolscheMatrix I(int anzahl) {
		BoolscheMatrix m = new BoolscheMatrix(anzahl, anzahl);
		for (int i=0; i<anzahl; i++)
			m.set(i, i, true);
		return m;
	}

//	private BoolscheMatrix E(int anzahl) {
//		BoolscheMatrix m = new BoolscheMatrix(anzahl, anzahl);
//		for (int i=0; i<anzahl; i++)
//			for (int j=0; j<anzahl; j++)
//				m.set(i, j, true);
//		return m;
//	}


////////////////////////////////////////////////////////////////////////////
//  PRIVATE Hilfsfunktionen																								//
////////////////////////////////////////////////////////////////////////////
/*	private KnotenTyp[] knotenVektor() {
		int n=0;
		Object[] knotenFeld = new Object[graph.anzahlKnoten()];
		for (Iterator<KnotenTyp> i = graph.knoten().iterator(); i.hasNext(); n++)
			knotenFeld[n] = i.next();
		return (KnotenTyp[]) knotenFeld;
	}*/
	
	private Vector<K> knotenVektor() {
		Vector<K> kv = new Vector<K>();
		for (K t : graph.knoten())
			kv.add(t);
		return kv;
	}

	private BoolscheMatrix kantenRelation(SchlichterGraph<K> graph, Vector<K> knotenFeld) {
		int anzahl = graph.anzahlKnoten();
		BoolscheMatrix bm = new BoolscheMatrix(anzahl, anzahl);
		for (int i=0; i<anzahl; i++)
			for (int j=0; j<anzahl; j++)
				bm.set(i, j, graph.enth�lt(knotenFeld.get(i), knotenFeld.get(j)));
		return bm;
	}

	private SchlichterGraph<K> graph(Vector<K> knotenFeld, BoolscheMatrix kanten) {
		SchlichterGraph<K> thg = new SchlichterGraph<K>();
		for (K t : knotenFeld)
			thg.hinzuf�gen(t);
		for (K t : knotenFeld)
			for (K s : knotenFeld)
				if (kanten.get(knotenFeld.indexOf(t),knotenFeld.indexOf(s)))
					thg.hinzuf�gen(t,s);
		
/*		for (int n=0; n<knotenFeld.length; n++)
			thg.hinzuf�gen((KnotenTyp)knotenFeld[n]);
		for (int i=0; i<knotenFeld.length; i++)
			for (int j=0; j<knotenFeld.length; j++)
				if (kanten.get(i, j))
					thg.hinzuf�gen((KnotenTyp)knotenFeld[i], (KnotenTyp)knotenFeld[j]);	*/
		return thg;
	}
}