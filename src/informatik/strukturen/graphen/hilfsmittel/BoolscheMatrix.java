
package informatik.strukturen.graphen.hilfsmittel;

/**
 *	Eine BoolscheMatrix ist eine Matrix, deren Werte aus {true, false} kommen.	*/
public class BoolscheMatrix {

	private boolean[][] werte;
	
	/**	Erzeugt eine BoolscheMatrix aus einem Feld mit Wahrheitswerten.	 */
	public BoolscheMatrix(boolean[][] werte) {
		this.werte = werte;
	}

	public BoolscheMatrix(BoolscheMatrix b) {
		werte = new boolean[b.werte.length][b.werte[0].length];
		for (int zeile=0; zeile<werte.length; zeile++) {
			for (int spalte=0; spalte<werte[0].length; spalte++) {
				werte[zeile][spalte] = b.werte[zeile][spalte];
			}
		}
	}

	/**	Erzeugt eine BoolscheMatrix mit angegebener Größe. Alle Werte sind mit false initialisiert.	 */
	public BoolscheMatrix(int zeilen, int spalten) {
		werte = new boolean[zeilen][spalten];
	}

	/**	Setzt den wert bei (zeile, spalte).	 */
	public void set(int zeile, int spalte, boolean wert) {
		werte[zeile][spalte] = wert;
	}

	/**	Liefert den wert bei (zeile, spalte).	 */
	public boolean get(int zeile, int spalte) {
		return werte[zeile][spalte];
	}

	/**	Formatierte Matrizendarstellung auf der Kommandozeile. Für die Ausgabe als Zeichenkette s. toString().	*/
	public void ausgeben() {
		for (int zeile=0; zeile<werte.length; zeile++) {
			for (int spalte=0; spalte<werte[0].length; spalte++) {
				System.out.print(werte[zeile][spalte] + "\t");
			}
			System.out.print("\n");
		}
	}

	public boolean enthält(BoolscheMatrix bm) {
		if (!(bm.werte.length==werte.length && bm.werte[0].length==werte[0].length))
			return false;
		for (int i=0; i<werte.length; i++)
			for (int j=0; j<werte[0].length; j++)
				if (!(!bm.werte[i][j] || werte[i][j]))
					return false;
		return true;
	}

	/**	Produkt aus dieser BoolscheMatrix und der BoolscheMatrix b.	 */
	public BoolscheMatrix mal(BoolscheMatrix b) {
		if (werte[0].length == b.werte.length) {
			BoolscheMatrix c = new BoolscheMatrix(werte.length, b.werte[0].length);
			for (int bspalte=0; bspalte<b.werte[0].length; bspalte++) {
				for (int azeile=0; azeile<werte.length; azeile++) {
					boolean temp = false;
					for (int aspalte=0; aspalte<werte[0].length; aspalte++) {
						temp |= werte[azeile][aspalte] & b.werte[aspalte][bspalte];
					}
					c.werte[azeile][bspalte] = temp;
				}
			}
			return c;
		}
		return null;
	}

	/**	Vereinigung dieser BoolscheMatrix mit der BoolscheMatrix b.	 */
	public BoolscheMatrix oder(BoolscheMatrix b) {
		BoolscheMatrix c = null;
		if (this.sameSizeAs(b)) {
			c = new BoolscheMatrix(werte.length, werte[0].length);
			for (int zeile=0; zeile<werte.length; zeile++) {
				for (int spalte=0; spalte<werte[0].length; spalte++) {
					c.set(zeile, spalte, (werte[zeile][spalte] | b.werte[zeile][spalte]));
				}
			}
		}
		return c;
	}

	/**	Durchschnitt aus dieser BoolscheMatrix und der BoolscheMatrix b.	 */
	public BoolscheMatrix und(BoolscheMatrix b) {
		BoolscheMatrix c = null;
		if (this.sameSizeAs(b)) {
			c = new BoolscheMatrix(werte.length, werte[0].length);
			for (int zeile=0; zeile<werte.length; zeile++) {
				for (int spalte=0; spalte<werte[0].length; spalte++) {
					c.set(zeile, spalte, (werte[zeile][spalte] & b.werte[zeile][spalte]));
				}
			}
		}
		return c;
	}

	/**	Transitive Hülle dieser BoolscheMatrix.	 */
	public BoolscheMatrix transitiveHülle() {
/*	BoolscheMatrix hülle = new BoolscheMatrix(1, 1);
		BoolscheMatrix hüllex = new BoolscheMatrix(this);
		BoolscheMatrix r_hoch_n = new BoolscheMatrix(this);
		while (!hülle.equals(hüllex)) {
			hülle = hüllex;
			r_hoch_n = r_hoch_n.mal(this);
			hüllex = hülle.oder(r_hoch_n);
		}
		return hülle;	*/

		int m, zeile, spalte;
		boolean hit;
		boolean[]  mark = new boolean[werte.length];
		boolean[][] hülle = new boolean[werte.length][werte.length];
		for (zeile=0; zeile<werte.length; zeile++) {
			System.arraycopy(werte[zeile], 0, hülle[zeile], 0, werte.length);
			for (m=0; m<werte.length; m++)
				mark[m] = false;
			mark[zeile] = true;
			do {
				hit = false;
				for(spalte=0; spalte<werte.length; spalte++)
					if (hit = (hülle[zeile][spalte] == true) && !mark[spalte])
						break;
				if (hit) {
					mark[spalte] = true;
					for (m=0; m<werte.length; m++)
						hülle[zeile][m] |= werte[spalte][m];
				}
			} while(hit);
		}
		return new BoolscheMatrix(hülle);
	}

	public BoolscheMatrix komplement() {
		int zeilen = werte.length, spalten = werte[0].length;
		BoolscheMatrix m = new BoolscheMatrix(zeilen, spalten);
		for (int i=0; i<zeilen; i++)
			for (int j=0; j<spalten; j++)
				m.werte[i][j] = !werte[i][j];		//m.set(i, j, true);
		return m;
	}

	/**	Transposition dieser BoolscheMatrix	 */
	public BoolscheMatrix transponierte() {
		boolean[][] feld = new boolean[werte[0].length][werte.length];
		for (int zeile=0; zeile<werte.length; zeile++)
			for (int spalte=0; spalte<werte[0].length; spalte++)
				feld[spalte][zeile] = werte[zeile][spalte];
		return new BoolscheMatrix(feld);
	}

	/**	Inverse dieser BoolscheMatrix	 */
//	public BoolscheMatrix inverse() {
//		...
//		return null;
//	}

	// Überprüfen ob Länge und Breite übereinstimmen
	private boolean sameSizeAs(BoolscheMatrix b) {
		return this.werte.length == b.werte.length && this.werte[0].length == b.werte[0].length;
	}

	// Überprüfen ob Breite übereinstimmt
	private boolean sameWidthAs(BoolscheMatrix b) {
		return this.werte[0].length == b.werte[0].length;
	}

	/**	Überprüft ob diese BoolscheMatrix und b gleich sind.	 */
	public boolean equals(Object objekt) {
		boolean w = true;
		if (!(objekt instanceof BoolscheMatrix))
			return false;
		BoolscheMatrix c = (BoolscheMatrix) objekt;
		if (!(c.werte.length==werte.length && c.werte[0].length==werte[0].length))
			return false;
		for (int zeile=0; zeile<c.werte.length; zeile++)
			for (int spalte=0; spalte<c.werte[0].length; spalte++)
				if (!(c.get(zeile,spalte)==get(zeile,spalte)))
					return false;
		return true;
	}

	/**	Die BoolscheMatrix als Zeichenkette.	 */
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
}