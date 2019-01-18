package temp;

import java.util.HashMap;

import grapheneditor.KnotenElement;
import grapheneditor.GraphenModell;
import grapheneditor.KantenAnsicht;
import grapheneditor.KnotenAnsicht;
import informatik.strukturen.Menge;

public class NpartitesGraphenModell extends GraphenModell {

	private static final long serialVersionUID = 1L;

	private Menge<KnotenMenge> 	  knotenMengen;
	private Menge<KantenRelation> kantenRelationen;
	
	private HashMap<KnotenMenge,HashMap<KnotenAnsicht,Object[]>>   knotenBewertungen;
	private HashMap<KantenRelation,HashMap<KantenAnsicht,Object[]>> kantenBewertungen;
	
	public NpartitesGraphenModell() {
		super( new KnotenElement("WURZEL"));
		knotenMengen = new Menge<KnotenMenge>();
		kantenRelationen = new Menge<KantenRelation>();
	}
	
	void hinzufügen(KnotenMenge menge) {
		knotenMengen.hinzufügen(menge);
	}
	
	void hinzufügen(KantenRelation relation) {
		kantenRelationen.hinzufügen(relation);
	}
}
