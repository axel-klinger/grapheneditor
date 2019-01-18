 
package informatik.strukturen.graphen.wege;

//import java.util.Arrays;
import java.util.Iterator;

import informatik.strukturen.Paar;
import informatik.strukturen.graphen.GewichteterGraph;


/**
 * @author klinger
 */
public class WegAlgebra {
    
    
    // Boolesche Wegalgebra
    /**
     * Liefert TRUE, wenn ein Weg von start zu ziel in G existiert, sonst FALSE.
     */
	public static Boolean existiertWeg(GewichteterGraph G, Object start, Object ziel) {

	    BooleanOperator op = new BooleanOperator() {
	        	public Object getNull() { 
	            	return new Boolean(false); 	}
	        	public Object getEins() {
	        	    return new Boolean(true);	}
	        	public Object hülle(Object o) {
	        	    return getEins();        	}
		    	public Object vereinigung(Object o1, Object o2) {	
		    	    return oder( o1, o2 );	}
		    	public Object kettung(Object o1, Object o2) 	{	
		    	    return und( o1, o2 ); 	}
			};

		return (Boolean) berechnen(G, start, ziel, op, 0);		
	}

    
    // Reelle Wegalgebra
	/**
	 * Liefert die minimale Distanz zwischen start und ziel in G.
	 */
	public static Double minimaleWeglänge(GewichteterGraph G, Object start, Object ziel) {

	    DoubleOperator op = new DoubleOperator() {
	        	public Object getNull() { 
	            	return new Double(Double.POSITIVE_INFINITY); 	}
	        	public Object getEins() {
	        	    return new Double(0.0);	}
	        	public Object hülle(Object o) {
	        	    return getEins();        	}
		    	public Object vereinigung(Object o1, Object o2) {	
		    	    return min( o1, o2 );	}
		    	public Object kettung(Object o1, Object o2) 	{	
		    	    return plus( o1, o2 ); 	}
			};

    	Object[] knoten = getKnotenVektor(G);

	    GleichungsLöser gl = new GleichungsLöser(op);
	    Object[][] A = getBewertungsMatrix(G, knoten, op, 0);
	    Object[]   b = getPunktVektor(ziel, knoten, op);
	    Object[]   x = gl.Jacobi(A, b);					// <- hier das Verfahren

	    for (int i = 0; i<knoten.length; i++)
	        if (knoten[i].equals(start))
	            return (Double) x[i];					// <- hier der Typ
		return new Double(0.0);
	}

	/**
	 * Liefert die maximale Distanz zwischen start und ziel in G.
	 * Scheitert an Zyklen. Noch keine Kontrolle auf Zyklen!!!
	 */
	public static Double maximaleWeglänge(GewichteterGraph G, Object start, Object ziel) {

	    DoubleOperator op = new DoubleOperator() {
	        	public Object getNull() { 
	            	return new Double(Double.NEGATIVE_INFINITY); 	}
	        	public Object getEins() {
	        	    return new Double(0.0);	}
	        	public Object hülle(Object o) {
	        	    return getEins();        	}
		    	public Object vereinigung(Object o1, Object o2) {	
		    	    return max( o1, o2 );	}
		    	public Object kettung(Object o1, Object o2) 	{	
		    	    return plus( o1, o2 ); 	}
			};

		return (Double) berechnen(G, start, ziel, op, 0);		
	}

	/**
	 * Liefert die maximale Zuverlässigkeit zwischen start und ziel in G. 
	 */
	public static Double maximaleWegzuverlässigkeit(GewichteterGraph G, Object start, Object ziel) {

	    DoubleOperator op = new DoubleOperator() {
	        	public Object getNull() { 
	            	return new Double(0.0); 	}
	        	public Object getEins() {
	        	    return new Double(1.0);	}
	        	public Object hülle(Object o) {
	        	    return getEins();        	}
		    	public Object vereinigung(Object o1, Object o2) {	
		    	    return max( o1, o2 );	}
		    	public Object kettung(Object o1, Object o2) 	{	
		    	    return mal( o1, o2 ); 	}
			};

		return (Double) berechnen(G, start, ziel, op, 0);		
	}

	/**
	 * Liefert die maximale Kapazität zwischen start und ziel in G. 
	 */
	public static Double maximaleWegkapazität(GewichteterGraph G, Object start, Object ziel) {

	    DoubleOperator op = new DoubleOperator() {
	        	public Object getNull() { 
	        	    return new Double(0.0);	}
	        	public Object getEins() {
	            	return new Double(Double.POSITIVE_INFINITY); 	}
	        	public Object hülle(Object o) {
	        	    return getEins();        	}
		    	public Object vereinigung(Object o1, Object o2) {	
		    	    return max( o1, o2 );	}
		    	public Object kettung(Object o1, Object o2) 	{	
		    	    return min( o1, o2 ); 	}
			};

		return (Double) berechnen(G, start, ziel, op, 0);		
	}
	
	// Literale Wegalgebra
	
	// ...
	
	///////////////////////////////////////////////////////////
	// 2-stellige Wegalgebra
	///////////////////////////////////////////////////////////

	// Das Ergebnis ist vom gleichen Typ wie die Bewertung. Die 
	// Bewertung ist vom Typ Paar<WegMenge, Wert>
	/**
	 * Liefert die Wege mit minimaler Länge als Paar aus einer WegMenge 
	 * und dem minimalen Wert zurück.
	 */
	public static Paar minimaleWege(GewichteterGraph G, Object start, Object ziel) {

	    WegMengenOperator wmop = new WegMengenOperator() {
	        	public Object getNull() { 
	        	    return new WegMenge();	}
	        	public Object getEins() {
	            	return new WegMenge(new Weg("Theta")); 	}
	        	public Object hülle(Object o) {
	        	    return getEins();        	}
		    	public Object vereinigung(Object o1, Object o2) {	
		    	    return plus( o1, o2 );	}
		    	public Object kettung(Object o1, Object o2) 	{	
		    	    return mal( o1, o2 ); 	}
			};
    
	    DoubleOperator dop = new DoubleOperator() {
	        	public Object getNull() { 
	            	return new Double(Double.POSITIVE_INFINITY); 	}
	        	public Object getEins() {	
	        	    return new Double(0.0);	}
	        	public Object hülle(Object o) {						
	        	    return getEins();        	}
		    	public Object vereinigung(Object o1, Object o2) {	
		    	    return min( o1, o2 );	}
		    	public Object kettung(Object o1, Object o2) 	{	
		    	    return plus( o1, o2 ); 	}
			};

	    Operator op = new WA2Operator(wmop,dop); 
	        			
		return (Paar) berechnen(G, start, ziel, op, 1);		
	}
	
	// Hilfsfunktionen
 	private static Object berechnen(GewichteterGraph G, Object start, Object ziel, Operator op, int bewertung) {			
		Object[] knoten = getKnotenVektor(G);
	
	    GleichungsLöser gl = new GleichungsLöser(op);
	    Object[][] A = getBewertungsMatrix(G, knoten, op, bewertung);
	    Object[]   b = getPunktVektor(ziel, knoten, op);
	    Object[]   x = gl.Jacobi(A, b);			// <- hier das Verfahren
//	    print(A);	print(b);	print(x);
	    for (int i = 0; i<knoten.length; i++)
	        if (knoten[i].equals(start))
	            return x[i];					// <- hier der Typ
		return new Object();
	}

	private static void print(Object[] X) {	
	    for (int i = 0; i<X.length; i++) {	
	        System.out.print(X[i] + "\t");	
	    }
	    System.out.println("");
	}
	private static void print(Object[][] X) {	
	    for (int i = 0; i<X.length; i++) {	
	        for (int j = 0; j<X[0].length; j++) {	
	            System.out.print(X[i][j] + "\t");	
	        }
	        System.out.println("");
	    }
	    System.out.println("");
	}
 	
	private static Object[][] getBewertungsMatrix(GewichteterGraph G, Object[] knoten, Operator op, int bewertung) {
	    int n = G.anzahlKnoten();
	    Object[][] A = new Object[n][n];
	    for (int i = 0; i<n; i++)
	        for (int j = 0; j<n; j++)
	            if (G.enthält(knoten[i], knoten[j]))
	                A[i][j] = G.getWert(knoten[i], knoten[j], bewertung);
	            else
	                A[i][j] = op.getNull();
	    return A;
	}
	
	private static Object[] getPunktVektor(Object k, Object[] knoten, Operator op) {
	    Object[] b = new Object[knoten.length];
	    for (int i = 0; i<knoten.length; i++)
	        if (knoten[i].equals(k))
	            b[i] = op.getEins();
	        else
	            b[i] = op.getNull();
	    return b;
	}
	
	private static Object[] getKnotenVektor(GewichteterGraph G) {
	    Object[] knoten = new Object[G.anzahlKnoten()];
	    int k = 0;
	    for (Iterator i = G.knoten().iterator(); i.hasNext(); k++) {
	        knoten[k] = i.next();
	    }
	    return knoten;
	}
}

class WA2Operator extends Operator {

	public Object getNull() { 
    	return new Paar(wop.getNull(), bop.getNull()); 	}
	public Object getEins() {
	    return new Paar(wop.getEins(), bop.getEins());	}
	public Object hülle(Object o) {
	    return getEins();        	}
    
    private Operator wop;
    private Operator bop;		// allgemeiner: ein Feld von Operatoren
    							// für ein Feld von Bewertungen				-> n-stellige Wegalgebra
    
    public WA2Operator(Operator wop, Operator bop) {
        this.wop = wop;
        this.bop = bop;
    }

    public Object kettung(Object o1, Object o2) {
        if (!(o1 instanceof Paar) || !(o2 instanceof Paar))
            return null;
        Paar p1 = (Paar) o1;
        Paar p2 = (Paar) o2;
        return new Paar(wop.kettung(p1.eins(),p2.eins()),bop.kettung(p1.zwei(), p2.zwei()));
    }

    public Object vereinigung(Object o1, Object o2) {
        if (!(o1 instanceof Paar) || !(o2 instanceof Paar))
            return null;
        Paar p1 = (Paar) o1;
        Paar p2 = (Paar) o2;
        
        // allgemein für Reelle Wegalgebra
        if (bop.vereinigung(p1.zwei(), p2.zwei()).equals(p1.zwei()))
            return p1;
        if (bop.vereinigung(p1.zwei(), p2.zwei()).equals(p2.zwei()))
            return p2;
        
        return new Paar(wop.vereinigung(p1.eins(),p2.eins()),bop.vereinigung(p1.zwei(), p2.zwei()));
    }
}
