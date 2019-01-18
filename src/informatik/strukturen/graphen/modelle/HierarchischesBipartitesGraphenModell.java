package informatik.strukturen.graphen.modelle;

import java.util.Observable;

import informatik.strukturen.Menge;
import informatik.strukturen.Relation;
import informatik.strukturen.graphen.Baum;
import informatik.strukturen.graphen.Graph;
import informatik.strukturen.graphen.SchlichterGraph;
import informatik.strukturen.graphen.BipartiterGraph;


/**
 *	<P><B>HierarchischesBipartitesGraphenModell</B> <I>(engl. 
 *	hierarchical bipartite graph model)</I>: 
 *	Ein hierarchisches bipartites Graphenmodell beschreibt einen Bipartiten 
 *	Graphen, der hierarchisch aufgebaut ist. Als Konsistenzbedingung (hier noch 
 *	nicht integriert) wird gefordert, dass verfeinerte Knoten mit ihrem Typ 
 *	berandet sind, d.h. in der Verfeinerung eines Knotens der ersten Menge 
 *	dürfen nur Knoten der ersten Menge eine Verbindung zu Knoten ausserhalb 
 *	der Verfeinerung haben. Gleiches gilt für die zweite Knotenmenge.  
 *
 * @author klinger
 *
 * @param <A> Typ der ersten Menge extends C
 * @param <B> Typ der zweiten Menge extends C
 * @param <C> gemeinsamer Typ von A und B
 */
public abstract class HierarchischesBipartitesGraphenModell<A,B,C> extends Observable {
	
	protected BipartiterGraph<A,B> struktur;
	protected Baum<C> hierarchie;
	protected C wurzel;
	
	protected boolean gerichtet = true;
	public boolean istGerichtet() {
		return gerichtet;
	}
	
	/**
	 * Erzeugt ein leeres hierarchisches bipartites Graphenmodell zu einer 
	 * gegebenen Wurzel.
	 * 
	 * @param wurzel
	 */
	public HierarchischesBipartitesGraphenModell(C wurzel) {
		struktur = new BipartiterGraph<A,B>();
		hierarchie = new Baum<C>(wurzel);
		this.wurzel = wurzel;
	}
	
	/**
	 * Erzeugt ein hierarchisches bipartites Graphenmodell aus zwei Mengen,
	 * den Relationen zwischen den Mengen und der Hierarchie über der 
	 * Vereinigung der beiden Mengen.
	 * 
	 * @param M1	erste Knotenmenge
	 * @param M2	zweite Knotenmenge
	 * @param R12	Relation zwischen M1 und M2
	 * @param R21	Relation zwischen M2 und M1
	 * @param hierarchie	über den Elementen von M1 U M2
	 */
	public HierarchischesBipartitesGraphenModell(Menge<A> M1, Menge<B> M2,
			   Relation<A,B> R12, Relation<B,A> R21,
			   Baum<C> hierarchie) {
		struktur = new BipartiterGraph<A,B>(M1, M2, R12, R21);
		this.hierarchie = hierarchie; 
		this.wurzel = hierarchie.getWurzel();
	}
	
	/**
	 * Liefert die Struktur des hierarchischen bipartiten Graphenmodells in
	 * Form eines bipartiten Graphen. Die Struktur ist der bipartite 
	 * Graph := (M1, M2; R12, R21).
	 * 
	 * @return Struktur des h. b. Graphenmodells in Form eines bipartiten Graphen
	 */
	public BipartiterGraph<A,B> getStruktur() {
		return struktur;
	}

	/**
	 * Liefert die Hierarchie des hierarchischen bipartiten Graphenmodells in 
	 * Form eines Baums. Die Hierarchie ist der Baum := ((M1 U M2) -> (M1 U M2 U Wurzel))
	 * 
	 * @return Hierarchie des h. B. Graphenmodells in Form eines Baumes
	 */
	public Baum<C> getHierarchie() {
		return hierarchie;
	}

	/**
	 * Fügt ein Element in das hierarchische bipartite Graphenmodell ein. 
	 */
	public abstract boolean hinzufügen(C element);
//	{
//		boolean hatFunktioniert = false;
//		
//		if (element instanceof A)
//			hatFunktioniert = struktur.hinzufügen1((A) element);
//		else if (element instanceof B)
//			hatFunktioniert = struktur.hinzufügen2((B) element);
//			
//		if (hatFunktioniert) {
//			hierarchie.hinzufügen(wurzel, element);
//			setChanged();
//			return true;
//		}
//		return false;
//	}

	/**
	 * Fügt eine Beziehung von element1 zu element2 in das hierarchische bipartite
	 * Graphenmodell ein.
	 * 
	 * @param element1
	 * @param element2
	 * @return true wenn die Beziehung hinzugefügt werden konnte, sonst false
	 */
	public abstract boolean hinzufügen(C element1, C element2);
//	{
//		boolean hatFunktioniert = false;
//		if (element1 instanceof A && element2 instanceof B)
//			hatFunktioniert = struktur.hinzufügen12((A) element1, (B) element2);
//		else if (element1 instanceof B && element2 instanceof A)
//			hatFunktioniert = struktur.hinzufügen21((B) element1, (A) element2);
//		
//		if (hatFunktioniert) {
//			setChanged();
//			return true;
//		}
//		return false;
//	}

	/**
	 * Entfernt das element aus dem hierarchischen bipartiten Graphenmodell.
	 * 
	 * @param element
	 */
	public Baum<C> entfernen(C element) {
		Baum<C> ast = new Baum<C>(element);
		struktur.entfernen(element);
		ast = hierarchie.entfernen(element);
		for (C p : ast.knoten())
			struktur.entfernen(p);
		setChanged();
		return ast;
	}

	/**
	 * Entfernt eine Beziehung von element1 zu element2 aus dem hierarchischen 
	 * bipartiten Graphenmodell.
	 * 
	 * @param element1
	 * @param element2
	 */
	public void entfernen(C element1, C element2) {
		struktur.entfernen(element1, element2);
		setChanged();
	}

	/**
	 * Fügt der Verfeinerung von grob das Element fein hinzu.
	 * 
	 * @param grob
	 * @param fein
	 */
	public void verfeinern(C grob, C fein) {
		if (!struktur.enthält(grob))
			return;
		if (!struktur.enthält(fein))
			hinzufügen(fein);
		Baum<C> b = hierarchie.entfernen(fein);
		hierarchie.hinzufügen(grob, b);
		setChanged();
	}

	/**
	 * Liefert die Menge aller Startknoten. Ein Startknoten ist ein Knoten
	 * der keine Vorgänger besitzt.
	 * 
	 * @return Menge der Startknoten
	 */
	public Menge<C> startKnoten() {
		Graph<C> g = getGraph();
		Menge<C> startKnoten = new Menge<C>();
		for (C e : g.knoten())
			if (g.vorgänger(e).anzahl() == 0)
				startKnoten.hinzufügen(e);
		return startKnoten;
	}
	
	/**
	 * Liefert die Menge aller Endknoten. Ein Endknoten ist ein Knoten
	 * der keine Nachfolger besitzt.
	 * 
	 * @return Menge der Endknoten
	 */
	public Menge<C> endKnoten() {
		Graph<C> g = getGraph();
		Menge<C> endKnoten = new Menge<C>();
		for (C e : g.knoten())
			if (g.nachfolger(e).anzahl() == 0)
				endKnoten.hinzufügen(e);
		return endKnoten;
	}

	public Menge<C> getEltern() {
		Menge<C> eltern = new Menge<C>();
		for (C pe : hierarchie.knoten())
			if (hierarchie.anzahlNachfolger(pe) > 0)
				eltern.hinzufügen(pe);
		return eltern;
	}
	
	/**
	 * Liefert die Struktur zwischen den vereinigten Mengen in Form eines 
	 * schlichten Graphen zurück.
	 * 
	 * @return
	 */
	public abstract SchlichterGraph<C> getGraph(); 
//	{
//		SchlichterGraph<C> graph = new SchlichterGraph<C>();
//		for (A a : struktur.alleKnoten1())
//			graph.hinzufügen((C) a);
//		for (B b : struktur.alleKnoten2())
//			graph.hinzufügen((C) b);
//		for (Paar<A,B> p : struktur.alleKanten12())
//			graph.hinzufügen((C) p.eins(), (C) p.zwei());
//		for (Paar<B,A> p : struktur.alleKanten21())
//			graph.hinzufügen((C) p.eins(), (C) p.zwei());
//		return graph;
//	}
}
