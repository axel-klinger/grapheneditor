/*
 * Created on 16.12.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

//import informatik.strukturen.petrinetze.PetriNetz;
//import grapheneditor.GraphenModell;
//import grapheneditor.GraphenModellEinAusgabe;
//import informatik.strukturen.graphen.SchlichterGraph;
import informatik.petrinetze.StellenTransitionenPetriNetz;

/**
 * @author klinger
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PetriNetzTest {

	public static void main(String[] args) {
		
        // Hier 3 Tests f�r 
        //  - BedingungsEreignisPetriNetz
        //  - StellenTransitionenPetriNetz
        //  - FarbigesPetriNetz
		
//		GraphenModell modell = GraphenModellEinAusgabe.loadXML("D:/test.graph");
//		SchlichterGraph g = modell.getGraph();
//		sop(g);
		
		StellenTransitionenPetriNetz netz = new StellenTransitionenPetriNetz();

		
//		netz.hinzuf�genStelle("START");
//		netz.hinzuf�genStelle("S1");
//		netz.hinzuf�genStelle("S2");
//		netz.hinzuf�genStelle("S3");
//		netz.hinzuf�genStelle("S4");
//		netz.hinzuf�genStelle("ENDE");
//		
//		netz.hinzuf�genTransition("Grundlagen");
//		netz.hinzuf�genTransition("Baugenehmigung");
//		netz.hinzuf�genTransition("Holzhaus planen");
//		netz.hinzuf�genTransition("Steinhaus planen");
//		netz.hinzuf�genTransition("Bauen");
//		
//		netz.hinzuf�genRelation("START", "Grundlagen");
//		netz.hinzuf�genRelation("Grundlagen", "S1");
//		netz.hinzuf�genRelation("S1", "Baugenehmigung");
//		netz.hinzuf�genRelation("Baugenehmigung", "S2");
//		netz.hinzuf�genRelation("S2", "Bauen");
//		netz.hinzuf�genRelation("Bauen", "ENDE");
//		netz.hinzuf�genRelation("Grundlagen", "S3");
//		netz.hinzuf�genRelation("S3", "Holzhaus planen");
//		netz.hinzuf�genRelation("S3", "Steinhaus planen");
//		netz.hinzuf�genRelation("Holzhaus planen", "S4");
//		netz.hinzuf�genRelation("Steinhaus planen", "S4");
//		netz.hinzuf�genRelation("S4", "Bauen");
//		
//		netz.setMarkierung("Start", 1);
//		
//		sop(netz);
//		sop("Free-Choice: " + netz.isFreeChoice());
		// Schalten
		// - ENDE erreichbar? (existiert ein Weg -> konsistenz WF)
		// - Endmarkierung erreichbar 
		// - Keine Deadlocks? (s.u.)
		
		
		// Zu WorkflowGraphen:
		// - Instanzgraphen
		// - Konsistenz
		// - Korrektheit
	}
	
	public static void sop(Object o) { System.out.println(o); }
}
