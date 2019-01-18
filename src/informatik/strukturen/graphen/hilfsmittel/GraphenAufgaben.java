
package informatik.strukturen.graphen.hilfsmittel;

import informatik.strukturen.graphen.wege.BewerteterWeg;
import informatik.strukturen.graphen.wege.WegMenge;
import informatik.strukturen.graphen.wege.Weg;
import informatik.strukturen.graphen.Baum;
import informatik.strukturen.graphen.GewichteterGraph;
import informatik.strukturen.graphen.Graph;
import informatik.strukturen.graphen.SchlichterGraph;
import informatik.strukturen.Abbildung;
import informatik.strukturen.BijektiveAbbildung;
import informatik.strukturen.Folge;
import informatik.strukturen.Menge;
import informatik.strukturen.Stapel;
import informatik.strukturen.PrioritaetsSchlange;
import informatik.strukturen.Schlange;
import informatik.strukturen.Paar;
import informatik.strukturen.Relation;

public class GraphenAufgaben {

	public static final int SCHWACH = 1;
	public static final int PSEUDO = 2;
	public static final int STRENG = 3;
	
//	public static final int RÜCKWÄRTS = 0;
//	public static final int VORWÄRTS = 1;
	
	
	// Topologische Sortierung
	// Liefert einen topologisch sortierten (isomorphen?) bewerteten Graph zurück
	// - Realisierung durch Tiefendurchlauf !!!
	public static <T extends Object> Folge<Menge<T>> topologischSortieren(Graph<T> g, boolean vorwärts) {
		
		SchlichterGraph<T> gtemp = new SchlichterGraph<T>(g.knoten(),g.kanten());
		Folge<Menge<T>> M = new Folge<Menge<T>>();
		// Wenn der Graph nicht azyklisch ist ...
		if (!new GraphenEigenschaften<T>(gtemp).istAzyklisch()) // -> ergibt sich schneller aus topsort !!!
			return M;
		
		while (gtemp.anzahlKnoten()>0) { // ... aber nicht so ;-)
			Menge<T> m = new Menge<T>();
			for (T k : gtemp.knoten())
				if (vorwärts ? gtemp.anzahlVorgänger(k)==0 : gtemp.anzahlNachfolger(k)==0) {
					m.hinzufügen(k);
				}
			for (T k : m)
				gtemp.entfernen(k);
			M.hinzufügen(m);
		}
		return M;
	}
	
	public static <T extends Object> Folge<T> topologischSortierteListe(Graph<T> g, boolean vorwärts) {
	
		Folge<T> liste = new Folge<T>();
		Folge<Menge<T>> F = topologischSortieren(g, vorwärts);
		for (Menge<T> m : F)
			for (T t : m)
				liste.hinzufügen(t);
		
		return liste;
	}
	
	public static <T extends Object> Menge<Menge<T>> zusammenhangsKomponenten(SchlichterGraph<T> graph, int stärke) {
		Menge<T> menge = graph.knoten();
		Relation<T,T> relation = graph.kanten();
		Relation<T,T> Rplus = graph.reflexivTransitiveHülle().kanten();
		Relation<T,T> Z = new Relation<T,T>();
		
		Menge<Menge<T>> M = new Menge<Menge<T>>();
		switch (stärke) {
		case SCHWACH:
			Relation<T,T> rtemp = relation.vereinigung(relation.transposition());
			Z = (new SchlichterGraph<T>(menge, rtemp)).reflexivTransitiveHülle().kanten();
			break;
		case PSEUDO:
			Z = Rplus.vereinigung(Rplus.transposition());
			break;
		case STRENG:
			Z = Rplus.durchschnitt(Rplus.transposition());
			break;
		}
		Menge<T> l = new Menge<T>(menge);
		for (T k : menge) {
			if (l.enthält(k)) {
				Menge<T> m = new Menge<T>();
				for (Paar<T,T> kante : Z)
					if (kante.zwei().equals(k))
						m.hinzufügen(kante.eins());
				for (T e : m)
					l.entfernen(e);
				M.hinzufügen((Menge<T>) m);
			}
		}
		return M;
	}
	
	// -> Eigenschaften: + pseudo
	// basis, sehnen kanten
	// trennende knoten, kanten
	
	/**
	 * Liefert einen kürzesten Weg von x nach y im bewerteten Graph G 
	 * nach dem Algorithmus von Dijkstra. Der index gibt an, welche 
	 * Bewertung verwendet werden soll. 
	 */
	public static <T extends Object> BewerteterWeg<T> kürzesterWeg(GewichteterGraph<T> G, T x, T y, int index) {		// Dijkstra
		BewerteterWeg<T> w = new BewerteterWeg<T>(1,1);

		// Baum<T> baum = minimalerSpannbaum(G);
//////	ANFANG Minimaler Spannbaum	??????????????????
//
		Menge<T>				N = new Menge<T>();			
		PrioritaetsSchlange<T> 	K = new PrioritaetsSchlange<T>();
		GewichteterGraph<T>		S = null;

		// Initialisierung
		for (T knoten : G.knoten())
			N.hinzufügen(knoten);

		N.entfernen(x);
		K.anstellen(x, new Double(0.0));
		S = new GewichteterGraph<T>(1,1);
		S.hinzufügen(x);
		S.setWert(x, 0, new Double(0.0));

		// Baum aufstellen ...
		while (K.anzahl() != 0) {
			Paar<T,Double> k = K.entfernenMinimum();
			for (T n : G.nachfolger(k.eins())) {
				Double pri = new Double(((Double) S.getWert(k.eins(), 0)).doubleValue() + ((Double) G.getWert(k.eins(), n, index)).doubleValue());
				if (N.enthält(n)) {
					N.entfernen(n);
					K.anstellen(n, pri);
					S.hinzufügen(n);				S.setWert(n,0,pri);		// bei Baum nicht nötig !!?
					S.hinzufügen(k.eins(), n);		S.setWert(k.eins(), n, 0, (Double) G.getWert(k.eins(), n, index));
				}
				if (K.enthält(n)) {
					if (K.getPrioritaet(n).compareTo(pri)>0) {
						K.setPrioritaet(n, pri);
						S.entfernen(n); 
						S.hinzufügen(n);			S.setWert(n,0,pri);
						S.hinzufügen(k.eins(), n);	S.setWert(k.eins(), n, 0, (Double) G.getWert(k.eins(), n, index));
					}
				}
			}
		}

//
//////	ENDE Minimaler Spannbaum
		
		// Weg w = baum.getPfad(y);
//////	ANFANG baum.getPfad(x);	// von der Wurzel zu x
//
		// ... und Weg raussuchen
		if (!S.enthält(y))
			return w;
		w.hinzufügen(y);
		T temp = null;
		while ( S.vorgänger(y).iterator().hasNext() ) {
			temp = S.vorgänger(y).iterator().next();
			w.hinzufügen(temp);
			w.setWert(temp, 0, S.getWert(temp, 0));
			y = temp;
		}
//
//////	ENDE baum.getPfad(x);
		
		return (BewerteterWeg<T>) w.umdrehen();
	}

	/**
	 * Ermittelt eine maximale Menge knotendisjunkter Wege von x nach y in g. 
	 * Die Wege werden ermittelt, indem zunächst ein Ersatzgraph erstellt wird, 
	 * der anstelle jedes Knotens einen Knoten für die eingehenden Kanten und einen
	 * Knoten für die Ausgehenden Kanten enthält. In diesem Ersatzgraphen werden 
	 * die Kantendisjunkten Wege gesucht und auf den ursprünglichen Graphen Projeziert.   
	 */
	public static <T extends Object> WegMenge<T> knotenDisjunkteWege(SchlichterGraph<T> g, T x, T y) {
		Weg<T> weg = null;
		
		SchlichterGraph<String> gk = new SchlichterGraph<String>();
		BijektiveAbbildung<T, Paar<String,String>> abb = new BijektiveAbbildung<T, Paar<String,String>>();
		Abbildung<String,T> ab = new Abbildung<String,T>();
		
		// Ersatzgraphen bilden mit aufgespaltenen Knoten und Abbildung 
		for (T knoten : g.knoten()) {
			String ek = new String("Eingang" + knoten);
		    gk.hinzufügen(ek);
			String ak = new String("Ausgang" + knoten);
		    gk.hinzufügen(ak);
		    gk.hinzufügen(ek, ak);
		    Paar<String, String> p = new Paar<String, String>(ek, ak);
		    abb.hinzufügen(knoten, p);
		    ab.hinzufügen(ek, knoten);
		    ab.hinzufügen(ak, knoten);
		}
		
		
		for (Paar<T,T> kante : g.kanten())
			gk.hinzufügen(abb.get2(kante.eins()).zwei(), abb.get2(kante.zwei()).eins());
			
		WegMenge<String> wms = kantenDisjunkteWege(gk, abb.get2(x).zwei(), abb.get2(y).eins());
		WegMenge<T> wm = new WegMenge<T>();		
		for (Weg<String> ws : wms) {
			Weg<T> w = new Weg<T>();
			for (Paar<String,String> p : ws) {
				if (abb.get1(p) == null)
					w.hinzufügen(ab.get(p.eins()),ab.get(p.zwei()));
			}
			wm.hinzufügen(w);
		}
	    return wm;
	}

	/**
	 *	Liefert die Menge der Kantendisjunkten Wege von einem Knoten VON
	 *	zu einem Knoten NACH. Ein Weg wird hier als eine Folge (Vector) von 
	 *	Knoten beschrieben.
	 */
	public static <T extends Object> WegMenge<T> kantenDisjunkteWege(SchlichterGraph<T> g, T x, T y) {
	    WegMenge<T> menge = new WegMenge<T>();

		// 1. Ein einfacher Weg
		Weg<T> weg = null; //getWeg(g, von, nach);
//		SchlichterGraph gk = (SchlichterGraph) g.clone();
		SchlichterGraph<T> gk = new SchlichterGraph<T>();
		for (T knoten : g.knoten())
		    gk.hinzufügen(knoten);
		for (Paar<T,T> kante : g.kanten())
		    gk.hinzufügen(kante.eins(),kante.zwei());
		// 1. Solange es noch einen einfachen Weg in gk gibt ...
		while( (weg = getWeg(gk, x, y)) != null ) {
			// 3. ... und Wege verschneiden
			// Für jede Kante im Weg ...
		    for (Paar<T,T> p : weg.kantenFolge()) {
			    // ... die Kante in gk umdrehen ...
			    gk.entfernen(p.eins(), p.zwei());
			    gk.hinzufügen(p.zwei(), p.eins());
				// ... prüfen, ob die Gegenkante im Originalgraph g enthalten ist
				if (g.enthält(p.zwei(), p.eins())) {
					// Wenn JA, den zuvor ermittelten Weg mit der Gegenkante finden ...
				    for (Weg<T> v : menge) {
				        if (v.enthält(p.zwei(), p.eins())) {
				            menge.entfernen(v);
							// ... und die beiden Wege weg und v verschneiden
			                Weg<T> wtemp = diff(weg, v);
			                v = diff(v, weg);
			                weg = wtemp;
			                menge.hinzufügen(v);
			                break;	// RICHTIG (nur die for-Schleife verlassen) ????
			            }
					}
				}
			}
			menge.hinzufügen(weg);
		} // End while
		return menge;	
	}

	// Verschneidet zwei Wege miteinander. Wird bei der Ermittlung  
	// der kantendisjunkten Wege verwendet.
	// aus <a,b,c,e,d> und <a,e,c,f,d> wird <a,e,d> und <a,b,c,f,d>
	private static <T extends Object> Weg<T> diff(Weg<T> a, Weg<T> b) {
	    Weg<T> c = new Weg<T>();
		boolean übernehmen = true;
		for (Paar<T,T> p : a.kantenFolge()) {
		    if (!b.enthält(p.zwei(), p.eins()))
		        c.hinzufügen(p.eins(), p.zwei());
		    else
		        übernehmen = false;
		    if (!übernehmen)
		        break;
		}
		for (Paar<T,T> p : b.kantenFolge()) {
		    if (a.enthält(p.zwei(), p.eins()))
		        übernehmen = true;
		    if (übernehmen)
		        c.hinzufügen(p.eins(), p.zwei());
		}
	    return c;
	}
	
	// Liefert einen einfachen Weg durch die Breitensuche.
	public static <T extends Object> Weg<T> getWeg(SchlichterGraph<T> g, T x, T y) {
		Weg<T>  weg  = null;
		Baum<T> baum = breitenSuchbaum(g, x, true);
		
		if (!baum.enthält(y))
			return weg;
		weg = new Weg<T>();
		weg.hinzufügen(y);
		T temp = null;
		while ( (temp = baum.getVorgänger(y)) != null) {
			weg.hinzufügen(temp);
			y = temp;
		}
		return weg.umdrehen();			
	}	

	public static <T extends Object> Baum<T> minimalerSpannbaum(GewichteterGraph<T> G, T wurzel) {
		Baum<T> S = new Baum<T>(wurzel);
		// ...
		
//		Menge<T>				N = new Menge<T>();			
//		PrioritätsSchlange<T> 	K = new PrioritätsSchlange<T>();
//		BewerteterGraph<T>		S = null;
//
//		// Initialisierung
//		for (T knoten : G.knoten())
//			N.hinzufügen(knoten);
//
//		N.entfernen(x);
//		K.anstellen(x, new Double(0.0));
//		S = new BewerteterGraph<T>(1,1);
//		S.hinzufügen(x);
//		S.setWert(x, 0, new Double(0.0));
//
//		// Baum aufstellen ...
//		while (K.anzahl() != 0) {
//			Paar<T,Double> k = K.entfernenMinimum();
//			for (T n : G.nachfolger(k.eins())) {
//				Double pri = new Double(((Double) S.getWert(k.eins(), 0)).doubleValue() + ((Double) G.getWert(k.eins(), n, index)).doubleValue());
//				if (N.enthält(n)) {
//					N.entfernen(n);
//					K.anstellen(n, pri);
//					S.hinzufügen(n);				S.setWert(n,0,pri);		// bei Baum nicht nötig !!?
//					S.hinzufügen(k.eins(), n);		S.setWert(k.eins(), n, 0, (Double) G.getWert(k.eins(), n, index));
//				}
//				if (K.enthält(n)) {
//					if (K.getPriorität(n).compareTo(pri)>0) {
//						K.setPriorität(n, pri);
//						S.entfernen(n); 
//						S.hinzufügen(n);			S.setWert(n,0,pri);
//						S.hinzufügen(k.eins(), n);	S.setWert(k.eins(), n, 0, (Double) G.getWert(k.eins(), n, index));
//					}
//				}
//			}
//		}
//
		return S;
	}

	/**
	 * Liefert einen Tiefensuchbaum ausgehend von einem Wurzelknoten.
	 * 
	 * @param G			ein schlichter Graph
	 * @param wurzel	Knoten von dem aus der Baum aufgebaut wird
	 * @param vorwärts	die Richtung in die der Baum aufgebaut wird
	 * @return	Tiefensuchbaum
	 */
	public static <T extends Object> Baum<T> tiefenSuchbaum(Graph<T> G, T wurzel, boolean vorwärts) {

		Menge<T>  N = new Menge<T>();			
		Stapel<T> K = new Stapel<T>();
		Baum<T>	  S = null;

		// Initialisierung
		T k = wurzel;
		for (T knoten : G.knoten())
			N.hinzufügen(knoten);

		N.entfernen(k);
		K.auflegen(k);
		S = new Baum<T>(k);

		// Durchlauf
		while (K.anzahl() != 0) {
			k = K.abnehmen();
			for (T n : vorwärts ? G.nachfolger(k) : G.vorgänger(k)) {
				if (N.enthält(n)) {
					N.entfernen(n);
					K.auflegen(n);
					S.hinzufügen(k,n);
				}
			}
		}

		return S;
	}
	
	/**
	 * Liefert einen Breitensuchbaum ausgehend von einem Wurzelknoten.
	 * 
	 * @param G			ein schlichter Graph
	 * @param wurzel	Knoten von dem aus der Baum aufgebaut wird
	 * @param vorwärts	die Richtung in die der Baum aufgebaut wird
	 * @return	Breitensuchbaum
	 */
	public static <T extends Object> Baum<T> breitenSuchbaum(Graph<T> G, T wurzel, boolean vorwärts) {
		Menge<T>	N = new Menge<T>();			
		Schlange<T> K = new Schlange<T>();
		Baum<T>	 	S = null;

		// Initialisierung
		T k = wurzel;
		for (T knoten : G.knoten())
			N.hinzufügen(knoten);

		N.entfernen(k);
		K.anstellen(k);
		S = new Baum<T>(k);

		// Durchlauf
		while (K.anzahl() != 0) {
			k = K.wegnehmen();
			for (T n : vorwärts ? G.nachfolger(k) : G.vorgänger(k)) {
				if (N.enthält(n)) {
					N.entfernen(n);
					K.anstellen(n);
					S.hinzufügen(k,n);
				}
			}
		}

		return S;
	}

}
