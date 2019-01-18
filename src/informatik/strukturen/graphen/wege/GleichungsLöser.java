
package informatik.strukturen.graphen.wege;

import java.util.*;

//		Beispiel: kürzeste Wege zu Knoten4
//
//		x = A o x u b
//
//		|x1|	|n n 2 n n|   |x1|   |n|		mit n = oo
//		|x2|	|1 n n 3 n|   |x2|   |n|			e = 0
//		|x3| = 	|n 4 n n 5| o |x3| u |n|			â = 0
//		|x4|	|n n 3 n n|   |x4|   |e|			o = +
//		|x5|	|n n n 2 n|   |x5|   |n|			u = min

/**
 *	<P><B>Gleichungslöser</B> <I>(engl. ...)</I>: Ein gleichungslöser für 
 *	Wegaufgaben liefert zu einem Gleichungssystem x = A o x u b den 
 *	Lösungsvektor x.</P>
 *	
 *  <p><strong>Version:</strong>
 *  <br><dd>1.1, Juni 2004
 *  <p><strong>Autor:</strong>
 *  <br><dd>Axel Klinger
 */
public class GleichungsLöser {

    private Operator op;
    
    public GleichungsLöser(Operator op) {
        this.op = op;
    }
    
	// Nur zum Vergleich auch mal x=A*b
    public Object[] kleinsteLösung(Object[][] A, Object[] b) {
        Object[] x = new Object[b.length];
        x = kettung(hülle(A),b);
        return x;
    }

	// Direkte Lösungen
	public Object[] eliminationGauß (Object[][] A, Object[] b) {
		int n = b.length;
		for (int k=0; k<n; k++) {
			b[k] = op.kettung(op.hülle(A[k][k]), b[k]);
			for (int j=k+1; j<n; j++) {
				A[k][j] = op.kettung(op.hülle(A[k][k]), A[k][j]);
				b[j] = op.vereinigung(b[j], op.kettung(A[j][k], b[k]));
				for (int i=k+1; i<n; i++) {
					A[i][j] = op.vereinigung(A[i][j], op.kettung(A[i][k], A[k][j]));
				}
			}
		}
		return rückwärtsAuflösen(A,b);
	}
	
	/**
	 * Reduktionsmethode von Dijkstra.
	 * Die Funktion löst ein gegebenes Gleichungssystem x = Ax u b mit n Unbekannten
	 * nach der Reduktionsmethode von Dijkstra. Die Objekte in A und b müssen von 
	 * Comparable abgeleitet sein.
	 */
	public Comparable[] reduktionDijkstra (Comparable[][] A, Comparable[] b) {
		int n = b.length;
		Vector<Integer> M = new Vector<Integer>();
		for (int i=0;i<n;i++){
			M.add(new Integer(i));
		}
		for (int j=0;j<n;j++){
			int k=0;
			Comparable bk = (Comparable) op.getNull(); // Double.POSITIVE_INFINITY;
			for (Integer m : M) {
				int i = m.intValue();
				if (b[i].compareTo(bk)==-1){
					bk = b[i];
					k = i;
				}
			}
			System.out.println ("Kleinstes Element: "+k+" -> "+bk);
			M.remove (new Integer (k));
			for (Integer m : M) {
				int i = m.intValue();
				b[i] = (Comparable)op.vereinigung(b[i],op.kettung(A[i][k],bk));
			}
		}
		return b;
	}

	// Iterative Lösungen
	// aus BewGraph .getBewMatrix() und .getPunktVektor( k )
	public Object[] Jacobi (Object[][] A, Object[] b) {
		Object[] x = new Object[0], x1 = b;
		while(!istGleich(x1, x)) {
		   x = x1;
		   x1 = vereinigung(kettung(A,x),b);
		}
		return x;
	}

	public Object[] GaußSeidel (Object[][] A, Object[] b) {
		// ...
		return new Object[b.length];
	}

	/**
	 * Funktion zum direkten Lösen eines Gleichungssystems. Die Matrix A enthält
	 * auf und unterhalb der Diagonalen nur Nullelemente. Sie ist eine Rechtsdreiecks-
	 * matrix. Das Gleichungssystem wird rückswärts aufgelöst.
	 * @param A Rechtsdreiecksmatrix
	 * @param b Punktvektor b
	 * @return Lösungsvektor des Gleichungssystems
	 */
	public Object[] rückwärtsAuflösen (Object[][] A, Object[] b) {
		Object[] x = b; int n = b.length;
		for (int k=n-1;k>=0;k--){
			for (int j=k+1;j<n;j++){
				x[k] = op.vereinigung(x[k],op.kettung(A[k][j],x[j]));		
			}
		}
		return x;
	}

	/**
	 * Funktion zum direkten Lösen eines Gleichungssystems. Die Matrix A enthält 
	 * auf und oberhalb der Diagonalen nur Nullelemente. Sie ist eine Linksdreiecksmatrix. 
	 * Das Gleichungssystem wird vorwärts aufgelöst.
	 * @param A Linksdreiecksmatrix
	 * @param b Punktvektor b
	 * @return Lösungsvector des Gleichungssystems
	 */
	public Object[] vorwärtsAuflösen (Object[][] A, Object[] b) {
		Object[] x = b;
		for (int k=1;k<b.length;k++){
			for (int j=0;j<k;j++){
				x[k] = op.vereinigung(x[k],op.kettung(A[k][j],x[j])); 	
			}
		}
		return x;
	}
	
	
	private void print(Object[] X) {
	    for (int i = 0; i<X.length; i++)
	        System.out.println(X[i]);
	}

	private void print(Object[][] X) {
	    for (int i = 0; i<X.length; i++)
		    for (int j = 0; j<X[0].length; j++)
		        System.out.println(X[i][j]);
	}

	/////////////////////////////////////////////////////////////
	// Hilfsfunktionen für allgemeine Matrizen - Object-Felder //
	private Object[] kettung(Object[][] X, Object[] Y) {
	    int n  = X.length;	    int m  = n>0 ? X[0].length : 0;
   	    int ny = Y.length;
	    if (m != ny)
	        return new Object[0][0];
	    Object[] Z = new Object[n];
		for (int i=0; i<n; i++) {
		    Z[i] = op.getNull();
			for (int k=0; k<m; k++) {
				Z[i] = op.vereinigung(Z[i], op.kettung(X[i][k],Y[k]));
			}
		}
	    return Z;
	}
	
	private Object[][] kettung(Object[][] X, Object[][] Y) {
	    int n  = X.length;	    int m  = n>0  ? X[0].length : 0;
   	    int ny = Y.length;	    int my = ny>0 ? Y[0].length : 0;
	    if (m != ny)
	        return new Object[0][0];
	    Object[][] Z = new Object[n][my];
		for (int i=0; i<n; i++)
			for (int j = 0; j<my; j++) {
			    Z[i][j] = op.getNull();
				for (int k=0; k<m; k++)
					Z[i][j] = op.vereinigung(Z[i][j], op.kettung(X[i][k],Y[k][j]));
			}
	    return Z;
	}
	
	private Object[] vereinigung(Object[] X, Object[] Y) {
	    if (X.length != Y.length)
	        return new Object[0];
	    Object[] Z = new Object[X.length];
	    for (int i = 0; i<X.length; i++)
            Z[i] = op.vereinigung(X[i], Y[i]);
	    return Z;
	}
	
	private Object[][] vereinigung(Object[][] X, Object[][] Y) {
	    int n  = X.length;	    int m  = n>0  ? X[0].length : 0;
   	    int ny = Y.length;	    int my = ny>0 ? Y[0].length : 0;
	    if ((n != ny) || (m != my))
	        return new Object[0][0];
	    Object[][] Z = new Object[n][m];
	    for (int i = 0; i<n; i++)
	        for (int j = 0; j<m; j++)
	            Z[i][j] = op.vereinigung(X[i][j], Y[i][j]);
	    return Z;
	}
	
	private Object[][] hülle(Object[][] A) {
	    // ...
	    return A;
	}
	
	private Object[][] transposition(Object[][] X) {
	    int n = X.length;
	    int m = n>0 ? X[0].length : 0;
	    Object[][] Y = new Object[m][n];
	    for (int i = 0; i<n; i++)
	        for (int j = 0; j<m; j++)
	            Y[j][i] = X[i][j];
	    return Y;
	}

	private boolean istGleich(Object[] X, Object[] Y) {
	    if (X.length != Y.length)
	        return false;
	    if (X.length==0)
	        return false;
	    for (int i = 0; i<X.length; i++) {
	        if (!X[i].equals(Y[i]))
	            return false;
	    }
	    return true;
	}
}
