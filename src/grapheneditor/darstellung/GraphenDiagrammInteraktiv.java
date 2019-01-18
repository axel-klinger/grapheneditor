package grapheneditor.darstellung;

import grapheneditor.GraphenModell;
import grapheneditor.KantenAnsicht;
import grapheneditor.KnotenAnsicht;
import grapheneditor.KnotenElement;
import grapheneditor.darstellung.figuren.Ellipse;
import grapheneditor.darstellung.figuren.KnotenForm;
import grapheneditor.darstellung.figuren.NEck;
import grapheneditor.darstellung.figuren.Pfeil;
import grapheneditor.darstellung.figuren.Rechteck;
import grapheneditor.steuerung.GraphenMethoden;
import informatik.strukturen.Menge;
import informatik.strukturen.graphen.Baum;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JColorChooser;

public class GraphenDiagrammInteraktiv extends  GraphenDiagramm
									implements 	MouseListener, 
												MouseMotionListener,
												MouseWheelListener {

	public  KnotenAnsicht start, ziel;
    private KantenAnsicht kante;
    protected  Pfeil pfeil;

    // ausgewählte Knoten und Kanten
    private Menge<KnotenAnsicht> 		knotenAuswahl;
    private Menge<KantenAnsicht>  		kantenAuswahl;
    private HashMap<KnotenElement,Gravitationsfeld> gravitationsfelder;
    
    

    GraphenMethoden steuerung;
	
    
	public GraphenDiagrammInteraktiv(GraphenModell modell, GraphenMethoden steuerung) {
		super(modell);
		this.steuerung = steuerung;
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addMouseListener(this);
		
        knotenAuswahl = new Menge<KnotenAnsicht>();
        kantenAuswahl = new Menge<KantenAnsicht>();
//        gravif = new Folge<Gravitationsfeld>();
//        graviBaum = new Baum<Gravitationsfeld>(new Gravitationsfeld());
        gravitationsfelder = new HashMap<KnotenElement,Gravitationsfeld>();

	}
	
	public void zeichneGraph(Graphics2D g2) {
		super.zeichneGraph(g2);
		
        // Auswahl der Knoten und Kanten grau zeichnen
        // Alle Knoten der Auswahl zeichnen
        g2.setPaint(Color.LIGHT_GRAY);
        g2.setStroke(strichStärke[5]);
        for (KnotenAnsicht k : knotenAuswahl) {
            g2.draw(k.form);
            if (opt.schrift)
                g2.drawString(k.titel, 
                    (int)k.form.x-g2.getFontMetrics().stringWidth(k.titel)/2, 
                    (int)(k.form.y-k.form.h*0.8));
        }
        for (KantenAnsicht k : kantenAuswahl) {
            g2.draw(k.form);
        }

        if (pfeil != null) g2.draw(pfeil);

	}

    public void setFüllFarbe() {
        Color farbe = JColorChooser.showDialog(this, "Füllfarbe", opt.standardFüllFarbe);
        if (farbe.equals(opt.standardFüllFarbe))
	        for (KnotenAnsicht k : knotenAuswahl)
	            k.füllFarbe = null;
        else
        	for (KnotenAnsicht k : knotenAuswahl)
	            k.füllFarbe = farbe;
        repaint();
    }
    public void setStandardFüllFarbe() {
    	opt.standardFüllFarbe = JColorChooser.showDialog(this, "Füllfarbe", null);
    }
    public void setLinienFarbe() {
        Color farbe = JColorChooser.showDialog(this, "Linienfarbe", Color.BLACK);
        for (KnotenAnsicht k : knotenAuswahl)
            k.linienFarbe = farbe;
        for (KantenAnsicht ka : kantenAuswahl)
            ka.linienFarbe = farbe;
        repaint();
    }


    private int fangen(double x) {
        return Math.round((float)x/opt.rasterMittel)*opt.rasterMittel;
    }
    
    // -> steuerung.addKnoten(knoten, form, position)
    public KnotenAnsicht erzeugeKnoten(int typ, int x, int y) {
        x = opt.fang ? fangen(x) : x;
        y = opt.fang ? fangen(y) : y;

        int b = modell.opt.knotenBreite;
        int h = modell.opt.knotenHöhe;
// ---------- Beispielobjekt ----------
//        int index = 0;
//        while (modell.getGraph().enthält("K" + index))
//            index++;
//        Object inhalt = "K" + index;
// ------------------------------------
        
//        String titel = inhalt.toString();
        KnotenElement e = new KnotenElement();
        KnotenForm form = new Ellipse(x, y, b, h);
        switch (typ) {
            case 16:    break;
            case 17:    form = new NEck(    x, y, b, h, 3);   break;  // Shift
            case 18:    form = new Rechteck(x, y, b, h);      break;  // Strg
            case 26:    form = new NEck(    x, y, b, h, 6);   break;  // Alt + Strg
            case 24:    form = new NEck(    x, y, b, h, 5);   break;  // Alt
//            case 25:    form = new Ellipse( x, y, knotenBreite*2, knotenHöhe);    break;  // Alt + Shift
//            case 19:    form = new Rechteck(x, y, knotenBreite*2, knotenHöhe);    break;  // Strg + Shift
        }
        
        KnotenAnsicht k = new KnotenAnsicht(e, /*id, */ e.getName(), form);
        
        modell.hinzufügen(k);
        modell.knotenMenge.put(e, k);
        return k; //modell.getNode(inhalt);
    }
    
    // -> steuerung.moveKnoten(knoten, position)
    protected void neueKnotenPosition(KnotenAnsicht k, double x, double y) {
        x = opt.fang ? fangen(x) : x;
        y = opt.fang ? fangen(y) : y;
        modell.knotenVerschieben(k, x, y);
        knotengrößeAnpassen(k);
        repaint();
    }
    protected void neueKnotenPositionen(double dx, double dy) {
//        x = fang ? fangen(x) : x;
//        y = fang ? fangen(y) : y;
        for (KnotenAnsicht k : knotenAuswahl)
            //k.form.setPosition(new Point2D.Double(k.form.x + dx, k.form.y + dy)); 
            modell.knotenVerschieben(k, k.form.x + dx, k.form.y + dy);
        repaint();
    }
    
    
    public Menge<KnotenAnsicht> wähleKnoten(Point p1, Point p2) {
        Rectangle    rechteck = new Rectangle(p1.x, p1.y, Math.abs(p2.x-p1.x), Math.abs(p2.y-p1.y));
        Menge<KnotenAnsicht> auswahl = new Menge<KnotenAnsicht>();
        for (KnotenAnsicht k : modell.knotenMenge.values())
            if (rechteck.contains(k.form.x, k.form.y))
                auswahl.hinzufügen(k);
        return auswahl;
    }
    public Menge<KantenAnsicht> wähleKanten(Menge<KnotenAnsicht> knotenMenge) {
        Menge<KantenAnsicht> auswahl = new Menge<KantenAnsicht>();
        for (KantenAnsicht k : modell.kantenMenge.values())
            if (knotenMenge.enthält(k.start) && knotenMenge.enthält(k.ziel))
                auswahl.hinzufügen(k);
        return auswahl;
    }
    
    // Methode zum einfachen Wechsel zwischen reversibler und irreversibler Auslenkung
    private void knotenAusrichten(KnotenAnsicht k){
    	switch (opt.modus) {
    	case 0:
    		break;
		case 1:
			ausrichtenIrreversibel(k);
			break;
		case 2:
			ausrichtenReversibel(k);
			break;
		}
    }
    
    // Algorithmus 2-3: Irreversible Auslenkung, umrissreferenziert, rekursiv 
    private void ausrichtenIrreversibel(KnotenAnsicht n) {
    	double minAbstand = 30;
		double zuschlag = 2;
		HashSet<KnotenAnsicht> bewegteKnoten = new HashSet<KnotenAnsicht>();
		Point2D pn = n.form.getPosition();
		Rectangle2D bounds = n.form.getBounds2D();
		Point2D pk, p1, p2;
		double distanz;
		for (KnotenAnsicht k : modell.knotenMenge.values()) {
			if(k.equals(n))continue;
			if(k.form.intersects(bounds)){
				p1 = n.form.getPosition();
				p2 = k.form.getPosition();
				zuschlag+=p1.distance(n.form.schnittPunkt(p2)) + p2.distance(k.form.schnittPunkt(p1)); 
			} else {
				pk = k.form.getPosition();
				p1 = n.form.schnittPunkt(pk);
				p2 = k.form.schnittPunkt(pn);
			}
			distanz = p1.distance(p2);
			if (distanz < minAbstand) {
				bewegteKnoten.add(k);
				double f = (minAbstand - distanz + zuschlag) / distanz;
				double dx = (p2.getX() - p1.getX()) * f;
				double dy = (p2.getY() - p1.getY()) * f;
				neueKnotenPosition(k,k.form.x + dx, k.form.y + dy);					
			}
		}
		for(KnotenAnsicht temp : bewegteKnoten)ausrichtenIrreversibel(temp);
	}
   
    // Algorithmus 3-1: Reversible Auslenkung in hierarchischen Graphen
    private void ausrichtenReversibel(KnotenAnsicht n) {
		Baum<KnotenElement> hierarchie = modell.getHierarchie();
		KnotenElement mutter = hierarchie.getVorgänger(n.getObjekt());
		if (!gravitationsfelder.containsKey(mutter)) {
			gravitationsfelder.put(mutter, new Gravitationsfeld());
		}
		Gravitationsfeld gravi = gravitationsfelder.get(mutter);
		gravi.knotenHinzufügen(n);
		Menge<KnotenElement> elemente = hierarchie.getAst(mutter).getEbene(1);
		for (KnotenElement e : elemente) {
			KnotenAnsicht k = modell.getKnoten(e);
			if (n.equals(k))continue;
			gravi.berechneKraft(k);
			Point2D p = gravi.getKraftvektor(k);
			int count = 0;
			while (p.distance(0.0, 0.0) > 1) {
				neueKnotenPosition(k, k.form.x + p.getX() / 10,
						              k.form.y + p.getY() / 10);
				gravi.berechneKraft(k);
				p = gravi.getKraftvektor(k);
				if (count++ > 100) {
					neueKnotenPosition(k, k.form.x + p.getX(),
							              k.form.y + p.getY());
					break;
				}
			}

		}
	}
    
    // Algorithmus 3-2: Größenanpassung verfeinerter Knoten
    private void knotengrößeAnpassen(KnotenAnsicht n) {
		KnotenElement mutter = modell.getHierarchie().getVorgänger(
				n.getObjekt());
		if (!mutter.getName().equals("WURZEL")) {
			KnotenAnsicht mutterknoten = modell.getKnoten(mutter);
			double d;
			
				Point2D p1 = mutterknoten.form.schnittPunkt(n.form.getPosition());
				Point2D p2 = n.form.schnittPunkt(p1);
				d = p1.distance(p2);
				if (d < 10) {
					mutterknoten.form.setSize(mutterknoten.form.b + 2,
							mutterknoten.form.h + 2);
					knotenAusrichten(mutterknoten);
					knotengrößeAnpassen(mutterknoten);
				} else if (mutterknoten.form.b > opt.knotenBreite2 ||
						   mutterknoten.form.h > opt.knotenHöhe2) {
					Baum<KnotenElement> hierarchie = modell.getHierarchie();
					d = Double.POSITIVE_INFINITY;
					Menge<KnotenElement> m = hierarchie.getAst(hierarchie.getVorgänger(n.getObjekt())).getEbene(1);
					for (KnotenElement e : m) {
						KnotenAnsicht k = modell.getKnoten(e);
						p1 = mutterknoten.form.schnittPunkt(k.form.getPosition());
						p2 = k.form.schnittPunkt(p1);
						d = Math.min(d, p1.distance(p2));
					}
					if (d > 10) {
						mutterknoten.form.setSize(mutterknoten.form.b - d + 5, mutterknoten.form.h - d + 5);
					}
				}
				
			
		}
	}
    
    // -> dringend überarbeiten: -> ins GeometrieModell als: Knoten getroffen(x,
	// y)
    /**
	 * Liefert an der Stelle x,y den Knoten mit dem weitesten Abstand von der
	 * Wurzel, d.h. den feinsten Knoten.
	 */
    public KnotenAnsicht trefferKnoten(int x, int y) {
    	Baum<KnotenElement> baum = modell.getHierarchie(); 
    	KnotenElement p = baum.getWurzel();
        for (Map.Entry<KnotenElement,KnotenAnsicht> k : modell.knotenMenge.entrySet()) {
            if (k.getValue().form.contains(x, y)) {
            	KnotenElement p2 = k.getKey(); 
                p = baum.getAbstandWurzel(p)<baum.getAbstandWurzel(p2) ? p2 : p;
            }
        }
        if (p.equals(baum.getWurzel())) 
        	p = null;
        return modell.getKnoten(p);
    }
    
    public KantenAnsicht trefferKante(int x, int y) {
        for (KantenAnsicht k : modell.kantenMenge.values())
            if (k.form.contains(x, y))
                return k;
        return null;
    }
    

    public void zeigeKnotenEigenschaften(KnotenAnsicht k, int x, int y) {
    	
//      String s = (String)JOptionPane.showInputDialog(this, text, titel, 
//  			JOptionPane.PLAIN_MESSAGE, null, null, knoten.toString());

      KnotenEigenschaften ke = new KnotenEigenschaften(k);
//      ke.setLocationRelativeTo(this);
      int posx = x-ke.getWidth()/2;
      int posy = y-ke.getHeight()/2;
      ke.setLocation(posx, posy);
      if (ke.showDialog() == KnotenEigenschaften.FERTIG) {
    	  modell.knotenMenge.get(k.getObjekt()).titel = ke.getKnoten().titel;
    	  repaint();
      }
  }
  

	
    // Maus-Ereignisse
    public void mouseClicked(MouseEvent e) {    }

    public void mousePressed(MouseEvent e) {

        start = trefferKnoten(e.getX(), e.getY());
        kante = trefferKante(e.getX(), e.getY());
        
        switch(getModus()) {
            case ERSTELLEN:
            	if (start != null) {
            		// STRG + SHIFT + Maustaste ...
            		if (e.isControlDown() && e.isShiftDown()) {
            			// ... Knoten Auf|Zu klappen
//System.out.println(modell.istAufgepklappt(start));            			
            			if (modell.istAufgepklappt(start))
            				modell.zuklappen(start);
            			else 
            				modell.aufklappen(start);
            			knotenAusrichten(start);
//System.out.println("Offen: " + modell.aufgeklappteKnoten);            			
            		} else {
            			// Linke Maustaste ...
	                	if (e.getButton() == MouseEvent.BUTTON1) {
	                		if (modell.istAufgepklappt(start)) {
	                        	KnotenAnsicht k = erzeugeKnoten(e.getModifiers(), e.getX(), e.getY());
//System.out.println("Treffer: " + start.getObjekt());	                        
	                			modell.hinzufügen(start, k);
	                			knotenAusrichten(k);
//System.out.println("Nach dem verfeinerten Einfügen");
//System.out.println("Baum:  " + modell.getHierarchie());	                        
//System.out.println("Graph: " + modell.getGraph());	                        
								start = null;
	                		} else {
	                            // ... Kante einfügen beginnen
		                		pfeil = new Pfeil(new Point2D.Double(start.form.x, start.form.y), 
		                						  new Point2D.Double(e.getX(), e.getY()), 
		                						  opt.pfeilLänge, opt.pfeilBreite, 1);
	                		}
	                	// Rechte Maustaste ...
	                    } else if (e.getButton() == MouseEvent.BUTTON3) {
	                        // ... Knoten löschen
	                        modell.entfernen(start);
//	                        modell.knotenMenge.remove(start.getObjekt());
//System.out.println("Nach dem Löschen");
//System.out.println("Baum:  " + modell.getHierarchie());	                        
//System.out.println("Graph: " + modell.getGraph());	                        
	                        start = null;
	                    }
            		} 
                } else if (kante != null) {
                	if (e.getButton() == MouseEvent.BUTTON3) {
                        // Kante löschen
                		modell.entfernen(kante);
//                		modell.kantenMenge.remove(new Paar<Element,Element>(kante.start.getObjekt(),kante.ziel.getObjekt()));
                		kante = null;
                    }
                } else {
                	// Knoten einfügen
                	KnotenAnsicht k = erzeugeKnoten(e.getModifiers(), e.getX(), e.getY());
                	modell.hinzufügen(k);
//                	modell.knotenMenge.put(k.getObjekt(), k);
                    knotenAusrichten(k);
//System.out.println("Nach dem Einfügen");
//System.out.println("Baum:  " + modell.getHierarchie());	                        
//System.out.println("Graph: " + modell.getGraph());	                        
                    //repaint();
                    start = null;
                }
                break;
            case BEARBEITEN:
                if (start != null) {
                    p1 = null;
                    // Knoten bewegen 
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        // Mit STRG + Linke Maustaste ...
                        if (e.isControlDown()) {
                        	// ... Auswahl verkleinern|vergrößern
                            if (!knotenAuswahl.enthält(start))
                                knotenAuswahl.hinzufügen(start);
                            else
                                knotenAuswahl.entfernen(start);
                        // Ohne STRG ...
                        } else {
                        	// ... nur aktuellen Knoten auswählen
                            knotenAuswahl.leeren();
                            kantenAuswahl.leeren();
                            knotenAuswahl.hinzufügen(start);
                        }
                        repaint();
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        // KnotenEigenschaftenDialog anzeigen
                        zeigeKnotenEigenschaften(start, e.getX(), e.getY());
                        start = null;
                    }
                        
                } else if (kante != null) {
                    p1 = null;
                    // Kantenauswahl erweitern
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        // Kantenauswahl festlegen
                        if (e.isControlDown()) {
                            if (!kantenAuswahl.enthält(kante))
                                kantenAuswahl.hinzufügen(kante);
                            else
                                kantenAuswahl.entfernen(kante);
                            repaint();
                        } else {
                            kantenAuswahl.leeren();
                            knotenAuswahl.leeren();
                            kantenAuswahl.hinzufügen(kante);
                            repaint();
                        }
                    } 
//                    else if (button == MouseEvent.BUTTON3) {
//                        // KnotenEigenschaftenDialog anzeigen
//                        zeigeKnotenEigenschaften(e.getX(), e.getY());
//                        start = null;
//                    }
                } else {
                    // Mehrfachauswahl
                	repaint();
                    // Auswahl zurücksetzen
                    knotenAuswahl.leeren();
                    kantenAuswahl.leeren();
                    // 1. Ecke setzen
                    p1 = new Point(e.getX(), e.getY()); 
                    
//                    hintergrund.scale(1.2, 1.2);
                }
                break;
            case ANALYSIEREN:
				// Start- und Zielknoten z.B. für eine Routensuche markieren
				if (start != null) {
                    // Anfangs- und [Endknoten] markieren
                    if (anfrageStart == null && anfrageZiel == null) {
                    	anfrageStart = start;
//                    	modell.start = start;
                    } else if (anfrageStart != null && anfrageZiel == null) {
                    	anfrageZiel = start;
//                    	modell.ziel = start;
                    } else if (anfrageStart != null && anfrageZiel != null) {
                    	anfrageStart = start;
//                    	modell.start = start;
                        anfrageZiel = null;
//                    	modell.ziel = null;

                        ergebnisGraph = null;
                    }
                } else {
                    anfrageStart = null;
//                	modell.start = null;
                    anfrageZiel  = null;
//                	modell.ziel  = null;
                    ergebnisGraph= null;
                }	
				repaint();
                break;
        }
//        repaint();
    }

//    public void mousePressed(MouseEvent e) {
//    	int   button = e.getButton();
//        Point pos = new Point(e.getX(), e.getY());
//
//        start = trefferKnoten(pos.x, pos.y);
//        kante = trefferKante(pos.x, pos.y);
//        
//        
//        switch(getModus()) {
//            case ERSTELLEN:
//            	if (start != null) {
//            		if (e.isControlDown() && e.isShiftDown()) {
//            			// Verfeinern | Vergröbern
//            			if (istAufgepklappt(start))
//            				zuklappen(start);
//            			else 
//            				aufklappen(start);
//            		} else {
//	                	if (button == MouseEvent.BUTTON1) {
//	//                        if (e.getClickCount()>=2) 
//	//                            zeigeKnotenEigenschaften(e.getX(), e.getY());
//	//                        else
//	                            // Kante einfügen beginnen ...
//	                		pfeil = new Pfeil(new Point2D.Double(start.form.x, start.form.y), new Point2D.Double(e.getX(), e.getY()), opt.pfeilLänge, opt.pfeilBreite, 1);
//	                        
//	                    } else if (button == MouseEvent.BUTTON3) {
//	                        // Knoten löschen
//	                        modell.removeNode(start.getObjekt());
//	                        start = null;
//	                    }
//            		} 
//                } else if (kante != null) {
//                	if (button == MouseEvent.BUTTON3) {
//                        // Kante löschen
//                        modell.removeEdge(kante.start.getObjekt(), kante.ziel.getObjekt());
//                    }
//                } else {
//                	// Knoten einfügen
//                	//knotenHinzufügen(e.getModifiers(), pos.x, pos.y);
//                    knotenNeuAusrichten(knotenHinzufügen(e.getModifiers(), pos.x, pos.y));
//                    //repaint();
//                }
//                break;
//            case BEARBEITEN:
//                if (start != null) {
//                    p1 = null;
//                    // Knoten bewegen 
//                    if (button == MouseEvent.BUTTON1) {
//                        // Knotenauswahl festlegen
//                        if (e.isControlDown()) {
//                            if (!knotenAuswahl.enthält(start))
//                                knotenAuswahl.hinzufügen(start);
//                            else
//                                knotenAuswahl.entfernen(start);
//                            repaint();
//                        } else {
//                            knotenAuswahl.leeren();
//                            kantenAuswahl.leeren();
//                            knotenAuswahl.hinzufügen(start);
//                            repaint();
//                        }
//                    } else if (button == MouseEvent.BUTTON3) {
//                        // KnotenEigenschaftenDialog anzeigen
//                        zeigeKnotenEigenschaften(start, e.getX(), e.getY());
//                        start = null;
//                    }
//                        
//                } else if (kante != null) {
//                    p1 = null;
//                    // Kantenauswahl erweitern
//                    if (button == MouseEvent.BUTTON1) {
//                        // Knotenauswahl festlegen
//                        if (e.isControlDown()) {
//                            if (!kantenAuswahl.enthält(kante))
//                                kantenAuswahl.hinzufügen(kante);
//                            else
//                                kantenAuswahl.entfernen(kante);
//                            repaint();
//                        } else {
//                            kantenAuswahl.leeren();
//                            knotenAuswahl.leeren();
//                            kantenAuswahl.hinzufügen(kante);
//                            repaint();
//                        }
//                    } 
////                    else if (button == MouseEvent.BUTTON3) {
////                        // KnotenEigenschaftenDialog anzeigen
////                        zeigeKnotenEigenschaften(e.getX(), e.getY());
////                        start = null;
////                    }
//                } else {
//                    // Mehrfachauswahl
//                	repaint();
//                    // Auswahl zurücksetzen
//                    knotenAuswahl.leeren();
//                    kantenAuswahl.leeren();
//                    // 1. Ecke setzen
//                    p1 = new Point(e.getX(), e.getY()); 
//                    
////                    hintergrund.scale(1.2, 1.2);
//                }
//                break;
//            case ANALYSIEREN:
///*				// Marken setzen
// 				if (button == MouseEvent.BUTTON1) {
//					if (start.getZustand() == null)
//						start.setZustand(new MarkeBoolean());
//					else
//						start.setZustand(null);
//				}	*/
//					
//				// Start- und Zielknoten z.B. für eine Routensuche markieren
//				if (start != null) {
//                    // Anfangs- und [Endknoten] markieren
//                    if (anfrageStart == null && anfrageZiel == null) {
//                    	anfrageStart = start;
//                    	modell.start = start;
//                    } else if (anfrageStart != null && anfrageZiel == null) {
//                    	anfrageZiel = start;
//                    	modell.ziel = start;
//                    } else if (anfrageStart != null && anfrageZiel != null) {
//                    	anfrageStart = start;
//                    	modell.start = start;
//                        anfrageZiel = null;
//                    	modell.ziel = null;
//
//                        ergebnisGraph = null;
//                    }
//                } else {
//                    anfrageStart = null;
//                	modell.start = null;
//                    anfrageZiel  = null;
//                	modell.ziel = null;
//                    ergebnisGraph= null;
//                }	
//				repaint();
//                break;
//        }
//    }

    public void mouseReleased(MouseEvent e) {
    	int button = e.getButton();
        Point pos = new Point(e.getX(), e.getY());

        ziel = trefferKnoten(pos.x, pos.y);
        
        switch(getModus()) {
            case ERSTELLEN:
            	if (start != null) {
            		if (e.isControlDown() && e.isShiftDown()) {
            			
            		} else {
	                    // Wenn die Kante ins Leere zeigt ...
	                	boolean leer = false;
	                    if (ziel == null) {
	                        // ... Knoten einfügen
	                        ziel = erzeugeKnoten(e.getModifiers(), pos.x, pos.y);
	                        leer = true;
	                    }
	                    // ... Kante einfügen beenden.
	//                  kanteHinzufügen(start, ziel);
	
	                    //----> auslagern                    
	                    Pfeil pf = new Pfeil(start.form.getPosition(), ziel.form.getPosition());    
	                    // Unterschiedliche Pfeilarten, abhängig von Strg + Alt + Shift 
	/*                  if (e.isControlDown() && !e.isShiftDown() && !e.isAltDown())
	                        pf = new Pfeil(start.form.getPosition(), ziel.form.getPosition(), pfeilLänge, pfeilBreite, 2); 
	                    else if (!e.isControlDown() && e.isShiftDown() && !e.isAltDown())
	                        pf = new Pfeil(start.form.getPosition(), ziel.form.getPosition(), pfeilLänge, pfeilBreite, 0); 
	                    else
	                        pf = new Pfeil(start.form.getPosition(), ziel.form.getPosition(), pfeilLänge, pfeilBreite, 1); */
	                    pf.setPfeil(start.form.schnittPunkt(ziel.form.getPosition()), ziel.form.schnittPunkt(start.form.getPosition()));
	                    //<----
	                    
	                    KantenAnsicht k = new KantenAnsicht(start, ziel, pf);
	                    modell.hinzufügen(k);
	                    
	                    if(leer && !start.equals(ziel)){
	                    	knotenAusrichten(ziel);
	                    }
            		}
                }
                break;
            case GraphenDiagramm.BEARBEITEN:
                // Mehrfachauswahl beenden
                if (p1 != null) {
                    knotenAuswahl = wähleKnoten(p1, pos);
                    kantenAuswahl = wähleKanten(knotenAuswahl);
                }
                for(Gravitationsfeld g:gravitationsfelder.values())g.release();
                repaint();
                break;
            case GraphenDiagramm.ANALYSIEREN:
                break;
        }
        pfeil = null;   p1 = null;
//        repaint();
    }
    
    public void mouseEntered(MouseEvent e) {    }

    public void mouseExited(MouseEvent e) {    }

    // Maus-Bewegungs-Ereignisse
    public void mouseDragged(MouseEvent e) {
    	
        Point pos = new Point(e.getX(), e.getY());

        switch(getModus()) {
            case ERSTELLEN:
                if(pfeil != null) { 
                    // ... Kante einfügen fortsetzen ...
                	pfeil.setPfeil(pfeil.getP1(), new Point(opt.fang ? fangen(pos.x) : pos.x, opt.fang ? fangen(pos.y) : pos.y)); //pfeil.getP2());
                    repaint();
                }
                break;
            case GraphenDiagramm.BEARBEITEN:
                if(start != null) {
//                    int dx = e.getX() - (int) start.form.x;
//                    int dy = e.getY() - (int) start.form.y;
//                    neueKnotenPositionen(dx, dy);
                    neueKnotenPosition(start, pos.x, pos.y);
                    knotenAusrichten(start);
                }
                break;
            case GraphenDiagramm.ANALYSIEREN:
                break;
        }
    }

    public void mouseMoved(MouseEvent e) {    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        setModus(3 + getModus() + e.getWheelRotation());
    }
    
	
}
