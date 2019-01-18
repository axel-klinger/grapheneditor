package test;

import grapheneditor.KnotenElement;
import grapheneditor.GraphenModell;
import grapheneditor.GraphenModellEinAusgabe;
import informatik.strukturen.Menge;
import informatik.strukturen.graphen.SchlichterGraph;
import informatik.strukturen.graphen.hilfsmittel.GraphenAufgaben;

import java.awt.Color;
import java.util.Random;

public class StrengeKomponentenTest {

	public static void main(String[] args) {
		GraphenModell modell = GraphenModellEinAusgabe.loadXML("D:/ns.graph");
		SchlichterGraph<KnotenElement> g = modell.getGraph();
		sop(g);

		Menge<Menge<KnotenElement>> M = GraphenAufgaben.zusammenhangsKomponenten(g, GraphenAufgaben.STRENG);
		sop(M);
		
		
		Random r = new Random();
		Color c = new Color(255, 255, 255);
		int step = 0;
		if (M.anzahl()>0) 
			step = 255 / M.anzahl();
		for (Menge m : M) {
			for (Object o : m) {
//				modell.getNode(o).füllFarbe = c;
			}
			c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
		}
		
		GraphenModellEinAusgabe.saveXML(modell, "D:/ns2.graph");
	}

	public static void sop(Object o) {
		System.out.println(o);
	}
}
