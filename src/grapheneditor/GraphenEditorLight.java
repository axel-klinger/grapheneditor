package grapheneditor;

import grapheneditor.darstellung.GraphenDiagrammInteraktiv;
import grapheneditor.darstellung.GraphenEigenschaftenAnsicht;
import grapheneditor.darstellung.GraphenDarstellung;
import grapheneditor.darstellung.GraphenDiagramm;
import grapheneditor.darstellung.MatrizenDiagramm;
import grapheneditor.darstellung.MengenSchreibweise;
import grapheneditor.dialoge.graphErzeugen;
import grapheneditor.steuerung.DateiSteuerung;
import grapheneditor.steuerung.GraphenMethoden;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GraphenEditorLight extends JFrame
								implements KeyListener {
    
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
	JPanel ansichtsFläche;
		
//		========== BEGINN Konfigurationsdatei ===========
//
	public Einstellungen opt = new Einstellungen();
//
//	========== ENDE Konfigurationsdatei ===========
		
    public GraphenEditorLight() {
		super();
		setSize(640,480);

//		vorlage    = new GraphenVorlage("Schlichter Graph"); // Modell = Vorlage???
		modell 	   = new GraphenModell( new KnotenElement("WURZEL"));
		steuerung  = new GraphenMethoden(modell);
        dateiSteuerung = new DateiSteuerung(modell);
		
		ansicht    = new GraphenDarstellung[3];
		ansicht[0] = new GraphenDiagrammInteraktiv(modell, steuerung);
		ansicht[1] = new MatrizenDiagramm(modell);
		ansicht[2] = new MengenSchreibweise(modell);
		
		ansichtIndex = 0;
		aktuelleAnsicht = ansicht[ansichtIndex];
		
//			addKeyListener((GraphenDiagramm) ansicht[0]);
		this.requestFocus();
		
		eigenschaftenAnsicht = new GraphenEigenschaftenAnsicht(modell);
		
		setLayout(new BorderLayout());
        
		ansichtsFläche = new JPanel();
		ansichtsFläche.setLayout(new BorderLayout());
		ansichtsFläche.add(aktuelleAnsicht);

		add(ansichtsFläche, Messages.getString("Text.1"));	 //$NON-NLS-1$
		add(eigenschaftenAnsicht,Messages.getString("Text.2")); //$NON-NLS-1$
		
		addKeyListener(this);
    }
    //---- Ansicht ----
    
    public void viewSwitch(int richtung) {
        aktuelleAnsicht.setVisible(false);
        ansichtsFläche.remove(aktuelleAnsicht);
        ansichtIndex = (ansicht.length + ansichtIndex + richtung) % ansicht.length;
        aktuelleAnsicht = ansicht[ansichtIndex];
        
        ansichtsFläche.add(aktuelleAnsicht);
        aktuelleAnsicht.setVisible(true);
    }
    public void viewProperties() {
	    int breite = eigenschaftenAnsicht.isVisible() ? aktuelleAnsicht.getWidth() + digitalBreite : aktuelleAnsicht.getWidth() - digitalBreite;
	    aktuelleAnsicht.setPreferredSize(new Dimension(breite, aktuelleAnsicht.getHeight()));
        eigenschaftenAnsicht.switchAnsicht();
    }
    public void viewGrid() {
    	if (aktuelleAnsicht == ansicht[0]) {
    		opt.raster = !opt.raster;
    		((GraphenDiagramm)aktuelleAnsicht).setRaster(opt.raster);
    	}
    }   // nur GraphenDiagramm!
    public void viewSnap() {
    	if (aktuelleAnsicht == ansicht[0]) {
    		opt.fang = !opt.fang;
    		((GraphenDiagramm)aktuelleAnsicht).setFang(opt.fang);
    	}
    }   // nur GraphenDiagramm!
    public void viewCaption() {
    	if (aktuelleAnsicht == ansicht[0]) {
    		opt.beschriftung = !opt.beschriftung; 
    		((GraphenDiagramm)aktuelleAnsicht).setSchrift(opt.beschriftung);
    	}
    }
        
    private String chooseFile() {
    	String pfad = "";
    	String dateiname = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(opt.arbeitsVerzeichnis));
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        	pfad = chooser.getCurrentDirectory().getPath();
            dateiname = chooser.getSelectedFile().getName();
        }
        if (pfad.equals("") || dateiname.equals(""))
        	return "";
        opt.arbeitsVerzeichnis = pfad;
        return pfad + File.separator + dateiname;
    }
    
    public void helpInfo() {
    	String s = "Steuerung\n\n";
    	s += "F1  Diese Hilfe\n";
    	s += "B   Beschriftung\n";
    	s += "C   Schwache Zusammenhangskomponenten\n";
    	s += "D   Struktureigenschaften\n";
    	s += "E   Kantendisjunkte Wege (a,b)\n";
    	s += "F   Fang\n";
    	s += "G   Gerichtete Kanten\n";
    	s += "I   Füllfarbe wählen (auswahl)\n";
    	s += "L   Linienfarbe wählen (auswahl)\n";
    	s += "N   Knotendisjunkte Wege (a,b)\n";
    	s += "T   Topologisch sortieren\n";
    	s += "P   Pseudostrenge Zusammenhangskomponenten\n";
    	s += "R   Ansicht Raster\n";
    	s += "X   Ansicht Schatten\n";
    	s += "RECHTS  Nächste Ansicht\n";
    	s += "LINKS   Letzte Ansicht\n";
    	s += "HOCH    Nächster Modus\n";
    	s += "RUNTER  Letzter Modus\n";
    	s += "ESCAPE  Farben zurücksetzen\n\n";
    	
    	s += "Strg + N   Neuer Graph\n";
    	s += "Strg + O   Graph öffnen\n";
    	s += "Strg + S   Graph speichern\n";
    	s += "Strg + U   Graph speichern unter ...\n";
    	s += "Strg + G   Graph als SVG exportieren\n";
    	s += "Strg + X   Graph als JPG exportieren\n";
    	s += "Strg + H   Hintergrundbild laden\n\n";
    	
    	s += "Alt + RUNTER Ansicht zurück\n";
    	s += "Alt + HOCH   Ansicht vor\n";
    	s += "Alt + H      Hintergrundbild anzeigen\n";
    	s += "Alt + F4     GraphenEditor beenden\n";
    	JOptionPane.showMessageDialog(this, s, "Der Titel", JOptionPane.PLAIN_MESSAGE);
    }
 
    // Das Programm
    public static void main(String[] args) {
//    	JFrame fenster = new JFrame("GraphenEditor");
    	
    	GraphenEditorLight fenster = new GraphenEditorLight();
//    	fenster.add(editor);
    	
        fenster.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e){ System.exit(0); }		}); 
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
        int posx = screenSize.width/2 - 400;
        int posy = screenSize.height/2 - 300;
        fenster.setTitle("GraphenEditor - <neu>");
        fenster.setLocation(posx, posy);
        fenster.setSize(800,600);
        fenster.setVisible(true);

//        fenster.addKeyListener(editor);
    }

	public void keyTyped(KeyEvent arg0) {	}
	public void keyPressed(KeyEvent e) {
		String s = null;
		if (e.isControlDown()) {
			if(e.isShiftDown()){
				switch (e.getKeyCode()) {
				case VK_N:
					new graphErzeugen(modell,aktuelleAnsicht.getSize());
					break;

				default:
					break;
				}
			}
	    	switch(e.getKeyCode()) {
	    		case VK_N:	dateiSteuerung.dateiNeu();					break;
	    		case VK_O:	if ((s = chooseFile()) != null && !s.equals(""))
	    						dateiSteuerung.dateiLaden(s);		break;
	    		case VK_S:	if (dateiSteuerung.dateiPfadName != null
	    						&& !dateiSteuerung.dateiPfadName.equals("")) 
	    	    				dateiSteuerung.dateiSpeichern(); 
	    	    			else 
	    	    				dateiSteuerung.dateiSpeichernAls(chooseFile());break;
	    		case VK_U:	if ((s = chooseFile()) != null && !s.equals(""))
	    						dateiSteuerung.dateiSpeichernAls(s);	break;
	    		case VK_G:	if ((s = chooseFile()) != null && !s.equals(""))
	    						dateiSteuerung.dateiExportierenSVG(s);	break;
	    		case VK_X:	if (dateiSteuerung.dateiPfadName != null) 
			    				dateiSteuerung.dateiExportierenJPG(aktuelleAnsicht.getSnapshot()); 
			    			else
			    				dateiSteuerung.dateiExportierenAlsJPG(aktuelleAnsicht.getSnapshot(),chooseFile());	break;
	    		case VK_H:	((GraphenDiagramm)ansicht[0]).setHintergrundBild(dateiSteuerung.dateiHintergrundLaden(chooseFile())); 
	    					aktuelleAnsicht.repaint();					break;
	    	}
		} else if (e.isAltDown()) {
			switch (e.getKeyCode()) {
				case VK_F4:	System.exit(0);
				case VK_H:	((GraphenDiagramm)ansicht[0]).switchHintergrund();	break;
			}
			
		} else {
			switch (e.getKeyCode()) {
				case VK_F1:	helpInfo();				break;
				case VK_S:	steuerung.strengeKomponenten();	break;
				case VK_P:	steuerung.pseudoStrengeKomponenten();	break;
				case VK_R:	viewGrid();			break;
				case VK_F:	viewSnap();			break;
				case VK_B:	viewCaption();		break;
				case VK_D:	viewProperties();	break;
				
	        	case VK_I:  ((GraphenDiagrammInteraktiv)ansicht[0]).setFüllFarbe();     break;		// -> Modell
	            case VK_L:  ((GraphenDiagrammInteraktiv)ansicht[0]).setLinienFarbe();   break;		// -> Modell
	            case VK_X:	((GraphenDiagramm)ansicht[0]).switchSchatten(); repaint(); break;	// -> AnsichtSteuerung
	            case VK_G:	((GraphenDiagramm)ansicht[0]).switchGerichtet(); repaint(); break;		

				case VK_C:	steuerung.schwacheKomponenten();		break;
				case VK_T:	steuerung.topologischSortieren();			break;
//				case VK_E:	steuerung.kantenDisjunkteWege();		break;
//				case VK_N:	steuerung.knotenDisjunkteWege();		break;
				case VK_RIGHT:		viewSwitch(UP);	break;
				case VK_LEFT:	viewSwitch(DOWN);	break;
				case VK_UP:		aktuelleAnsicht.switchModus(UP);	break;
				case VK_DOWN:	aktuelleAnsicht.switchModus(DOWN);	break;
//				case VK_ESCAPE:	steuerung.FarbenZurücksetzen();	
//								aktuelleAnsicht.repaint();	break;
			}
		}
		aktuelleAnsicht.repaint();
		
		if (dateiSteuerung.dateiPfadName != null 
				&& !dateiSteuerung.dateiPfadName.equals(""))
			setTitle("GraphenEditor - " + dateiSteuerung.dateiPfadName);
		else
			setTitle("GraphenEditor - <neu>");
	}
	public void keyReleased(KeyEvent arg0) {	}
	
}

class Einstellungen {
//	public String überschrift;
	public String vorlagenVerzeichnis = "Beispiele/Vorlagen";
	public String arbeitsVerzeichnis = "Beispiele";
	public int knotenGröße;
	public int schriftGröße;
	public boolean digital 	 	= true;
	public boolean raster  	 	= false;
	public boolean fang         = false;
	public boolean beschriftung = true;
}