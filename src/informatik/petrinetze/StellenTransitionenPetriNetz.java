
package informatik.petrinetze;

import java.util.HashMap;

import informatik.strukturen.Paar;
//import informatik.strukturen.graphen.GewichteterBipartiterGraph;

public class StellenTransitionenPetriNetz<S,T,C> extends PetriNetz<S,T,C,Integer> {
	
	private HashMap<Stelle,Integer> kapazitäten;
	private HashMap<Paar<PetriNetzElement,PetriNetzElement>,Integer> kantenGewichte;

	/**
	 *	Erstellt ein Stellen/Transitionen-Netz.
	 */
	public StellenTransitionenPetriNetz () {
		super();
		kapazitäten = new HashMap<Stelle,Integer>();
		kantenGewichte = new HashMap<Paar<PetriNetzElement,PetriNetzElement>,Integer>();
	}

	/**
	 *	Liefert true wenn die Transition t aktiviert ist, sonst false.
	 *	Eine Transition ist aktiviert, wenn alle Eingangsstellen mindestens ein Token enthalten.
	 */
	public boolean istAktiviert(Transition t) {
//		for (Stelle<Integer> s : vorgänger(t))
//			if (s.wert < kantenGewichte.get(t))
//				return false;
		return true;
	}

	@Override
	public boolean beginne(T t) {
//		if (!istAktiviert(t))
//			return false;
//		for (Stelle<Integer> s : vorgänger(t))
//			s.wert -= kantenGewichte.get(new Paar<Stelle,Transition>(s, t));
//		t.status = Transition.AKTIV;
		return true;
	}

	@Override
	public boolean beende(T t) {
//		if (!(t.status == Transition.AKTIV))
//			return false;
//		for (Stelle<Integer> s : nachfolger(t))
//			s.wert += kantenGewichte.get(new Paar<Transition,Stelle>(t, s));
//		t.status = Transition.INAKTIV;
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

	@Override
	public boolean istAktiviert(T t) {
		// TODO Auto-generated method stub
		return false;
	}

//	public void setMarkierung(Object stelle, int anzMarken) {	}
//
//	public void setKapazität(Object stelle, int kapazität) {	}
//	
//	public void setGewicht(Object k1,Object k2, int gewicht) {	}
}
