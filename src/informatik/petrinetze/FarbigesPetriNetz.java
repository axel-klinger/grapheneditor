package informatik.petrinetze;

import informatik.strukturen.Menge;

public class FarbigesPetriNetz<S,T,C,K> extends PetriNetz<S,T,C,Menge<K>> {

	public FarbigesPetriNetz() {
		super();

	}

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
	public void hinzuf�gen(C knoten) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hinzuf�gen(C k1, C k2) {
		// TODO Auto-generated method stub
		
	}

}
