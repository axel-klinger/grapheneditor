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
	 * F�gt ein ProzessElement in en hierarchischen Graphen ein. 
	 */
	public boolean hinzuf�gen(A element) {
		return (struktur.hinzuf�gen(element)) ? 
				hierarchie.hinzuf�gen(wurzel, element) : false;
	}

	/**
	 * F�gt eine Beziehung von element1 zum element2 hierarchischen Graphen hinzu.
	 * 
	 * @param element1
	 * @param element2
	 * @return true wenn die Beziehung hinzugef�gt werden konnte, sonst false
	 */
	public boolean hinzuf�gen(A element1, A element2) {
		return struktur.hinzuf�gen(element1, element2);
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
	 * F�gt der Verfeinerung von grob das Element fein hinzu.
	 * 
	 * @param grob
	 * @param fein
	 */
	public void verfeinern(A grob, A fein) {
		if (!struktur.enth�lt(grob))
			return;
		if (!struktur.enth�lt(fein))
			hinzuf�gen(fein);
		Baum<A> b = hierarchie.entfernen(fein);
		hierarchie.hinzuf�gen(grob, b);
		setChanged();
	}

	/**
	 * Liefert die Menge aller Startknoten. Ein Startknoten ist ein Knoten
	 * der keine Vorg�nger besitzt.
	 * 
	 * @return Menge der Startknoten
	 */
	public Menge<A> startKnoten() {
		Graph<A> g = getGraph();
		Menge<A> startKnoten = new Menge<A>();
		for (A e : g.knoten())
			if (g.vorg�nger(e).anzahl() == 0)
				startKnoten.hinzuf�gen(e);
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
				endKnoten.hinzuf�gen(e);
		return endKnoten;
	}

	public Menge<A> getEltern() {
		Menge<A> eltern = new Menge<A>();
		for (A pe : hierarchie.knoten())
			if (hierarchie.anzahlNachfolger(pe) > 0)
				eltern.hinzuf�gen(pe);
		return eltern;
	}
	
	public SchlichterGraph<A> getGraph() {
		return struktur;
	}
}
