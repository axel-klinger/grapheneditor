package grapheneditor.darstellung;

import grapheneditor.KnotenAnsicht;
import informatik.strukturen.Menge;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class Gravitationsfeld {
	
	private	HashMap<KnotenAnsicht,Point2D> 	kraftvektoren;
	private HashMap<KnotenAnsicht,Point2D> 	festpositionen;
	public  Menge<KnotenAnsicht>			spannungsKnoten;
	private int b = 50; //Einflussbereich n
	private double mue = 0.3;
	
	public Gravitationsfeld(){
		kraftvektoren   = new HashMap<KnotenAnsicht,Point2D>();
		festpositionen  = new HashMap<KnotenAnsicht,Point2D>();
		spannungsKnoten = new Menge<KnotenAnsicht>();
	}
	
	public boolean knotenHinzufügen(KnotenAnsicht k){
		if(spannungsKnoten.enthält(k))return false;
		kraftvektoren.put(k,new Point2D.Double());
		festpositionen.put(k,k.form.getPosition());
		spannungsKnoten.hinzufügen(k);
		return true;
	}
	
	public void knotenEntfernen(KnotenAnsicht k){
		kraftvektoren.remove(k);
		festpositionen.remove(k);
		spannungsKnoten.entfernen(k);
	}
	
	public void release(){
		for(KnotenAnsicht k: spannungsKnoten){
			festpositionen.get(k).setLocation(k.form.x,k.form.y);
			kraftvektoren.get(k).setLocation(0,0);
		}
	}
	
	public Point2D getKraftvektor(KnotenAnsicht k){
		return kraftvektoren.get(k);
	}
	
	public void berechneKraft(KnotenAnsicht n) {
		double x = 0.0;
		double y = 0.0;
		for (KnotenAnsicht k : spannungsKnoten) {
			Point2D f = berechneKraft(k, n);
			x += f.getX();
			y += f.getY();
		}
		Point2D f = berechneZug(n);
		x += f.getX();
		y += f.getY();
		kraftvektoren.get(n).setLocation(x, y);
	}

	public Point2D berechneKraft(KnotenAnsicht n, KnotenAnsicht k) {
		if (n.equals(k))
			return new Point2D.Double(0.0, 0.0);
		double dx = 0;
		double dy = 0;
		Rectangle2D bounds = n.form.getBounds2D();
		Point2D p1, p2;
		double zuschlag = 2;
		if (k.form.intersects(bounds)) {
			p1 = n.form.getPosition();
			p2 = k.form.getPosition();
			zuschlag += p1.distance(n.form.schnittPunkt(p2))
					+ p2.distance(k.form.schnittPunkt(p1));
		} else {
			p1 = n.form.schnittPunkt(k.form.getPosition());
			p2 = k.form.schnittPunkt(n.form.getPosition());
		}
		double d = p1.distance(p2);
		if (d > b + zuschlag)
			return new Point2D.Double(0.0, 0.0);
		double f = b + zuschlag - d;
		f = f / d;
		dx += (p2.getX() - p1.getX()) * f;
		dy += (p2.getY() - p1.getY()) * f;
		return new Point2D.Double(dx, dy);
	}

	public Point2D berechneZug(KnotenAnsicht k) {
		Point2D p = festpositionen.get(k);
		double dx = (p.getX()-k.form.x)*mue;
		double dy = (p.getY()-k.form.y)*mue;
		return new Point2D.Double(dx,dy);
	}

}
