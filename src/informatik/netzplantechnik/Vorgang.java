package informatik.netzplantechnik;

/**
 * AblaufElement das ein bestimmtes Geschehen beschreibt. Hierzu gehört auch,
 * dass Anfang und Ende definiert sind.
 * 
 * @author klinger
 */
public class Vorgang extends AblaufElement {
	
	public Object name;
    
    public long dauer; // in millisekunden?
    
    public long frühesterBeginn;
    public long spätesterBeginn;
    public long frühestesEnde;
    public long spätestesEnde;
    
    public long gesamtePufferZeit;
    public long freiePufferZeit;
    public long freieRückwärtsPufferZeit;
    public long unabhängigePufferZeit;
    
	private static int zähler = 0;

	public Vorgang() {
		super("v" + zähler++);
		name = ID;
	}
	
	
    public Vorgang(Object name) {
		super("v" + zähler++);
    	this.name = name;
    }
    
    public Vorgang(Object name, long dauer) {
		super("v" + zähler++);
    	this.name = name;
    	this.dauer = dauer;
    }
    

    public Vorgang(String id, String name) {
    	super(id);
    	this.name = name;
		if (id.matches("v(\\d+)")) {
			id = id.replaceAll("v", "");
			zähler = Math.max(zähler, Integer.parseInt(id)) + 1;
		}

    }
    
    public String toString() {
    	return ID + ";" + name + ";" + dauer;
    }
    
	// <ID>,[ ]<Name>,[ ]<Dauer>
    public static Vorgang fromString(String s) {
    	Vorgang a = new Vorgang();
		String[] attribute = s.split(";");
		
    	if (attribute.length != 3) 	return a;
    	for (String t : attribute)	t.trim();
    	
		a.ID = attribute[0];
		a.name = attribute[1];
		a.dauer = Long.parseLong(attribute[2]);
    	return a;
    }
    


}
