package informatik.petrinetze;

import java.util.HashMap;

import informatik.strukturen.graphen.BipartiterGraph;


/**
 * @author klinger
 */
public class BedingungsEreignisNetz<B,E,C> extends PetriNetz<B,E,C,Boolean> {

//	protected HashMap<B,Boolean> markierung;
	
	protected HashMap<E,Boolean> status;	// AKTIV, INAKTIV
	
	/**
	 * Erstellt ein Bedingungs/Ereignis-Netz aus einem 
	 * bipartiten Graph. Die Markierung des Netzes wird 
	 * separat gesetzt über setMarkierung.
	 */
	public BedingungsEreignisNetz() {
		super();
		markiereStartKnoten(true);
//		this.markierung = markierung;
	}

	public BedingungsEreignisNetz(BipartiterGraph<B,E> struktur) {
		super(struktur);
		for (B b : struktur.alleKnoten1())
			markierung.put(b, false);
		markiereStartKnoten(true);

		status = new HashMap<E,Boolean>();
		for (E e : struktur.alleKnoten2())
			status.put(e, false);
	}
	
	@Override
	public boolean istAktiviert(E t) {
		for (Object b : vorgänger(t))
			if (markierung.get(b) == false)
				return false;
		return true;
	}

	@Override
	public boolean beginne(E t) {
		if (!istAktiviert(t))
			return false;
		for (Object b : vorgänger(t))
			markierung.put((B) b, false);
		status.put(t,true);
		return true;
	}

	@Override
	public boolean beende(E t) {
		if (status.get(t) == false)
			return false;
		for (Object b : nachfolger(t))
			markierung.put((B) b, true);
		status.put(t, false);
		return true;
	}

	@Override
	public void hinzufügen(C knoten) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hinzufügen(C k1, C k2) {
		// TODO Auto-generated method stub
		
	}
	
}

//class Bedingung {}
//
//class Ereignis {}