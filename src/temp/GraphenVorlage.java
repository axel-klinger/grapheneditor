package temp;

import grapheneditor.KnotenElement;
import grapheneditor.GraphenModell;
import informatik.strukturen.Menge;

public class GraphenVorlage {

	private String name;
	private String beschreibung;
	private String typ;
	
	private Menge<MengenVorlage> mengen;
	private Menge relationen;
	private Menge eigenschaften;
	private Menge methoden;
	
	public GraphenVorlage (String name) {
		this.name = name;
		beschreibung = "";
		typ = null;
		mengen 		  = new Menge<MengenVorlage>();
		relationen    = new Menge();
		eigenschaften = new Menge();
		methoden	  = new Menge();
	}

	public GraphenModell neueInstanz() {
		GraphenModell modell = new GraphenModell(new KnotenElement("WURZEL"));
		for (MengenVorlage mv : mengen) {
//			Class x = Class.forName(mv.typ);
			KnotenMenge m = new KnotenMenge(mv.name, mv.typ, mv.knotenForm);
//	
//			modell.addMenge(m);
		}
		return modell;
	}
	
	
}

class MengenVorlage {
	public String name = "";
	public String kürzel = "";
	public String knotenForm = "";
	public String typ = "";
	
	MengenVorlage() {
		
	}
}

class RelationenVorlage {
	public String anfangsMenge = "";
	public String endMenge = "";
	public String totalität = "";
	public String eindeutigkeit = "";

	RelationenVorlage() {
		
	}
}