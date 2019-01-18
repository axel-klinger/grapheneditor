/* Erstellt am 31.10.2004 */
package temp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

//import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
//import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * <P><B>WizardDialog.java</B> <I>(engl. )</I>: Ein WizardDialog.java ist ... </P>
 *
 * @author Axel
 */
public class WizardDialog extends JDialog {
	/** Comment for <code>serialVersionUID</code>     */
    private static final long serialVersionUID = -5209021071771353163L;
    
    Vector<Seite> seiten = new Vector<Seite>();
	int z�hler = 0;
    int r�ckgabe = -1;

    static JPanel steuerung = new JPanel();
	static JPanel schalter = new JPanel();
	static JPanel inhalt = new JPanel();
	static JPanel bild = new JPanel();
	JPanel aktuell;
	JButton weiter;
	JButton zur�ck;
	JButton fertig;
	JButton abbruch;
	JButton hilfe;
	
	public static final int FERTIG = 0;
	public static final int ABBRUCH = 1;

	public WizardDialog() {
		setModal(true);
		hilfe  = new JButton("?");		schalter.add(hilfe);
		abbruch= new JButton("X");		schalter.add(abbruch);
		zur�ck = new JButton("<-");		schalter.add(zur�ck);
		weiter = new JButton("->");		schalter.add(weiter);
		fertig = new JButton("Fertig");	schalter.add(fertig);

		inhalt.add(new JLabel("Welt"));
		bild.setSize(200, 300);
		bild.add(new JLabel("Hallo"));
		steuerung.setSize(300, 300);
		this.getContentPane().setLayout(new BorderLayout());
//		this.getContentPane().add(bild);
		this.getContentPane().add(schalter,BorderLayout.SOUTH);
		this.getContentPane().add(inhalt,BorderLayout.CENTER);
//		steuerung.setLayout(new BorderLayout());
//		steuerung.add(inhalt,BorderLayout.CENTER);
//		steuerung.add(schalter,BorderLayout.SOUTH);
		schalterAufSeite(z�hler);
		weiter.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me) {
				if(me.getSource() == weiter && weiter.isEnabled()) {
					seite(++z�hler);
					setVisible(true);
				}
			}
		});
		zur�ck.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me) {
				if(me.getSource() == zur�ck && zur�ck.isEnabled()) {
					seite(--z�hler);
					setVisible(true);
				}
			}
		});
		fertig.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me) {
				if(me.getSource() == fertig && fertig.isEnabled()) {
				    r�ckgabe = FERTIG;
				    setVisible(false);
				}
			}
		});
		abbruch.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me) {
				if(me.getSource() == abbruch && abbruch.isEnabled()) {
				    r�ckgabe = ABBRUCH;
				    setVisible(false);
				}
			}
		});
	}

    public int showDialog() {
        seite(0);
        setVisible(true);
        return r�ckgabe;
    }
    
	/**
	*	Validating which button to show and which one to disable.
	*/
	public void schalterAufSeite(int z�hler)	{
	    zur�ck.setEnabled( z�hler > 0 );
	    weiter.setEnabled( z�hler < (seiten.size()-1));
	    fertig.setEnabled( z�hler > 1 );
	}

	/**
	*	This is the method that decides which page to show.
	*/
	public void seite(int seite) {
		this.getContentPane().remove(1);
//	    steuerung.remove(1);
		aktuell = (Seite) seiten.get(seite);
		this.getContentPane().add(aktuell, BorderLayout.CENTER);
//		steuerung.add(aktuell, BorderLayout.CENTER);
		this.setTitle("Schritt " + (seite+1) + " von " + seiten.size() + " - " + ((Seite)aktuell).titel);
		this.getContentPane().update(aktuell.getGraphics());
		schalterAufSeite(seite);
	}
}

class Seite extends JPanel {
        
    private static final long serialVersionUID = 1L;
    
    String titel;
    
    Seite(String titel, JPanel inhalt) {
        this.titel = titel;
        setLayout(new FlowLayout());
        setSize(400, 300);
        add(inhalt);
    }
}