/* Erstellt am 07.11.2004 */
package grapheneditor.darstellung;

//import grapheneditor.darstellung.Knoten;

import grapheneditor.KnotenAnsicht;

import java.awt.BorderLayout;
//import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * Das Fenster wird durch die XSD-Vorlage generiert! 
 *
 * @author Axel
 */
public class KnotenEigenschaften extends JDialog implements MouseListener,
                                                            KeyListener {

    /** Comment for <code>serialVersionUID</code>    */
    private static final long serialVersionUID = 1L;

    // Definition der Knoten aus XSD einlesen
    // Oberfläche generieren
    
    private KnotenAnsicht k;
    int rückgabe = -1;

	public static final int FERTIG = 0;
	public static final int ABBRUCH = 1;
    
	static JPanel inhalt;
	static JPanel schalter;

    static JButton fertig;
    static JButton abbruch;
    JTextField bezeichnung;

	static {
	    inhalt = new JPanel();
	    schalter = new JPanel();
	    
	    fertig  = new JButton("Fertig");	schalter.add(fertig);
	    abbruch = new JButton("Abbruch");	schalter.add(abbruch);
	}
	
    
    public KnotenEigenschaften(KnotenAnsicht k) {
        super();
        setSize(300, 200);
        setModal(true);
        this.k = new KnotenAnsicht(k.getObjekt(), /*k.getID(), */k.form);
        
        addMouseListener(this);
        fertig.addMouseListener(this);
        abbruch.addMouseListener(this);

	    bezeichnung = new JTextField(20);		
//	    bezeichnung.setPreferredSize(new Dimension(100, 20));
        bezeichnung.addKeyListener(this);
        bezeichnung.setText(k.titel);
        bezeichnung.selectAll();
        
        inhalt.add(bezeichnung);
        
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(inhalt,BorderLayout.CENTER);
		this.getContentPane().add(schalter,BorderLayout.SOUTH);
		pack();
		
		// fertig.addActionAdapter ? ...
    }
    
    public int showDialog() {
        setVisible(true);
        return rückgabe;
    }
    
    public KnotenAnsicht getKnoten() {
        return k;
    }	

    public void mouseClicked(MouseEvent e) {    
        if (e.getSource() == fertig) {
		    k.titel = new String(bezeichnung.getText());
		    rückgabe = FERTIG;
		    setVisible(false);
		    inhalt.remove(bezeichnung);
        }
        
        if (e.getSource().equals(abbruch)) {
		    rückgabe = ABBRUCH;
		    setVisible(false);
		    inhalt.remove(bezeichnung);
        }
    }

    public void mousePressed(MouseEvent e) {    }

    public void mouseReleased(MouseEvent e) {    }

    public void mouseEntered(MouseEvent e) {    }

    public void mouseExited(MouseEvent e) {    }

    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            k.titel = new String(bezeichnung.getText());
            rückgabe = FERTIG;
            setVisible(false);
            inhalt.remove(bezeichnung);
        }
         
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            rückgabe = ABBRUCH;
            setVisible(false);
            inhalt.remove(bezeichnung);
        }
    }

    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }
}
