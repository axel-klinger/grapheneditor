package temp;

import informatik.strukturen.Menge;
import informatik.strukturen.Paar;
import informatik.strukturen.Relation;

import java.util.HashMap;


public class AllgemeinesGraphenModell {

	private HashMap<Class,Menge> mengen;
	private HashMap<Paar<Class,Class>,Relation> relationen;
	
	public AllgemeinesGraphenModell() {
		mengen 	   = new HashMap<Class,Menge>();
		relationen = new HashMap<Paar<Class,Class>,Relation>();
		// -> die Relationen in den Knoten speichern -> Geschwindigkeit!
		// --> bedeutet neu kompilieren der Strukturen
	}
	
	public void hinzuf�gen(Class typ, Menge M) {
		mengen.put(typ, M);
	}
	public void hinzuf�gen(Paar<Class,Class> typen, Relation R) {
		relationen.put(typen, R);
	}

	public <E extends Object> void hinzuf�gen(Class<E> name) {
		// TODO Auto-generated method stub
		
	}

	public <A extends Object, B extends Object> void hinzuf�gen(Class<A> name, Class<B> name2) {
		// TODO Auto-generated method stub
		
	}
}
