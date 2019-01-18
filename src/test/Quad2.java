package test;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class Quad2 extends JApplet{

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -6902604810932109824L;
    static protected JLabel label;
    QuadPanel quadPanel;

    public void init(){
	//Initialize the layout.
        getContentPane().setLayout(new BorderLayout());
        quadPanel = new QuadPanel();
        quadPanel.setBackground(Color.white);
        getContentPane().add(quadPanel);
        label = new JLabel("Drag the points to adjust the curve.");
        getContentPane().add("South", label);
    }

    public static void main(String s[]) {
        JFrame f = new JFrame("Quad");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JApplet applet = new Quad2();
        f.getContentPane().add(applet, BorderLayout.CENTER);
        applet.init();
        f.pack();
        f.setSize(new Dimension(350,250));
        f.setVisible(true);
    }
}

class QuadPanel extends JPanel implements MouseListener, MouseMotionListener{

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -3224301966928164551L;
    BufferedImage bi;
	Graphics2D big;
	int x, y;
	Rectangle fläche, startFigur, endFigur, ctrlFigur, figur;	
	QuadCurve2D.Double linie = new QuadCurve2D.Double();
	Point2D.Double startPunkt, endPunkt, ctrlPunkt, punkt;
	boolean firstTime = true;
	boolean pressOut = false;

	public QuadPanel(){

                setBackground(Color.white);
                addMouseMotionListener(this);
                addMouseListener(this);

		startPunkt = new Point2D.Double();
		ctrlPunkt = new Point2D.Double();
		endPunkt = new Point2D.Double();

                linie.setCurve(startPunkt, ctrlPunkt, endPunkt);
		startFigur = new Rectangle(0, 0, 8, 8);
		endFigur = new Rectangle(0, 0, 8, 8);
		ctrlFigur = new Rectangle(0, 0, 8, 8);
	}

	public void mousePressed(MouseEvent e){

		x = e.getX();
		y = e.getY();

		if(startFigur.contains(x, y)){
			figur = startFigur;
			punkt = startPunkt;
			x = startFigur.x - e.getX();
			y = startFigur.y - e.getY();
			updateLocation(e);
		}
 		else if(endFigur.contains(x, y)){
			figur = endFigur;
			punkt = endPunkt;
			x = endFigur.x - e.getX();
			y = endFigur.y - e.getY();
			updateLocation(e);
		}
		else if(ctrlFigur.contains(x, y)){
			figur = ctrlFigur;
			punkt = ctrlPunkt;
			x = ctrlFigur.x - e.getX();
			y = ctrlFigur.y - e.getY();
			updateLocation(e);
		}
		else {
			pressOut = true;
		}
	}

	public void mouseDragged(MouseEvent e){
                if(!pressOut) {
                        updateLocation(e);
                } 
	}

	public void mouseReleased(MouseEvent e){

               if(startFigur.contains(e.getX(), e.getY())){
                        figur = startFigur;
                        punkt = startPunkt;
                        updateLocation(e);
                }
                else if(endFigur.contains(e.getX(), e.getY())){
                        figur = endFigur;
                        punkt = endPunkt;
                        updateLocation(e);
                }
                else if(ctrlFigur.contains(e.getX(), e.getY())){
                        figur = ctrlFigur;   
                        punkt = ctrlPunkt;
                        updateLocation(e); 
                }
                else {
                        pressOut = false;
                }
	}

	public void mouseMoved(MouseEvent e){}

	public void mouseClicked(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}

	public void updateLocation(MouseEvent e){

		figur.setLocation((x + e.getX())-4, (y + e.getY())-4);
		punkt.setLocation(x + e.getX(), y + e.getY());

                checkPoint();

		linie.setCurve(startPunkt, ctrlPunkt, endPunkt);
		repaint();
	}

	public void paintComponent(Graphics g){
                super.paintComponent(g);
		update(g);
	}

	public void update(Graphics g){

		Graphics2D g2 = (Graphics2D)g;
		Dimension dim = getSize();
		int w = dim.width;
                int h = dim.height; 
                 
          	if(firstTime){
		  bi = (BufferedImage)createImage(w, h);
		  big = bi.createGraphics();

		  startPunkt.setLocation(w/2-50, h/2);
		  endPunkt.setLocation(w/2+50, h/2);
		  ctrlPunkt.setLocation((int)(startPunkt.x)+50, (int)(startPunkt.y)-50);

		  startFigur.setLocation((int)((startPunkt.x)-4), (int)((startPunkt.y)-4));
          endFigur.setLocation((int)((endPunkt.x)-4), (int)((endPunkt.y)-4));
          ctrlFigur.setLocation((int)((ctrlPunkt.x)-4), (int)((ctrlPunkt.y)-4));
		  linie.setCurve(startPunkt, ctrlPunkt, endPunkt);

		  big.setColor(Color.black);
		  big.setStroke(new BasicStroke(5.0f));
          big.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

          fläche = new Rectangle(dim);
		  firstTime = false;
		}

		// Clears the rectangle that was previously drawn.
		big.setColor(Color.white);
		big.clearRect(0, 0, fläche.width, fläche.height);

		// Draws and fills the newly positioned rectangle to the buffer.
		big.setPaint(Color.black);
		big.draw(linie);
		big.setPaint(Color.red);
		big.fill(startFigur);
		big.setPaint(Color.magenta);
		big.fill(endFigur);
		big.setPaint(Color.blue);
		big.fill(ctrlFigur);

		// Draws the buffered image to the screen.
		g2.drawImage(bi, 0, 0, this);

	}
	/*
         * Checks if the rectangle is contained within the applet window.  If the rectangle
         * is not contained withing the applet window, it is redrawn so that it is adjacent
         * to the edge of the window and just inside the window.
	 */

	void checkPoint(){
		
		if (fläche == null) {
			return;
		}

		if((fläche.contains(figur)) && (fläche.contains(punkt))){
			return;
		}
		int new_x = figur.x;
		int new_y = figur.y;

		double new_px = punkt.x;
		double new_py = punkt.y;

		if((figur.x+figur.width)>fläche.getWidth()){
			new_x = (int)fläche.getWidth()-(figur.width-1);
		}
		if(punkt.x > fläche.getWidth()){
			new_px = (int)fläche.getWidth()-1;
		}
		if(figur.x < 0){  
			new_x = -1;
		}
		if(punkt.x < 0){
			new_px = -1;
		}
		if((figur.y+figur.width)>fläche.getHeight()){
			new_y = (int)fläche.getHeight()-(figur.height-1);
		}
		if(punkt.y > fläche.getHeight()){
			new_py = (int)fläche.getHeight()-1;
		}
		if(figur.y < 0){  
			new_y = -1;
		}
		if(punkt.y < 0){
                        new_py = -1;
                }
		figur.setLocation(new_x, new_y);
		punkt.setLocation(new_px, new_py);

	}
}
