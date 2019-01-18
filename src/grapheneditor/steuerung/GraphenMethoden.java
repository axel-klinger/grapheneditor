package grapheneditor.steuerung;

import java.awt.Color;
import java.util.Random;

import grapheneditor.KnotenElement;
import grapheneditor.GraphenModell;
import grapheneditor.KantenAnsicht;
import grapheneditor.KnotenAnsicht;
import informatik.strukturen.Folge;
import informatik.strukturen.Menge;
import informatik.strukturen.Paar;
import informatik.strukturen.graphen.SchlichterGraph;
import informatik.strukturen.graphen.hilfsmittel.GraphenAufgaben;
import informatik.strukturen.graphen.hilfsmittel.GraphenEigenschaften;
import informatik.strukturen.graphen.wege.Weg;

/**
 * <P><B>GraphenSteuerung</B> <I>(engl. graph control)</I>: Die Graphensteuerung 
 * beinhaltet Mehtoden f�r die Manipulation des Graphen und f�r einfach 
 * Graphenaufgaben.</P>
 * 
 * @author klinger
 */
public class GraphenMethoden {

	private GraphenModell 	modell;
//	private GeometrischesGraphenModell ansicht;
//	private GraphenDiagrammInteraktiv ansicht;
	
	public GraphenMethoden(GraphenModell modell) {
		this.modell = modell;
//		this.ansicht = ansicht;
	}
	
	
//	public void hinzuf�gen(Knoten k) {
//		modell.addNode(k.getObjekt());
////		ansicht.knotenMenge.put(k.getObjekt(), k);
//		
//		// ansicht.aktualisieren
//	}
//	
//	public void entfernen(Knoten k) {
//		modell.removeNode(k.getObjekt());
////		ansicht.knotenMenge.remove(k.getObjekt());
//		
//		// ansicht.aktualisieren
//	}
//	
//	public void hinzuf�gen(Kante k) {
//		modell.addEdge(k.start.getObjekt(), k.ziel.getObjekt());
//		ansicht.kantenMenge.put(new Paar<Element,Element>(k.start.getObjekt(), 
//														  k.ziel.getObjekt()), k);
//		
//		// ansicht.aktualisieren
//	}
//	
//	public void entfernen(Kante k) {
//		modell.removeEdge(k.start.getObjekt(), k.ziel.getObjekt());
//		ansicht.kantenMenge.remove(new Paar<Element,Element>(k.start.getObjekt(), 
//															 k.ziel.getObjekt()));
//		
//		// ansicht.aktualisieren
//	}
//	
//	public void verschieben(Knoten k, int dx, int dy) {
//		
//	}
	
//	knotenMenge.clear();
//	kantenMenge.clear();

	
	public void knotenDisjunkteWege(KnotenElement start, KnotenElement ziel) {
		if (start == null || ziel == null) return;
		Menge<Weg<KnotenElement>> M = GraphenAufgaben.knotenDisjunkteWege(modell.getGraph(), start, ziel);
		KantenF�rben(M);
	}
	
	public void kantenDisjunkteWege(KnotenElement start, KnotenElement ziel) {
		if (start == null || ziel == null) return;
		Menge<Weg<KnotenElement>> M = GraphenAufgaben.kantenDisjunkteWege(modell.getGraph(), start, ziel);
		KantenF�rben(M);
	}

	public void wegSuchen() {	// ???
//		FarbenZur�cksetzen();
//		Weg w = GraphenAufgaben.getWeg(modell.getGraph());
		
	}
	
	public void strengeKomponenten() {
		ZusammenhangsKomponenten(GraphenAufgaben.STRENG);
	}

	public void pseudoStrengeKomponenten() {
		ZusammenhangsKomponenten(GraphenAufgaben.PSEUDO);
	}

	public void schwacheKomponenten() {
		ZusammenhangsKomponenten(GraphenAufgaben.SCHWACH);
	}

	private void ZusammenhangsKomponenten(int st�rke) {
		SchlichterGraph<KnotenElement> g = modell.getGraph();
		Menge<Menge<KnotenElement>> M = GraphenAufgaben.zusammenhangsKomponenten(g, st�rke);
		KnotenF�rben(M);
	}

	public void topologischSortieren() {
		SchlichterGraph<KnotenElement> g = modell.getGraph();
		GraphenEigenschaften<KnotenElement> ge = new GraphenEigenschaften<KnotenElement>(g);
		if (!ge.istAzyklisch())
			return;
		Folge<Menge<KnotenElement>> F = GraphenAufgaben.topologischSortieren(g, true);
		KnotenF�rben(F);
	}

	
    // ==== Knoten und Kanten farbig markieren ====
    
    
	private void FarbenZur�cksetzen() {
    	for (KnotenAnsicht k: modell.knotenMenge.values()) {
    		k.f�llFarbe = Color.WHITE;
    		k.linienFarbe = Color.BLACK;
    	}
    	for (KantenAnsicht k : modell.kantenMenge.values())
    		k.linienFarbe = Color.BLACK;
	}

	private void KantenF�rben(Menge<Weg<KnotenElement>> M) {
		Random r = new Random();
		Color c = null;
		FarbenZur�cksetzen();
		for (Weg<KnotenElement> m : M) {
			c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			for (Paar<KnotenElement,KnotenElement> p : m.kantenFolge()) {
				modell.kantenMenge.get(new Paar<KnotenElement,KnotenElement>(p.eins(), p.zwei())).linienFarbe = c;
			}
		}
	}

	private void KnotenF�rben(Menge<Menge<KnotenElement>> M) {
		FarbenZur�cksetzen();
		Random r = new Random();
		Color c = null;
		for (Menge m : M) {
			c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			for (Object o : m) {
				modell.knotenMenge.get(o).f�llFarbe = c;
			}
		}

	} 
	
	private void KnotenF�rben(Folge<Menge<KnotenElement>> F) {
		FarbenZur�cksetzen();
		Random r = new Random();
		Color c = new Color(255, 255, 255);
		int step = 0;
		if (F.anzahl()>0) 
			step = 255 / F.anzahl();
		for (Menge m : F) {
			for (Object o : m) {
				modell.knotenMenge.get(o).f�llFarbe = c;
			}
			c = new Color(c.getRed()-step, c.getGreen()-step, c.getBlue()-step);
		}

	} 

}
