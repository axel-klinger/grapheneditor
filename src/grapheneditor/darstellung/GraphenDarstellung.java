/* Erstellt am 23.11.2004 */
package grapheneditor.darstellung;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import grapheneditor.GraphenModell;

import javax.swing.JComponent;
//import javax.swing.JPanel;

/**
 * <P><B>GraphenDarstellung.java</B> <I>(engl. )</I>: Ein GraphenDarstellung.java ist ... </P>
 *
 * @author klinger
 */
public class GraphenDarstellung extends JComponent 
							 implements ComponentListener,
							 			Observer {

    protected GraphenModell modell;
    
    protected BufferedImage bild;
    
    protected Graphics2D 	hintergrund;
//    protected VectorGraphics    hintergrund;
    
    protected Rectangle 	fläche;
    
    private boolean erstesMal = true;

//  ---------- allg. GraphenDarstellung ----------
    //
    // Die Modi des Editors
    public static final int ERSTELLEN = 0;
    public static final int BEARBEITEN = 1;
    public static final int ANALYSIEREN = 2;
    
    protected boolean modi[] = {false, false, false};
    protected int modus = -1;
    
//    private Image hintergrundBild;
    
    // Vordefinierte Hintergrundfarben für die verschiedenen Modi des Editors
    protected Color[] hintergrundFarbe = {new Color(255, 235, 235), 
			   					   new Color(255, 255, 230),
			   					   new Color(255, 255, 255)};

    // Vordefinierte Strichstärken für die Zeichnungselemente und das Raster
    protected Stroke[] strichStärke ={new BasicStroke(0.1f),
						            new BasicStroke(1.0f),
						            new BasicStroke(1.5f),
						            new BasicStroke(2.0f),
						            new BasicStroke(2.5f),
						            new BasicStroke(3.0f)};
    //
//     ----------
    
    
    public GraphenDarstellung(GraphenModell modell) {
        this.modell = modell;
        this.modell.addObserver(this);
        
        addComponentListener(this);
        setBackground(Color.WHITE);

//		hintergrundBild = Toolkit.getDefaultToolkit().getImage( "D:/klinger/eclipse42/GraphenEditor/Beispiele/Hintergrund/Stadtplan.jpg" );

    }

    public void paintComponent(Graphics g) {
		Dimension dim = getSize();
		int w = dim.width;
    	int h = dim.height; 
                 
    	if(erstesMal){
			bild = (BufferedImage)createImage(w, h);
            hintergrund = bild.createGraphics();
//			hintergrund = VectorGraphics.create(bild.createGraphics());//bild.createGraphics();
			
			hintergrund.setColor(Color.black);
			hintergrund.setStroke(strichStärke[3]);
			hintergrund.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			fläche = new Rectangle(dim);
			erstesMal = false;
    	}
    	
		// Lösche den bisherigen Zeichenbereich
    	if (modi[modus])
    	    hintergrund.setBackground(hintergrundFarbe[modus]);
    	else
    	    hintergrund.setBackground(Color.WHITE);
    	hintergrund.setColor(Color.BLACK);
    	hintergrund.clearRect(0, 0, fläche.width, fläche.height);

//    	hintergrund.drawImage(hintergrundBild, 0, 0, this);

    }
    
    // Besser allgemein in Darstellung
    public void switchModus(int richtung) {
    	int tempMod = modus;
    	do
    		modus = (modi.length + modus + richtung) % modi.length;
    	while (modi[modus] == false && tempMod != modus);
        repaint();
    }
	public int getModus() {
		return modus;
	}
    public void setModus(int m) {
        if ((modi[m % modi.length]) != false) {
            modus = m % 3;
            repaint();
        }
    }

    public void update(Observable o, Object arg) {
        repaint();
    }

    // Komponenten-Ereignisse
    public void componentResized(ComponentEvent e) {
		Dimension dim = getSize();
		int w = dim.width > 0 ? dim.width : 1;
    	int h = dim.height >0 ? dim.height : 1; 
                 
		bild = (BufferedImage)createImage(w, h);
        hintergrund = bild.createGraphics();
//		hintergrund =  VectorGraphics.create(bild.createGraphics());
        
		hintergrund.setBackground(Color.white);
		hintergrund.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		fläche = new Rectangle(dim);
		
		repaint();
    }

    public void componentMoved(ComponentEvent e) {    }

    public void componentShown(ComponentEvent e) {    }

    public void componentHidden(ComponentEvent e) {   }

    /**
     * @return bild
     */
    public BufferedImage getSnapshot() {
        return bild;
    }

}
