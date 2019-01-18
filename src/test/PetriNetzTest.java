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
		
        // Hier 3 Tests für 
        //  - BedingungsEreignisPetriNetz
        //  - StellenTransitionenPetriNetz
        //  - FarbigesPetriNetz
		
//		GraphenModell modell = GraphenModellEinAusgabe.loadXML("D:/test.graph");
//		SchlichterGraph g = modell.getGraph();
//		sop(g);
		
		StellenTransitionenPetriNetz netz = new StellenTransitionenPetriNetz();

		
//		netz.hinzufügenStelle("START");
//		netz.hinzufügenStelle("S1");
//		netz.hinzufügenStelle("S2");
//		netz.hinzufügenStelle("S3");
//		netz.hinzufügenStelle("S4");
//		netz.hinzufügenStelle("ENDE");
//		
//		netz.hinzufügenTransition("Grundlagen");
//		netz.hinzufügenTransition("Baugenehmigung");
//		netz.hinzufügenTransition("Holzhaus planen");
//		netz.hinzufügenTransition("Steinhaus planen");
//		netz.hinzufügenTransition("Bauen");
//		
//		netz.hinzufügenRelation("START", "Grundlagen");
//		netz.hinzufügenRelation("Grundlagen", "S1");
//		netz.hinzufügenRelation("S1", "Baugenehmigung");
//		netz.hinzufügenRelation("Baugenehmigung", "S2");
//		netz.hinzufügenRelation("S2", "Bauen");
//		netz.hinzufügenRelation("Bauen", "ENDE");
//		netz.hinzufügenRelation("Grundlagen", "S3");
//		netz.hinzufügenRelation("S3", "Holzhaus planen");
//		netz.hinzufügenRelation("S3", "Steinhaus planen");
//		netz.hinzufügenRelation("Holzhaus planen", "S4");
//		netz.hinzufügenRelation("Steinhaus planen", "S4");
//		netz.hinzufügenRelation("S4", "Bauen");
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
