
package mathematik;

public class Vektor {

	private double[] werte;

	public Vektor(int anzahl) {
		werte = new double[anzahl];
	}

	public Vektor(double[] werte) {
		this.werte = werte;
	}
}
