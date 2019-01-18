
package grapheneditor.darstellung.figuren;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import static java.lang.Math.*;

/**
 * <P><B>Pfeil.java</B> <I>(engl. )</I>: Ein Pfeil.java ist ... </P>
 *
 * Typen:	-->		0
 * 			--|>	1
 * 			--)>	2
 *
 * @author Axel
 */
public class Pfeil implements Shape, Serializable {

    private GeneralPath weg;
    private Point2D p1, p2;
//    private	double x1, y1, x2, y2;
    private float l;
    private float b;
    private int typ;
    private double l2;
    
    public Pfeil(Point2D p1, Point2D p2) {
        this(p1, p2, 15, 10, 1);
//        this(p1, p2, 75, 50, 0);
    }
    
    public Pfeil(double x1, double y1, double x2, double y2) {
        this(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2));
    }
    
    public Pfeil(double x1, double y1, double x2, double y2, int l, int b, int typ) {
        this(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2), l, b, typ);
    }
    
    public Pfeil(Point2D p1, Point2D p2, int l, int b, int typ) {
        this.p1 = p1;
        this.p2 = p2;
        this.l  = l;
        this.b  = b;
        this.typ = typ;
        l2  = sqrt(l*l + b*b);
        weg = new GeneralPath();
        erstelleWeg();
    }
    
    public void setPfeil(Point2D p1, Point2D p2) {
        this.p1 = p1;
        this.p2 = p2;
        erstelleWeg();
    }
    
    public Point2D getP1() { return p1; }
    public Point2D getP2() { return p2; }
    public int getTyp()    { return typ; }
    //public float getLängeSpitze() {	return l;   } //TODO Evtl. wieder auf int zurückstellen
    //public float getBreiteSpitze(){	return b;	} //TODO Evtl. wieder auf int zurückstellen
    public int getBreiteSpitze(){	return (int)b;	}
    public int getLängeSpitze() {	return (int)l;   }
    
    
	private void erstelleWeg() {   
	    double w2 = atan2(b/2, l);

        float x0 = (float) p1.getX(); 
        float y0 = (float) p1.getY(); 
        float x1 = (float) p2.getX(); 
        float y1 = (float) p2.getY(); 
	    
	    double winkel = atan2(x1-x0, y1-y0);
	    
	    weg.reset();
        weg.moveTo(x0, y0);
        weg.lineTo(x1, y1);
        weg.moveTo((float)(x1-l2*sin(winkel+w2)),(float) (y1 - l2*cos(winkel+w2)));
        weg.lineTo(x1, y1);
        weg.lineTo((float)(x1-l2*sin(winkel-w2)),(float) (y1 - l2*cos(winkel-w2)));

//        weg.reset();
//        weg.moveTo(x0, y0);
//        weg.lineTo(x1, y1);
//        weg.moveTo((float)(x1- l2*sin(winkel+w2)),(float) (y1 - l2*cos(winkel+w2)));
//        weg.lineTo(x1, y1);
//        weg.lineTo((float)(x1-l2*sin(winkel-w2)),(float) (y1 - l2*cos(winkel-w2)));
        switch (typ) {
        	case 0:
        	    break;
        	case 1:
                weg.closePath();	
        	    break;
        	case 2:
                weg.quadTo( (float)(x1 - l2/2*sin(winkel)), 
                        	(float)(y1 - l2/2*cos(winkel)),
                        	(float)(p2.getX() - l2*sin(winkel+w2)), 
                        	(float)(p2.getY() - l2*cos(winkel+w2)));
                weg.closePath();	
        	    break;
        }
	} 
    
    public Rectangle getBounds() {        			return weg.getBounds();    }
    public Rectangle2D getBounds2D() {    			return weg.getBounds2D();  }
    public boolean contains(double x, double y) {   return weg.contains(x, y); }
    public boolean contains(Point2D p) {  			return weg.contains(p);    }
    public boolean intersects(double x, double y, double w, double h) {        	return weg.intersects(x, y, w, h);    }
    public boolean intersects(Rectangle2D r) {   	return weg.intersects(r); }
    public boolean contains(double x, double y, double w, double h) {        	return weg.contains(x, y, w, h);    }
    public boolean contains(Rectangle2D r) {     	return weg.contains(r);   }
    public PathIterator getPathIterator(AffineTransform at) {        return weg.getPathIterator(at);    }
    public PathIterator getPathIterator(AffineTransform at, double flatness) {	return weg.getPathIterator(at, flatness);    }
}