package informatik.netzplantechnik;


public class AblaufElement extends Element{
	
	public AblaufElement(String id) {
		ID = id;
	}
	
    public String toString() {
    	return ID;
    }
    
    public boolean equals(Object o) {
    	if (!( o instanceof AblaufElement))
    		return false;
    	AblaufElement p = (AblaufElement) o;
    	return this.ID.equals(p.ID);
    }
    
    public int hashCode() {
    	return ID.hashCode();
    }

}
