
package grapheneditor;

import static java.lang.Math.max;
import static java.lang.Math.min;
import grapheneditor.darstellung.figuren.Pfeil;
import informatik.strukturen.Menge;
import informatik.strukturen.Paar;
import informatik.strukturen.Relation;
import informatik.strukturen.graphen.Baum;
import informatik.strukturen.graphen.SchlichterGraph;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Observable;

/**
 * Das Graphenmodell beinhaltet den Graph mit seinen Daten 
 * und die Informationen zur grafischen Darstellung, d. h. 
 * die Graphen-, Geometrie- und Formatinformationen.
 *  
 * @author Axel
 */
public class GraphenModell extends Observable implements /*Graph,*/ Serializable {
//    public String name;
//    public String beschreibung;
//    public Knoten start;
//    public Knoten ziel;

    protected SchlichterGraph<KnotenElement> 	graph;
    protected Baum<KnotenElement> 			hierarchie;
//    protected KnotenElement 					wurzel;
    
    // Ansichten aller Knoten und Kanten
    public HashMap<KnotenElement,KnotenAnsicht> 				knotenMenge;	// Bewertung !!!
    public HashMap<Paar<KnotenElement,KnotenElement>,KantenAnsicht> kantenMenge;

    // 
    public Menge<KnotenElement>		aufgeklappteKnoten;


    public Einstellungen opt = new Einstellungen();


    /**
     * Erstellt ein leeres Graphenmodel zu einem gegebenen Wurzelelement.
     * @param wurzel
     */
    public GraphenModell(KnotenElement wurzel) {
    	this(  new SchlichterGraph<KnotenElement>(), wurzel );
    }

    public GraphenModell(SchlichterGraph<KnotenElement> graph, KnotenElement wurzel) {
//    	this.wurzel = wurzel;
        this.graph = graph;
        hierarchie = new Baum<KnotenElement>( wurzel );

        // Knoten- und KantenDarstellungen
        knotenMenge = new HashMap<KnotenElement,KnotenAnsicht>();
        kantenMenge = new HashMap<Paar<KnotenElement,KnotenElement>,KantenAnsicht>();
        aufgeklappteKnoten = new Menge<KnotenElement>();

//        name = "";
//        beschreibung = "";
    }
    
    public void copy(GraphenModell modell) {
//    	wurzel = modell.wurzel;
    	hierarchie = new Baum<KnotenElement>(modell.getHierarchie().getWurzel());
    	graph = new SchlichterGraph<KnotenElement>();
    	
	    for (KnotenAnsicht k : modell.knotenMenge.values())
	        hinzufügen(k);
	    for (KantenAnsicht k : modell.kantenMenge.values())
	        hinzufügen(k);
		for (KnotenElement e : hierarchie.knoten())
			if (!e.equals(hierarchie.getWurzel())) 
				hierarchie.hinzufügen(modell.getHierarchie().getVorgänger(e), e);
    }
    
	public boolean istAufgepklappt(KnotenAnsicht k) {
		return aufgeklappteKnoten.enthält(k.getObjekt());
	}
    
	public SchlichterGraph<KnotenElement> getGraph() {
        return graph;
    }	
	
	public Baum<KnotenElement> getHierarchie() {
		return hierarchie;
	}
    
    public void leeren() {
    	
    	// - graph leeren				Modell.leeren (HIER!)	
    	// - hierarchie leeren			Modell.leeren (HIER!)
    	// - WURZEL bleibt erhalten				
    	
    	// - knoten- und kantenMenge leeren			-> Modell.leeren (HIER!)
    	
    	// - knoten- und kantenAnsichten leeren		-> Diagramm.hierarchischSortieren
    	//												über update
    	
    	for (KnotenElement knoten : graph.knoten()) {
//    		knotenMenge.remove(knoten);
    		hierarchie.entfernen(knoten);
    		graph.entfernen(knoten);
    	}
    	
    	knotenMenge.clear();
    	kantenMenge.clear();

    	aufgeklappteKnoten.leeren();	
    	
    	setChanged();
		notifyObservers(this);	//!!!!!?????
    }
    
    public void hinzufügen(KnotenAnsicht k) {
        graph.hinzufügen(k.getObjekt());
        hierarchie.hinzufügen(hierarchie.getWurzel(), k.getObjekt());			// Hierarchie
        knotenMenge.put(k.getObjekt(), k);
        
		setChanged();
		notifyObservers(this);	//!!!!!?????
    }
    
    public void hinzufügen(KnotenAnsicht grob, KnotenAnsicht fein) {
    	
		if (!graph.enthält(grob.getObjekt()))
			return;
		if (!graph.enthält(fein.getObjekt()))
			hinzufügen(fein);
		Baum<KnotenElement> b = hierarchie.entfernen(fein.getObjekt());
		hierarchie.hinzufügen(grob.getObjekt(), b);

//        graph.hinzufügen(fein.getObjekt());
//        hierarchie.hinzufügen(grob.getObjekt(), fein.getObjekt());			// Hierarchie
        
        knotenMenge.put(fein.getObjekt(), fein);
        
        

		
		setChanged();
		notifyObservers(this);	//!!!!!?????
    }
    
    public void hinzufügen(KantenAnsicht k) {
        graph.hinzufügen(k.start.getObjekt(), k.ziel.getObjekt());
        kantenMenge.put(new Paar<KnotenElement,KnotenElement>(k.start.getObjekt(),k.ziel.getObjekt()), k);
        
        // Projektion einfürgen ???
        
		setChanged();
		notifyObservers(this);	//!!!!!?????
    }
    
    public KnotenAnsicht getKnoten(KnotenElement e) {
    	return knotenMenge.get(e);
    }
    
    public KantenAnsicht getKante(KnotenElement e1, KnotenElement e2) {
    	return kantenMenge.get(new Paar<KnotenElement,KnotenElement>(e1,e2));
    }
    
    
    public void entfernen(KnotenAnsicht k) {
    	KnotenElement e = k.getObjekt();
// bereits in graph.entfernen knoten enthalten!
		for (Paar<KnotenElement,KnotenElement> p : eingangsKanten(e))
		    kantenMenge.remove(new Paar<KnotenElement,KnotenElement>(p.eins(), p.zwei()));
		for (Paar<KnotenElement,KnotenElement> p : ausgangsKanten(e))
		    kantenMenge.remove(new Paar<KnotenElement,KnotenElement>(p.eins(), p.zwei()));

        knotenMenge.remove(e);
        graph.entfernen(e);
        hierarchie.entfernen(e);
        
        
		setChanged();
		notifyObservers(this);	//!!!!!?????
    }

    public void entfernen(KantenAnsicht k) {
        graph.entfernen(k.start.getObjekt(), k.ziel.getObjekt());
        kantenMenge.remove(new Paar<KnotenElement,KnotenElement>(k.start.getObjekt(), k.ziel.getObjekt()));
        
		setChanged();
		notifyObservers(this);	//!!!!!?????
    }

/*    public void removeEdge(Knoten k1, Knoten k2) {
        graph.entfernen(k1.getObjekt(), k2.getObjekt());
        kantenMenge.remove(new Paar<Object,Object>(k1.getObjekt(), k2.getObjekt()));
		setChanged();
		notifyObservers(this);	//!!!!!?????
    }	*/
    
    //TODO Perfomanceverbesserung durch direkte übergabe der Collection inkonsistent?

//    public Menge<Knoten> alleKnoten() {
//		Menge<Knoten> m = new Menge<Knoten>();
//		for (Knoten k : knotenMenge.values())
//			m.hinzufügen(k);
//		return m;
//    }
    
    public Menge<KnotenElement> alleKnoten() {
    	return graph.knoten();
    }
    
//    public Menge<Kante> alleKanten() {
//		Menge<Kante> m = new Menge<Kante>();
//		for (Kante k : kantenMenge.values())
//			m.hinzufügen(k);
//		return m;
//    }
    
    public Relation<KnotenElement,KnotenElement> alleKanten() {
    	return graph.kanten();
    }
    
    public Relation<KnotenElement,KnotenElement> eingangsKanten(KnotenElement k) {
    	Relation<KnotenElement,KnotenElement> eingänge = new Relation<KnotenElement,KnotenElement>();
    	for (KnotenElement e : graph.vorgänger(k))
    		eingänge.hinzufügen(e, k);
    	return eingänge;
    }
    
    public Relation<KnotenElement,KnotenElement> ausgangsKanten(KnotenElement k) {
    	Relation<KnotenElement,KnotenElement> ausgänge = new Relation<KnotenElement,KnotenElement>();
    	for (KnotenElement e : graph.nachfolger(k))
    		ausgänge.hinzufügen(k, e);
    	return ausgänge;
    }
    
	public void zuklappen(KnotenAnsicht k) {
		k.form.setSize(opt.knotenBreite, opt.knotenHöhe);
		knotenVerschieben(k, k.form.getPosition().getX(), k.form.getPosition().getY());
		aufgeklappteKnoten.entfernen(k.getObjekt());
		
		setChanged();
		notifyObservers(this);	//!!!!!?????

//		update(this,null);
	}
	
	public void aufklappen(KnotenAnsicht k) {
		Menge<KnotenElement> verfeinerung = getHierarchie().nachfolger(k.getObjekt());
		if (verfeinerung.anzahl() > 0) {
//			SchlichterGraph teilgraph = getGraph().getTeilgraph(verfeinerung);
			
			Rectangle umfangBrutto = graphenRahmenBrutto(verfeinerung);
				// von welchen Knoten ???
			
			k.form.setSize(umfangBrutto.getWidth()+2*opt.randBreite, umfangBrutto.getHeight()+2*opt.randBreite);
		} else {
//			double x = k.form.getBounds().getCenterX();
//			double y = k.form.getBounds().getCenterY();
			k.form.setSize(5*opt.knotenBreite, 5*opt.knotenHöhe);
		}
		knotenVerschieben(k, k.form.getPosition().getX(), k.form.getPosition().getY());
		aufgeklappteKnoten.hinzufügen(k.getObjekt());
		
		setChanged();
		notifyObservers(this);	//!!!!!?????

//		update(this,null);
	}
	

    public void knotenVerschieben(KnotenAnsicht k, double x, double y) {	// oder dx, dy?
    	
    	KnotenAnsicht k1 = knotenMenge.get(k.getObjekt());
    	Point2D altePosition = k1.form.getPosition(); 
    	double dx = x-altePosition.getX();
    	double dy = y-altePosition.getY();

        k.form.setPosition(new Point2D.Double(x, y));
        
    	// Ast mitverschieben!!!
    	for (KnotenElement e : hierarchie.getAst(k.getObjekt()).knoten()) {
    		if (!e.equals(k.getObjekt())) {
	    		KnotenAnsicht k2 = knotenMenge.get(e);
	    		double x2 = k2.form.getPosition().getX();
	    		double y2 = k2.form.getPosition().getY();
	    		Point2D neuePosition = new Point2D.Double(x2+dx, y2+dy);
	    		k2.form.setPosition(neuePosition);
//	        	ansicht.getPositionen().put(c, neuePosition);
	    		angrenzendeKantenAktualisieren(e);
    		}
    	}

    	angrenzendeKantenAktualisieren(k.getObjekt());

//		// Für alle Eingangskanten die Endpunkte neu setzen !!!
//		for (Paar<Element,Element> p : modell.eingangsKanten(k.getObjekt())) {
//			Kante ka = modell.kantenMenge.get(p);
//			((Pfeil) ka.form).setPfeil(ka.start.form.schnittPunkt(k.form.getPosition()), 
//		    							  k.form.schnittPunkt(ka.start.form.getPosition()));
//		}
//			
//		// Für alle Ausgangskanten die Startpunkte neu setzen !!!
//		for (Paar<Element,Element> p : modell.ausgangsKanten(k.getObjekt())) {
//			Kante ka = modell.kantenMenge.get(p);
//		    ((Pfeil) ka.form).setPfeil(k.form.schnittPunkt(ka.ziel.form.getPosition()), 
//		    							  ka.ziel.form.schnittPunkt(k.form.getPosition()));
//		}
//		// besser: mit start.position auch alle Kanten geändert haben !!!
    }
    
    /**
     * Beim Verschieben oder Zoomen eines Knotens müssen die ein- und 
     * ausgehenden Kanten neu positioniert werden.
     * @param k
     */
    private void angrenzendeKantenAktualisieren(KnotenElement k) {
    	KnotenAnsicht knotenAnsicht = knotenMenge.get(k);
		// Für alle Eingangskanten die Endpunkte neu setzen !!!
    	for (KnotenElement e : graph.vorgänger(k)) {
    		KantenAnsicht kante = kantenMenge.get(new Paar<KnotenElement,KnotenElement>(e, k)); 
			Point2D p1 = knotenMenge.get(e).form.schnittPunkt(knotenAnsicht.form.getPosition());
			Point2D p2 = knotenAnsicht.form.schnittPunkt(knotenMenge.get(e).form.getPosition());
//			if (kante.form instanceof Pfeil)
//				((Pfeil) kante.form).setPosition(p1, p2);
//			else
			((Pfeil) kante.form).setPfeil(p1, p2);
    	}
    		
		// Für alle Ausgangskanten die Startpunkte neu setzen !!!
    	for (KnotenElement e : graph.nachfolger(k)) {
    		KantenAnsicht kante = kantenMenge.get(new Paar<KnotenElement,KnotenElement>(k, e)); 
    		Point2D p1 = knotenAnsicht.form.schnittPunkt(knotenMenge.get(e).form.getPosition());
    		Point2D p2 = knotenMenge.get(e).form.schnittPunkt(knotenAnsicht.form.getPosition());
		    ((Pfeil) kante.form).setPfeil(p1, p2);
		}
    }
    
    public Rectangle graphenRahmenBrutto(Menge<KnotenElement> m) {
    	Point pmin = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
    	Point pmax = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
    	
    	for (KnotenElement e : m) {
    		KnotenAnsicht k = getKnoten(e);
    		Point p = new Point((int) k.form.getPosition().getX(), 
    							(int) k.form.getPosition().getY());
    		Dimension d = k.form.getBounds().getSize();
    		pmin.setLocation(min(pmin.x, p.x-d.getWidth()/2), min(pmin.y, p.y-d.getHeight()/2));
    		pmax.setLocation(max(pmax.x, p.x+d.getWidth()/2), max(pmax.y, p.y+d.getHeight()/2));
    	}
    	Rectangle r = new Rectangle(pmin.x, pmin.y, pmax.x-pmin.x, pmax.y-pmin.y);
    	return r;
    }


    
    // -> aus knotenAnsichten, statt aus knotenMenge, oder ???
    /**
     * Ermittelt das kleinste den Graph umspannende Rechteck bezogen auf 
     * die äußeren Grenzen der darstellenden Komponente. Es werden hierbei  
     * die Ausdehnungen der Knoten berücksichtigt. Dieses Rechteck umfasst 
     * alle KnotenAnsichten.
     */
    public Rectangle graphenRahmenBrutto() {
    	Point pmin = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
    	Point pmax = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
    	
    	for (KnotenAnsicht k : knotenMenge.values()) {
    		Point p = new Point((int) k.form.getPosition().getX(), 
    							(int) k.form.getPosition().getY());
    		Dimension d = k.form.getBounds().getSize();
    		pmin.setLocation(min(pmin.x, p.x-d.getWidth()/2), min(pmin.y, p.y-d.getHeight()/2));
    		pmax.setLocation(max(pmax.x, p.x+d.getWidth()/2), max(pmax.y, p.y+d.getHeight()/2));
    	}
    	Rectangle r = new Rectangle(pmin.x, pmin.y, pmax.x-pmin.x, pmax.y-pmin.y);
    	return r;
    }
    
    /**
     * Ermittelt das kleinste den Graph umspannende Rechteck bezogen auf 
     * die Nullpunkte der darstellenden Komponente. Es werden hierbei nicht 
     * die Ausdehnungen der Knoten berücksichtigt. Dieses Rechteck umfasst 
     * nur die Mittelpunkte der Knoten.
     */
    public Rectangle graphenRahmenNetto() {
    	Point pmin = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
    	Point pmax = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
    	
    	for (KnotenAnsicht k : knotenMenge.values()) {
    		Point p = new Point((int) k.form.getPosition().getX(), 
    							(int) k.form.getPosition().getY());
    		pmin.setLocation(min(pmin.x, p.x), min(pmin.y, p.y));
    		pmax.setLocation(max(pmax.x, p.x), max(pmax.y, p.y));
    	}
    	Rectangle r = new Rectangle(pmin.x, pmin.y, pmax.x-pmin.x, pmax.y-pmin.y);
    	return r;
    }


    public void aktualisieren() {
    	setChanged();
		notifyObservers(this);	//!!!!!?????
    }


    public class Einstellungen {
    	
        // Parameter der Knoten und Kanten
    	public int knotenBreite = 25, knotenHöhe = 25;
        public int randBreite = 25;

    }


    // -> diese Funktion gehört in das GeometrieModell !!!(den GeometrieController ???)
//    public void knotenVerschiebenALT(Knoten k, int x, int y) {	// oder dx, dy?
//    	//TODO statt int auf double umstrukturieren!!
//
//        k.form.setPosition(new Point(x, y));
//		// Für alle Eingangskanten die Endpunkte neu setzen !!!
//		for (Kante kante : eingangsKanten(k.getObjekt())) {
//			((Pfeil) kante.form).setPfeil(kante.start.form.schnittPunkt(k.form.getPosition()), 
//		    							  k.form.schnittPunkt(kante.start.form.getPosition()));
//		}
//			
//		// Für alle Ausgangskanten die Startpunkte neu setzen !!!
//		for (Kante kante : ausgangsKanten(k.getObjekt())) {
//		    ((Pfeil) kante.form).setPfeil(k.form.schnittPunkt(kante.ziel.form.getPosition()), 
//		    							  kante.ziel.form.schnittPunkt(k.form.getPosition()));
//		}
//		// besser: mit start.position auch alle Kanten geändert haben !!!
//    }
    


		/*
    	 * - In der ersten KantenEbene sind die Kanten zwischen den Knoten 
    	 *   der ersten KnotenEbene.
    	 * - In der zweiten KantenEbene sind die Kanten zwischen den Knoten 
    	 *   der zweiten KnotenEbenen und die Kanten zwischen erster und 
    	 *   zweiter KnotenEbene.
    	 * - In der dritten KantenEbene sind die Kanten zwischen den Knoten
    	 *   der dritten KnotenEbene und die Kanten zwischen erster und dritter
    	 *   sowie zweiter und dritter KnotenEbene
    	 * - ...
    	 * - In der n-ten KantenEbene sind die Kanten zwischen den 
    	 * 
    	 */
    	// Es gibt MAXIMAL soviele KantenEbenen wie KnotenEbenen
//    	ansichtGraph.kantenAnsichten = new Folge<Menge<KantenAnsicht>>();
//    	// Für alle ... kantenMenge.elemente
//    	// Für alle angezeigten KnotenEbenen
//    	for (Menge<KnotenAnsicht> knotenEbene : ansichtGraph.knotenAnsichten) {
//    		Menge<KantenAnsicht> mka = new Menge<KantenAnsicht>();
//    		// Für alle ... ebenen
//				// wenn knotenAnsichten.get(i) ...
//    				// mka.hinzufügen(ansichtGraph.kantenMenge.get( Paar ... ));
//    		
////    		if (mka.anzahl()>0)
//    		ansichtGraph.kantenAnsichten.hinzufügen(mka);
//    	}
    

//    public int anzahlKnoten() {
//        return graph.anzahlKnoten();
//    }
//
//    public int anzahlKanten() {
//        return graph.anzahlKanten();
//    }
//
//    public int anzahlVorgänger(Object knoten) {
//        return graph.anzahlVorgänger(knoten);
//    }
//
//    public int anzahlNachfolger(Object knoten) {
//        return graph.anzahlNachfolger(knoten);
//    }
//
//    public Menge<Object> knoten() {
//        return graph.knoten();
//    }
//
//    public Relation<Object,Object> kanten() {
//        return graph.kanten();
//    }
//
//    public Menge<Object> vorgänger(Object knoten) {
//        return graph.vorgänger(knoten);
//    }
//
//    public Menge<Object> nachfolger(Object knoten) {
//        return graph.nachfolger(knoten);
//    }
//
//    public boolean enthält(Object knoten) {
//        return graph.enthält(knoten);
//    }
//
//    public boolean enthält(Object knoten1, Object knoten2) {
//        return graph.enthält(knoten1, knoten2);
//    }
//
//	public Menge nachfahren(Object knoten) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public Menge vorfahren(Object knoten) {
//		// TODO Auto-generated method stub
//		return null;
//	}

    // Aufgaben
    // ---------
  //  public Weg einWeg(Object knoten1, Object knoten2) {
//        System.out.println("graph == null ? " + graph==null);
//        System.out.println("knoten1== null ? " + knoten1==null);
//        System.out.println("knoten2== null ? " + knoten2==null);
  //      Weg w = GraphenAufgaben.getWeg(graph, knoten1, knoten2);
        
   //     return w;
   // }
}
