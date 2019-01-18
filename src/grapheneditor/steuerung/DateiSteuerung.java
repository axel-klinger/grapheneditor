package grapheneditor.steuerung;

import grapheneditor.GraphenModell;
import grapheneditor.GraphenModellEinAusgabe;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
//import java.io.File;
import java.io.FileOutputStream;

//import javax.swing.JFileChooser;
//import java.awt.Component;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class DateiSteuerung {

	private GraphenModell modell;
//	public String pfad;
//    public String dateiname;
	public  String dateiPfadName;

	
	public DateiSteuerung (GraphenModell modell) {
		this.modell = modell;
		dateiPfadName = null;
	}
	
		
    public void dateiNeu() {
    	modell.leeren();
    	dateiPfadName = null;
    }
    
    public void dateiLaden(String datei) {
    	if (datei.equals("\\"))
    		return;
        modell.leeren();
        // Daten in Modell einfügen
        
        
        GraphenModell tempModell = GraphenModellEinAusgabe.loadXML(datei);
        if ( tempModell != null)
        	modell.copy(tempModell);
        datei = datei.replaceAll(".xml", "");
        dateiPfadName = datei;
        
        modell.aktualisieren();
//System.out.println("Hierarchie: " + modell.getHierarchie());
    }
    
    public void dateiSpeichern() {
    	GraphenModellEinAusgabe.saveXML(modell, dateiPfadName);
    }	
    
    public void dateiSpeichernAls(String datei) {
        datei = datei.replaceAll(".xml", "");
    	GraphenModellEinAusgabe.saveXML(modell, datei + ".xml");
    	dateiPfadName = datei;
    }

    public void dateiExportierenSVG(String datei) {
        datei = datei.replaceAll(".xml", "");
        datei = datei.replaceAll(".svg", "");
    	GraphenModellEinAusgabe.saveSVG(modell, datei + ".svg");
    	dateiPfadName = datei;
    }

//  public void fileClose() {}  // erst bei Multidocument
    
    public void dateiDruckenVorschau() {}
    
    public void dateiDrucken() {}
    
    public void dateiExportierenJPG(BufferedImage bild) {
//        String dateiname = "D:\\Bild1.jpg";
        
//      String s = ""; // Dateiname des Graphen (ohne Endung) und der gleiche Pfad
        // ...außerdem immer eine Kopie vom Graphen passend zum Bild mitspeichern 
        // -> Das Bild kann als Preview im Explorer angezeigt werden.
        // -> DoppelKlick in Word soll die Datei im Editor öffnen !!!
        //    ... oder sogar in einer Komponente in Word ???
    	dateiSpeichernAls(dateiPfadName + ".xml");
        writeJPEG(dateiPfadName + ".jpg", bild); // =print?
    }
    
    public void dateiExportierenAlsJPG(BufferedImage bild, String dateiname) {
//        String dateiname = "D:\\Bild1.jpg";
        
//      String s = ""; // Dateiname des Graphen (ohne Endung) und der gleiche Pfad
        // ...außerdem immer eine Kopie vom Graphen passend zum Bild mitspeichern 
        // -> Das Bild kann als Preview im Explorer angezeigt werden.
        // -> DoppelKlick in Word soll die Datei im Editor öffnen !!!
        //    ... oder sogar in einer Komponente in Word ???
    	dateiExportierenSVG(dateiname);
    	dateiSpeichernAls(dateiname + ".xml");
        writeJPEG(dateiname + ".jpg", bild); // =print?
    }
    
    private static void writeJPEG(String dateiname, BufferedImage bild) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream( 0xfff );
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder( out );
            JPEGEncodeParam param;
            param = encoder.getDefaultJPEGEncodeParam( bild );
            param.setQuality( 1.0f, true );
            encoder.encode( bild, param );

            FileOutputStream fos = new FileOutputStream(dateiname);
            fos.write( out.toByteArray() );
            fos.close();
            out.close();
        } catch (Exception e) { e.printStackTrace();    }   
    }

    public void dateiEigenschaften() {}

	public Image dateiHintergrundLaden(String dateiname) {
		Image bild = Toolkit.getDefaultToolkit().getImage(dateiname);
		return bild;
	}


//	public void fileNewType(String vorlagenVerzeichnis) {
//		// TODO Auto-generated method stub
//		GraphenVorlage vorlage = null;
//		NeuerGraphDialog ngd = new NeuerGraphDialog(vorlagenVerzeichnis);
//        if (ngd.showDialog() == WizardDialog.FERTIG) {
//            System.out.println("Vorlage: " + ngd.getVorlage());
//            vorlage = ngd.getVorlage();
//        }
//        // modell = !Achtung: nicht den Link verlieren!
//    }

}


//public void fileExport() {
//    // + automatisch hochzählen
//    // ----------------------
//    // als Pixelgrafik
//    // ----------------------
//    String dateiname = "D:\\Bild1.jpg";
////  String s = ""; // Dateiname des Graphen (ohne Endung) und der gleiche Pfad
//    // ...außerdem immer eine Kopie vom Graphen passend zum Bild mitspeichern 
//    // -> Das Bild kann als Preview im Explorer angezeigt werden.
//    // -> DoppelKlick in Word soll die Datei im Editor öffnen !!!
//    //    ... oder sogar in einer Komponente in Word ???
//    writeJPEG(dateiname, aktuelleAnsicht.getSnapshot()); // =print?
//    
//    // ----------------------
//    // als Vektorgrafik
//    // ----------------------
//    // dateiname = "D:\\Bild1.wmf";
//    // writeEMF(dateiname, aktuelleAnsicht);
//    
///*        String dateiname2 = "D:\\Bild1.wmf";
//    Properties p = new Properties();
////    p.setProperty("PageSize","A5");
//    VectorGraphics g = null;
//    try {
//        g = new EMFGraphics2D(new File(dateiname2), aktuelleAnsicht);
////        g = new EMFGraphics2D(new File(dateiname2), new Dimension(1024,768));
//    } catch (FileNotFoundException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//    } 
//    if (g != null) {
//        g.setProperties(p); 
//        g.startExport(); 
//        aktuelleAnsicht.print(g); 
//        g.endExport();
//        
//        ExportDialog export = new ExportDialog();
//        export.showExportDialog( this, "Export view as ...", aktuelleAnsicht, "export" );
//
//    }
////=======
//    writeJPEG(dateiname, aktuelleAnsicht.getSnapshot());
//    
////    dateiname = "D:\\Bild1.emf";
//    ExportDialog export = new ExportDialog();
//    export.showExportDialog(this, "Export view as ...", aktuelleAnsicht, "export");
//    */
//}

