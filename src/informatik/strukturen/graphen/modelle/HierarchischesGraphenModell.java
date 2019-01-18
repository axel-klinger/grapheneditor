package informatik.strukturen.graphen.modelle;

import java.util.Observable;

import informatik.strukturen.Menge;
import informatik.strukturen.Relation;
import informatik.strukturen.graphen.Baum;
import informatik.strukturen.graphen.Graph;
import informatik.strukturen.graphen.SchlichterGraph;

/**
 *	<P><B>HierarchischesGraphenModell</B> <I>(engl. 
 *	hierarchical graph model)</I>: 
 *	Ein hierarchisches Graphenmodell beschreibt einen Schlichten 
 *	Graphen, der hierarchisch aufgebaut ist. 
 *
 * @author klinger
 *
 * @param <A>
 */
public class HierarchischesGraphenModell<A> extends Observable {
	
	protected SchlichterGraph<A> struktur;
	protected Baum<A> hierarchie;
	protected A wurzel;
	
	public HierarchischesGraphenModell(A wurzel) {
		struktur = new SchlichterGraph<A>();
		hierarchie = new Baum<A>(wurzel);
		this.wurzel = wurzel;
	}
	
	public HierarchischesGraphenModell(Menge<A> M, 
							   Relation<A,A> R, 
							   Baum<A> hierarchie) {
		struktur = new SchlichterGraph<A>(M, R);
		this.hierarchie = hierarchie; 
		this.wurzel = hierarchie.getWurzel();
	}

	/**
	 * Fügt ein ProzessElement in en hierarchischen Graphen ein. 
	 */
	public boolean hinzufügen(A element) {
		return (struktur.hinzufügen(element)) ? 
				hierarchie.hinzufügen(wurzel, element) : false;
	}

	/**
	 * Fügt eine Beziehung von element1 zum element2 hierarchischen Graphen hinzu.
	 * 
	 * @param element1
	 * @param element2
	 * @return true wenn die Beziehung hinzugefügt werden konnte, sonst false
	 */
	public boolean hinzufügen(A element1, A element2) {
		return struktur.hinzufügen(element1, element2);
	}

	/**
	 * Entfernt das element aus der ProzessStruktur.
	 * 
	 * @param element
	 */
	public Baum<A> entfernen(A element) {
		Baum<A> ast = new Baum<A>(element);
		struktur.entfernen(element);
		ast = hierarchie.entfernen(element);
		for (A p : ast.knoten())
			struktur.entfernen(p);
		setChanged();
		return ast;
	}

	/**
	 * Entfernt eine Beziehung von element1 zu element2 aus der ProzessStruktur.
	 * 
	 * @param element1
	 * @param element2
	 */
	public void entfernen(A element1, A element2) {
		struktur.entfernen(element1, element2);
		setChanged();
	}

	/**
	 * Fügt der Verfeinerung von grob das Element fein hinzu.
	 * 
	 * @param grob
	 * @param fein
	 */
	public void verfeinern(A grob, A fein) {
		if (!struktur.enthält(grob))
			return;
		if (!struktur.enthält(fein))
			hinzufügen(fein);
		Baum<A> b = hierarchie.entfernen(fein);
		hierarchie.hinzufügen(grob, b);
		setChanged();
	}

	/**
	 * Liefert die Menge aller Startknoten. Ein Startknoten ist ein Knoten
	 * der keine Vorgänger besitzt.
	 * 
	 * @return Menge der Startknoten
	 */
	public Menge<A> startKnoten() {
		Graph<A> g = getGraph();
		Menge<A> startKnoten = new Menge<A>();
		for (A e : g.knoten())
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
	public Menge<A> endKnoten() {
		Graph<A> g = getGraph();
		Menge<A> endKnoten = new Menge<A>();
		for (A e : g.knoten())
			if (g.nachfolger(e).anzahl() == 0)
				endKnoten.hinzufügen(e);
		return endKnoten;
	}

	public Menge<A> getEltern() {
		Menge<A> eltern = new Menge<A>();
		for (A pe : hierarchie.knoten())
			if (hierarchie.anzahlNachfolger(pe) > 0)
				eltern.hinzufügen(pe);
		return eltern;
	}
	
	public SchlichterGraph<A> getGraph() {
		return struktur;
	}
}
