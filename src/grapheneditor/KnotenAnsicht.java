
package grapheneditor;

import grapheneditor.darstellung.figuren.KnotenForm;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

/**
 * @author Axel
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class KnotenAnsicht implements Serializable {
    
//    private Integer id;
    private KnotenElement  inhalt;
    public String  titel;
    
    public KnotenForm form;
	public Color füllFarbe;
    public Color linienFarbe;
	
    public KnotenAnsicht(KnotenElement inhalt, /*Integer id, */KnotenForm form) {
        this(inhalt, inhalt.toString(), form);
	}
        
    public KnotenAnsicht(KnotenElement inhalt, /*Integer id, */String titel, KnotenForm form) {
//        this.id = id;
        this.inhalt = inhalt;
        this.titel = titel;
        this.form = form;
        füllFarbe = Color.WHITE; //new Color(0,0,0);
        linienFarbe = Color.BLACK;
	}
    
    public KnotenElement getObjekt() {
        return inhalt;
    }
    
    public void draw(Graphics2D g2) {
        g2.setColor(füllFarbe);
        g2.fill(form);
        
        g2.setColor(linienFarbe);
        g2.draw(form);
        
//        if (opt.schrift) {
    	g2.setColor(Color.BLACK);
//        	g2.setFont(opt.schriftArt);
        g2.drawString(titel, 
                (int)form.x-g2.getFontMetrics().stringWidth(titel)/2, 
                (int)(form.y-form.h*0.8));
//        }
    }
    
/*    public Integer getID() {
        return id;
    }	*/
        
    public boolean equals(Object o) {
        if (!(o instanceof KnotenAnsicht))
            return false;
        KnotenAnsicht ka = (KnotenAnsicht) o;
        return this.inhalt.equals(ka.inhalt); 
    }
    
    public String toString() {
    	return "(" + inhalt.getID() + "," + inhalt.getName() + ")";
    }
    
    // HashCode
    // ToString
}
