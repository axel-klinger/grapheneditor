
package informatik.strukturen;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

/**
 * <P><B>Menge</B> <I>(engl. set)</I>: Eine Menge ist eine 
 * Zusammenfassung von gut unterscheidbaren Objekten zu
 * einem Ganzen. Diese Objekte heissen <I>Elemente</I> der Menge. Man schreibt
 * <I>x E M</I>, wenn <I>x</I> ein Element der Menge <I>M</I> ist.</P>
 * <P>Die Elemente einer Menge sollten zur Identifikation die Methode hashCode() 
 * überladen haben!</P>
 *
 *  <p><strong>Version:</strong>
 *  <br><dd>1.1, Juni 2004
 *  <p><strong>Autor:</strong>
 *  <br><dd>Axel Klinger
 */
public class Menge<E> implements Iterable<E>, Serializable { //extends HashSet<E> { // implements Cloneable, Serializable  { 

    private HashSet<E> elemente;

    /** Erzeugt eine leere Menge.	 */
	public Menge() { 
	    elemente = new HashSet<E>();
	}

	/** Erzeugt eine Menge aus einer gegebenen Menge M. */
	public Menge (Menge<? extends E> M) {
	    elemente = new HashSet<E>();
	    elemente.addAll(M.elemente);
	}
	
	/**	Fügt ein Element zur Menge hinzu.	*/
	public boolean hinzufügen (E element) { 
		if (!enthält(element))	// mit Kontrolle (etwas) schneller als ohne (warum auch immer???)
			return elemente.add(element); 
		return false;
	}
	
//	public void hinzufügen(Menge<E> menge) {
//		for (E e : menge)
//			hinzufügen(e);
//	}

	/**	Entfernt das element aus der Menge.	*/
	public boolean entfernen (Object element) { 
		if (enthält(element))
			return elemente.remove(element); 
		return false;
	}

	/**	Überprüft ob das element in dieser Menge vorhanden ist.	*/
	public boolean enthält (Object element) { 
		return elemente.contains(element); 
	}

	/** Prüft, ob M in dieser Menge enthalten ist. Vorsicht bei Menge von Mengen!!!*/	
	public boolean enthält (Menge M) {
		for (Object m : M)
			if ( !enthält(m) ) 
				return false;
		return true; 	
	}

	/** Liefert die Anzahl der Elemente in der Menge. */
	public int anzahl () { 
		return elemente.size(); 
	} 
	
	public boolean istLeer() {
		return anzahl() <= 0 ? true : false;
	}

	/**	Liefert die Menge der Elemente, die in dieser Menge UND in der Menge M enthalten sind.	 */
	public Menge<E> durchschnitt (Menge<E> M) {
		Menge<E> menge = (Menge<E>) new Menge<E>(); // this.clone();
		for (E m : this)
			if (M.enthält(m))
				menge.hinzufügen(m);				
		return menge;
	}

	/**	Liefert die Menge der Elemente, die in dieser Menge ODER in der Menge M enthalten sind.	 */
	public Menge<E> vereinigung (Menge<E> M) {
		Menge<E> menge = (Menge<E>) new Menge<E>(); // this.clone();
		for (E m : this)
			menge.hinzufügen(m);
		for (E m : M)
			menge.hinzufügen(m);
		return menge;
	}

	/**	Liefert die Menge der Elemente, die in dieser Menge enthalten sind UND NICHT in der Menge M enthalten sind.	 */
	public Menge<E> differenz (Menge<E> M) {
		Menge<E> menge = (Menge<E>) new Menge<E>(); // this.clone();
		for (E m : this)
			if (!M.enthält(m))
				menge.hinzufügen(m);
		return menge;
	}

	/** Entfernt alle Elemente aus der Menge. */
	public void leeren () {
	    elemente.clear();
	}

	// public Object clone(Object clone) {}		!!!!!!!!!!!!!!!!!!

	/**	Überprüft, ob diese Menge die gleichen Elemente enthält wie die Menge M.	 */
	public boolean equals (Object o) { 
	    if (!(o instanceof Menge))
	        return false;
	    Menge m = (Menge) o;
	    if (m.anzahl() != this.anzahl())
	        return false;
	    for (E e : this)
	        if (!m.enthält(e))
	            return false;
	    return true;
	}

	/**	
	 * Liefert die Menge als Zeichenkette.	 
	 */
	public String toString () { 
		String s = "{";
		for (E e : this) {
			if (!s.equals("{"))
				s += ",";
			s += e;
		}	
		s += "}";
		return s;
		
	}

    public Iterator<E> iterator() {
        return elemente.iterator();
    }	
}