/* Erstellt am 02.10.2004 */
package grapheneditor.darstellung.figuren;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;


// Form (KnotenForm (Ellipse, Rechteck, N-eck), KantenForm (Linie, Pfeil)) ! ! !

public abstract class KnotenForm implements Shape, Serializable {

    protected GeneralPath weg;
//    public Point2D 	  position;
    public double x;
    public double y;
    public double b;
    public double h;
    protected double 	  winkel;

    public KnotenForm() {
        weg 	 = new GeneralPath();
//        position = new Point2D.Double(0, 0);
        x = 0.0;
        y = 0.0;
        
        b = 0.0;
        h = 0.0;
    }
    
    public void setPosition(Point2D position) {
//        this.position = position;
        x = position.getX();
        y = position.getY();
        erstelleWeg();
    }

    public void setSize(double b, double h) {
    	this.b = b;
    	this.h = h;
    	erstelleWeg();
    }
    
    public void setBounds(Rectangle r) {
    	x = r.getX();
    	y = r.getY();
    	b = r.getWidth();
    	h = r.getHeight();
    	erstelleWeg();
    }
    
    public Point2D getPosition() {
        return new Point2D.Double(x, y);
    }
    
    public abstract Point2D schnittPunkt(Point2D richtung);
    
	protected abstract void erstelleWeg();
    
    public Rectangle getBounds() 		{	return weg.getBounds();    }
    public Rectangle2D getBounds2D() 	{	return weg.getBounds2D();  }
    public boolean contains(double x, double y) {   return weg.contains(x, y); }
    public boolean contains(Point2D p) 	{	return weg.contains(p);    }
    public boolean intersects(double x, double y, double w, double h) {        return weg.intersects(x, y, w, h);    }
    public boolean intersects(Rectangle2D r) {   return weg.intersects(r); }
    public boolean contains(double x, double y, double w, double h) {        return weg.contains(x, y, w, h);    }
    public boolean contains(Rectangle2D r) {     return weg.contains(r);   }
    public PathIterator getPathIterator(AffineTransform at) {        return weg.getPathIterator(at);    }
    public PathIterator getPathIterator(AffineTransform at, double flatness) {        return weg.getPathIterator(at, flatness);    }
}