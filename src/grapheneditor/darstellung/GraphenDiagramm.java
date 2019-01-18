 
package grapheneditor.darstellung;

import grapheneditor.KnotenElement;
import grapheneditor.GraphenModell;
import grapheneditor.KantenAnsicht;
import grapheneditor.KnotenAnsicht;
import grapheneditor.darstellung.figuren.Pfeil;

import informatik.strukturen.Folge;
import informatik.strukturen.Menge;
import informatik.strukturen.Paar;
import informatik.strukturen.graphen.SchlichterGraph;
import informatik.strukturen.graphen.wege.Weg;

import java.awt.*;
import java.awt.geom.*;
import java.util.Observable;


/**
 * 
 *  
 * @author Axel
 */
public class GraphenDiagramm extends GraphenDarstellung {
    
    private static int anzahl = 0;  // -> nur beim Speichern und laden !!!
    
    // Globale Hilfsvariablen
    public KnotenAnsicht anfrageStart;
    public KnotenAnsicht anfrageZiel;
    
    protected SchlichterGraph<KnotenElement> ergebnisGraph;
    protected Weg ergebnisWeg;
    private Image hintergrundBild;
    
    protected Point p1;
//    private AffineTransform ht;
    
    //======================================================
    
    protected Einstellungen opt = new Einstellungen();
    
    //======================================================
    
    // Ansichten aller anzuzeigenden (!= aufgeklappten) Knoten und Kanten
    public Folge<Menge<KnotenAnsicht>> knotenAnsichten;
    public Folge<Menge<KantenAnsicht>>  kantenAnsichten;

    
    // ==== Konstruktor ====
    
    public GraphenDiagramm(GraphenModell modell) {
        super(modell);

        // Eingabesteuerungen anmelden
//        addMouseMotionListener(this);
//        addMouseWheelListener(this);
//        addMouseListener(this);
//        addKeyListener(this);

        // Mögliche Modi für diese Komponente auswählen
        modi[ERSTELLEN]   = true;
        modi[BEARBEITEN]  = true;
        modi[ANALYSIEREN] = true;
        modus = ERSTELLEN;

        // Auswahlelemente initialisieren
        hintergrundBild = null;
        
        knotenAnsichten = new Folge<Menge<KnotenAnsicht>>();
        kantenAnsichten = new Folge<Menge<KantenAnsicht>> ();
        
//        ht = new AffineTransform();
        
        opt.schriftArt = new Font("Sans Serif", Font.BOLD, 14);
    }

    public void update(Observable o, Object arg) {
//    	System.out.println("Sortieren?");
    	hierarchischSortieren();
//System.out.println("Habe sortiert");
//System.out.println(knotenAnsichten);
    	repaint();
    }

    /**
     * Erstellt die Aufstellung der Knoten und Kanten, die gezeichnet
     * werden sollen. Es sollen nur die Knoten gezeichnet werden, die 
     * nicht in einer zusammengeklappten Komponente enthalten sind, 
     * und nur die Kanten, von denen beide Knoten gezeichnet werden.
     * 
     * knotenAnsichten Teil aus knotenMenge
     * kantenAnsichten Teil aus kantenMenge
     */
    public void hierarchischSortieren() {
    	// Behauptung: höhere Kanten (wenn mindestens ein Knoten ein ElternKnoten ist)
    	// existieren nicht im Modell, sondern sind nur Projektionen "ihrer" Kinder.
    	// - Wechsel eines Knoten Eltern<->Blatt durch hinz/entf Verfeinerung
    	//   -> ...
    	// => es muss KantenAnsichten geben, zu denen keine Kante des Modells
    	//    gehört - diese aber nur in der Menge der zu malenden Kanten - grau
    	
    	// Wann wird eine Kante bei Änderung in/aus Modell hinz/entf ? ? ?
    	
    	Menge<Object> pmenge = new Menge<Object>();
    	Menge<Object> pmengei = new Menge<Object>();
    	
    	// Die Folgen von Ebenen ...
    	knotenAnsichten = new Folge<Menge<KnotenAnsicht>>();
    	kantenAnsichten = new Folge<Menge<KantenAnsicht>>();
    	
//System.out.println(modell.getHierarchie());
    	
    	// Für alle Ebenen ...
    	for (Menge<KnotenElement> ebene : modell.getHierarchie().getEbenen()) {
    		
    		Menge<KnotenAnsicht> ma = new Menge<KnotenAnsicht>();
    		pmengei = new Menge<Object>();
    		for (KnotenElement element : ebene) {
    			KnotenElement elternKnoten = (KnotenElement) modell.getHierarchie().getVorgänger(element);
    			if (modell.aufgeklappteKnoten.enthält(elternKnoten) 
    					|| elternKnoten.equals(modell.getHierarchie().getWurzel())) {
    				ma.hinzufügen(modell.knotenMenge.get(element));
    				pmenge.hinzufügen(element);
    				pmengei.hinzufügen(element);
    			}
    		}
    		
    		Menge<KantenAnsicht> mka = new Menge<KantenAnsicht>();
    		for (Paar<KnotenElement,KnotenElement> kante : modell.kantenMenge.keySet()) {
    			if (   (pmengei.enthält(kante.eins()) && pmengei.enthält(kante.zwei())) 
    				|| (pmengei.enthält(kante.eins()) && pmenge.enthält(kante.zwei())) 
    				|| (pmenge.enthält(kante.eins()) && pmengei.enthält(kante.zwei())) ) {

//    				|| vom einen zur Vergöberung des anderen, wenn dessen Eltern
//    				   zusammengeklappt sind !!!
    				
    				// ... hinzufügen
    				mka.hinzufügen(modell.kantenMenge.get(kante));
    			}
    		}
    		kantenAnsichten.hinzufügen(mka);
    		knotenAnsichten.hinzufügen(ma);
    	}
//System.out.println(knotenAnsichten);
    }    	


    // ==== Methoden ====
    
//    public Knoten getNode(Object knoten) {
//        return knotenMenge.containsKey(knoten) ? knotenMenge.get(knoten) : null;
//    }
//    
//    public Kante getEdge(Object knoten1, Object knoten2) {
//        return kantenMenge.get(new Paar<Object,Object>(knoten1, knoten2));
//    }

    

    // ==== Einstellungen ====
    
	public void switchGerichtet() { opt.pfeilspitze = !opt.pfeilspitze; }
	public void switchSchatten() { opt.schatten = !opt.schatten; }
    public void setHintergrundBild(Image bild) {
    	hintergrundBild = bild;
    }
    
    // Speziell im GraphenDiagramm
    public void setRaster(boolean b) {
    	opt.raster = b;
        repaint();
    }
    
    public void setFang(boolean b) {
    	opt.fang = b;
//      repaint();
    }
    
    public void setSchrift(boolean b) {
    	opt.schrift = b;
        repaint();
    }
    
    public void setSchriftArt(String type, int opt, int size) {
    	this.opt.schriftArt = new Font(type, opt, size);
    }
    
//    public void setKnotenGröße(int breite, int höhe) {
//    	knotenBreite = breite;
//    	knotenHöhe = höhe;
//    }
    
    public void switchHintergrund() {
    	opt.hintergrundAnzeigen = !opt.hintergrundAnzeigen;
    	repaint();
    }
    

    // ==== Zeichenfunktionen ====
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
// -------------        
//
        if (opt.hintergrundAnzeigen && hintergrundBild != null)
        	hintergrund.drawImage(hintergrundBild,0,0,this);
        if (opt.raster) 	zeichneRaster(hintergrund);
        if (opt.schatten) 	zeichneSchatten(hintergrund);
        
        zeichneGraph(hintergrund);

//
// -------------        
        // Zeichne das gepufferte Bild auf den Bildschirm
        
        g2.drawImage(bild, 0, 0, this);
        
        

    }

    public void zeichneSchatten(Graphics2D g2) {
    	int offSetX = 5;
    	int offSetY = 5;
    	
        //  alle Elemente hellgrau
    	g2.translate(offSetX, offSetY);
        g2.setPaint(Color.lightGray);
        
        // Alle Kanten zeichnen
        g2.setStroke(strichStärke[1]);
		for (Menge<KantenAnsicht> mka : kantenAnsichten) {
			for (KantenAnsicht k : mka) {
	            g2.draw(k.form);
	            if (k.form instanceof Pfeil)
	            	if (((Pfeil) k.form).getTyp() != 0)
	                	g2.fill( k.form);
			}
        }
		
        // Alle Knoten zeichnen - Hierarchisch !!!
        g2.setStroke(strichStärke[3]);
		for (Menge<KnotenAnsicht> ma : knotenAnsichten) {
			for (KnotenAnsicht k : ma) {
	            g2.fill(k.form);
	            g2.draw(k.form);
			}
        }
    	g2.translate(-offSetX, -offSetY);
    }
    
    public void zeichneGraph(Graphics2D g2) {
        //  Farben der Knoten und Kanten berücksichtigen
        g2.setPaint(Color.black);
        
        // Alle Knoten zeichnen - Hierarchisch ! ! !
        g2.setStroke(strichStärke[3]);
		for (Menge<KnotenAnsicht> ma : knotenAnsichten)
			for (KnotenAnsicht k : ma)
				k.draw(g2);

        // Alle Kanten zeichnen
        g2.setStroke(strichStärke[1]);
        for (Menge<KantenAnsicht> ma : kantenAnsichten)
        	for (KantenAnsicht k : ma)
        		k.draw(g2);
        
        if (anfrageStart != null) {
            g2.setColor(Color.RED);
            g2.fill(anfrageStart.form);
            g2.setColor(anfrageStart.linienFarbe);
            g2.draw(anfrageStart.form);
        }
        if (anfrageZiel != null) {
            g2.setColor(Color.GREEN);
            g2.fill(anfrageZiel.form);
            g2.setColor(anfrageZiel.linienFarbe);
            g2.draw(anfrageZiel.form);
        }
        
        if (ergebnisGraph != null) {
            // Alle Kanten zeichnen
            g2.setColor(Color.RED);
            g2.setStroke(strichStärke[5]);
            for (Paar<KnotenElement,KnotenElement> p : ergebnisGraph.kanten()) {
                KantenAnsicht k = modell.kantenMenge.get(p); 
                g2.draw(k.form);
                if (((Pfeil)k.form).getTyp() != 0)
                    g2.fill(k.form);
            }
            
            for (KnotenElement e : ergebnisGraph.knoten())
                g2.draw(modell.knotenMenge.get(e).form);
        }
            
    }
    
    public void zeichneRaster(Graphics2D g2) {
        int x = 0, y = 0;
        int dx = opt.rasterFein, dy = opt.rasterFein;
        g2.setColor(new Color(200, 200, 200));
        Line2D l = new Line2D.Double(0, 0, fläche.width, fläche.height);
        // Senkrechte Linien zeichnen
        for (int i = 0; i<fläche.width/dx; i++) {
            l.setLine(i*dx, 0, i*dx, fläche.height);
            g2.setStroke( strichStärke[ (i%4==0) ? 1 : 0 ] );
            g2.draw(l);
        }
        // Waagerechte Linien zeichnen
        for (int i = 0; i<fläche.height/dy; i++) {
            l.setLine(0, i*dy, fläche.width, i*dy);
            g2.setStroke( strichStärke[ (i%4==0) ? 1 : 0 ] );
            g2.draw(l);
        }
    }
    
}


class Einstellungen {
	
    // interne Zustände
	public boolean raster = false;
    public boolean fang = false;
    public boolean schrift = true;
    public boolean pfeilspitze = true;
	public boolean schatten = true;
	public boolean hintergrundAnzeigen = true;
	
    // Parameter der Knoten und Kanten
//	public int knotenBreite = 25, knotenHöhe = 25;
    public int knotenBreite2 = 125, knotenHöhe2 = 125;
    public int pfeilBreite = 10, pfeilLänge = 15;
//    public int randBreite = 25;
    
    // Vordefinierte Rasterabstände
    public int rasterFein   = 25;
    public int rasterMittel = 25;
    public int rasterGrob   = 50;

    public int schriftGröße = 10;
    
    public Color standardFüllFarbe = Color.white;
    public Font schriftArt;
    
    // Parameter zr Steuerung der Abstandseinhaltung
    public int modus = 2; //0=aus, 1=statisch, 2=gravitation
    public int minimalabstand = 40;
}


//// Tastatur-Ereignisse
//public void keyPressed(KeyEvent e) {
//  switch (e.getKeyCode()) {   
////      case KeyEvent.VK_R:     switchRaster();     break;	// -> AnsichtSteuerung
////      case KeyEvent.VK_F:     switchFang();       break;	// -> AnsichtSteuerung
////      case KeyEvent.VK_B:     switchSchrift();    break;	// -> AnsichtSteuerung
//
////  	case KeyEvent.VK_PLUS:     ht.scale(1.25, 1.25); hintergrund.setTransform(ht);/* .scale(1.25, 1.25); */    repaint();	break;		// -> Modell
////  	case KeyEvent.VK_MINUS:    ht.scale(0.8, 0.8);   hintergrund.setTransform(ht);      repaint();	break;		// -> Modell
////  	case KeyEvent.VK_RIGHT:    ht.translate(0,-10);   hintergrund.setTransform(ht);      repaint();	break;		// -> Modell
////  	case KeyEvent.VK_LEFT:     ht.translate(0, 10);   hintergrund.setTransform(ht);      repaint();	break;		// -> Modell
////  	case KeyEvent.VK_UP:       ht.translate( 10, 0);   hintergrund.setTransform(ht);      repaint();	break;		// -> Modell
////  	case KeyEvent.VK_DOWN:     ht.translate(-10, 0);   hintergrund.setTransform(ht);      repaint();	break;		// -> Modell
//
////  	case KeyEvent.VK_I:     setFüllFarbe();     break;		// -> Modell
////      case KeyEvent.VK_L:     setLinienFarbe();   break;		// -> Modell
////      case KeyEvent.VK_X:		schatten = !schatten; repaint(); break;	// -> AnsichtSteuerung
//      case KeyEvent.VK_Y:		setStandardFüllFarbe();	break;	// -> Modell
////      case KeyEvent.VK_P:		
////			pfeilspitze = !pfeilspitze; 
////			repaint();
////			break;
//      case KeyEvent.VK_W:
////          if (modell.start != null && anfrageZiel != null) {
////              Weg w = modell.einWeg(modell.start.getObjekt(), anfrageZiel.getObjekt());
////              ergebnisGraph = w.getGraph();
////          }
//          repaint();
//          break;
//  }
//}
//
//public void keyTyped(KeyEvent arg0) {    }
//
//public void keyReleased(KeyEvent arg0) {    }
//

//
//  HIER eigentlich nur a und b auswählen, die 
//      Funktion wird einer Taste zugewiesen, wenn zb. a&b!null
//
// Start [, Ziel] setzen und Funktion auswählen
//
// Kanten
// ------
//   nichtSymmetrische() -> Menge<Kante>
//        symmetrische() -> Menge<Kante>
//   nichtReflexive()    -> Menge<Kante>
//        reflexive()    -> Menge<Kante>
//   ...
// 
// Wege
// ----
//   einWeg(a, b)       -> Weg 
//   kürzesterWeg(a, b) -> Weg
//   kanDisWege(a, b)   -> Menge<Weg>
//   knoDisWege(a, b)   -> Menge<Weg>
//
// Zyklen
// ------
//   einZyklus(a)           -> Weg  // längster Zyklus -> wenn Weg.länge = 0, dann nicht auf Zyklus
//   strengeKomponente(a)   -> Menge<Knoten> 
//   schwacheKomponente(a)  -> Menge<Knoten>
//   strengeKomponenten()   -> Menge<Menge<Knoten>>
//   schwacheKomponenten()  -> Menge<Menge<Knoten>>
//
// Bäume
// -----
//   ...
//
//         (TODRAW)
//      -> global:  tmpWeg, tmpWegMenge, tmpKnotenMenge,
//                  tmpKantenMenge, tmpKnotenMengenMenge,
//                  tmp
//
// Rechner
// -------
//   execute("A*B") -> Graph|Relation?
//   ...
//
