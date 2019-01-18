
package mathematik;


public class Matrix {

	double[][] werte;
	
	public Matrix(double[][] werte) {
		this.werte = werte;
	}

	public Matrix(int zeilen, int spalten) {
		werte = new double[zeilen][spalten];
	}

	/**
	 *	Setzt den wert bei (zeile, spalte).
	 */
	public void set(int zeile, int spalte, double wert) {
		werte[zeile][spalte] = wert;
	}

	/**
	 *	Liefert den wert bei (zeile, spalte).
	 */
	public double get(int zeile, int spalte) {
		return werte[zeile][spalte];
	}

	/**
	 *	Multiplikation dieser Matrix mit der Matrix m2.
	 */
	public Matrix mal(Matrix m2) {
		if (!this.sameWidthAs(m2.transponierte()))
			return null;
			
		double[][] feld = new double[this.werte.length][m2.werte[0].length];
		for (int zeile=0; zeile<this.werte.length; zeile++)
			for (int spalte = 0; spalte<m2.werte[0].length; spalte++)
				for (int k=0; k<this.werte[0].length; k++)
					feld[zeile][spalte] += this.werte[zeile][k]*m2.werte[k][spalte];
		return new Matrix(feld);
	}

	/**
	 *	Multiplikation dieser Matrix mit einem Skalar.
	 */
	public Matrix mal(double d) {
		double[][] feld = new double[werte.length][werte[0].length];
		for (int zeile=0; zeile<werte.length; zeile++)
			for (int spalte=0; spalte<werte[0].length; spalte++)
				feld[zeile][spalte] = this.werte[zeile][spalte] * d;
		return new Matrix(feld);
	}

	/**
	 *	Addition der Matrix m2 zu dieser Matrix.
	 */
	public Matrix plus(Matrix m2) {
		if (!this.sameSizeAs(m2))
			return null;

		double[][] feld = new double[werte.length][werte[0].length];
		for (int zeile=0; zeile<werte.length; zeile++)
			for (int spalte=0; spalte<werte[0].length; spalte++)
				feld[zeile][spalte] = this.werte[zeile][spalte] + m2.werte[zeile][spalte];
		return new Matrix(feld);
	}

	/**
	 *	Determinante dieser Matrix.
	 */
	public double determinante() {
		if (werte.length!=werte[0].length)
			return Double.NaN;

		double d = 0.0;
		int n = werte.length;
		double tempplus = 1.0;
		double tempminus = 1.0;

		for (int zeile=0; zeile<n; zeile++)	{
			for (int spalte=0; spalte<n; spalte++)	{
				tempplus *= werte[(zeile+spalte)%n][spalte];
				tempminus *= werte[(zeile+spalte)%n][n-1-spalte];
				System.out.print(werte[(zeile+spalte)%n][spalte] + "  ");
			}
			System.out.println(tempplus + "  " + tempminus);
			d += tempplus-tempminus;
			tempplus = 1.0;
			tempminus = 1.0;
		}

		return d;
	}

	/**
	 *	Transposition dieser Matrix
	 */
	public Matrix transponierte() {
		double[][] feld = new double[werte[0].length][werte.length];
		for (int zeile=0; zeile<werte.length; zeile++)
			for (int spalte=0; spalte<werte[0].length; spalte++)
				feld[spalte][zeile] = werte[zeile][spalte];
		return new Matrix(feld);
	}

	/**
	 *	Inverse dieser Matrix
	 */
	public Matrix inverse() {
		// ...
		return null;
	}

	// Überprüfen ob Länge und Breite übereinstimmen
	private boolean sameSizeAs(Matrix m2) {
		return this.werte.length == m2.werte.length && this.werte[0].length == m2.werte[0].length;
	}

	// Überprüfen ob Breite übereinstimmt
	private boolean sameWidthAs(Matrix m2) {
		return this.werte[0].length == m2.werte[0].length;
	}

	/**
	 *	Überprüft ob diese Matrix und m2 gleich sind.
	 */
	public boolean equals(Object m2) {
		boolean b = true;
		if (m2 instanceof Matrix) {
			Matrix mtemp = (Matrix) m2;
			if (mtemp.werte.length==werte.length && mtemp.werte[0].length==werte[0].length) {
				for (int zeile=0; zeile<mtemp.werte.length; zeile++) {
					for (int spalte=0; spalte<mtemp.werte[0].length; spalte++) {
						b &= (mtemp.get(zeile,spalte)==get(zeile,spalte));
					}
				}
				return b;
			}
		}
		return false;
	}

	/**
	 *	Die Matrix als Zeichenkette
	 */
	public String toString() {
		String s = "{";
		for (int zeile=0; zeile<werte.length; zeile++) {
			s += "{";
			for (int spalte=0; spalte<werte[0].length; spalte++) {
				s += werte[zeile][spalte];
				if (spalte!=(werte[0].length-1))
					s += ", ";
			}
			s += "}";
			if (zeile!=(werte.length-1))
				s += ",";
		}
		s += "}";
		return s;
	}

///////////////////Es folgen Hilfsmethoden für die inverse Matrix///////////////////

	
//	int iDF = 0;


	/**
	 *  Hilfsmethode zur Berechnung der inverse Matrix.
	 */
/*	public double[][] Vorz(double[][] a) {
	    int tms = a.length; 
		double m[][] = new double[tms][tms];

	    int ii, jj, ia, ja;
		double determinante;

	    for (int zeile=0; zeile < tms; zeile++) {
		    for (int spalte=0; spalte < tms; spalte++) {
			    ia = ja = 0;
				double ap[][] = new double[tms-1][tms-1];
				for (ii=0; ii < tms; ii++)  {
					for (jj=0; jj < tms; jj++)  {
						if ((ii != zeile) && (jj != spalte))  {
							ap[ia][ja] = a[ii][jj];
					        ja++;
						}
				    }
					if ((ii != zeile ) && (jj != spalte)) 
						ia++;
				    ja=0; 
		        }
			    determinante = Determinant(ap);
				m[zeile][spalte] = (double)Math.pow(-1, zeile+spalte) * determinante;
		    }
		}
	    m = trans2(m);
		return m;
	}	*/

	/**
	 *  Hilfsmethode zur Berechnung der inverse Matrix.
	 *  Hier wird das obere Dreieck der umgeformten Matrix
	 *  als Matrix zurückgeliefert.
	 */
/*	public double[][] obereDreieck(double[][] m) {
	    double f1 = 0;   double t = 0;
		int tms = m.length;  
	    int v=1;
		iDF=1;

	    for (int spalte=0; spalte < tms-1; spalte++) {
		    for (int zeile=spalte+1; zeile < tms; zeile++) {
			    v=1;
				sprung:
	            while(m[spalte][spalte] == 0) {                          
	                if (spalte+v >= tms) {
						iDF=0;
	                    break sprung; 
		            } else {
	                    for(int c=0; c < tms; c++) {  
							t = m[spalte][c];
							m[spalte][c]=m[spalte+v][c];       
							m[spalte+v][c] = t;   
	                    }
		                v++;                          
			            iDF = iDF * -1;                
					}
				}
				if ( m[spalte][spalte] != 0 ) {
					try {
			            f1 = (-1) * m[zeile][spalte] / m[spalte][spalte];
				        for (int zeile=spalte; zeile < tms; zeile++) 
							m[zeile][zeile] = f1*m[spalte][zeile] + m[zeile][zeile]; 
					} catch(Exception e) {}
	            }            
	        }
	    }
		return m;
	}	*/

	/**
	 *  Hilfsmethode zur Berechnung der inverse Matrix.
	 *  Hier wird die Determinate durch diagonale Multiplikation
	 *  durch das bestimmte obere Dreieck errechnet.
	 */
/*	public double Determinant(double[][] matrix) {
	    int tms = matrix.length;    
		double determinante=1;

	    matrix = obereDreieck(matrix);

	    for (int zeile=0; zeile < tms; zeile++)
			determinante = determinante * matrix[zeile][zeile]; 
	    determinante = determinante * iDF; 
		return determinante;
	}	*/

/////////////////////////////Ende der Hilfsfunktionen///////////////////////////////

}