/* Erstellt am 18.11.2004 */
package grapheneditor.darstellung;

import informatik.strukturen.Paar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
//import java.util.Arrays;
import java.util.Observable;

import grapheneditor.KnotenElement;
import grapheneditor.GraphenModell;
import grapheneditor.KantenAnsicht;
//import grapheneditor.Kante;

/**
 * <P><B>MatrizenDiagramm.java</B> <I>(engl. )</I>: Ein MatrizenDiagramm.java ist ... </P>
 *
 * @author Matthias Flammiger und Axel Klinger
 */
public class MatrizenDiagramm extends GraphenDarstellung 
						   implements MouseListener {

    // + Tastatursteuerung {recht} {links} {hoch} {runter} {space}
    
	private KnotenElement[] knotenFeld;
	private int spaltenBreite = 40;
	private int zeilenHöhe = 40;
	private int xPosition = 60;
	private int yPosition = 60;
	
    private int dy = (int)(0.7*zeilenHöhe);
    private int dx = (int) (0.4*spaltenBreite); 
        
//    private Kante kante = null;

	
    // ----------   Konstruktor    ----------
    public MatrizenDiagramm(GraphenModell modell) {
        super(modell);

        this.modell = modell;
		this.modell.addObserver(this);

		knotenFeld = new KnotenElement[0];

		// Knoten mit Indizes versehen
/*		Menge eins = modell.alleKnoten();
		Iterator it = eins.iterator();
		while (it.hasNext()) {
		    Object zwei = it.next();
		    knotenVector.add(zwei);
		}	*/

//		for( Knoten k : modell.alleKnoten() )
//		    knotenVector.add(k);
		
		addMouseListener(this);
		
        modi[ERSTELLEN]   = true;
        modi[ANALYSIEREN] = true;
        modus = ERSTELLEN;
    }
    
    
    // ---------- Hauptfunktionen  ----------
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
		Graphics2D g2 = (Graphics2D) g;
		
		hintergrund.setColor(Color.BLACK);
		hintergrund.setFont(new Font("Courier New", Font.BOLD, 20));
		
		// ----- Auf den hintergrund malen -----    	
		
		zeichneRaster(hintergrund);   // Linien
		zeichneMatrix(hintergrund);   // Werte
		
		// -------------    	
		// Zeichne das gepufferte Bild auf den Bildschirm
		g2.drawImage(bild, 0, 0, this);
        
    }
    
    public void zeichneMatrix(Graphics2D g2) {
        int x = xPosition;
        int y = yPosition;
        
		g2.setColor(Color.BLACK);
		for (int spalte = 0; spalte<knotenFeld.length; spalte++) {
			String b = knotenFeld[spalte].toString();
			x += spaltenBreite;
			y += zeilenHöhe;
			g2.drawString(b, x, yPosition + dy);
			g2.drawString(b, xPosition, y + dy);
			
			for (int zeile = 0; zeile<knotenFeld.length; zeile++)
				if (modell.getGraph().enthält(knotenFeld[zeile],knotenFeld[spalte])) 
				    g2.drawString("X", dx + xPosition + (spalte+1)*spaltenBreite, yPosition + (zeile+1)*zeilenHöhe + dy);
//				else
//				    g2.drawString("0", dx + xPosition + (spalte+1)*spaltenBreite, yPosition + (zeile+1)*zeilenHöhe + dy);
		}
    }
    
	// Achtung: nur für homogene Relationen!!! (Quadratisch)
    public void zeichneRaster(Graphics2D g2) {
		g2.setColor(Color.LIGHT_GRAY);
		int maxX = xPosition + (knotenFeld.length+1)*spaltenBreite;
		int maxY = yPosition + (knotenFeld.length+1)*zeilenHöhe;
        for (int spalte = 0; spalte<=knotenFeld.length; spalte++) {
			g2.draw(new Line2D.Double(xPosition + spaltenBreite, yPosition + (spalte+1)*zeilenHöhe, maxX, yPosition + (spalte+1)*zeilenHöhe));
			g2.draw(new Line2D.Double(xPosition + (spalte+1)*spaltenBreite, yPosition + zeilenHöhe, xPosition + (spalte+1)*spaltenBreite, maxY));
        }
    }
    
    public Paar<KnotenElement,KnotenElement> kanteGetroffen(MouseEvent e)	{
        int i = (e.getX()-xPosition)/spaltenBreite-1;
		int j = (e.getY()-yPosition - zeilenHöhe)/zeilenHöhe;
		if (i<0 || i>=knotenFeld.length)
		    return null;
		if (j<0 || j>=knotenFeld.length)
		    return null;
		return new Paar<KnotenElement,KnotenElement>(knotenFeld[j], knotenFeld[i]);
//				new Kante(modell.getNode(knotenFeld[j]), 
//		        		 modell.getNode(knotenFeld[i])); //modell.getEdge(knotenFeld[j],knotenFeld[i]);
    }

    public void update(Observable graphmod, Object arg) {
        if (modell.getGraph().anzahlKnoten() != knotenFeld.length) {
            // eigentlich erst sortieren ...
            //Arrays.sort(knotenFeld);
            
            knotenFeld = new KnotenElement[modell.getGraph().anzahlKnoten()];
        	int i = 0;
            for (KnotenElement e : modell.getGraph().knoten())
                knotenFeld[i++] = e;	// Hier nicht Titel, da sonst falsche Kreuze!!!
        }
        repaint();
    }

    
    // ---------- EingabeSteuerung ----------
    public void mousePressed(MouseEvent e) { 
        Paar<KnotenElement,KnotenElement> p = kanteGetroffen(e);
        KantenAnsicht kold = modell.getKante(p.eins(), p.zwei());
        
        int spalte = (e.getX()-xPosition)/spaltenBreite-1;
		int zeile = (e.getY()-yPosition - zeilenHöhe)/zeilenHöhe;
		if ((spalte>=0 && spalte<knotenFeld.length) &&
			(zeile>=0 && zeile<knotenFeld.length))
		    //if (k != null)
		    if (modell.getGraph().enthält(p.eins(), p.zwei()))
		        modell.entfernen(kold);
		    else {
		    	KantenAnsicht k = new KantenAnsicht(modell.getKnoten(p.eins()),modell.getKnoten(p.zwei()));
		        modell.hinzufügen(k);
// ???????
//		        modell.knotenVerschieben(k.start,k.start.form.x, k.start.form.y);
//		        modell.knotenVerschieben(k.ziel, k.ziel.form.x, k.ziel.form.y);
		    }
		repaint();
    }

    public void mouseReleased(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) {	}

    public void mouseExited(MouseEvent e) {	}
    
    public void mouseClicked(MouseEvent arg0) { }
}
