
package informatik.strukturen.graphen;

import informatik.strukturen.Relation;
import informatik.strukturen.Menge;
import informatik.strukturen.Paar;

import java.util.HashMap;

/**
 *	<P><B>Bipartiter Graph</B> <I>(engl. bipartite graph)</I>: 
 *	Ein BipartiterGraph beschreibt die Beziehungen zwischen den 
 *	Elementen zweier Mengen. 
 *
 *	<CENTER><IMG SRC="imagesGraph/Bipartit.png" BORDER=0 ALT="bipartiter Graph"></CENTER>
 */
public class BipartiterGraph<K1,K2> implements Graph<Object> { 

	// boolean gerichtet -> bezieht sich auf das einf�gen und entfernen einer kante!!!

	private HashMap<K1,BiKnoten<K1,K2>> knoten1;
	private HashMap<K2,BiKnoten<K2,K1>> knoten2;

	public BipartiterGraph () {
		knoten1 = new HashMap<K1,BiKnoten<K1,K2>>(); 
		knoten2 = new HashMap<K2,BiKnoten<K2,K1>>();
	}

	public BipartiterGraph(Menge<K1> X, Menge<K2> Y, Relation<K1,K2> XY, Relation<K2,K1> YX) {
		knoten1 = new HashMap<K1,BiKnoten<K1,K2>>(); 
		knoten2 = new HashMap<K2,BiKnoten<K2,K1>>();
		for (K1 x : X)
			hinzuf�gen1(x);
		for (K2 y : Y)
			hinzuf�gen2(y);
		for (Paar<K1,K2> p : XY)
			hinzuf�gen12(p.eins(), p.zwei());
		for (Paar<K2,K1> p : YX)
			hinzuf�gen21(p.eins(), p.zwei());
	}	
	
	public synchronized Object clone () {
	    BipartiterGraph<K1,K2> g2 = new BipartiterGraph<K1,K2>();
	    for (BiKnoten<K1,K2> k : knoten1.values())
	        g2.knoten1.put(k.wert,k);
	    for (BiKnoten<K2,K1> k : knoten2.values())
	        g2.knoten2.put(k.wert,k);
	    return g2;
	}

	private BiKnoten<K1,K2> getKnoten1(K1 k) {	
	    return knoten1.get(k);	
	}
	
	private BiKnoten<K2,K1> getKnoten2(K2 k) {	
	    return knoten2.get(k);	
	}
	
	public int anzahlKnoten1() {	
	    return knoten1.size();	
	}

	public int anzahlKnoten2() { 	
	    return knoten2.size();	
	}

	public int anzahlKnoten() {	    
	    return anzahlKnoten1() + anzahlKnoten2();	
	}
	
	public int anzahlKanten12() {
	    return kantenRelation12().anzahl();
	}
	
	public int anzahlKanten21() {
	    return kantenRelation21().anzahl();
	}
	
	public int anzahlKanten() { 
		return anzahlKanten12() + anzahlKanten21(); 
	}

	public int anzahlVorg�nger(Object k) {	// indegree
		if (enth�ltMenge1(k))
		    return getKnoten1((K1) k).vorg�nger.anzahl();
		if (enth�ltMenge2(k))
		    return getKnoten2((K2) k).vorg�nger.anzahl();
	    return 0;
  }

	public int anzahlNachfolger(Object k) {	// outdegree
		if (enth�ltMenge1(k))
		    return getKnoten1((K1) k).nachfolger.anzahl();
		if (enth�ltMenge2(k))
		    return getKnoten2((K2) k).nachfolger.anzahl();
	    return 0;
	}

	public boolean enth�lt(Object k) {
		return enth�ltMenge1(k) || enth�ltMenge2(k);
	}
	
	private boolean enth�ltMenge1(Object k) {
	    return knoten1.containsKey(k);
	}    
  
	private boolean enth�ltMenge2(Object k) {
	    return knoten2.containsKey(k);
	}    
  
	public boolean enth�lt(Object k1, Object k2) {			// edgemember
		return enth�ltRelation12(k1, k2) || enth�ltRelation21(k1, k2);
	}

	public boolean enth�ltRelation12(Object k1, Object k2) {
	    if (!enth�ltMenge1(k1) || !enth�ltMenge2(k2))
	        return false;
	    return getKnoten1((K1) k1).nachfolger.enth�lt(getKnoten2((K2) k2));
	}
	
	public boolean enth�ltRelation21(Object k1, Object k2) {
	    if (!enth�ltMenge2(k1) || !enth�ltMenge1(k2))
	        return false;
	    return getKnoten2((K2) k1).nachfolger.enth�lt(getKnoten1((K1) k2));
	}
	
	public boolean enth�lt(BipartiterGraph BG) {
	    return (knotenMenge1().enth�lt(BG.knotenMenge1()))
	    	&& (knotenMenge2().enth�lt(BG.knotenMenge2()))
	    	&& (kantenRelation12().enthaelt(BG.kantenRelation12()))
	    	&& (kantenRelation21().enthaelt(BG.kantenRelation21()));
	}	
  
	public boolean hinzuf�gen1(K1 k) {	// addNode
		if (enth�lt(k))
			return false;
		knoten1.put(k, new BiKnoten<K1,K2>(k));
		return true;
	}

	public boolean hinzuf�gen2(K2 k) {	// addNode
		if (enth�lt(k))
			return false;
		knoten2.put(k, new BiKnoten<K2,K1>(k));
		return true;
	}

	public boolean hinzuf�gen12(K1 knoten1, K2 knoten2) {
	    BiKnoten<K1,K2> k1 = getKnoten1(knoten1);                          // Item zu u suchen 
	    BiKnoten<K2,K1> k2 = getKnoten2(knoten2);                      // ggf. Item zu v suchen
	    if (k1!=null && k2!=null) {
	        if (k1.nachfolger.enth�lt(k2)) 
	            return false;
	        k1.nachfolger.hinzuf�gen(k2); 
	        k2.vorg�nger.hinzuf�gen(k1); 
	        return true;
	    }
	    return false;
	}

	public boolean hinzuf�gen21(K2 knoten1, K1 knoten2) {
	    BiKnoten<K1,K2> k2 = getKnoten1(knoten2);                          // Item zu u suchen 
	    BiKnoten<K2,K1> k1 = getKnoten2(knoten1);                      // ggf. Item zu v suchen
	    if (k1!=null && k2!=null) {
	        if (k1.nachfolger.enth�lt(k2)) 
	            return false;
	        k1.nachfolger.hinzuf�gen(k2); 
	        k2.vorg�nger.hinzuf�gen(k1); 
	        return true;
	    }
	    return false;
	}

	public boolean entfernen(Object k) {
		return entfernen1(k) || entfernen2(k);
	}

	private boolean entfernen1(Object knoten) {
	    if (!enth�ltMenge1(knoten))
	        return false;
		BiKnoten<K1,K2> k = getKnoten1((K1) knoten);
		if (k != null) {
			Menge<BiKnoten<K2,K1>> mn = new Menge<BiKnoten<K2,K1>>();
			for (BiKnoten<K2,K1> n : k.nachfolger)
				mn.hinzuf�gen(n);
		    for (BiKnoten<K2,K1> n : mn) {
				n.vorg�nger.entfernen(k);
				k.nachfolger.entfernen(n);
		    }
			Menge<BiKnoten<K2,K1>> mv = new Menge<BiKnoten<K2,K1>>();
			for (BiKnoten<K2,K1> n : k.vorg�nger)
				mv.hinzuf�gen(n);
		    for (BiKnoten<K2,K1> v : mv) {
				v.nachfolger.entfernen(k);
				k.vorg�nger.entfernen(v);
		    }
		    knoten1.remove(knoten);
			return true;
		}
		return false;
	}
	
	private boolean entfernen2(Object knoten) {
	    if (!enth�ltMenge2(knoten))
	        return false;
		BiKnoten<K2,K1> k = getKnoten2((K2) knoten);
		if (k != null) {
			Menge<BiKnoten<K1,K2>> mn = new Menge<BiKnoten<K1,K2>>();
			for (BiKnoten<K1,K2> n : k.nachfolger)
				mn.hinzuf�gen(n);
		    for (BiKnoten<K1,K2> n : mn) {
				n.vorg�nger.entfernen(k);
				k.nachfolger.entfernen(n);
		    }
			Menge<BiKnoten<K1,K2>> mv = new Menge<BiKnoten<K1,K2>>();
			for (BiKnoten<K1,K2> n : k.vorg�nger)
				mv.hinzuf�gen(n);
		    for (BiKnoten<K1,K2> v : mv) {
				v.nachfolger.entfernen(k);
				k.vorg�nger.entfernen(v);
		    }
		    knoten2.remove(knoten);		
			return true;
		}
		return false;
	}
	
	public boolean entfernen(Object k1, Object k2) {
		return entfernen12(k1, k2) || entfernen21(k1, k2);
	}
	
	private boolean entfernen12(Object knoten1, Object knoten2) {
	    if (!enth�ltMenge1(knoten1) || !enth�ltMenge2(knoten2))
	        return false;
		BiKnoten<K1,K2> k1 = getKnoten1((K1)knoten1);
		BiKnoten<K2,K1> k2 = getKnoten2((K2)knoten2);
		for (BiKnoten<K2,K1> k : k1.nachfolger) {
			if (knoten2.equals(k.wert)) {
				k1.nachfolger.entfernen(k);
				k.vorg�nger.entfernen(k1);
				return true;
			}
		}
		return false;
	}
	
	private boolean entfernen21(Object knoten1, Object knoten2) {
	    if (!enth�ltMenge2(knoten1) || !enth�ltMenge1(knoten2))
	        return false;
		BiKnoten<K2,K1> k1 = getKnoten2((K2)knoten1);
		BiKnoten<K1,K2> k2 = getKnoten1((K1)knoten2);
		for (BiKnoten<K1,K2> k : k1.nachfolger) {
			if (knoten2.equals(k.wert)) {
				k1.nachfolger.entfernen(k);
				k.vorg�nger.entfernen(k1);
				return true;
			}
		}
		return false;
	}
	
	
	
	private Menge<K1> knotenMenge1() {
	    Menge<K1> m = new Menge<K1>();
	    for (K1 k : knoten1.keySet())
	        m.hinzuf�gen(k);
	    return m;
	}
	
	private Menge<K2> knotenMenge2() {
	    Menge<K2> m = new Menge<K2>();
	    for (K2 k : knoten2.keySet())
	        m.hinzuf�gen(k);
	    return m;
	}
	
	private Relation<K1,K2> kantenRelation12() {
	    Relation<K1,K2> r = new Relation<K1,K2>();
	    for (BiKnoten<K1,K2> k1 : knoten1.values())
	        for (BiKnoten<K2,K1> k2 : k1.nachfolger)
	            r.hinzuf�gen(k1.wert, k2.wert);
	    return r;
	}
	
	private Relation<K2,K1> kantenRelation21() {
	    Relation<K2,K1> r = new Relation<K2,K1>();
	    for (BiKnoten<K2,K1> k1 : knoten2.values())
	        for (BiKnoten<K1,K2> k2 : k1.nachfolger)
	            r.hinzuf�gen(k1.wert, k2.wert);
	    return r;
	}
	
	public synchronized boolean equals (Object objekt) { 
	    if (!(objekt instanceof BipartiterGraph)) 
	        return false;
	    BipartiterGraph graph = (BipartiterGraph) objekt;
	    return this.enth�lt(graph) && graph.enth�lt(this);
	}

	public BipartiterGraph<K1,K2> transposition () {
	    return new BipartiterGraph<K1,K2>(knotenMenge1(), knotenMenge2(), 
	            				   kantenRelation21().transposition(),
	            				   kantenRelation12().transposition());
  } 

	public BipartiterGraph<K1,K2> durchschnitt(BipartiterGraph<K1,K2> BG) {
	    Menge<K1>	 	X  = (Menge<K1>)	   this.knotenMenge1().durchschnitt(BG.knotenMenge1());
	    Menge<K2>	 	Y  = (Menge<K2>)	   this.knotenMenge2().durchschnitt(BG.knotenMenge2());
	    Relation<K1,K2> XY = (Relation<K1,K2>) this.kantenRelation12().durchschnitt(BG.kantenRelation12());
	    Relation<K2,K1> YX = (Relation<K2,K1>) this.kantenRelation21().durchschnitt(BG.kantenRelation21());
	    return new BipartiterGraph<K1,K2>(X, Y, XY, YX);
	}
 
	public BipartiterGraph<K1,K2> vereinigung(BipartiterGraph<K1,K2> BG) {
	    Menge<K1>	 	X  = (Menge<K1>)	   this.knotenMenge1().vereinigung(BG.knotenMenge1());
	    Menge<K2>	 	Y  = (Menge<K2>)	   this.knotenMenge2().vereinigung(BG.knotenMenge2());
	    Relation<K1,K2> XY = (Relation<K1,K2>) this.kantenRelation12().vereinigung(BG.kantenRelation12());
	    Relation<K2,K1> YX = (Relation<K2,K1>) this.kantenRelation21().vereinigung(BG.kantenRelation21());
	    return new BipartiterGraph<K1,K2>(X, Y, XY, YX);
	}
 
	public BipartiterGraph<K1,K2> minus(BipartiterGraph<K1,K2> BG) {
		BipartiterGraph<K1,K2> differenz = new BipartiterGraph<K1,K2>();
	    for (BiKnoten<K1,K2> k : knoten1.values())
	        differenz.knoten1.put(k.wert,k);
	    for (BiKnoten<K2,K1> k : knoten2.values())
	        differenz.knoten2.put(k.wert,k);
		
	    for (BiKnoten<K1,K2> k : BG.knoten1.values())
			differenz.entfernen1(k);
	    for (BiKnoten<K2,K1> k : BG.knoten2.values())
			differenz.entfernen2(k);
		return differenz;
	}

	public BipartiterGraph mal(BipartiterGraph BG) {
		BipartiterGraph produkt = new BipartiterGraph(); 
/*  Menge a = new Menge();
    Menge b = new Menge();
    Paar  p1, p2;
    if(anzahlKnoten() != graph.anzahlKnoten()) 
			return null;
    for (Iterator i = alleKnoten(); i.hasNext(); ) {
			Object wert = i.next(); 
			a.hinzuf�gen(wert); 
			produkt.hinzuf�gen(wert);	
		}
    for (Iterator i = graph.alleKnoten(); i.hasNext(); )
      b.hinzuf�gen(i.next());   
    if (!a.equals(b)) 
			return null;
    for (Iterator i = alleKanten(); i.hasNext(); ) {
			p1 = (Paar) i.next(); 
		  for (Iterator j = graph.alleKanten(); j.hasNext(); ) {
				p2 = (Paar) j.next();
        if (p1.zwei().equals(p2.eins()))
          produkt.hinzuf�gen(p1.eins(), p2.zwei());
      }
    }		*/
    return produkt;
	}

	public Menge<Object> knoten() {
//	    HashMap<Object,Object> knoten = new HashMap<Object,Object>();
//	    knoten.putAll(knoten1);
//	    knoten.putAll(knoten2);
	    Menge<Object> knoten = new Menge<Object>();
	    for(Object key : knoten1.keySet())
	        knoten.hinzuf�gen(key);
	    for(Object key : knoten2.keySet())
	        knoten.hinzuf�gen(key);
	    return knoten;
	}
	
	public Menge<K1> alleKnoten1() {
	    Menge<K1> knoten = new Menge<K1>();
	    for(K1 key : knoten1.keySet())
	        knoten.hinzuf�gen(key);
		return knoten; 
	}

	public Menge<K2> alleKnoten2() {
	    Menge<K2> knoten = new Menge<K2>();
	    for(K2 key : knoten2.keySet())
	        knoten.hinzuf�gen(key);
		return knoten; 
	}

	public Relation<Object,Object> kanten() {
	    Relation<Object,Object> kanten = new Relation<Object,Object>();
	    for (Paar kante : alleKanten12())
	        kanten.hinzuf�gen(kante.eins(), kante.zwei());
	    for (Paar kante : alleKanten21())
	        kanten.hinzuf�gen(kante.eins(), kante.zwei());
	    return kanten;
	}
	
	public Relation<K1,K2> alleKanten12() {
		return kantenRelation12(); 
	}

	public Relation<K2,K1> alleKanten21() {
		return kantenRelation21(); 
	}

	// Iterator K1 bzw K2 statt BiKnoten...
	public Menge<Object> vorg�nger(Object knoten) {
		if (enth�ltMenge1(knoten)) {
		    Menge<Object> m = new Menge<Object>();
		    for (BiKnoten<K2,K1> k : getKnoten1((K1)knoten).vorg�nger)
		        m.hinzuf�gen(k.wert);
		    return m;
		}
		if (enth�ltMenge2(knoten)) {
		    Menge<Object> m = new Menge<Object>();
		    for (BiKnoten<K1,K2> k : getKnoten2((K2)knoten).vorg�nger)
		        m.hinzuf�gen(k.wert);
		    return m;
		}
	    return new Menge<Object>();
	}

	public Menge<Object> nachfolger(Object knoten) {
		if (enth�ltMenge1(knoten)) {
		    Menge<Object> m = new Menge<Object>();
		    for (BiKnoten<K2,K1> k : getKnoten1((K1)knoten).nachfolger)
		        m.hinzuf�gen(k.wert);
		    return m;
		}
		if (enth�ltMenge2(knoten)) {
		    Menge<Object> m = new Menge<Object>();
		    for (BiKnoten<K1,K2> k : getKnoten2((K2)knoten).nachfolger)
		        m.hinzuf�gen(k.wert);
		    return m;
		}
	    return new Menge<Object>();
	}

	public String toString () {
		return "{" + knotenMenge1() + "," + knotenMenge2() + ";" 
				   + kantenRelation12() + "," + kantenRelation21() + "}";
	}

	public Menge<Object> nachfahren(Object knoten) {
		// TODO Nachfahren im BG
		return null;
	}

	public Menge<Object> vorfahren(Object knoten) {
		// TODO Vorfahren im BG
		return null;
	}
}

////////////////////////////////////////////////////////////////////////////
//KLASSE BiKnoten																													//
////////////////////////////////////////////////////////////////////////////
class BiKnoten<T1,T2> {
	T1 	wert;
	Menge<BiKnoten<T2,T1>>  	vorg�nger;
	Menge<BiKnoten<T2,T1>>	nachfolger;

	public BiKnoten(T1 knoten) {
		wert	   = knoten;
		vorg�nger  = new Menge<BiKnoten<T2,T1>>();
		nachfolger = new Menge<BiKnoten<T2,T1>>();
	}

	public boolean equals(Object objekt) {
		if (!(objekt instanceof BiKnoten))
			return false;
		BiKnoten k = (BiKnoten) objekt;
		return wert.equals(k.wert);
	}

	public String toString() {
		return wert.toString();
	}

	public int hashCode() {
    int hash = 7;
	  hash = hash * 31 + ((wert == null) ? 0 : wert.hashCode());
    return hash;
	}
}

// 402	// 582	// 734	// 812