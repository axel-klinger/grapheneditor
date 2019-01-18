
package informatik.strukturen;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *	<B>Folge</B> <I>(engl. sequence)</I>: Eine Folge ist eine 
 *	verkettete Folge von Elementen. 
 *	Die lineare Folge gehört zu den wichtigsten dynamischen 
 *	Datenstukturen. Grundoperationen auf Listen sind: 
 *	Aufbau einer Folge aus n Elementen, Durchlaufen einer Folge, 
 *	Einfügen und Entfernen eines Elements.	 
 *
 *  <p><strong>Version:</strong>
 *  <br><dd>1.0, April 2004
 *  <p><strong>Autor:</strong>
 *  <br><dd>Institut für Bauinformatik
 *  <br><dd>Universität Hannover
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

	public boolean enthält(E element) {
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
	 *	Fügt ein element am Ende der Folge hinzu.	*/
	public void hinzufügen(E element) {
	    elemente.add(element);
	}


	/**
	 *	Fügt ein element an der position ein. Die Folge wird an dieser Stelle auseinandergeschoben.	 */
	public void einfügen(int position, E element) {
	    elemente.add(position, element);
	}

	/**
	 *	Ersetzt ein vorhandenes Element an der position durch element.	 */
	public void ersetzen(int position, E element) {
	    elemente.set(position, element);
	}

	/**
	 *	Entfernt das element aus der Folge. Die Lücke wird geschlossen.	 */
	public void entfernen(Object element) {
	    elemente.remove(element);
	}

	/**
	 *	Entfernt das Element an der position. Die Lücke wird geschlossen.	 */
	public void entfernen(int position) {
	    elemente.remove(position);
	}

	/**
	 *	Liefert eine Aufzählung aller Elemente der Folge.	 */
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