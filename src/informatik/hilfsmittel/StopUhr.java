package informatik.hilfsmittel;

import java.util.Date;

/**
 * Die Stopuhr ist ein kleines Hilfsmittel zur Zeitmessung von Methoden.
 * Sie ist nicht instanzierbar und damit nicht mehrfach Verwendbar, aber 
 * praktisch für einzelne Geschwindigkeitstests.
 * Typische Verwendung:
 *  - start, stop, print dauer ... oder start, status, status, status ...
 * @author klinger
 *
 */
public class StopUhr {

	private static Date start;
	private static Date stop;
	
	public static void start() {
		start = new Date();
	}
	
	public static void stop() {
		stop = new Date();
	}
	
	public static long dauer() {
		if (start == null)	return 0;
		if (stop  == null)	stop();
		long dauer = stop.getTime()-start.getTime();
		start = null; stop = null;
		return dauer;
	}
	
	public static void status(String s) {
		stop();	
		System.out.println(s + ": " + dauer() + "ms");
		start();
	}
}
