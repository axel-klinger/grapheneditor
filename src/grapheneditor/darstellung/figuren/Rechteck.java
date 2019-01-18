/* Erstellt am 02.10.2004 */
package grapheneditor.darstellung.figuren;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import static java.lang.Math.*;


public class Rechteck extends KnotenForm {

//    private Dimension maße;
    
    public Rechteck(Point2D position, Dimension maße) {
        super();
        x = position.getX();
        y = position.getY();
        b = maße.getWidth();
        h = maße.getHeight();
        erstelleWeg();
    }
    
    public Rechteck(double x, double y, double b, double h) {
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
        weg.moveTo((float)(x - b/2), (float)(y - h/2));
        weg.lineTo((float)(x + b/2), (float)(y - h/2));
        weg.lineTo((float)(x + b/2), (float)(y + h/2));
        weg.lineTo((float)(x - b/2), (float)(y + h/2));
        weg.closePath();
    }

    public Point2D schnittPunkt(Point2D richtung) {
        double x0 = this.x; 
        double y0 = this.y; 
//        double b = maße.getWidth();
//        double h = maße.getHeight();
        double alpha = atan2((x0 - richtung.getX()),(y0 - richtung.getY()));
        double x = 0, y = 0;
        if (alpha>=-PI/4 && alpha<=PI/4) {
            x = x0-b/2*tan(alpha);
            y = y0-h/2;
        } else if (alpha<-PI*3/4 || alpha>PI*3/4) { 
            x = x0+b/2*tan(alpha);
            y = y0+h/2;
        } else if (alpha>=-PI*3/4 && alpha<-PI/4) { 
            x = x0+b/2;
            y = y0-h/2*tan(alpha+PI/2);
        } else if (alpha>PI/4 && alpha<=PI*3/4) { 
            x = x0-b/2;
            y = y0+h/2*tan(alpha-PI/2);
        }
        return new Point2D.Double(x, y);
    }
}
