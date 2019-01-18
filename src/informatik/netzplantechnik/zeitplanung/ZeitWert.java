package informatik.netzplantechnik.zeitplanung;

public abstract class ZeitWert {
	
	protected long wert;

	public static final int ERRECHNET = 0;
	public static final int GEGEBEN   = 1;
	public static final int GEPLANT   = 2;
	public static final int IST       = 3;
	
	protected int zustand = 2;
	
}
