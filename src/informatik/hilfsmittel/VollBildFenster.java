package informatik.hilfsmittel;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JWindow;


public class VollBildFenster extends JWindow {

	public VollBildFenster() {
		super();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
		setSize(screenSize.width,screenSize.height);
	}
}
