/* Erstellt am 03.10.2004 */
package test;
import grapheneditor.darstellung.figuren.KnotenForm;

import java.awt.Dimension;
import java.awt.geom.Point2D;

/**
 * <P><B>Dreieck.java</B> <I>(engl. )</I>: Ein Dreieck.java ist ... </P>
 *
 * @author Axel
 */
public class Dreieck extends KnotenForm {

        /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3257007670018913849L;
        private Dimension maße;
        
        public Dreieck(Point2D position, Dimension maße) {
            super();
            x = position.getX();
            y = position.getY();
            this.maße = maße;
            erstelleWeg();
        }
        
        public Dreieck(double x, double y, int b, int h) {
            super();
            this.x = x;
            this.y = y;
            this.maße = new Dimension(b, h);
            erstelleWeg();
        }
        
        protected void erstelleWeg() {
            // TODO Auto-generated method stub
            float x = (float)this.x;
            float y = (float)this.y;
            weg.reset();
            weg.moveTo(x, y-maße.height/2);
            weg.lineTo(x+maße.width/2, y+maße.height/2);
            weg.lineTo(x-maße.width/2, y+maße.height/2);
            weg.closePath();
        }

        public Point2D schnittPunkt(Point2D richtung) {
            double PI = Math.PI;
            double x0 = this.x; 
            double y0 = this.y; 
            double b = maße.getWidth();
            double h = maße.getHeight();
            double alpha = Math.atan2((x0-richtung.getX()),(y0-richtung.getY()));
            double beta = Math.atan2(b/2, h/2);
            double x = 0, y = 0; // = x0+h/2*tan(alpha);
            
            if (alpha>=-beta && alpha<=beta) {
                x = x0 - b/2*Math.tan(alpha);
                y = y0 - h/2; 
            } else if (alpha>=-PI && alpha<-beta) {
                x = x0 + b/2;
                y = y0 + h/2;
            } else if (alpha<=PI && alpha>beta) {
                x = x0 - b/2;
                y = y0 + h/2;
            }
            
/*          if (alpha>=-PI/4 && alpha<=PI/4) {
                x = x0-b/2*Math.tan(alpha);
                y = y0-h/2;
            } else if (alpha<-PI*3/4 || alpha>PI*3/4) { 
                x = x0+b/2*Math.tan(alpha);
                y = y0+h/2;
            } else if (alpha>=-PI*3/4 && alpha<-PI/4) { 
                x = x0+b/2;
                y = y0-h/2*Math.tan(alpha+PI/2);
            } else if (alpha>PI/4 && alpha<=PI*3/4) { 
                x = x0-b/2;
                y = y0+h/2*Math.tan(alpha-PI/2);
            }	*/
            return new Point2D.Double(x, y);
        }
    }
