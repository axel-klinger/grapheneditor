package informatik.strukturen.graphen.wege;

/**
 * @author Axel
 */
public abstract class Operator{

//	protected static Object NULL;
//	protected static Object EINS;
//	protected static Object HÜLLE;

	public abstract Object vereinigung(Object o1, Object o2);
	public abstract Object kettung(Object o1, Object o2);
	
	public abstract Object hülle(Object o); // { return HÜLLE; }
	public abstract Object getNull(); // 	  { return NULL;  }
	public abstract Object getEins(); // 	  {	return EINS;  }
	
}