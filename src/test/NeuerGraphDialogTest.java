/* Erstellt am 31.10.2004 */
package test;


import javax.swing.JFrame;
import javax.swing.UIManager;

import temp.NeuerGraphDialog;
import temp.WizardDialog;

public class NeuerGraphDialogTest {

    public static void main(String[] args) {

	    try {
	        UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
	    } catch (Exception e) {	e.printStackTrace();	}
        
	    JFrame 	fenster = new JFrame("NGD-Test");      
	    		fenster.setSize(200,200);
	    		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    		fenster.setVisible(true);

//--------	    		
        NeuerGraphDialog ngd = new NeuerGraphDialog("D:/klinger/eclipse42/GraphenEditor/Beispiele/Volagen");
        if (ngd.showDialog() == WizardDialog.FERTIG)
            System.out.println("Vorlage: " + ngd.getVorlage());
//--------        
        System.out.println("ausgewählt");
    }
}
