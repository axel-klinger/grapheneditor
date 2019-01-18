/* Erstellt am 02.10.2004 */
package test;

import grapheneditor.darstellung.figuren.Ellipse;
import grapheneditor.darstellung.figuren.Pfeil;
import grapheneditor.darstellung.figuren.Rechteck;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * <P><B>PfeilTest.java</B> <I>(engl. )</I>: Ein PfeilTest.java ist ... </P>
 *
 * @author Axel
 */
public class PfeilTest extends JFrame {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    
	Ansicht ansicht;

    public PfeilTest() {
		super("Ein PfeilTest");

		setSize(640,480);
		ansicht  = new Ansicht();
		
		setLayout(new BorderLayout());
		add(ansicht); 	
    }
    
    public static void main(String[] args) {
		PfeilTest editor = new PfeilTest();
		editor.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e){ System.exit(0); }		}); 
		editor.pack();
		editor.setSize(640,480);
		editor.setVisible(true);
    }
    
}

class Ansicht extends JPanel {

    private static final long serialVersionUID = 1L;

	Point2D p1;
	Point2D p2;
	Pfeil[] p = {
	        
	        new Pfeil(new Point2D.Double(200,200), new Point2D.Double(200, 100)),
	        new Pfeil(new Point2D.Double(200,200), new Point2D.Double(300, 100)),
	        new Pfeil(new Point2D.Double(200,200), new Point2D.Double(300, 200)),
	        new Pfeil(new Point2D.Double(200,200), new Point2D.Double(300, 300)),
	        new Pfeil(new Point2D.Double(200,200), new Point2D.Double(200, 300)),
	        new Pfeil(new Point2D.Double(200,200), new Point2D.Double(100, 100)),
	};
	Rechteck r = new Rechteck(100, 100, 60, 20);
	Ellipse e = new Ellipse(200, 200, 40, 40);
	
    public Ansicht() {
        setBackground(Color.white);
		p1 = new Point2D.Double(50, 50);
		p2 = new Point2D.Double(200, 50);
//		p = new Pfeil(p1, p2);
    }
    
	public void paintComponent(Graphics g){
        super.paintComponent(g);
        
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2.0f));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (int i = 0; i<p.length; i++) {
		    g2.draw(p[i]);
		    g2.fill(p[i]);
		}
		g2.draw(r);
		g2.draw(e);
	}
} 

/*class Pfeil implements Shape {

    GeneralPath weg;
    Point2D p1, p2;
    int l = 30;
    int b = 20;
    double l2 = Math.sqrt(l*l+b*b);
    
    Pfeil(Point2D p1, Point2D p2) {
        this.p1 = p1;
        this.p2 = p2;
        weg = new GeneralPath();
        erstelleWeg();
    }
    
    public void setPfeil(Point2D p1, Point2D p2) {
        this.p1 = p1;
        this.p2 = p2;
        erstelleWeg();
    }

	private void erstelleWeg() {   
//	    double winkel = Math.atan((p2.getX()-p1.getX())/(p2.getY()-p1.getY()));
	    double w2 = Math.atan2(b/2, l);
	    double winkel = Math.atan2((p2.getX()-p1.getX()),(p2.getY()-p1.getY()));
System.out.println(Math.toDegrees(w2));
System.out.println("Winkel: " + Math.toDegrees(winkel) 
        		 + ", Sinus: " + Math.sin(winkel)
        		 + ", l*sin: " + l*Math.sin(winkel));
	    weg.reset();
//	    weg.moveTo(50, 50);
//	    weg.lineTo(100, 100);
//	    weg.lineTo(50, 100);
        weg.moveTo((float)p1.getX(), (float)p1.getY());
        weg.lineTo((float)p2.getX(), (float)p2.getY());
        weg.moveTo((float)(p2.getX()-l2*Math.sin(winkel+w2)), (float)(p2.getY() - l2*Math.cos(winkel+w2)));
        weg.lineTo((float)p2.getX(), (float)p2.getY());
        weg.lineTo((float)(p2.getX()-l2*Math.sin(winkel-w2)), (float)(p2.getY() - l2*Math.cos(winkel-w2)));
        weg.closePath();	
	} 
    
    public Rectangle getBounds() {        return weg.getBounds();    }
    public Rectangle2D getBounds2D() {    return weg.getBounds2D();  }
    public boolean contains(double x, double y) {   return weg.contains(x, y); }
    public boolean contains(Point2D p) {  return weg.contains(p);    }
    public boolean intersects(double x, double y, double w, double h) {        return weg.intersects(x, y, w, h);    }
    public boolean intersects(Rectangle2D r) {   return weg.intersects(r); }
    public boolean contains(double x, double y, double w, double h) {        return weg.contains(x, y, w, h);    }
    public boolean contains(Rectangle2D r) {     return weg.contains(r);   }
    public PathIterator getPathIterator(AffineTransform at) {        return weg.getPathIterator(at);    }
    public PathIterator getPathIterator(AffineTransform at, double flatness) {        return weg.getPathIterator(at, flatness);    }
}*/