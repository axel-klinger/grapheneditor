package temp;

import grapheneditor.darstellung.figuren.Ellipse;
import grapheneditor.darstellung.figuren.KnotenForm;

import java.awt.Color;

public class MarkeBoolean {

	public KnotenForm form;
	public Color farbe;
	public int gr��e;
	
	public MarkeBoolean() {
		farbe = Color.BLACK;
		gr��e = 10;
		form = new Ellipse(0, 0, gr��e, gr��e);
	}
	
	public MarkeBoolean(Color farbe, int durchmesser) {
		this.farbe = farbe;
		this.gr��e = durchmesser;
		form = new Ellipse(0, 0, gr��e, gr��e);
	}
	
	
}
