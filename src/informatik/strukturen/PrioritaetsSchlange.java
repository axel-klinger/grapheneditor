
package informatik.strukturen;

import java.util.Iterator;
import java.util.Vector;

/**
 * <P><B>Prioritätsschlange</B> <I>(engl. priority queue)</I>: Eine  
 * Prioritätsschlange enthält gewichtete Elemente. Im Vergleich zu 
 * einer Schlange wird nicht das erste Element entnommen, sondern das 
 * Element mit dem größten bzw. kleinsten Gewicht.</P>
 * 
 * (Eigentlich auch keine Schlange, sondern vielmehr eine sortierte Menge !?!?)
 * 
 * @author klinger
 */
public class PrioritaetsSchlange<E> implements Iterable { //extends Schlange<E> {

    private Vector<Paar<E,Double>> elemente;
     
	public PrioritaetsSchlange () {
	    elemente = new Vector<Paar<E,Double>>();
	}
	
	public int anzahl() {
	    return elemente.size();	    
	}
	
	public boolean anstellen(Paar<E,Double> element) {
		if (elemente.size()==0)
			return elemente.add(element);
		for (int i = 0; i<elemente.size(); i++) {
			Paar p = (Paar) elemente.get(i);
			if (((Double) p.zwei()).compareTo((Double) element.zwei())>0) {
				elemente.add(i, element);
				return true;
			} 
		}		
		elemente.add(element);
		return true;
	}

	public boolean anstellen(E x, Double p) {
		return anstellen(new Paar<E,Double>(x, p));
	}

	public void entfernen(Object x) {
	    for (Paar p : elemente)
			if (p.eins().equals(x))
				elemente.remove(p);
	}

	public boolean setPrioritaet(E x, Double p) {
	    for (Paar paar : elemente) {
			if (paar.eins().equals(x)) {
				elemente.remove(paar);
				anstellen(new Paar<E,Double>((E)paar.eins(), p));
				return true;
			}
		}
		return false;
	}

	public Double getPrioritaet(Object x) {
	    for (Paar p : elemente)
			if (p.eins().equals(x))
				return (Double) p.zwei();
		return null;
	}

	public Paar<E,Double> entfernenMinimum() {
		return (Paar<E,Double>)elemente.remove(0);
	}

	public Paar<E,Double> entfernenMaximum() {
		return (Paar<E,Double>) elemente.remove(elemente.size()-1);
	}

	public boolean enthält(Object x) {
	    for (Paar p : elemente) {
			if (p.eins().equals(x))
				return true;
		}
		return false;
	}

//	public boolean equals(Object o) { return false; }	
	
	public String toString() {
		int i = 0;
		String s = "{";
		for (Paar e : elemente) {
			if (!s.equals("{"))
				s += ",";
			s += (i++) + ": " + e;
		}	
		s += "}";
		return s;
	}

	public Iterator iterator() {
	    return elemente.iterator();
	}
}
