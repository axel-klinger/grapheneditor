package informatik.strukturen.graphen;

import informatik.strukturen.Menge;
import informatik.strukturen.Relation;

/**
 * <P><B>NPartiterGraph</B> <I>(engl. n-partite graph)</I>: 
 * Ein n-partiter Graph beschreibt die Beziehungen zwischen 
 * den Elementen n disjunkter Knotenmengen.</P>
 * 
 * 
 * <P>Schlichter Graph G = (M;R)
 * Bipartiter Graph BG = (M1,M2;R12,R21)
 * NPartiter Graph  NG = (M<M>;R<R>) mit I<R> IN R<R> KOMPLEMENT</P>
 *  
 * 
 * @author klinger
 *
 */
public class NPartiterGraph {

	public Menge<Menge> mengen;
	public Menge<Relation> relationen;

	public Relation<Menge,Menge> metaRelation; 

	public NPartiterGraph() {
		mengen = new Menge<Menge>();
		relationen = new Menge<Relation>();
		metaRelation = new Relation<Menge,Menge>();
	}
	
	public void hinzufügen(Menge M) {
		mengen.hinzufügen(M);
	}
	
	public void hinzufügen(Menge M, Menge N, Relation R) {
		mengen.hinzufügen(M);
		mengen.hinzufügen(N);
		relationen.hinzufügen(R);
		metaRelation.hinzufügen(M,N);
	}
	
	public String toString() {
		return "(" + mengen + ";" + relationen + ")";
	}
}
