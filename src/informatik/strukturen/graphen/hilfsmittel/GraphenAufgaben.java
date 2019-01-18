
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
	
//	public static final int R�CKW�RTS = 0;
//	public static final int VORW�RTS = 1;
	
	
	// Topologische Sortierung
	// Liefert einen topologisch sortierten (isomorphen?) bewerteten Graph zur�ck
	// - Realisierung durch Tiefendurchlauf !!!
	public static <T extends Object> Folge<Menge<T>> topologischSortieren(Graph<T> g, boolean vorw�rts) {
		
		SchlichterGraph<T> gtemp = new SchlichterGraph<T>(g.knoten(),g.kanten());
		Folge<Menge<T>> M = new Folge<Menge<T>>();
		// Wenn der Graph nicht azyklisch ist ...
		if (!new GraphenEigenschaften<T>(gtemp).istAzyklisch()) // -> ergibt sich schneller aus topsort !!!
			return M;
		
		while (gtemp.anzahlKnoten()>0) { // ... aber nicht so ;-)
			Menge<T> m = new Menge<T>();
			for (T k : gtemp.knoten())
				if (vorw�rts ? gtemp.anzahlVorg�nger(k)==0 : gtemp.anzahlNachfolger(k)==0) {
					m.hinzuf�gen(k);
				}
			for (T k : m)
				gtemp.entfernen(k);
			M.hinzuf�gen(m);
		}
		return M;
	}
	
	public static <T extends Object> Folge<T> topologischSortierteListe(Graph<T> g, boolean vorw�rts) {
	
		Folge<T> liste = new Folge<T>();
		Folge<Menge<T>> F = topologischSortieren(g, vorw�rts);
		for (Menge<T> m : F)
			for (T t : m)
				liste.hinzuf�gen(t);
		
		return liste;
	}
	
	public static <T extends Object> Menge<Menge<T>> zusammenhangsKomponenten(SchlichterGraph<T> graph, int st�rke) {
		Menge<T> menge = graph.knoten();
		Relation<T,T> relation = graph.kanten();
		Relation<T,T> Rplus = graph.reflexivTransitiveH�lle().kanten();
		Relation<T,T> Z = new Relation<T,T>();
		
		Menge<Menge<T>> M = new Menge<Menge<T>>();
		switch (st�rke) {
		case SCHWACH:
			Relation<T,T> rtemp = relation.vereinigung(relation.transposition());
			Z = (new SchlichterGraph<T>(menge, rtemp)).reflexivTransitiveH�lle().kanten();
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
			if (l.enth�lt(k)) {
				Menge<T> m = new Menge<T>();
				for (Paar<T,T> kante : Z)
					if (kante.zwei().equals(k))
						m.hinzuf�gen(kante.eins());
				for (T e : m)
					l.entfernen(e);
				M.hinzuf�gen((Menge<T>) m);
			}
		}
		return M;
	}
	
	// -> Eigenschaften: + pseudo
	// basis, sehnen kanten
	// trennende knoten, kanten
	
	/**
	 * Liefert einen k�rzesten Weg von x nach y im bewerteten Graph G 
	 * nach dem Algorithmus von Dijkstra. Der index gibt an, welche 
	 * Bewertung verwendet werden soll. 
	 */
	public static <T extends Object> BewerteterWeg<T> k�rzesterWeg(GewichteterGraph<T> G, T x, T y, int index) {		// Dijkstra
		BewerteterWeg<T> w = new BewerteterWeg<T>(1,1);

		// Baum<T> baum = minimalerSpannbaum(G);
//////	ANFANG Minimaler Spannbaum	??????????????????
//
		Menge<T>				N = new Menge<T>();			
		PrioritaetsSchlange<T> 	K = new PrioritaetsSchlange<T>();
		GewichteterGraph<T>		S = null;

		// Initialisierung
		for (T knoten : G.knoten())
			N.hinzuf�gen(knoten);

		N.entfernen(x);
		K.anstellen(x, new Double(0.0));
		S = new GewichteterGraph<T>(1,1);
		S.hinzuf�gen(x);
		S.setWert(x, 0, new Double(0.0));

		// Baum aufstellen ...
		while (K.anzahl() != 0) {
			Paar<T,Double> k = K.entfernenMinimum();
			for (T n : G.nachfolger(k.eins())) {
				Double pri = new Double(((Double) S.getWert(k.eins(), 0)).doubleValue() + ((Double) G.getWert(k.eins(), n, index)).doubleValue());
				if (N.enth�lt(n)) {
					N.entfernen(n);
					K.anstellen(n, pri);
					S.hinzuf�gen(n);				S.setWert(n,0,pri);		// bei Baum nicht n�tig !!?
					S.hinzuf�gen(k.eins(), n);		S.setWert(k.eins(), n, 0, (Double) G.getWert(k.eins(), n, index));
				}
				if (K.enth�lt(n)) {
					if (K.getPrioritaet(n).compareTo(pri)>0) {
						K.setPrioritaet(n, pri);
						S.entfernen(n); 
						S.hinzuf�gen(n);			S.setWert(n,0,pri);
						S.hinzuf�gen(k.eins(), n);	S.setWert(k.eins(), n, 0, (Double) G.getWert(k.eins(), n, index));
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
		if (!S.enth�lt(y))
			return w;
		w.hinzuf�gen(y);
		T temp = null;
		while ( S.vorg�nger(y).iterator().hasNext() ) {
			temp = S.vorg�nger(y).iterator().next();
			w.hinzuf�gen(temp);
			w.setWert(temp, 0, S.getWert(temp, 0));
			y = temp;
		}
//
//////	ENDE baum.getPfad(x);
		
		return (BewerteterWeg<T>) w.umdrehen();
	}

	/**
	 * Ermittelt eine maximale Menge knotendisjunkter Wege von x nach y in g. 
	 * Die Wege werden ermittelt, indem zun�chst ein Ersatzgraph erstellt wird, 
	 * der anstelle jedes Knotens einen Knoten f�r die eingehenden Kanten und einen
	 * Knoten f�r die Ausgehenden Kanten enth�lt. In diesem Ersatzgraphen werden 
	 * die Kantendisjunkten Wege gesucht und auf den urspr�nglichen Graphen Projeziert.   
	 */
	public static <T extends Object> WegMenge<T> knotenDisjunkteWege(SchlichterGraph<T> g, T x, T y) {
		Weg<T> weg = null;
		
		SchlichterGraph<String> gk = new SchlichterGraph<String>();
		BijektiveAbbildung<T, Paar<String,String>> abb = new BijektiveAbbildung<T, Paar<String,String>>();
		Abbildung<String,T> ab = new Abbildung<String,T>();
		
		// Ersatzgraphen bilden mit aufgespaltenen Knoten und Abbildung 
		for (T knoten : g.knoten()) {
			String ek = new String("Eingang" + knoten);
		    gk.hinzuf�gen(ek);
			String ak = new String("Ausgang" + knoten);
		    gk.hinzuf�gen(ak);
		    gk.hinzuf�gen(ek, ak);
		    Paar<String, String> p = new Paar<String, String>(ek, ak);
		    abb.hinzuf�gen(knoten, p);
		    ab.hinzuf�gen(ek, knoten);
		    ab.hinzuf�gen(ak, knoten);
		}
		
		
		for (Paar<T,T> kante : g.kanten())
			gk.hinzuf�gen(abb.get2(kante.eins()).zwei(), abb.get2(kante.zwei()).eins());
			
		WegMenge<String> wms = kantenDisjunkteWege(gk, abb.get2(x).zwei(), abb.get2(y).eins());
		WegMenge<T> wm = new WegMenge<T>();		
		for (Weg<String> ws : wms) {
			Weg<T> w = new Weg<T>();
			for (Paar<String,String> p : ws) {
				if (abb.get1(p) == null)
					w.hinzuf�gen(ab.get(p.eins()),ab.get(p.zwei()));
			}
			wm.hinzuf�gen(w);
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
		    gk.hinzuf�gen(knoten);
		for (Paar<T,T> kante : g.kanten())
		    gk.hinzuf�gen(kante.eins(),kante.zwei());
		// 1. Solange es noch einen einfachen Weg in gk gibt ...
		while( (weg = getWeg(gk, x, y)) != null ) {
			// 3. ... und Wege verschneiden
			// F�r jede Kante im Weg ...
		    for (Paar<T,T> p : weg.kantenFolge()) {
			    // ... die Kante in gk umdrehen ...
			    gk.entfernen(p.eins(), p.zwei());
			    gk.hinzuf�gen(p.zwei(), p.eins());
				// ... pr�fen, ob die Gegenkante im Originalgraph g enthalten ist
				if (g.enth�lt(p.zwei(), p.eins())) {
					// Wenn JA, den zuvor ermittelten Weg mit der Gegenkante finden ...
				    for (Weg<T> v : menge) {
				        if (v.enth�lt(p.zwei(), p.eins())) {
				            menge.entfernen(v);
							// ... und die beiden Wege weg und v verschneiden
			                Weg<T> wtemp = diff(weg, v);
			                v = diff(v, weg);
			                weg = wtemp;
			                menge.hinzuf�gen(v);
			                break;	// RICHTIG (nur die for-Schleife verlassen) ????
			            }
					}
				}
			}
			menge.hinzuf�gen(weg);
		} // End while
		return menge;	
	}

	// Verschneidet zwei Wege miteinander. Wird bei der Ermittlung  
	// der kantendisjunkten Wege verwendet.
	// aus <a,b,c,e,d> und <a,e,c,f,d> wird <a,e,d> und <a,b,c,f,d>
	private static <T extends Object> Weg<T> diff(Weg<T> a, Weg<T> b) {
	    Weg<T> c = new Weg<T>();
		boolean �bernehmen = true;
		for (Paar<T,T> p : a.kantenFolge()) {
		    if (!b.enth�lt(p.zwei(), p.eins()))
		        c.hinzuf�gen(p.eins(), p.zwei());
		    else
		        �bernehmen = false;
		    if (!�bernehmen)
		        break;
		}
		for (Paar<T,T> p : b.kantenFolge()) {
		    if (a.enth�lt(p.zwei(), p.eins()))
		        �bernehmen = true;
		    if (�bernehmen)
		        c.hinzuf�gen(p.eins(), p.zwei());
		}
	    return c;
	}
	
	// Liefert einen einfachen Weg durch die Breitensuche.
	public static <T extends Object> Weg<T> getWeg(SchlichterGraph<T> g, T x, T y) {
		Weg<T>  weg  = null;
		Baum<T> baum = breitenSuchbaum(g, x, true);
		
		if (!baum.enth�lt(y))
			return weg;
		weg = new Weg<T>();
		weg.hinzuf�gen(y);
		T temp = null;
		while ( (temp = baum.getVorg�nger(y)) != null) {
			weg.hinzuf�gen(temp);
			y = temp;
		}
		return weg.umdrehen();			
	}	

	public static <T extends Object> Baum<T> minimalerSpannbaum(GewichteterGraph<T> G, T wurzel) {
		Baum<T> S = new Baum<T>(wurzel);
		// ...
		
//		Menge<T>				N = new Menge<T>();			
//		Priorit�tsSchlange<T> 	K = new Priorit�tsSchlange<T>();
//		BewerteterGraph<T>		S = null;
//
//		// Initialisierung
//		for (T knoten : G.knoten())
//			N.hinzuf�gen(knoten);
//
//		N.entfernen(x);
//		K.anstellen(x, new Double(0.0));
//		S = new BewerteterGraph<T>(1,1);
//		S.hinzuf�gen(x);
//		S.setWert(x, 0, new Double(0.0));
//
//		// Baum aufstellen ...
//		while (K.anzahl() != 0) {
//			Paar<T,Double> k = K.entfernenMinimum();
//			for (T n : G.nachfolger(k.eins())) {
//				Double pri = new Double(((Double) S.getWert(k.eins(), 0)).doubleValue() + ((Double) G.getWert(k.eins(), n, index)).doubleValue());
//				if (N.enth�lt(n)) {
//					N.entfernen(n);
//					K.anstellen(n, pri);
//					S.hinzuf�gen(n);				S.setWert(n,0,pri);		// bei Baum nicht n�tig !!?
//					S.hinzuf�gen(k.eins(), n);		S.setWert(k.eins(), n, 0, (Double) G.getWert(k.eins(), n, index));
//				}
//				if (K.enth�lt(n)) {
//					if (K.getPriorit�t(n).compareTo(pri)>0) {
//						K.setPriorit�t(n, pri);
//						S.entfernen(n); 
//						S.hinzuf�gen(n);			S.setWert(n,0,pri);
//						S.hinzuf�gen(k.eins(), n);	S.setWert(k.eins(), n, 0, (Double) G.getWert(k.eins(), n, index));
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
	 * @param vorw�rts	die Richtung in die der Baum aufgebaut wird
	 * @return	Tiefensuchbaum
	 */
	public static <T extends Object> Baum<T> tiefenSuchbaum(Graph<T> G, T wurzel, boolean vorw�rts) {

		Menge<T>  N = new Menge<T>();			
		Stapel<T> K = new Stapel<T>();
		Baum<T>	  S = null;

		// Initialisierung
		T k = wurzel;
		for (T knoten : G.knoten())
			N.hinzuf�gen(knoten);

		N.entfernen(k);
		K.auflegen(k);
		S = new Baum<T>(k);

		// Durchlauf
		while (K.anzahl() != 0) {
			k = K.abnehmen();
			for (T n : vorw�rts ? G.nachfolger(k) : G.vorg�nger(k)) {
				if (N.enth�lt(n)) {
					N.entfernen(n);
					K.auflegen(n);
					S.hinzuf�gen(k,n);
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
	 * @param vorw�rts	die Richtung in die der Baum aufgebaut wird
	 * @return	Breitensuchbaum
	 */
	public static <T extends Object> Baum<T> breitenSuchbaum(Graph<T> G, T wurzel, boolean vorw�rts) {
		Menge<T>	N = new Menge<T>();			
		Schlange<T> K = new Schlange<T>();
		Baum<T>	 	S = null;

		// Initialisierung
		T k = wurzel;
		for (T knoten : G.knoten())
			N.hinzuf�gen(knoten);

		N.entfernen(k);
		K.anstellen(k);
		S = new Baum<T>(k);

		// Durchlauf
		while (K.anzahl() != 0) {
			k = K.wegnehmen();
			for (T n : vorw�rts ? G.nachfolger(k) : G.vorg�nger(k)) {
				if (N.enth�lt(n)) {
					N.entfernen(n);
					K.anstellen(n);
					S.hinzuf�gen(k,n);
				}
			}
		}

		return S;
	}

}
