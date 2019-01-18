
package informatik.strukturen.graphen.hilfsmittel;

import informatik.strukturen.graphen.SchlichterGraph;

import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;

public class GraphAnsicht<K> extends JPanel {

//    private SchlichterGraph<K> modell;

	public GraphAnsicht() {
		super();
//		modell = new SchlichterGraph<K>();
	}

	public void setGraph(SchlichterGraph<K> g) {
//		modell = g;
	}

	public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.draw( new Rectangle2D.Float( 50, 50, 50, 50 ) );

		
	}
}
