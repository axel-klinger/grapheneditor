package temp;

import grapheneditor.KnotenElement;
import grapheneditor.KnotenAnsicht;
import grapheneditor.darstellung.figuren.Ellipse;
import grapheneditor.darstellung.figuren.KnotenForm;
import grapheneditor.darstellung.figuren.NEck;
import grapheneditor.darstellung.figuren.Rechteck;

import informatik.strukturen.Menge;


public class KnotenMenge extends Menge<KnotenAnsicht> {

	private static final long serialVersionUID = 1L;
	
	String name;
	Class typ;
	String form;
	int knotenBreite = 20;
	int knotenHöhe   = 20;
	Menge<KnotenAnsicht> elemente;
	
	public KnotenMenge(String name, String typ, String form) {
		this(new Menge<KnotenAnsicht>(), name, typ, form);
	}
	
	public KnotenMenge(Menge<KnotenAnsicht> menge, String name, String typ, String form) {
		elemente = menge;
		this.name = name;
		this.form = form;
		try {
			this.typ =  Class.forName(typ);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean hinzufügen(KnotenElement e, int x, int y) {
		if (!(e.getClass().equals(typ.getClass())))
			return false;
		return super.hinzufügen(new KnotenAnsicht(e, string2form(form, x, y, knotenBreite, knotenHöhe)));
	}
	
	// Hilfsfunktion für die Knotenformen
    private static KnotenForm string2form(String s, double x, double y, int b, int h) {
    	KnotenForm kf = null;
    	
    	if (s.equals("Ellipse"))
    		return new Ellipse(x, y, b, h);
    	
    	if (s.equals("Rechteck"))
    		return new Rechteck(x, y, b, h);

    	if (s.equals("Dreieck"))
    		return new NEck(x, y, b, h, 3);
    	
    	if (s.equals("Viereck"))
    		return new NEck(x, y, b, h, 4);
    	
    	if (s.equals("Fünfeck"))
    		return new NEck(x, y, b, h, 5);
    	
    	if (s.equals("Sechseck"))
    		return new NEck(x, y, b, h, 6);
    	
    	if (s.equals("Siebeneck"))
    		return new NEck(x, y, b, h, 7);
    	
    	if (s.equals("Achteck"))
    		return new NEck(x, y, b, h, 8);
    	
    	return kf;
    }

}
