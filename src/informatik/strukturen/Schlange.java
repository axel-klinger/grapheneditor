
package informatik.strukturen;

import java.util.Iterator;
import java.util.Vector;

/**
 *	<P><B>Schlange</B> <I>(engl. queue)</I>: Eine Schlange ist eine 
 *	Folge von Elementen, der am Ende Elemente hinzugefügt und am Anfang 
 *	Elemente entnommen werden können. Elemente dürfen nur am Ende 
 *	einer Schlange eingefügt und nur am Anfang der Schlange entnommen 
 *	werden. Eine Schlange heißt daher auch FIFO-Speicher. 
 *	(FIFO: First In First Out)</P>
 *
 *	<CENTER><IMG SRC="imagesSchlange/Schlange.png" BORDER=0 ALT="Schlange"></CENTER>
 *
 *  <p><strong>Version:</strong>
 *  <br><dd>1.1, Juni 2004
 *  <p><strong>Autor:</strong>
 *  <br><dd>Axel Klinger
 */
public class Schlange<E> implements Iterable<E> { //extends Vector<E> {

    private Vector<E> elemente;
    
    /**
	 * Erzeugt eine leere Schlange.	 */
	public Schlange () {
	    elemente = new Vector<E>();
	}

	/**
	 *	Überprüft ob das element in dieser Menge vorhanden ist.	*/
	public boolean enthält (E element) { 
		return elemente.contains(element); 
	}

	/**
	 *	Fügt ein <code>element</code> am Ende der Schlange hinzu. 
	 *	<BR><BR>
	 *	<CENTER><IMG SRC="imagesSchlange/Schlange.ElementAnstellen.png" BORDER=0 ALT=""></CENTER>
	 */
	public boolean anstellen(E element) { 
		return elemente.add(element);
	}

	/**
	 *	Liefert das erste <code>element</code> aus der Schlange ohne es zu entfernen. 
	 *	<BR><BR>
	 *	<CENTER><IMG SRC="imagesSchlange/Schlange.erstesElementEntfernen.png" BORDER=0 ALT=""></CENTER>
	 */
	public E erstes() {
		return elemente.get(0);
	}

	/**
	 *	Entfernt das erste <code>element</code> aus der Schlange und liefert es zurück. 
	 *	<BR><BR>
	 *	<CENTER><IMG SRC="imagesSchlange/Schlange.erstesElementEntfernen.png" BORDER=0 ALT=""></CENTER>
	 */
	public E wegnehmen() {
		return elemente.remove(0);
	}

	/**
	 *	Liefert die Anzahl der Elemente in der Schlange.	
	 */
	public int anzahl() {
		return elemente.size();
	}

	/**
	 *	Vergleicht zwei Schlangen auf gleichheit. Zwei Schlangen sind gleich, wenn sie die gleichen 
	 *	Elemente in der gleichen Reihenfolge enthalten.
	 */
	public boolean equals(Object o) {
		return elemente.equals(o);
	}

	/**
	 *	Liefert die Zeichenkettendarstellung einer Schlange.
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
	// equals

    public Iterator<E> iterator() {
        return elemente.iterator();
    }
}