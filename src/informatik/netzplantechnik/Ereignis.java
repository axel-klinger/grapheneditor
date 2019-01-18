package informatik.netzplantechnik;

import informatik.netzplantechnik.zeitplanung.ZeitPunkt;


public class Ereignis extends AblaufElement {


    public ZeitPunkt zeitPunkt;

    
	private static int z�hler = 0;

    private Object name;
    
    public void setName(String beschreibung) {
    	this.name = beschreibung;
    }
    public Object getName() {
    	return name;
    }

	public Ereignis() {
		super("e" + z�hler++);
		name = ID;
	}
		
	public Ereignis(Object name) {
		super("e" + z�hler++);
		this.name = name;
	}
	
	public Ereignis(String id, Object name) {
		super(id);
		this.name = name;
		if (id.matches("e(\\d+)")) {
			id = id.replaceAll("e", "");
			z�hler = Math.max(z�hler, Integer.parseInt(id)) + 1;
		}
			
	}
	
	public String toString() {
		return ID + ";" + name;
	}
	
	
	// <ID>,[ ]<Name>,[ ]<Dauer>
    public static Ereignis fromString(String s) {
    	Ereignis z = new Ereignis();
		String[] attribute = s.split(";");
		
    	if (attribute.length != 2) 	return z;
    	for (String t : attribute)	t.trim();
    	
		z.ID = attribute[0];
		z.name = attribute[1];
    	return z;
    }

}
