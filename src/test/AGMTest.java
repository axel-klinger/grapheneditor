package test;

import temp.AllgemeinesGraphenModell;
import temp.typen.Aktivität;
import temp.typen.Person;
import temp.typen.Zustand;

public class AGMTest {

	public static void main(String[] args) {

		AllgemeinesGraphenModell agm = new AllgemeinesGraphenModell();
		
		agm.hinzufügen(Person.class);
		agm.hinzufügen(Aktivität.class);
		agm.hinzufügen(Zustand.class);
		
		agm.hinzufügen(Aktivität.class, Person.class);
		agm.hinzufügen(Aktivität.class, Zustand.class);
		agm.hinzufügen(Zustand.class, Aktivität.class);
		
		// ============
		
		//agm.hinzufügen( new Person("001") );
	}

}
