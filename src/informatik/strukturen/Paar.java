
package informatik.strukturen;

import java.io.Serializable;

/**
 *	<P><B>Paar</B> <I>(engl. pair)</I>: Ein geordnetes Paar 
 *	beschreibt die Zuordnung eines Elements zu einem anderen.</P>
 *	
 *  <p><strong>Version:</strong>
 *  <br><dd>1.0, April 2004
 *  <p><strong>Autor:</strong>
 *  <br><dd>Institut für Bauinformatik
 *  <br><dd>Universität Hannover
 *  <br><dd>Dipl.-Ing. Axel Klinger
 */
public class Paar<E1,E2> implements Serializable { 

	E1 eins;			// erstes  Element des geordneten Paares
	E2 zwei;			// zweites Element des geordneten Paares

	/**
	 *	Erzeugt ein geordnetes Paar aus zwei Objekten.
	 */
	public Paar (E1 eins, E2 zwei) { 
		this.eins = eins; 
		this.zwei = zwei;
	}

	/**
	 *	Liefert das erste Element des geordneten Paares.
	 */
	public E1 eins () { 
		return eins; 
	}        

	/**
	 *	Liefert das zweite Element des geordneten Paares.
	 */
	public E2 zwei () { 
		return zwei; 
	}

	/**
	 *	Vergleicht zwei Paare auf Gleichheit. Zwei Paare sind gleich, wenn sie 
	 *	die gleichen Elemente in der gleichen Reihenfolge besitzen.
	 */
	public boolean equals (Object objekt) { 
		if (!(objekt instanceof Paar)) 
			return false;
		Paar paar = (Paar) objekt;  
		return (eins.equals(paar.eins) && zwei.equals(paar.zwei)); 
	}

	/**
	 *	Liefert den Hashcode eines geordneten Paares.
	 */
	public int hashCode() {
	    int hash = 7;
	    hash = hash * 31 + ((eins == null) ? 0 : eins.hashCode());
	    hash = hash * 31 + ((zwei == null) ? 0 : zwei.hashCode());
	    return hash;
	}

	/**
	 *	Liefert die Zeichenkettendarstellung eines geordneten Paares.
	 */
	public String toString () { 
		return "(" + eins + "," + zwei + ")";
	}
}