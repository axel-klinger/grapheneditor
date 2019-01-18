package informatik.petrinetze;

public interface Transition extends PetriNetzElement {

	static final int INAKTIV = 0;
	static final int AKTIV = 1;

	public void setZustand(Object o);
	
	public Object getZustand();
	
//	Object inhalt;
//	int status; // AKTIV, INAKTIV, d.h. wird gerade ausgeführt oder nicht
//	
//	public Transition (Object inhalt) {
//		this.inhalt = inhalt;
//	}
//
//	public String toString() {
//		return inhalt.toString();
//	}

}
