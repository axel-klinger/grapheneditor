package informatik.strukturen.graphen.wege;

/**
 * @author Axel
 */
public abstract class Operator{

//	protected static Object NULL;
//	protected static Object EINS;
//	protected static Object H�LLE;

	public abstract Object vereinigung(Object o1, Object o2);
	public abstract Object kettung(Object o1, Object o2);
	
	public abstract Object h�lle(Object o); // { return H�LLE; }
	public abstract Object getNull(); // 	  { return NULL;  }
	public abstract Object getEins(); // 	  {	return EINS;  }
	
}