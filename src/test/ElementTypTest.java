package test;


import java.io.File;

import temp.typen.ElementTyp;

public class ElementTypTest {

	public static void main(String[] args) {

//		String pfad = "D:\\klinger\\eclipse42\\GraphenEditor\\Beispiele\\Typen\\Aktivit�t.xml";
		String pfad = "G:\\eclipse42\\GraphenEditor\\Beispiele\\Typen\\Aktivit�t.xml";
//		ElementTyp typ = new ElementTyp(new File(pfad), "D:\\klinger\\Java\\");	// \\Beispiele\\Typen\\
		ElementTyp typ = new ElementTyp(new File(pfad), "G:\\eclipse42\\GraphenEditor\\Beispiele\\Typen\\");	// \\Beispiele\\Typen\\
		typ.�bersetzen();
		
		Object o = typ.instanzieren();
		sop(o);
		
	}

	private static void sop(Object o) {
		System.out.println(o.toString());
	}
}
