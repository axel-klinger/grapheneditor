package grapheneditor;

public class KnotenElement {

	protected String idPr�fix = "K"; // getClass().getSimpleName();
    protected static int z�hler = 0;

	protected String ID;
    public String getID() {	return ID;	}
    
    protected String name;
    public void   setName(String name) 	{	this.name = name;	}
    public String getName() 			{	return name;	}
    
	public KnotenElement() {
		ID = idPr�fix + z�hler++;
		name = ID;
	}
	
    public KnotenElement(String name) {
		this();
    	this.name = name;
    }

    // Beim Einlesen aus einer bestehenden Datei ist die 
    // ID vorgegeben. Der Z�HLER darf diese Nummer nicht 
    // noch mal verwenden und wird deshalb auf max(id+1, Z)
    // gesetzt.
    public KnotenElement(String id, String name) {
    	this.ID = id;
    	this.name = name;
		if (id.matches(idPr�fix + "(\\d+)")) {
			String temp = id;
			temp = temp.replaceAll(idPr�fix, "");
			z�hler = Math.max(z�hler, Integer.parseInt(temp)) + 1;
		}
    }
    
    public String toString() {
    	return /*ID + ";" + */name;
    }
    
    public boolean equals(Object o) {
    	if ( !(o instanceof KnotenElement) )
    		return false;
    	KnotenElement e = (KnotenElement) o;
    	return this.ID.equals(e.ID);
    }

    public void fromString(String s) {
		String[] attribute = s.split(";");
		
    	if (attribute.length < 2) 	return;
    	for (String t : attribute)	t.trim();
    	
		ID = attribute[0];
		name = attribute[1];
    }
}
