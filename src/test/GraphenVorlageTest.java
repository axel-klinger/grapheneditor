package test;

import temp.GraphenVorlage;
import temp.GraphenVorlageEinAusgabe;
import grapheneditor.GraphenModell;

public class GraphenVorlageTest {

	public static void main(String[] args) {
		
		GraphenVorlage gv = GraphenVorlageEinAusgabe.loadXML("../GraphenEditor/Beispiele/Vorlagen/ProzessModell0.xml");
		GraphenModell prozess = gv.neueInstanz();
		
		sop(prozess.getGraph());
	}
	
	public static void sop(Object o) {
		System.out.println(o);
	}
}

