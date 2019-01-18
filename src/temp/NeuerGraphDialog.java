/* Erstellt am 31.10.2004 */
package temp;


import grapheneditor.KnotenElement;
import grapheneditor.GraphenModell;

import java.awt.Dimension;
import java.io.File;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 * <P><B>NeuerGraphDialog.java</B> <I>(engl. )</I>: Ein NeuerGraphDialog.java ist ... </P>
 *
 * @author Axel
 */
public class NeuerGraphDialog extends WizardDialog {

    private String verzeichnis;
    private GraphenVorlage vorlage;
    
    public NeuerGraphDialog(String verzeichnis) {
        super();
        setSize(400, 300);
        
        this.verzeichnis = verzeichnis;
        this.vorlage = null;
        
        ///////////////////////////////////////////////////
        // Vorlage auswählen
        ///////////////////////////////////////////////////
        Seite vorlagen = new Seite("Vorlage auswählen", new VorlagenDialog(verzeichnis));
        seiten.add(vorlagen);

        ///////////////////////////////////////////////////
        // Vorlage erstellen (Name, Beschreibung, Links)
        ///////////////////////////////////////////////////
        Seite neueVorlage = new Seite("Vorlage Erstellen", new EigenschaftenVorlageDialog());
        seiten.add(neueVorlage);
        
        ///////////////////////////////////////////////////
        // Mengen Definieren
        ///////////////////////////////////////////////////
        Seite mengen   = new Seite("Mengen definieren", new MengenDialog());
        //	  mengen einrichten	
        seiten.add(mengen);
        
        ///////////////////////////////////////////////////
        // Relationen definieren
        ///////////////////////////////////////////////////
        Seite relationen = new Seite("Relationen definieren", new RelationenDialog());
        //	  relationen einrichten	
        seiten.add(relationen);
        
        ///////////////////////////////////////////////////
        // Eigenschaften definieren
        ///////////////////////////////////////////////////
        Seite eigenschaften = new Seite("Eigenschaften definieren", new EigenschaftenDialog());
        //	  eigenschaften einrichten	
        seiten.add(eigenschaften);
        
        seite(0);
    }
    
    public GraphenVorlage getVorlage() {
        return vorlage;
    }
}

class VorlagenDialog extends JPanel {

	private static final long serialVersionUID = 1L;

	// - Vorlagen aus vordefiniertem oder wählbarem Verzeichnis auflisten
	// - Vorlagen hinzufügen, bearbeiten, entfernen, instanzieren 
	public VorlagenDialog (String verzeichnis) {
		super();
		// - BeispielPanel -> s. Wizard, abgespecktes GraphenDiagramm (graph)
		// - Einleitender Text
		// - Liste mit Vorlagennamen
		// - Schaltflächen -> s.Wizard
		//	 * Fetig: Vorlage instanzieren
		//	 * Weiter: Vorlage bearbeiten
		
        //	Liste der Vorlagen aus Verzeichnis ermitteln	
        File vorlagenOrdner = new File(verzeichnis);
        String[] dateien = vorlagenOrdner.list();
        Vector<String> vorlagen = new Vector<String>();
        vorlagen.add("<NEU>");
        for (int i=0; i<dateien.length; i++) {
            if (dateien[i].endsWith(".gvl"))
                vorlagen.add(dateien[i].replace(".gvl",""));
        }
        String[] data = new String[vorlagen.size()];
        data = vorlagen.toArray(data);
        
        // Listenfeld mit verfügbaren Vorlagen + Option "Neu"
        JList list = new JList(data);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setPreferredSize(new Dimension(200,10));
//        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        add(listScrollPane);

	}

	// Hilfsfunktionen
	public static GraphenVorlage vorlageLaden(String dateiname) {
		GraphenVorlage gvl = GraphenVorlageEinAusgabe.loadXML(dateiname);
		
		return gvl;
	}
	
	// -> GraphenVorlage.neueInstanz();
	public static GraphenModell vorlageInstanzieren(GraphenVorlage gvl) {
		GraphenModell gm = new GraphenModell( new KnotenElement("WURZEL"));
		// GraphenModell = GraphenVorlage ???
		// Definition Modell:
		// Definition Vorlage:
		
		return gm;
	}
}



class EigenschaftenVorlageDialog extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public EigenschaftenVorlageDialog () {
		super();
        add(new JLabel("Name"));
        add(new JTextField("xy"));
        add(new JLabel("Beschreibung"));
        add(new JTextField("ein .. ist ein ..."));
	}
}

class MengenDialog extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public MengenDialog () {
		
	}
}

class RelationenDialog extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public RelationenDialog () {
		
	}
}

class EigenschaftenDialog extends JPanel {

	private static final long serialVersionUID = 1L;
	
}

class MethodenDialog extends JPanel {

	private static final long serialVersionUID = 1L;
	
}