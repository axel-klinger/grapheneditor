package informatik.strukturen;

import java.util.Iterator;

/**
 *	<P><B>Relation</B> <I>(engl. relation)</I>: Eine Relation 
 *	ist eine Menge von geordneten Paaren. Eine bin�re Relation,
 *	wie sie hier verwendet wird, beschreibt Beziehungen zwischen 
 *	den Elementen einer oder zweier Mengen.</P>
 *
 *	<P><B>Relationenalgebra</B>: </P>
 *
 *  <p><strong>Version:</strong>
 *  <br><dd>1.1, Juni 2004
 *  <p><strong>Autor:</strong>
 *  <br><dd>Axel Klinger
 */
public class Relation<E1,E2> implements Iterable<Paar<E1,E2>> {// extends Menge<Paar<E1,E2>>{
// Eigenschaften (homogen): reflexiv, antireflexiv, symmetrisch, asymmetrisch, identitiv, linear, konvex, transitiv
// Eigenschaften (homogen + heterogen): linkstotal, rechtstotal, linkseindeutig, rechtseindeutig, injektiv, surjektiv, bijektiv

// Weitere Operationen: transponierte, transitiveH�lle, reflexivTransitiveH�lle, ...

    private Menge<Paar<E1,E2>> paare;

    public Relation () { 
        paare = new Menge<Paar<E1,E2>>();
	}

	public boolean enth�lt(E1 eins, E2 zwei) {
		return paare.enth�lt(new Paar<E1,E2>(eins, zwei));
	}        
	
	public boolean enthaelt(Relation R) {
	    return this.paare.enth�lt(R.paare);
	}

	public boolean hinzuf�gen(E1 eins, E2 zwei) {
		return paare.hinzuf�gen(new Paar<E1,E2>(eins, zwei));
	}

	public boolean entfernen(E1 eins, E2 zwei) { 
		return paare.entfernen(new Paar<E1,E2>(eins, zwei));
	}

	public Relation<E2,E1> transposition () {
		Relation<E2,E1> relation = new Relation<E2,E1>();
		for (Paar<E1,E2> p : this)
		    relation.hinzuf�gen(p.zwei(), p.eins());
		return relation;
	}  

	public Relation<E1,E2> kettung(Relation<E1,E2> R) {
		Relation<E1,E2> produkt = new Relation<E1,E2>();
		for (Paar<E1,E2> p1 : this)
		    for (Paar<E1,E2> p2 : R)
		        if (p1.zwei().equals(p2.eins()))
		            produkt.hinzuf�gen(p1.eins(), p2.zwei());
		return produkt;
	}

	public Relation<E1,E2> durchschnitt(Relation<E1,E2> R) {
		Relation<E1,E2> r3 = new Relation<E1,E2>();
		r3.paare = this.paare.durchschnitt(R.paare);
		return r3;
	}
	
	public Relation<E1,E2> vereinigung(Relation<E1,E2> R) {
		Relation<E1,E2> r3 = new Relation<E1,E2>();
		r3.paare = this.paare.vereinigung(R.paare);
		return r3;
	}
	
	public Relation<E1,E2> differenz(Relation<E1,E2> R) {
		Relation<E1,E2> r3 = new Relation<E1,E2>();
		r3.paare = this.paare.differenz(R.paare);
		return r3;
	}

	public int anzahl() {
	    return paare.anzahl();
	}
	
	public void leeren () {
	    paare.leeren();
	}

	public Object clone() {
		Relation<E1,E2> R = new Relation<E1,E2>();
		for (Paar<E1,E2> p : this)
			R.hinzuf�gen(p.eins, p.zwei);
		return R;
	}
	
	////////// ANFANG Homogene Bin�re Relation
	//
	
	/**
	 * Berechnung der transitiven H�lle der Relation durch 
	 * Kettung der Potenzen.
	 */
	public Relation transitiveHuelle() {
		Relation<E1,E2> Rplus = null;
		Relation<E1,E2> Rtemp = this;
		Relation<E1,E2> Rhoch = this;
		while (!Rtemp.equals(Rplus)) {
			Rplus = Rtemp;
			Rhoch = Rhoch.kettung(this);
			Rtemp = Rtemp.vereinigung(Rhoch);
		}
		return Rplus;
	}
	
	/**
	 * Berechnung der transitiven H�lle der Relation durch
	 * Suchb�ume.
	 */
	public Relation transitiveHuelle2() {
		Relation<E1,E2> Rplus = new Relation<E1,E2>();

		return Rplus;
	}
	
	/**
	 * Berechnung der transitiven H�lle der Relation durch 
	 * Warshall-Algorithmus
	 */
	public Relation transitiveHuelle3() {
		Relation<E1,E2> Rplus = new Relation<E1,E2>();
		/*
//		calculate the transitive hull
		for (int via = 0; via < matrix.length; via++)
			for (int from = 0; from < matrix.length; from++)
				for (int to = 0; to < matrix.length; to++)
					if (!hull[from][to] && hull[from][via] && hull[via][to])
						hull[from][to] = true;
		*/
		
		return Rplus;
	}
	
	
	
	public Relation reflexivTransitiveHuelle(Menge m) {
		return this.reflexiveHuelle(m).transitiveHuelle();
	}

	public Relation reflexiveHuelle(Menge m) {
		return this.vereinigung(I(m));
	}
	
	public static Relation I(Menge m) {
		Relation I = new Relation();
		for (Object o : m)
			I.hinzuf�gen(o,o);
		return I;
	}
	
	public static Relation E(Menge m) {
		Relation E = new Relation();
		for (Object x : m)
			for (Object y : m)
				E.hinzuf�gen(x,y);
		return E;
	}
	
	//
	////////// ENDE Homogene Bin�re Relationen
	
	public boolean equals(Object o) {
	    if (!(o instanceof Relation))
	        return false;
	    Relation r = (Relation) o;
	    return paare.equals(r.paare);
	}
	
    public String toString() {
        return paare.toString();
    }

    public Iterator<Paar<E1,E2>> iterator() {
        return (Iterator<Paar<E1,E2>>) paare.iterator();
    }
}

// transitive H�lle usw.? -> s. SchlichterGraph (homogene Relation!)