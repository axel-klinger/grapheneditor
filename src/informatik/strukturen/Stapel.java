
package informatik.strukturen;

import java.util.Iterator;
import java.util.Vector;

/**
 *	<P><B>Stapel</B> <I>(engl. stack)</I>: Ein Stapel ist eine Folge 
 *	von Elementen der am Ende Elemente hinzugefügt und am Ende wieder
 *	Elemente entfernt werden können. Auf einen Stapel können Elemente 
 *	eines Datentyps aufgelegt und von oben wieder abgenommen werden.</P> 
 *
 *	<CENTER><IMG SRC="imagesStapel/Stapel.png" BORDER=0 ALT="Stapel"></CENTER>
 *
 *  <p><strong>Version:</strong>
 *  <br><dd>1.1, Juni 2004
 *  <p><strong>Autor:</strong>
 *  <br><dd>Axel Klinger
 */
public class Stapel<E> implements Iterable<E> { //extends Vector<E> {

    private Vector<E> elemente;
    
    /**
	 * Erzeugt einen leeren Stapel.	 
	 */
	public Stapel () {
	    elemente = new Vector<E>();
	}

	/**
	 *	Legt ein <code>element</code> auf den Stapel. 
	 *	<BR><BR>
	 *	<CENTER><IMG SRC="imagesStapel/Stapel.ElementAuflegen.png" BORDER=0 ALT=""></CENTER>
	 */
	public void auflegen (E element) {
		elemente.add(element);
	}

	/**
	 *	Liefert das oberste Stapelelement ohne es zu entfernen.
	 *	<BR><BR>
	 *	<CENTER><IMG SRC="imagesStapel/Stapel.oberstesElement.png" BORDER=0 ALT=""></CENTER>
	 */
	public E oberstes() {
		return elemente.get(elemente.size()-1); 
	}

	/**
	 *	Entfernt das oberste Element vom Stapel und liefert es zurück. 
	 *	<BR><BR>
	 *	<CENTER><IMG SRC="imagesStapel/Stapel.ElementAbnehmen.png" BORDER=0 ALT=""></CENTER>
	 */
	public E abnehmen() {
		return elemente.remove(elemente.size()-1);
	}

	/**
	 *	Liefert die Anzahl der Elemente im Stapel.
	 */
	public int anzahl() {
		return elemente.size();
	}

	/**
	 *	Vergleicht zwei Stapel auf gleichheit. Zwei Stapel sind gleich, wenn sie die gleichen 
	 *	Elemente in der gleichen Reihenfolge enthalten.
	 */
	public boolean equals(Object o) {
		return elemente.equals(o);
	}

	/**
	 *	Liefert die Zeichenkettendarstellung eines Stapels.
	 */
	public synchronized String toString () { 
		int i = 0;
		String s = "{";
		for (E e : this) {
			if (!s.equals("{"))
				s += ",";
			s += (i++) + ": " + e;
		}	
		s += "}";
		return s;
	}

    /* (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<E> iterator() {
        return elemente.iterator();
    }

	public void leeren() {
		elemente = new Vector<E>();
	}	
}