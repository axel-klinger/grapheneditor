
package informatik.strukturen;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *	<B>Folge</B> <I>(engl. sequence)</I>: Eine Folge ist eine 
 *	verkettete Folge von Elementen. 
 *	Die lineare Folge geh�rt zu den wichtigsten dynamischen 
 *	Datenstukturen. Grundoperationen auf Listen sind: 
 *	Aufbau einer Folge aus n Elementen, Durchlaufen einer Folge, 
 *	Einf�gen und Entfernen eines Elements.	 
 *
 *  <p><strong>Version:</strong>
 *  <br><dd>1.0, April 2004
 *  <p><strong>Autor:</strong>
 *  <br><dd>Institut f�r Bauinformatik
 *  <br><dd>Universit�t Hannover
 *  <br><dd>Dipl.-Ing. Axel Klinger
 */
public class Folge<E> implements Iterable<E> {//extends ArrayList<E>{

	protected ArrayList<E>	elemente;

    /**
	 *	Erzeugt eine leere Folge.	 */
	public Folge() {
		elemente = new ArrayList<E>();
	}

	/**
	 *	Erzeugt eine Folge aus einem Feld von Objekten.	*/
/*	public Folge(Object[] elemente) {
		this.elemente = new ArrayList();
		for (int i=0; i<elemente.length; i++) {
			this.elemente.add(elemente[i]);
		}
	}	*/

	public boolean enth�lt(E element) {
		return elemente.contains(element);
	}

	/**
	 *	Liefert das Element an der Stelle position.	 */
	public E get(int position) {
		return elemente.get(position);
	}

	/**
	 * Liefert die Position eines elements in der Folge.
	 */
	public int getPosition(E element) {
		return elemente.indexOf(element);
	}
	
	/**
	 *	F�gt ein element am Ende der Folge hinzu.	*/
	public void hinzuf�gen(E element) {
	    elemente.add(element);
	}


	/**
	 *	F�gt ein element an der position ein. Die Folge wird an dieser Stelle auseinandergeschoben.	 */
	public void einf�gen(int position, E element) {
	    elemente.add(position, element);
	}

	/**
	 *	Ersetzt ein vorhandenes Element an der position durch element.	 */
	public void ersetzen(int position, E element) {
	    elemente.set(position, element);
	}

	/**
	 *	Entfernt das element aus der Folge. Die L�cke wird geschlossen.	 */
	public void entfernen(Object element) {
	    elemente.remove(element);
	}

	/**
	 *	Entfernt das Element an der position. Die L�cke wird geschlossen.	 */
	public void entfernen(int position) {
	    elemente.remove(position);
	}

	/**
	 *	Liefert eine Aufz�hlung aller Elemente der Folge.	 */
/*	public Iterator<E> alleElemente() {
		return iterator();
	}	*/

	/**
	 *	Liefert die Anzahl der Elemente in der Folge.	*/
	public int anzahl() {
		return elemente.size();
	}

	public boolean equals(Object o) {
	    if (!(o instanceof Folge))
	        return false;
	    Folge f = (Folge) o;
	    if (this.anzahl() != f.anzahl())
	        return false;
	    for (int i=0; i<anzahl(); i++) {
	        if (!this.get(i).equals(f.get(i)))
	            return false;
	    }
	    return true;
	}
	
	/**
	 *	Liefert die Folge als Zeichenkette.
	 */
	public synchronized String toString () { 
		String s = "<";
		for (E e : this) {
			if (!s.equals("<"))
				s += ",";
			s += e;
		}	
		s += ">";
		return s;
	}

    public Iterator<E> iterator() {
        return elemente.iterator();
    }	
}