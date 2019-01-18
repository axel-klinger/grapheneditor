package informatik.strukturen;

import java.util.HashMap;
import java.util.Iterator;

/**
 * <P><B>Bijektive Abbildung</B> <I>(engl. bijective mapping)</I>: 
 * Eine bijektive Abbildung ist eine bitotale und eineindeutige 
 * bin�re Relation. </P>
 * 
 * @author klinger
 *
 */
public class BijektiveAbbildung<E1,E2> implements Iterable<E1> {

	HashMap<E1,E2> abbildung12;
	HashMap<E2,E1> abbildung21;
	
	
	public BijektiveAbbildung () {
		abbildung12 = new HashMap<E1,E2>();
		abbildung21 = new HashMap<E2,E1>();
	}

	public void hinzuf�gen(E1 e1, E2 e2) {
		abbildung12.put(e1, e2);
		abbildung21.put(e2, e1);
	}
	
	public E2 get2(E1 schl�ssel) {
		return abbildung12.get(schl�ssel);
	}
	
	public E1 get1(E2 schl�ssel) {
		return abbildung21.get(schl�ssel);
	}
	
	public Iterator<E1> iterator() {
		return abbildung12.keySet().iterator();
	}
	
	public String toString() {
//		String s = "";
		
		return abbildung12.toString();
	}
}
