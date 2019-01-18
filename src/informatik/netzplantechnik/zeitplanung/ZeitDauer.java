package informatik.netzplantechnik.zeitplanung;

public class ZeitDauer extends ZeitWert{
	
	public ZeitDauer() {
		wert = 0;
		zustand = GEPLANT;
	}
	
	public ZeitDauer(long ms) {
		wert = ms;
	}
	
	public void setDauer(long ms) {
		wert = ms;
	}
	
	public long getDauer() {
		return wert;
	}
}
