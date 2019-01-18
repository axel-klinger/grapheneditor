package test;

public class CompilerTest {
	public static void main(String[] args) {

/*		try {
			//Compiler.compileClass(Class.forName("Person"));
			//Compiler.compileClasses("Person");
		} catch (Exception e) {
			e.printStackTrace();
		}	*/

		String klasse = "Bauklotz";

		Runtime R = Runtime.getRuntime();
		try {
			R.exec("javac D:\\" + klasse + ".java");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Person p = new Person();
		p.setVorname("Axel");
		p.setNachname("Klinger");
		p.setEmailDienstlich("klinger@bauinf.uni-hannover.de");
		sop(p.toString());

		Object p2 = null;
		try {
			p2 = Class.forName("Person").newInstance();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sop(p2.toString());

		//////////////////////////////////////////////////////////////
		// Ansatzpunkte, um auf den richtigen Typ zu kommen:				//
		// - Class																									//
		// - ClassLoader ? ? ?																			//
		// - java.lang.reflection																		//
		//////////////////////////////////////////////////////////////

		Class typPerson = p.getClass();
		sop(typPerson.getName());

		

/*		p2.setVorname("Donald");
		p2.setNachname("Duck");
		sop(p2.toString());	*/

	}

	private static void sop(String s) {	System.out.println(s);	}
}
