
package informatik.petrinetze;

import java.util.HashMap;

import informatik.strukturen.graphen.BipartiterGraph;

/**
 * <P><B>Petri-Netz</B> <I>(engl. petri net)</I>:
 * Ein Petri-Netz ist ein bipartites Graphenmodell zur Steuerung und Kontrolle 
 * von Arbeitsabläufen. Ein Petri-Netz besteht aus einer Menge von Transitionen
 * (Aktivitäten) und einer Menge von Stellen (Zuständen), sowie den 
 * Anordnungsbeziehungen zwischen den Elementen dieser Mengen.</P>
 * <P>Für unterschiedlich abstrakte Ablaufmodelle gibt es verschiedene Typen von
 * Petri-Netzen. Im Wesentlichen unterscheiden sich diese Typen durch die Typen
 * der Token (Marken/Spielsteine), mit denen ein Petri-Netz markiert wird.</P>
 * <P>Die abstrakte Klasse PetriNetz bildet die Grundlage für:
 * <LI>BedingungsEreignisNetz: Zustände können {wahr,falsch} sein
 * <LI>StellenTransitionenNetz: ...
 * <LI>...
 * </P>
 */
public abstract class PetriNetz<S,T,C,TokenTyp> extends BipartiterGraph<S,T> {

	protected HashMap<S,TokenTyp> markierung;
//	private HashMap<Stelle,TokenTyp> markierung;	// enthalten in Stellen !!!

	public PetriNetz() {
		super();
		markierung = new HashMap<S,TokenTyp>();
//		markierung  = new HashMap<Stelle,TokenTyp>();
	}
	
	public PetriNetz(BipartiterGraph<S,T> struktur) {
		super(struktur.alleKnoten1(), struktur.alleKnoten2(), 
			struktur.alleKanten12(), struktur.alleKanten21());
		markierung = new HashMap<S,TokenTyp>();
	}
	
	public void markiereStartKnoten(TokenTyp startToken) {
		for (S s : this.alleKnoten1())
			if (vorgänger(s).anzahl() == 0)
				markierung.put(s, startToken);
				
			
	}
	
	public abstract void hinzufügen(C knoten);
//	{
//		if (knoten instanceof Stelle)
//			hinzufügen1((Stelle) knoten);
//		else if (knoten instanceof Transition)
//			hinzufügen2((Transition) knoten);
//	}

	public abstract void hinzufügen(C k1, C k2);
//	{
//		if (k1 instanceof Stelle && k2 instanceof Transition)
//			hinzufügen12((Stelle) k1, (Transition) k2);
//		else if (k1 instanceof Transition && k2 instanceof Stelle)
//			hinzufügen21((Transition) k1, (Stelle) k2);
//	}

	/**
	 * Prüft ob eine Transition aktiviert ist. Eine Transition ist
	 * aktiviert unter einer Markierung M (geschrieben M[t>), wenn
	 * die Markierung aller Vorgängerstellen größer ist als die jeweiligen
	 * Kantengewichte.
	 */
	public abstract boolean istAktiviert(T t);
	
	/**
	 * Schaltet eine Transition. Eine Transition kann schalten, wenn sie 
	 * aktiviert ist. Das Schalten einer Transition erfolgt hier in zwei 
	 * Schritten: Beginnen und Beenden.
	 */
	public void schalte(T t) {
		if (!istAktiviert(t))
			return;
		beginne(t);
		beende(t);
	}

	public abstract boolean beginne(T t);
	
	public abstract boolean beende(T t);
	
	
	/**
	 *	Setzt die Markierung des Netzes.
	 */
//	public void markierung (Menge marken) {
//		M = marken;
//	}
//
	/**
	 *	Liefert die Menge der Markierungen des Petri-Netzes.
	 */
//	public Menge markierung () {
//		return M;
//	}
//

	//-----------------------------------------
	// PETRI-NETZ-EIGENSCHAFTEN
	//-----------------------------------------
	// Bezogen auf die Markierung
	//-----------------------------------------
//	public boolean isBounded(int bound) {
//		return false;
//	}
//	
//	public boolean isLive(HashMap Markierung) {
//		return false;
//	}	
//	
//	public boolean isSave() {
//		return false;
//	}
//	
//	public boolean isWellFormed() {
//		return false;
//	}
//	
//	public boolean isConflictFree() {
//		return false;
//	}
	
	//-----------------------------------------
	// Bezogen auf die Struktur
	//-----------------------------------------
//	public boolean isStronglyConnected() {
//		return false;
//	}
//	
//	public boolean isFreeChoice() {
//		//Wenn zwei Transitionen einen Vorgänger gemeinsam
//		//haben, dann haben sie alle Vorgänger gemeinsam, 
//		//sonst ist das Netz nicht FreeChoice.
//		
//		// ALLE x AUS T ALLE y aus T : 
//		//		(!x.vg().durchschnitt(y.vg()).anzahl() == 0) || x.vg().equals(y.vg())
//		for (Transition x : alleKnoten2())
//			for (Transition y : alleKnoten2())
//				if ((vorgänger(x).durchschnitt(vorgänger(y)).anzahl() != 0) 
//					&& (!vorgänger(x).equals(vorgänger(y))))
//					return false;
//		return true;
//	}
}

//class Stelle {	}
//class Transition {	}