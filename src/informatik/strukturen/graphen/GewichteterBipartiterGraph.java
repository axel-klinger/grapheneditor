
package informatik.strukturen.graphen;

//import informatik.strukturen.graphen.wege.Bewertet;
//import informatik.strukturen.Menge;
import informatik.strukturen.Paar;

import java.util.HashMap;

/**
 *  <P><B>Bewerteter Graph</B> <I>(engl. weight graph)</I>: Ein BewerteterGraph erweitert einen schlichten Grahp um eine 
 *  Bewertung der Kanten und Knoten.</P>
 *
 *  <P>Die Bewertung des Graphen (Interface) setzt die Definition 
 *  gewisser Rechenregeln vorraus. Es müssen die Kettung und die 
 *  Vereinigung definiert werden. Außerdem muss (zumindest intern!?)
 *  eine Bewertungsmatrix ausgegeben werden können.</P>
 *
 *  <CENTER><IMG SRC="imagesGraph/Gewichtet.png" BORDER=0 ALT="gewichteter Graph"></CENTER>
 *
 *  <I>Anwendungsbeispiele</I>:
 *      <UL>
 *          <LI>Kürzeste Wege Suche
 *          <LI>Maximale Flüsse
 *      </UL>
 *
 *  <p><strong>Version:</strong>
 *  <br><dd>1.0, April 2004
 *  <p><strong>Autor:</strong>
 *  <br><dd>Institut für Bauinformatik
 *  <br><dd>Universität Hannover
 *  <br><dd>Dipl.-Ing. Axel Klinger
 */
public class GewichteterBipartiterGraph<K1,K2> extends BipartiterGraph<K1,K2> /*implements Bewertet<K>*/ { //implements gewichtet{ 

    //  private HashMap knoten;                                             // Menge mit den Knoten
    private HashMap<K1,Object[]>         knotenBewertungenA;  //das Gleiche für Geweichtete Graphen - Kompatibilität!
    private HashMap<K2,Object[]>         knotenBewertungenB;  //das Gleiche für Geweichtete Graphen - Kompatibilität!
    private HashMap<Paar<K1,K2>,Object[]> kantenBewertungenAB;
    private HashMap<Paar<K2,K1>,Object[]> kantenBewertungenBA;
    private int werteProKnotenA;
    private int werteProKnotenB;
    private int werteProKanteAB;
    private int werteProKanteBA;

    // ODER DOCH NUR EIN GEWICHT JE KANTE BZW. KNOTEN ???????????
    // =======================================================


    /** Erzeugt einen Nullgraph. Ein Nullgraph ist ein Graph ohne Knoten.    */
  public GewichteterBipartiterGraph(int werteProKnotenA, int werteProKnotenB, int werteProKanteAB, int werteProKanteBA) { 
        super();
        knotenBewertungenA = new HashMap<K1,Object[]>();
        knotenBewertungenB = new HashMap<K2,Object[]>();
        
        kantenBewertungenAB = new HashMap<Paar<K1,K2>,Object[]>();
        kantenBewertungenBA = new HashMap<Paar<K2,K1>,Object[]>();
        
        this.werteProKnotenA = werteProKnotenA;
        this.werteProKnotenB = werteProKnotenB;
        
        this.werteProKanteAB = werteProKanteAB;
        this.werteProKanteBA = werteProKanteBA;
    }

    /** Erzeugt einen leeren Graph zu einer gegebenen Menge von Knoten. 
     *  Ein Leerer Graph ist ein Graph ohne Kanten.
     */
//  public BewerteterBipartiterGraph(Menge<K1> knotenA, Menge<K2> knotenB) { 
//        super(knotenA, knotenB);
//      this.knoten = new HashMap();
//      for (Iterator i = knoten.alleElemente(); i.hasNext(); )
//          hinzufügen(i.next());
//    }

/*    public boolean hinzufügen(K x) {
        if (!super.hinzufügen(x))
            return false;
        knotenBewertungen.put(x, new Object[werteProKnoten]);
        return true;
    }

    public boolean hinzufügen(K x, K y) {
        if (!super.hinzufügen(x, y))
            return false;
        kantenBewertungen.put(new Paar<K,K>(x, y), new Object[werteProKante]);
        return true;
    }   */

    /**
     *  Setzt ein Gewicht eines Knotens auf einen neuen Wert.
     */
/*    public boolean setWert(K x, int index, Object wert) {
        if (!knotenBewertungen.containsKey(x))
            return false;
        ((Object[]) knotenBewertungen.get(x))[index] = wert;
        return true;
    }   */

    /**
     *  Liefert ein Gewicht eines Knotens.
     */
/*    public Object getWert(K x, int index) {
        if (!knotenBewertungen.containsKey(x))
            return null;
        return ((Object[]) knotenBewertungen.get(x))[index];
    }   */

    /**
     *  Setzt ein Gewicht einer Kante auf einen neuen Wert.
     */
/*    public boolean setWert(K x, K y, int index, Object wert) {
        Paar<K,K> p = new Paar<K,K>(x, y);
        if (!kantenBewertungen.containsKey(p))
            return false;
        ((Object[]) kantenBewertungen.get(p))[index] = wert;
        return true;
    }   */

    /**
     *  Liefert ein Gewicht einer Kante.
     */
/*    public Object getWert(K x, K y, int index) {
        Paar<K,K> p = new Paar<K,K>(x, y);
        if (!kantenBewertungen.containsKey(p))
            return null;
        return ((Object[]) kantenBewertungen.get(p))[index];
    }   */

/*    public String toString() {
        String s = "({";
        int c = 0;

        for (K k : knoten()) {
            if (c>0)
                s += ",";
            s += "(" + k + ",[";
            for (int j=0; j<werteProKnoten; j++) {
                if (j>0)
                    s += ",";
                s += ((Object[]) knotenBewertungen.get(k))[j];
            }
            s += "])";
        }

        s += "};{";

        c = 0;
        for (Paar<K,K> p : kanten()) {
            if (c>0)
                s += ",";
            s += "(" + p + ",[";
            for (int j=0; j<werteProKante; j++) {
                if (j>0)
                    s += ",";
                s += ((Object[]) kantenBewertungen.get(p))[j];
            }
            s += "])";
        }
        s += "})";
        return s;
    }   */
}   