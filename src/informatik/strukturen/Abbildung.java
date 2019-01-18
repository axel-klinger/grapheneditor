package informatik.strukturen;

import java.util.HashMap;
import java.util.Iterator;

/**
 * <P><B>Abbildung</B> <I>(engl. mapping)</I>: Eine Abbildung 
 * ist eine linkstotale und rechtseindeutige bin�re Relation.
 * @author klinger
 *
 */
public class Abbildung<E1,E2> implements Iterable<E1> {

	HashMap<E1,E2> abbildung;
	
	public Abbildung () {
		abbildung = new HashMap<E1,E2>();
	}

	public void hinzuf�gen(E1 schl�ssel, E2 wert) {
		abbildung.put(schl�ssel, wert);
	}
	
	public E2 get(E1 schl�ssel) {
		return abbildung.get(schl�ssel);
	}
	
	public Iterator<E1> iterator() {
		return abbildung.keySet().iterator();
	}
}
