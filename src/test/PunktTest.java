/* Erstellt am 02.10.2004 */
package test;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * <P><B>PunktTest.java</B> <I>(engl. )</I>: Ein PunktTest.java ist ... </P>
 *
 * @author Axel
 */
public class PunktTest {

    public static void main(String args[]) {
        
        Point2D p1 = new Point2D.Double(5, 5);
        Point2D p2 = new Point2D.Double(10, 10);
        Line2D.Double l = new Line2D.Double(0, 0, 20, 20);
        System.out.println("Von: " + l.x1 + "," + l.y1 + " bis: " + l.x2 + "," + l.y2);
        l.setLine(p1, p2);
        System.out.println("Von: " + l.x1 + "," + l.y1 + " bis: " + l.x2 + "," + l.y2);
        p1.setLocation(15, 15);
        p2.setLocation(p1);
        System.out.println("Von: " + l.x1 + "," + l.y1 + " bis: " + l.x2 + "," + l.y2);
        Linie l2 = new Linie(p1, p2);
        System.out.println(l2);
        p2.setLocation(50, 40);
        System.out.println(l2);
    }
}

class Linie {
    Point2D p1;
    Point2D p2;
    
    Linie(Point2D p1, Point2D p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
    
    public String toString() {
        return "((" + p1.getX() + "p1.getY()" + "),(" + p2.getX() + "," + p2.getY() + "))"; 
    }
}