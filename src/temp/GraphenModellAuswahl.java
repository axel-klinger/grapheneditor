package temp;

import grapheneditor.KantenAnsicht;
import grapheneditor.KnotenAnsicht;
import informatik.strukturen.Menge;

import java.awt.Color;


public class GraphenModellAuswahl {

	// Benannte Mengen von Knoten und benannte Mengen von Kanten
	// jeweils mit Informationen zur Linienfarbe und -art.
	public Menge<KnotenAnsicht> knotenAuswahl;
	public Menge<KantenAnsicht>  kantenAuswahl;
	private Color linienFarbe;
	private Color füllFarbe;
	
	public GraphenModellAuswahl() {
		reset();
	}
	
	public void reset() {
		knotenAuswahl = new Menge<KnotenAnsicht>();
		kantenAuswahl = new Menge<KantenAnsicht>();
		linienFarbe = Color.BLACK;
		füllFarbe = Color.WHITE;
	}
	
	
}


