package test;

import temp.AllgemeinesGraphenModell;
import temp.typen.Aktivit�t;
import temp.typen.Person;
import temp.typen.Zustand;

public class AGMTest {

	public static void main(String[] args) {

		AllgemeinesGraphenModell agm = new AllgemeinesGraphenModell();
		
		agm.hinzuf�gen(Person.class);
		agm.hinzuf�gen(Aktivit�t.class);
		agm.hinzuf�gen(Zustand.class);
		
		agm.hinzuf�gen(Aktivit�t.class, Person.class);
		agm.hinzuf�gen(Aktivit�t.class, Zustand.class);
		agm.hinzuf�gen(Zustand.class, Aktivit�t.class);
		
		// ============
		
		//agm.hinzuf�gen( new Person("001") );
	}

}
