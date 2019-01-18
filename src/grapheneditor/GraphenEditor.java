/* Created on 25.09.2004 */

package grapheneditor;

import grapheneditor.darstellung.GraphenEigenschaftenAnsicht;
import grapheneditor.darstellung.GraphenDarstellung;
import grapheneditor.darstellung.GraphenDiagramm;
import grapheneditor.darstellung.MatrizenDiagramm;
import grapheneditor.darstellung.MengenSchreibweise;
import grapheneditor.steuerung.DateiSteuerung;
import grapheneditor.steuerung.GraphenMethoden;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author <a href="">Axel Klinger<a>
 */
public class GraphenEditor extends JFrame implements /*KeyListener,*/
                                                     ActionListener {
    
    // F�r die Verarbeitung der Men�befehle entsprechende Controller
    // - BearbeitungsSteuerung
    DateiSteuerung   dateiSteuerung;
    GraphenMethoden steuerung;
    
//    GraphenVorlage   vorlage;
    GraphenModell    modell;
	GraphenEigenschaftenAnsicht   eigenschaftenAnsicht;
	
    GraphenDarstellung aktuelleAnsicht;
	GraphenDarstellung[] ansicht;
	
	int ansichtIndex;
	final static int UP = 1;
	final static int DOWN = -1;
	
	int index = 0;
	int digitalBreite = 160;
	JPanel ansichtsFl�che;
	
//	========== BEGINN Konfigurationsdatei ===========
//
	
	private String vorlagenVerzeichnis = "Beispiele/Vorlagen";
	private String arbeitsVerzeichnis = "Beispiele";
	private int knotenGr��e;
	private int schriftGr��e;
	private boolean digital 	 = true;
	private boolean raster  	 = false;
	private boolean fang         = false;
	private boolean beschriftung = true;
//
//========== ENDE Konfigurationsdatei ===========
	
	public String �berschrift;
	
	// Konstruktor
    public GraphenEditor(String titel) {
		super(titel);
//		addKeyListener(this);
//      addActionListener(this);
		�berschrift = titel;
		setSize(640,480);
        setTitle(titel + Messages.getString("Text.0")); //$NON-NLS-1$

//		vorlage    = new GraphenVorlage("Schlichter Graph");
		modell 	   = new GraphenModell( new KnotenElement("WURZEL"));
		steuerung  = new GraphenMethoden(modell);
        dateiSteuerung = new DateiSteuerung(modell);
		
		ansicht    = new GraphenDarstellung[3];
		ansicht[0] = new GraphenDiagramm(modell);
		ansicht[1] = new MatrizenDiagramm(modell);
		ansicht[2] = new MengenSchreibweise(modell);
		
		ansichtIndex = 0;
		aktuelleAnsicht = ansicht[ansichtIndex];
		
//		addKeyListener((GraphenDiagramm) ansicht[0]);
		
		eigenschaftenAnsicht = new GraphenEigenschaftenAnsicht(modell);
		
		setLayout(new BorderLayout());
        setJMenuBar(erzeugeMen�Leiste());
        
		ansichtsFl�che = new JPanel(); //	JJPanel
		ansichtsFl�che.setLayout(new BorderLayout());
		ansichtsFl�che.add(aktuelleAnsicht);

		add(ansichtsFl�che, Messages.getString("Text.1"));	 //$NON-NLS-1$
		add(eigenschaftenAnsicht,Messages.getString("Text.2")); //$NON-NLS-1$
		
		loadProperties();
		((GraphenDiagramm)ansicht[0]).setFang(fang);
		((GraphenDiagramm)ansicht[0]).setRaster(raster);
		((GraphenDiagramm)ansicht[0]).setSchrift(beschriftung);
		((GraphenDiagramm)ansicht[0]).setSchriftArt("sans-serif", java.awt.Font.PLAIN, schriftGr��e);

    }
    
    private void loadProperties() {
    	String filename = "properties.txt";
    	try {
    		FileInputStream propInFile = new FileInputStream( filename );
    		Properties einstellungen = new Properties();
    		einstellungen.load( propInFile );

    		vorlagenVerzeichnis = einstellungen.getProperty("VorlagenVerzeichnis");
    		arbeitsVerzeichnis = einstellungen.getProperty("ArbeitsVerzeichnis");
    		knotenGr��e = Integer.parseInt(einstellungen.getProperty("KnotenGr��e"));
    		schriftGr��e = Integer.parseInt(einstellungen.getProperty("SchriftGr��e"));
    		raster = Boolean.parseBoolean(einstellungen.getProperty("Raster"));
    		fang = Boolean.parseBoolean(einstellungen.getProperty("Fang"));
    		beschriftung = Boolean.parseBoolean(einstellungen.getProperty("Beschriftung"));
    		
	     } catch ( FileNotFoundException e ) {
	       System.err.println( "Datei " + filename + " nicht gefunden.");
	     } catch ( IOException e ) {
	       System.err.println( "Ein-/Ausgabe fehlgeschlagen." );
	     }	
    }
    
    private void saveProperties() {
    	String filename = "properties.txt";
    	try {
    		FileOutputStream propOutFile = new FileOutputStream( filename );
    		Properties  einstellungen = new Properties(/* System.getProperties() */);
    		
    		einstellungen.setProperty( "VorlagenVerzeichnis", vorlagenVerzeichnis );
    		einstellungen.setProperty( "ArbeitsVerzeichnis",  arbeitsVerzeichnis );
    		einstellungen.setProperty( "KnotenGr��e", 		  "" + knotenGr��e );
    		einstellungen.setProperty( "SchriftGr��e",  	  "" + schriftGr��e );
    		einstellungen.setProperty( "Raster", 			  "" + raster );
    		einstellungen.setProperty( "Fang",   			  "" + fang );
    		einstellungen.setProperty( "Beschriftung",    	  "" + beschriftung );
    		
    		
    		einstellungen.store( propOutFile, "Einstellungen f�r den GraphenEditor" );
	     } catch ( FileNotFoundException e ) {
	       System.err.println( "Datei " + filename + " nicht gefunden.");
	     } catch ( IOException e ) {
	       System.err.println( "Ein-/Ausgabe fehlgeschlagen." );
	     }
    }
    
    public void switchAnsicht(int richtung) {
        aktuelleAnsicht.setVisible(false);
        ansichtsFl�che.remove(aktuelleAnsicht);
        ansichtIndex = (ansicht.length + ansichtIndex + richtung) % ansicht.length;
        aktuelleAnsicht = ansicht[ansichtIndex];
        
        ansichtsFl�che.add(aktuelleAnsicht);
        aktuelleAnsicht.setVisible(true);
    }
    
    private void switchDigital() {
	    int breite = eigenschaftenAnsicht.isVisible() ? aktuelleAnsicht.getWidth() + digitalBreite : aktuelleAnsicht.getWidth() - digitalBreite;
	    aktuelleAnsicht.setPreferredSize(new Dimension(breite, aktuelleAnsicht.getHeight()));
        eigenschaftenAnsicht.switchAnsicht();
        pack();
    }
    
    public JMenuBar erzeugeMen�Leiste() {
        
        JMenuBar men� = new JMenuBar();
        
        //////////////////////////////////
        // &Datei                       //
        // ================             //
        // - Neu            Strg + N    //
        // - �ffnen         Strg + O    //
        // - Speichern      Strg + S    //
        // - Schlie�en      Strg + C    //
        // - ------------               //
        // - Vorschau                   //
        // - Drucken                    //
        // - ------------               //
        // - Eigenschaften  Alt + Return//
        // - ------------               //
        // - Beenden        Alt + F4    //
        //////////////////////////////////
        JMenu    datei = erzeugeMen�(Messages.getString("Text.3"), KeyEvent.VK_D); //$NON-NLS-1$
        men�.add(datei);
        
        JMenuItem neu           = erzeugeMen�Eintrag(Messages.getString("Text.4"),           KeyEvent.VK_N, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));  //$NON-NLS-1$
        JMenuItem neu_vorlage   = erzeugeMen�Eintrag(Messages.getString("Text.97"),           KeyEvent.VK_N, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK|ActionEvent.SHIFT_MASK));  //$NON-NLS-1$
        JMenuItem �ffnen        = erzeugeMen�Eintrag(Messages.getString("Text.5"),        KeyEvent.VK_F, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem speichern     = erzeugeMen�Eintrag(Messages.getString("Text.6"),     KeyEvent.VK_S, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem speichern_unter     = erzeugeMen�Eintrag(Messages.getString("Text.7"), KeyEvent.VK_U, KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
//      JMenuItem schlie�en     = erzeugeMen�Eintrag("Schlie�en", KeyEvent.VK_C, KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        JMenuItem vorschau      = erzeugeMen�Eintrag(Messages.getString("Text.8"),      KeyEvent.VK_V, null); //$NON-NLS-1$
        JMenuItem drucken       = erzeugeMen�Eintrag(Messages.getString("Text.9"),       KeyEvent.VK_D, KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem importieren   = erzeugeMen�Eintrag(Messages.getString("Text.10"),   KeyEvent.VK_I, null); //$NON-NLS-1$
        JMenuItem exportieren   = erzeugeMen�Eintrag(Messages.getString("Text.11"),   KeyEvent.VK_E,  KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem hintergrund_laden  = erzeugeMen�Eintrag(Messages.getString("Text.12"),   KeyEvent.VK_H,  KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.SHIFT_MASK|ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem dateiEigenschaften = erzeugeMen�Eintrag(Messages.getString("Text.13"), KeyEvent.VK_E, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, ActionEvent.ALT_MASK)); //$NON-NLS-1$
        JMenuItem beenden       = erzeugeMen�Eintrag(Messages.getString("Text.14"),       KeyEvent.VK_B, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK)); //$NON-NLS-1$

        datei.add( neu          );
        datei.add( neu_vorlage  );
        datei.add( �ffnen       );
        datei.add( speichern    );
        datei.add( speichern_unter    );
//      datei.add( schlie�en    );
        datei.addSeparator(     );
        datei.add( vorschau     );  vorschau.setEnabled(false);
        datei.add( drucken      );  drucken.setEnabled(false);
        datei.addSeparator(     );
        datei.add( importieren  );  importieren.setEnabled(false);
        datei.add( exportieren  );  //exportieren.setEnabled(false);
        datei.add( hintergrund_laden );
        datei.addSeparator(     );
        datei.add( dateiEigenschaften);  dateiEigenschaften.setEnabled(false);
        datei.addSeparator(     );
        datei.add( beenden      );

        //////////////////
        // Bearbeiten   //
        //////////////////
        JMenu    bearbeiten     = erzeugeMen�(Messages.getString("Text.15"), KeyEvent.VK_B); //$NON-NLS-1$
        men�.add(bearbeiten);
        
        JMenuItem zur�ck        = erzeugeMen�Eintrag(Messages.getString("Text.16"),      KeyEvent.VK_R, KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem vor           = erzeugeMen�Eintrag(Messages.getString("Text.17"),     KeyEvent.VK_W, KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem ausschneiden  = erzeugeMen�Eintrag(Messages.getString("Text.18"),    KeyEvent.VK_N, KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem kopieren      = erzeugeMen�Eintrag(Messages.getString("Text.19"),        KeyEvent.VK_K, KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem einf�gen      = erzeugeMen�Eintrag(Messages.getString("Text.20"),        KeyEvent.VK_E, KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem suchen        = erzeugeMen�Eintrag(Messages.getString("Text.21"),      KeyEvent.VK_S, KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem ersetzen      = erzeugeMen�Eintrag(Messages.getString("Text.22"),    KeyEvent.VK_T, KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem alle          = erzeugeMen�Eintrag(Messages.getString("Text.23"),  KeyEvent.VK_A, KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK)); //$NON-NLS-1$

        bearbeiten.add( zur�ck      );  zur�ck.setEnabled(false);
        bearbeiten.add( vor         );  vor.setEnabled(false);
        bearbeiten.addSeparator(    );
        bearbeiten.add( ausschneiden);  ausschneiden.setEnabled(false);
        bearbeiten.add( kopieren    );  kopieren.setEnabled(false);
        bearbeiten.add( einf�gen    );  einf�gen.setEnabled(false);
        bearbeiten.addSeparator(    );
        bearbeiten.add( suchen      );  suchen.setEnabled(false);
        bearbeiten.add( ersetzen    );  ersetzen.setEnabled(false);
        bearbeiten.addSeparator(    );
        bearbeiten.add( alle        );

        //////////////////
        // Ansicht      //
        //////////////////
        JMenu    ansicht    = erzeugeMen�(Messages.getString("Text.24"), KeyEvent.VK_A); //$NON-NLS-1$
        men�.add(ansicht);

        JMenuItem raster    	= erzeugeMen�Eintrag(Messages.getString("Text.25"),        	KeyEvent.VK_R, KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem fang      	= erzeugeMen�Eintrag(Messages.getString("Text.26"),          	KeyEvent.VK_F, KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem schrift   	= erzeugeMen�Eintrag(Messages.getString("Text.27"),	KeyEvent.VK_B, KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem ansichtVor   	= erzeugeMen�Eintrag(Messages.getString("Text.28"),  	KeyEvent.VK_B, KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem ansichtZur�ck = erzeugeMen�Eintrag(Messages.getString("Text.29"),  	KeyEvent.VK_B, KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem modusVor   	= erzeugeMen�Eintrag(Messages.getString("Text.30"),  		KeyEvent.VK_B, KeyStroke.getKeyStroke(KeyEvent.VK_UP, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem modusZur�ck   = erzeugeMen�Eintrag(Messages.getString("Text.31"),  		KeyEvent.VK_B, KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem hintergrundbild_anzeigen   = erzeugeMen�Eintrag(Messages.getString("Text.32"),  KeyEvent.VK_B, KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem ansichtEigenschaften   = erzeugeMen�Eintrag(Messages.getString("Text.33"),  KeyEvent.VK_B, KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK)); //$NON-NLS-1$
        JMenuItem zoom      	= erzeugeMen�Eintrag(Messages.getString("Text.34"),      	KeyEvent.VK_Z, null); //$NON-NLS-1$

        ansicht.add(raster);
        ansicht.add(fang);
        ansicht.add(schrift);
        ansicht.addSeparator();
        ansicht.add(ansichtVor);
        ansicht.add(ansichtZur�ck);
        ansicht.add(modusVor);
        ansicht.add(modusZur�ck);
        ansicht.add(hintergrundbild_anzeigen);
        ansicht.add(ansichtEigenschaften);
        ansicht.addSeparator();
        ansicht.add(zoom);      zoom.setEnabled(false);
        
        //////////////////
        // Relation		//
        //////////////////
        //	Eigenschaften (werden Knoten und/oder Kanten betrachtet???)
        //	- Symmetrie	(asymmetrische Kanten ROT)
        //	- Antisymmetrie (symmetrische Kanten ROT)
        //  - Reflexivit�t (antireflexive Knoten ROT)
        //	- Antireflexivit�t (reflexive Knoten ROT)
        //	- ...
        JMenu relation = erzeugeMen�(Messages.getString("Text.35"), KeyEvent.VK_R);       //$NON-NLS-1$
        men�.add(relation);

        JMenu h�llen = new JMenu(Messages.getString("Text.36")); //$NON-NLS-1$
        relation.add(h�llen);
        
        JMenuItem transitive = erzeugeMen�Eintrag(Messages.getString("Text.37"), KeyEvent.VK_P, KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.ALT_MASK)); //$NON-NLS-1$
        JMenuItem reflexive = erzeugeMen�Eintrag(Messages.getString("Text.38"), KeyEvent.VK_P, KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.ALT_MASK)); //$NON-NLS-1$
        JMenuItem reflexivTransitive = erzeugeMen�Eintrag(Messages.getString("Text.39"), KeyEvent.VK_P, KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.ALT_MASK)); //$NON-NLS-1$

        h�llen.add(transitive);     //rrr.setEnabled(false);
        h�llen.add(reflexive);     //rrr.setEnabled(false);
        h�llen.add(reflexivTransitive);     //rrr.setEnabled(false);
        
        //	JE OBJEKTMENUE EIN STRUKTUR-PLUG-IN !!!
        //
        //	AKTION EBENSO WIE PARAMETER HINSCHREIBEN !!!
        //
        //////////////////
        // Graph		//
        //////////////////
        //
        //	Zur H�04: aus BG(V,S) ->BewSG(V) mit Kantengewicht S.l�nge
        //	-> Vorteil, wenn Gewichte in HashMap gespeichert
        //
        //	Zusammenhang
        //	FERTIG- Komponenten (SCHWACH|PSEUDO|STRENG) (Random-Color)
        //	FERTIG- Disjunkte Wege (Random-Color)
        //	FERTIG- Topologisch Sortieren (Sorted-Color)
        //	- Einfacher Weg (einfarbige Knoten F�llung)
        //	- Tiefensuchbaum
        //	- Breitensuchbaum
        //	- Minimaler Spannbaum (???)
        //	- Eulerscher Weg (Kantendisjunkt)
        //	- Hamiltonscher Weg (Knotendisjunkt)
        
        //	Bewertung
        //	- k�rzester Weg
        //	- l�ngster Weg (nur azyklisch)
        
        //	Netzpl�ne
        //	- Kritischer Weg
        //	- Fr�heste und Sp�teste Zeiten berechnen
        
        //	Petri-Netze
        //	- ...
        
        JMenu graph = erzeugeMen�(Messages.getString("Text.40"), KeyEvent.VK_G);       //$NON-NLS-1$
        men�.add(graph);

        JMenu komponenten = new JMenu(Messages.getString("Text.41")); komponenten.setMnemonic('K');//erzeugeMen�Eintrag("Zusammenhangskomponenten", KeyEvent.VK_P, KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.ALT_MASK)); //$NON-NLS-1$
        JMenuItem streng = erzeugeMen�Eintrag(Messages.getString("Text.42"), KeyEvent.VK_S, KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK)); //$NON-NLS-1$
        JMenuItem pseudo = erzeugeMen�Eintrag(Messages.getString("Text.43"), KeyEvent.VK_P, KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK)); //$NON-NLS-1$
        JMenuItem schwach = erzeugeMen�Eintrag(Messages.getString("Text.44"), KeyEvent.VK_C, KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK)); //$NON-NLS-1$

        JMenu wege = new JMenu(Messages.getString("Text.45")); wege.setMnemonic('W');//erzeugeMen�Eintrag("Zusammenhangskomponenten", KeyEvent.VK_P, KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.ALT_MASK)); //$NON-NLS-1$
        JMenuItem einWeg = erzeugeMen�Eintrag(Messages.getString("Text.46"), KeyEvent.VK_W, KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.ALT_MASK)); //$NON-NLS-1$
        JMenuItem kantenDisjunkte = erzeugeMen�Eintrag(Messages.getString("Text.47"), KeyEvent.VK_A, KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.ALT_MASK)); //$NON-NLS-1$
        JMenuItem knotenDisjunkte = erzeugeMen�Eintrag(Messages.getString("Text.48"), KeyEvent.VK_O, KeyStroke.getKeyStroke(KeyEvent.VK_6, ActionEvent.ALT_MASK)); //$NON-NLS-1$
        
        JMenu sortieren = new JMenu(Messages.getString("Text.49")); sortieren.setMnemonic('S'); //erzeugeMen�Eintrag("Zusammenhangskomponenten", KeyEvent.VK_P, KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.ALT_MASK)); //$NON-NLS-1$
        JMenuItem topologisch = erzeugeMen�Eintrag(Messages.getString("Text.50"), KeyEvent.VK_T, KeyStroke.getKeyStroke(KeyEvent.VK_7, ActionEvent.ALT_MASK)); //$NON-NLS-1$

        JMenuItem reset = erzeugeMen�Eintrag(Messages.getString("Text.51"), KeyEvent.VK_R, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE , 0)); //$NON-NLS-1$

        graph.add(komponenten);     //komponenten.setEnabled(false);
        komponenten.add(streng);
        komponenten.add(pseudo);
        komponenten.add(schwach);

        graph.add(wege);
        wege.add(einWeg);
        wege.add(kantenDisjunkte);
        wege.add(knotenDisjunkte);
        
        graph.add(sortieren);
        sortieren.add(topologisch);
        
        graph.add(reset);
        
        //////////////////
        // Fenster      //
        //////////////////
        
        //////////////////
        // Hilfe        //
        //////////////////
        JMenu    hilfe = erzeugeMen�(Messages.getString("Text.52"), KeyEvent.VK_H);        //$NON-NLS-1$
        men�.add(hilfe);
        
        JMenuItem hilfeInhalt = erzeugeMen�Eintrag(Messages.getString("Text.53"), KeyEvent.VK_H, KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.ALT_MASK)); //$NON-NLS-1$
        JMenuItem info        = erzeugeMen�Eintrag(Messages.getString("Text.54"),      KeyEvent.VK_H, KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.ALT_MASK)); //$NON-NLS-1$

        hilfe.add(hilfeInhalt);
        hilfe.add(info);
        
        return men�;
    }
    
    public JMenu erzeugeMen�(String name, int zeichen) {
        JMenu men� = new JMenu(name);
              men�.setMnemonic(zeichen);
        return men�;
    }
    
    public JMenuItem erzeugeMen�Eintrag(String name, int zeichen, KeyStroke kombi) {
        JMenuItem eintrag = new JMenuItem(name);
                  if (kombi != null) eintrag.setAccelerator(kombi);
                  eintrag.setMnemonic(zeichen);
                  eintrag.addActionListener(this);
        return    eintrag;
    }
    
    //---------------------------
    // Men�-Funktionen
    //---------------------------
    //---- Datei ----

    //---- Bearbeiten ----
    public void editUndo() {}
    
    public void editRedo() {}
    
    public void editCut()  {}    // nur GraphenDiagramm!
    
    public void editCopy() {}    // nur GraphenDiagramm!
    
    public void editPaste() {}    // nur GraphenDiagramm!
    
    public void editFind() {}
    
    public void editReplace() {}
    
    public void editSelectAll() {}
    
    //---- Ansicht ----
    public void viewGrid() {
    	if (aktuelleAnsicht == ansicht[0]) {
    		raster = !raster;
    		((GraphenDiagramm)aktuelleAnsicht).setRaster(raster);
    		saveProperties();
    	}
    }   // nur GraphenDiagramm!
    
    public void viewSnap() {
    	if (aktuelleAnsicht == ansicht[0]) {
    		fang = !fang;
    		((GraphenDiagramm)aktuelleAnsicht).setFang(fang);
    		saveProperties();
    	}
    }   // nur GraphenDiagramm!
    
    public void viewCaption() {
    	if (aktuelleAnsicht == ansicht[0]) {
    		beschriftung = !beschriftung; 
    		((GraphenDiagramm)aktuelleAnsicht).setSchrift(beschriftung);
    		saveProperties();
    	}
    }
    
    public void viewZoom() {}
    
    private String chooseFile() {
    	String pfad = "";
    	String dateiname = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(arbeitsVerzeichnis));
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        	pfad = chooser.getCurrentDirectory().getPath();
            dateiname = chooser.getSelectedFile().getName();
        }
        if (pfad.equals("") || dateiname.equals(""))
        	return "";
        arbeitsVerzeichnis = pfad;
        saveProperties();
        return pfad + File.separator + dateiname;
    }
    
    public void info() {
    	JOptionPane.showMessageDialog(this, "Der inhalt der �bersicht", "Der Titel", JOptionPane.PLAIN_MESSAGE);
    }
  

    //======================================================//
    // Zuordnung der Men�befehle 							//
    // zu den entsprechenden Methoden 						//
    //======================================================//
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String text = source.getText();
        
        // Datei
             if (text.equals(Messages.getString("Text.58")))        dateiSteuerung.dateiNeu(); //$NON-NLS-1$
//        else if (text.equals(Messages.getString("Text.97")))        dateiSteuerung.fileNewType(vorlagenVerzeichnis); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.59")))        dateiSteuerung.dateiLaden(chooseFile()); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.60")))		if (dateiSteuerung.dateiPfadName != null) dateiSteuerung.dateiSpeichern(); else dateiSteuerung.dateiSpeichernAls(chooseFile()); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.61"))) 		dateiSteuerung.dateiSpeichernAls(chooseFile()); //$NON-NLS-1$
//      else if (text.equals("Schlie�en"))       dateiSteuerung.fileClose();
        else if (text.equals(Messages.getString("Text.62")))   		dateiSteuerung.dateiDruckenVorschau(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.63")))        dateiSteuerung.dateiDrucken(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.64")))     	dateiSteuerung.dateiExportierenJPG(aktuelleAnsicht.getSnapshot()); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.65"))) {  ((GraphenDiagramm)ansicht[0]).setHintergrundBild(dateiSteuerung.dateiHintergrundLaden(chooseFile())); aktuelleAnsicht.repaint(); } //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.66")))   		dateiSteuerung.dateiEigenschaften(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.67")))        System.exit(0); //$NON-NLS-1$

        // Graph -> Komponenten
        else if (text.equals(Messages.getString("Text.68")))     	  steuerung.strengeKomponenten(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.69")))     	  steuerung.pseudoStrengeKomponenten(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.70")))     	  steuerung.schwacheKomponenten(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.71")))		  steuerung.wegSuchen(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.72")))	  steuerung.topologischSortieren(); //$NON-NLS-1$
//        else if (text.equals(Messages.getString("Text.73"))) steuerung.kantenDisjunkteWege(); //$NON-NLS-1$
//        else if (text.equals(Messages.getString("Text.74"))) steuerung.knotenDisjunkteWege(); //$NON-NLS-1$
//        else if (text.equals(Messages.getString("Text.75"))) 			{  steuerung.FarbenZur�cksetzen(); aktuelleAnsicht.repaint();	}  //$NON-NLS-1$
             
        // Bearbeiten
        else if (text.equals(Messages.getString("Text.76")))      editUndo(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.77")))     editRedo(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.78")))    editCut(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.79")))        editCopy(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.80")))        editPaste(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.81")))      editFind(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.82")))    editReplace(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.83")))  editSelectAll(); //$NON-NLS-1$

        // Ansicht
        else if (text.equals(Messages.getString("Text.84")))    viewGrid(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.85")))    viewSnap(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.86")))    viewCaption(); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.87")))    switchAnsicht(UP); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.88")))    switchAnsicht(DOWN); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.89")))    aktuelleAnsicht.switchModus(UP); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.90")))    aktuelleAnsicht.switchModus(DOWN); //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.91")))  ((GraphenDiagramm)ansicht[0]).switchHintergrund();  //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.92")))    switchDigital();  //$NON-NLS-1$
        else if (text.equals(Messages.getString("Text.93")))            viewZoom(); //$NON-NLS-1$

        // Hilfe
//      else if (text.equals("Hilfe"))           help();
        else if (text.equals(Messages.getString("Text.54")))            info();

        setTitle(�berschrift + Messages.getString("Text.94") + (dateiSteuerung.dateiPfadName != null ? dateiSteuerung.dateiPfadName : Messages.getString("Text.95"))); //$NON-NLS-1$ //$NON-NLS-2$
        aktuelleAnsicht.repaint();
    }
    
    // Das Programm
    public static void main(String[] args) {

//	    try {
//	        UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
//	    } catch (Exception e) {	e.printStackTrace();	}
        
		GraphenEditor editor = new GraphenEditor(Messages.getString("Text.96")); //$NON-NLS-1$

//        if (args.length > 0) 
//            editor.load(args[0]);

        editor.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e){ System.exit(0); }		}); 
		editor.pack();
		editor.setLocationByPlatform(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
        int posx = screenSize.width/2 - 320;
        int posy = screenSize.height/2 - 240;
        editor.setLocation(posx, posy);
        editor.setSize(640,480);
		editor.setVisible(true);
    }
}
