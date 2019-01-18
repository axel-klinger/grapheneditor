package test;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.Vector;

//import grapheneditor.darstellung.figuren.Ellipse;
//import grapheneditor.darstellung.figuren.NEck;
import grapheneditor.darstellung.figuren.Pfeil;

public class PathIteratorTest {

	public static void main(String args[]) {
		Pfeil p = new Pfeil(20,50, 100, 50);
//		NEck p = new NEck(20, 20, 100, 100, 4);
//		Ellipse p = new Ellipse(20, 20, 100, 100);

		Vector<Double> poly = new Vector<Double>();
		int ecken = 0;
//		double[] poly = new double[];
		
		double[] f = new double[6];
		PathIterator pi = p.getPathIterator(new AffineTransform());
		while(!pi.isDone()) {
			int i = pi.currentSegment(f);
			System.out.println(i + " " + print(f));
			if (i == 0 || i == 1) {
				poly.add(f[0]);
				poly.add(f[1]);
			}
			pi.next();
			ecken++;
		}
		
		System.out.println(poly);

//		
//		System.out.println(PathIterator.SEG_CLOSE + " " 
//				+ PathIterator.SEG_LINETO + " "
//				+ PathIterator.SEG_MOVETO);
	}
	
	static String print(double[] f) {
		String s = "";
		for (double fi : f)
			s += fi + ",";
		return s;
	}
}
