/* Erstellt am 18.11.2004 */
package grapheneditor.darstellung;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import grapheneditor.GraphenModell;

/**
 * <P><B>MengenSchreibweise.java</B> <I>(engl. )</I>: Ein MengenSchreibweise.java ist ... </P>
 *
 * @author Axel
 */
public class MengenSchreibweise extends GraphenDarstellung {

    /** Comment for <code>serialVersionUID</code>   */
    private static final long serialVersionUID = 1L;

    public MengenSchreibweise(GraphenModell modell) {
        super(modell);
        // ...
        modi[ANALYSIEREN] = true;
        modus = ANALYSIEREN;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
		Graphics2D g2 = (Graphics2D) g;

		hintergrund.setBackground(Color.WHITE);
		hintergrund.setColor(Color.BLACK);
		hintergrund.setFont(new Font("Courier New", Font.BOLD, 20));
		
// -------------    	
//
		hintergrund.drawString("" + modell.getGraph(), 50, 50);
//
// -------------    	
		
		// Zeichne das gepufferte Bild auf den Bildschirm
		g2.drawImage(bild, 0, 0, this);
    }
}
