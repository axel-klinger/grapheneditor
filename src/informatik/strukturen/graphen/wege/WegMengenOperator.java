package informatik.strukturen.graphen.wege;

import java.util.Iterator;

/**
 * @author Axel
 */
public abstract class WegMengenOperator extends Operator {
    
    //für die vereinigung 
    public Object plus(Object a, Object b) {
		WegMenge C = new WegMenge();
		if (!(a instanceof WegMenge) | !(b instanceof WegMenge))
			return C;
		WegMenge A = (WegMenge) a;
		WegMenge B = (WegMenge) b;
		if (A.anzahl() == 0 && B.anzahl() != 0)		// A u 0z = A
		    return B;
		if (A.anzahl() != 0 && B.anzahl() == 0)		// 0z u A = A
		    return A;
		for (Iterator i = A.alleWege(); i.hasNext(); )
		    C.hinzufügen((Weg) i.next());
		for (Iterator i = B.alleWege(); i.hasNext(); )
		    C.hinzufügen((Weg) i.next());
		return C;
    }
    
    //für die kettung
    public Object mal(Object a, Object b) {
		WegMenge C = new WegMenge();
		if (!(a instanceof WegMenge) | !(b instanceof WegMenge))
			return C;
		WegMenge A = (WegMenge) a;
		WegMenge B = (WegMenge) b;
		if (A.anzahl()==0)	// 0z o A = 0z
		    return A;
		if (B.anzahl()==0)	// A o 0z = 0z
		    return B;
		if (A.anzahl()==1) {
		    Weg w = (Weg) A.alleWege().next();
		    if (w.start().equals(w.ziel()))
		        return B;
		}
		if (B.anzahl()==1) {
		    Weg w = (Weg) B.alleWege().next();
		    if (w.start().equals(w.ziel()))
		        return A;
		}
		for (Iterator i = A.alleWege(); i.hasNext(); ) {
		    Weg w1 = (Weg) i.next();
		    for (Iterator j = B.alleWege(); j.hasNext(); ) {
		        Weg w2 = (Weg) j.next();
		        C.hinzufügen(w1.ketten(w2));
		    }
		}
        return C;
    }
    
	public abstract Object vereinigung(Object o1, Object o2);
	
	public abstract Object kettung(Object o1, Object o2);
}
