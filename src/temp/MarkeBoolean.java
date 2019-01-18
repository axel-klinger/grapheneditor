package temp;

import grapheneditor.darstellung.figuren.Ellipse;
import grapheneditor.darstellung.figuren.KnotenForm;

import java.awt.Color;

public class MarkeBoolean {

	public KnotenForm form;
	public Color farbe;
	public int größe;
	
	public MarkeBoolean() {
		farbe = Color.BLACK;
		größe = 10;
		form = new Ellipse(0, 0, größe, größe);
	}
	
	public MarkeBoolean(Color farbe, int durchmesser) {
		this.farbe = farbe;
		this.größe = durchmesser;
		form = new Ellipse(0, 0, größe, größe);
	}
	
	
}
