package informatik.petrinetze;

import java.util.Date;

public class ZeitPetriNetz<S,T,C> extends PetriNetz<S,T,C,Date> {

	@Override
	public boolean istAktiviert(T t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean beginne(T t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean beende(T t) {
		// TODO Auto-generated method stub
		return false;
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
