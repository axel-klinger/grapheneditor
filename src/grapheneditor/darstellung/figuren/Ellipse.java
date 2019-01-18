/* Erstellt am 02.10.2004 */
package grapheneditor.darstellung.figuren;

import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import static java.lang.Math.*;

public class Ellipse extends KnotenForm {

//    private Dimension maﬂe;
	
	
    public Ellipse(Point2D position, Dimension maﬂe) {
        super();
        x = position.getX();
        y = position.getY();
        b = maﬂe.getWidth();
        h = maﬂe.getHeight();
        erstelleWeg();
    }
    
    public Ellipse(double x, double y, int b, int h) {
        super();
        this.x = x;
        this.y = y;
        this.b = b;
        this.h = h;
        erstelleWeg();
    }
    
    protected void erstelleWeg() {
        float x = (float)this.x;
        float y = (float)this.y;
        weg.reset();
        PathIterator pi = new Ellipse2D.Double(this.x-b/2, this.y-h/2, b, h).getPathIterator(new AffineTransform()); 
        weg.append(pi, true);
    }

    public Point2D schnittPunkt(Point2D richtung) {
        double x0 = this.x; 
        double y0 = this.y; 
//        double b = maﬂe.getWidth();
//        double h = maﬂe.getHeight();
        double alpha = atan2((x0-richtung.getX()),(y0-richtung.getY()));
        double x = 0, y = 0;
        x = x0 - b/2*cos(alpha-PI/2);
        y = y0 + h/2*sin(alpha-PI/2);
        return new Point2D.Double(x, y);
    }
}
