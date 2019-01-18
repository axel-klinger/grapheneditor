package informatik.netzplantechnik;

import informatik.strukturen.Menge;
import informatik.strukturen.Paar;
import informatik.strukturen.graphen.BewerteterGraph;
import informatik.strukturen.graphen.SchlichterGraph;
import informatik.strukturen.graphen.hilfsmittel.GraphenAufgaben;

/**
 *	<P><B>NetzPlan</B> <I>(engl. network plan)</I>: 
 * Ein Netzplan ist eine grafische oder tabellarische Darstellung von Abl�ufen
 * und deren Anordnungsbeziehungen (DIN 69900).</P>
 * <P>Ein Netzplanverfahren ist die grunds�tzliche Form der Zuordnung von 
 * Ablaufelementen zu Darstellungselementen. Die wesentlichen Verfahren f�hren 
 * zu Ereignisknotennetzpl�nen, Vorgangsknotennetzpl�nen und 
 * Vorgangspfeilnetzpl�nen.</P>
 * 
 * 
 * Der NetzPlan selbst als bipartiter Graph.<BR>
 * Die Hauptarten produzierbar �ber getVKN, ...
 * 
 * <P>Drei Hauptarten der Netzpl�ne: VKN, VPN, EKN</P>
 * Vorgang
 *  - Dauer
 *  
 * Beziehung
 *  - Normal-, Anfangs-, End- und Sprungfolge   
 * 
 * Regeln
 *  - ayzklisch => mindestens ein Start- und ein Endknoten
 * 
 * Funktionen:
 *  - Topologisch sortieren
 *  - kritischer Weg
 *  - fr�heste und sp�teste Anfangs- und Endzeiten einzelner Vorg�nge
 * 
 * @author klinger
 */
public class NetzPlan<K> extends BewerteterGraph<K,long[]> {
	
	// eigentlich bewerteter BIPARTITER Graph<Vorgang,Ereignis>
	// -> VKN
	// -> VPN
	// -> EKN
	
	public static final int D  = 0;
	public static final int FA = 1, FE =  2, SA = 3,  SE  = 4;
	public static final int GP = 5, FP =  6, UP =  7, FRP = 8;
	private static final int PD = 9, HD = 10, OD = 11, MD  = D;
	
	private static final int ANZAHL_WERTE = 9;
	
	public NetzPlan() {
		super();
	}
	
	public NetzPlan(SchlichterGraph<K> g) {
		super();
		for (K k : g.knoten()) {
			hinzuf�gen(k);
			setWert(k, new long[ANZAHL_WERTE]);
		}
		
		for (Paar<K,K> p : g.kanten()) {
			hinzuf�gen(p.eins(), p.zwei());
		}
			
//		super(g);
//		for (K ae : knoten())
//			setWert(ae, new long[ANZAHL_WERTE]);
	}

	public boolean hinzuf�gen(K ae) {
		if (super.hinzuf�gen(ae)) {
			long[] wert = new long[ANZAHL_WERTE];
			setWert(ae, wert);
			
// FIXME Das ist Mist! Weshalb sollte K ein Vorgang sein ???			
			
			// Mapping AE->long[]
			if (ae instanceof Vorgang) {
				Vorgang v = (Vorgang) ae;
				wert[D] = v.dauer;
				// ...
				return true;
			} else if (ae instanceof Ereignis) {
				Ereignis e = (Ereignis) ae;
				wert[D] = 0;
				// ...
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Liefert die Verflechtungszahl des Netzplans. Die Verflechtungszahl gibt 
	 * das Verh�ltnis von Pfeilen zu Knoten an.  
	 */
	public int getVerflechtungsZahl() {
		return anzahlKanten()/(anzahlKnoten()-1);
	}
	
	/**
	 * Liefert den Rang eines Knotens. Der Rang eines Knotens ist die maximale
	 * Anzahl von Pfeilen von einem Startknoten zum betrachteten Knoten. 
	 */
	public int getRang(K e) {
		// FIXME FALSCH: Rang ermitteln aus Folge<Menge> ! ! ! !
		return GraphenAufgaben.topologischSortierteListe(this, true).getPosition(e);
	}

	private long max(Menge<K> M, int index) {
		if (M.anzahl() == 0)
			return 0;
		long ms = Long.MIN_VALUE;
		for (K k : M)
			ms = Math.max(ms, getWert(k)[index]);
		return ms;
	}

	private long min(Menge<K> M, int index) {
		if (M.anzahl() == 0)
			return 0;
		long ms = Long.MAX_VALUE;
		for (K k : M)
			ms = Math.min(ms, getWert(k)[index]);
		return ms;
	}

	// Im VKN:
	public void berechneZeitPlan() {	// Liefert: ZeitPlan
		
		long maxGesamtDauer = 0;
		
		// fr�heste VorgangsZeitpunkte
		// Die Struktur ...
		for (K ae : GraphenAufgaben.topologischSortierteListe(this, true)) {
			Menge<K> V = vorg�nger(ae);
			
			// ... die Bewertung
			long[] wert = getWert(ae);
			wert[FA] = V.anzahl()>0 ? max(V, FE) : 0;
			wert[FE] = wert[FA] + wert[D];

			maxGesamtDauer = Math.max(maxGesamtDauer, wert[FE]);
		}
		
		// sp�teste VorgangsZeitpunkte
		for (K ae : GraphenAufgaben.topologischSortierteListe(this, false)) {
			Menge<K> N = nachfolger(ae);

			long[] wert = getWert(ae);
			wert[SE] = N.anzahl()>0 ? min(N, SA) : maxGesamtDauer;
			wert[SA] = wert[SE] - wert[D];
		}
		
		// PufferZeiten
		// (in einem Abwasch?)
		for (K ae : knoten()) {
			Menge<K> V = vorg�nger(ae);
			Menge<K> N = nachfolger(ae);
			
			long[] wert = getWert(ae);
			long minNFA = N.anzahl()>0 ? min(N, FA) : maxGesamtDauer;
			long maxVSE = V.anzahl()>0 ? max(V, SE) : 0;
			
			wert[GP] = wert[SA] - wert[FA];
			wert[FP] = minNFA - wert[FE];
			wert[UP] = Math.max(minNFA - maxVSE - wert[D], 0);
			wert[FRP]= wert[SA] - maxVSE;
		}
		
//		System.out.println(this);
		
	}
	
	public Menge<K> kritischeVorg�nge() {
		Menge<K> m = new Menge<K>();
		for (K ae : knoten())
			if ((ae instanceof Vorgang) & (getWert(ae)[FA] == getWert(ae)[SA]))
				m.hinzuf�gen(ae);
		return m;
	}
	
	public String toString() {
		// Ein NetzPlan ist ein Bew.Graph ist ein Gebilde aus ...
		String s = "(";
		
		// ... einer Menge von bewerteten Knoten ...
		s += "{";
		int i = 0;
		for (K ae : knoten()) {
			s += i++>0 ? "," : "";
			s += "(" + ae + "," + print(getWert(ae)) + ")\n";
		}
		
		return s += ")";
	}
	
	private String print(long[] feld) {
		String s = "<";
		int i = 0;
		for (long l : feld)
			s += (i++>0 ? "," : "") + l;
		return s += ">";
	}
	
}
