package test;

//import java.awt.Color;

/*

import pme.GraphenModell;
import pme.GraphenModellEinAusgabe;

//import grapheneditor.Kante;
//import grapheneditor.Knoten;
//import informatik.strukturen.Folge;
//import informatik.strukturen.Menge;
import informatik.strukturen.graphen.SchlichterGraph;
//import informatik.strukturen.graphen.hilfsmittel.GraphenAufgaben;
//import informatik.strukturen.graphen.hilfsmittel.GraphenEinAusgabe;
*/

public class TopSortTest {

	public static void main(String[] args) {
		
/*		GraphenModell modell = GraphenModellEinAusgabe.loadXML("D:/test.graph");
		SchlichterGraph g = modell.getGraph();
		sop(g);

		//g.hinzuf�gen("K7");		// Das GraphenModell m�sste den Graph beobachten!!!
//		modell.removeNode("K0");
//		modell.removeNode("K1");
//		modell.getNode("K2").f�llFarbe = Color.green;
//		modell.addEdge(new Kante(modell.getNode("K3"), modell.getNode("K2")));

/*		Folge<Menge> M = GraphenAufgaben.topologischSortieren(g);
		sop(M);
		
		Color c = new Color(255, 255, 255);
		int step = 0;
		if (M.anzahl()>0) 
			step = 255 / M.anzahl();
		for (Menge m : M) {
			for (Object o : m) {
				modell.getNode(o).f�llFarbe = c;
			}
			c = new Color(c.getRed()-step, c.getGreen()-step, c.getBlue()-step);
		}
//		sop(g);
		
		GraphenModellEinAusgabe.saveXML(modell, "D:/test2.graph");	*/
	}

	public static void sop(Object o) {
		System.out.println(o);
	}
}
