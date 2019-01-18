/* Created on 25.09.2004 */
package grapheneditor.darstellung;

import grapheneditor.KnotenElement;
import grapheneditor.GraphenModell;
import informatik.strukturen.graphen.hilfsmittel.GraphenEigenschaften;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class GraphenEigenschaftenAnsicht extends JPanel implements Observer {

    private GraphenModell modell;
    private GraphenEigenschaften<KnotenElement> ge;
    private boolean anzeige = false;
    
    private TextLayout[] zeilen;
    private String text;
    
    private int x1=20, x2=30, x3=120;
    private int y1=20, dy=20, dy1=30; 
    private int x=0, y=0;
    
    public GraphenEigenschaftenAnsicht(GraphenModell modell) {
        this.modell = modell;
        this.modell.addObserver(this);
        text = " ";
        ge = new GraphenEigenschaften<KnotenElement>(modell.getGraph());
        setPreferredSize(new Dimension(160, 100));
        setBackground(Color.white);
    }
    
    public void switchAnsicht() {
        if (anzeige){
            this.modell.addObserver(this);
            setVisible(true);
        } else {
            this.modell.deleteObserver(this);
            setVisible(false);
        }
        anzeige = !anzeige;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
	    g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	    g2.drawString("Knoten: " + modell.getGraph().anzahlKnoten() + 
	            	", Kanten: " + modell.getGraph().anzahlKanten(), 20, 20);
	    y = y1; 

	    ds("EIGENSCHAFTEN", g2);
	    ds("Reflexiv:    ", ge.istReflexiv(), 	  g2);
	    ds("Antireflexiv:", ge.istAntiReflexiv(), g2);
	    ds("Symmetrisch: ", ge.istSymmetrisch(),  g2);
	    ds("Asymmetrisch:", ge.istAsymmetrisch(), g2);
	    ds("Identitiv:   ", ge.istIdentitiv(), 	  g2);
	    ds("Linear:      ", ge.istLinear(), 	  g2);
	    ds("Konnex:      ", ge.istKonnex(), 	  g2);
	    ds("Transitiv:   ", ge.istTransitiv(), 	  g2);

	    ds("ZYKLEN       ", g2);
	    ds("Zyklisch:    ", ge.istZyklisch(), 	  g2);
	    ds("Azyklisch:   ", ge.istAzyklisch(), 	  g2);
	    
	    ds("ZUSAMMENHANG ", g2);
	    ds("Streng:   	", ge.istStrengZusammenhängend(),  g2);
	    ds("Schwach:   	", ge.istSchwachZusammenhängend(), g2);
	    
	    y = 0;
//		System.out.println(modell.getGraph() + " " + modell.alleKanten().anzahl());
    }

    private void ds(String s, Graphics2D g) {	
        y+=dy1;	g.drawString(s, x1, y);	
    }
    
    private void ds(String s, boolean b, Graphics2D g) {
        y+=dy;	g.drawString(s, x2, y);	g.drawString("" + b, x3, y);
    }
    
    public void update(Observable o, Object arg) {
        repaint();
    }
}
