/*
 * Created on 28.11.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package grapheneditor;

import grapheneditor.darstellung.figuren.Pfeil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

/**
 * @author Axel
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class KantenAnsicht implements Serializable {

    public KnotenAnsicht start;
    public KnotenAnsicht ziel;
    public Pfeil  form;

    public Color linienFarbe;

    public KantenAnsicht(KnotenAnsicht start, KnotenAnsicht ziel) {
        this(start, ziel, new Pfeil(start.form.getPosition(), ziel.form.getPosition()));	// unsauber: Pfeil!!!
    }

    
    public KantenAnsicht(KnotenAnsicht start, KnotenAnsicht ziel, Pfeil form) {
        this.start = start;
        this.ziel = ziel;
        this.form = form;
        linienFarbe = new Color(0,0,0);
    }
    
    public void draw(Graphics2D g2) {
//		if (opt.pfeilspitze == true) 
//	        for (Kante k : modell.kantenMenge.values()) {
		        g2.setColor(linienFarbe);
	            g2.draw(form);
	            if (((Pfeil)form).getTyp() != 0)
	                g2.fill(form);
//	        }
//		else
//	        for (Kante k : modell.kantenMenge.values()) {
//		        g2.setColor(k.linienFarbe);
//	            g2.draw(new Line2D.Double(k.start.form.getPosition(), k.ziel.form.getPosition()));
//	        }

    }
}
