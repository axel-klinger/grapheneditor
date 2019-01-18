/* Erstellt am 03.10.2004 */
package grapheneditor.darstellung.figuren;

//import java.awt.Dimension;
import java.awt.geom.Point2D;
import static java.lang.Math.*;

public class NEck extends KnotenForm {

//    private Dimension maﬂe;
    
    private int anzahl;
    private double winkel;
    private float r; 
    
    public int anzahlEcken() {
    	return anzahl;
    }
        
    public NEck(double x, double y, int b, int h, int anzahl) {
        super();
        this.x = x;
        this.y = y;
        this.b = b;
        this.h = h;
        
        this.anzahl = anzahl;
//        this.h = h;
        winkel = 2*PI/anzahl;
        if (anzahl%2 == 0)
            r = (float)(h/(2*cos(winkel/2)));
        else
            r = (float)(h/(1+cos(winkel/2)));
        erstelleWeg();
    }
    
    protected void erstelleWeg() {
        double dr, dx, dwinkel;
        
        if (anzahl%2 == 0)
            r = (float)(h/(2*cos(winkel/2)));
        else
            r = (float)(h/(1+cos(winkel/2)));
        
        if (anzahl%2 == 0) {
            dr = r*cos(winkel/2);
            dwinkel = winkel/2;
            dx = r*sin(winkel/2);
        } else { 
            dr = r;
            dwinkel = 0;
            dx = 0;
        }
        weg.reset();
        weg.moveTo((float)(x+dx), (float)(y-dr));
        for (int i = 1; i<anzahl; i++)
            weg.lineTo((float)(x + r*sin(i*winkel + dwinkel)), 
                       (float)(y - r*cos(i*winkel + dwinkel)));
        weg.closePath();
    }

    public Point2D schnittPunkt(Point2D richtung) {
    	
        if (anzahl%2 == 0)
            r = (float)(h/(2*cos(winkel/2)));
        else
            r = (float)(h/(1+cos(winkel/2)));

        double tau = atan2((x-richtung.getX()),(y-richtung.getY())) + PI;
        
        int i = 0;
        while (i*winkel+winkel/2 < tau) {	i++; }
        
        double alpha = tau - (i-1)*winkel - winkel/2;
        double beta  = PI/2 - PI/anzahl;
        double gamma = PI - beta - alpha;
        double b 	 = r*sin(beta)/sin(gamma);
        
        return new Point2D.Double(x + b*sin(tau), y + b*cos(tau));
    }
}
