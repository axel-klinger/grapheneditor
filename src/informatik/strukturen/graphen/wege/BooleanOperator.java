package informatik.strukturen.graphen.wege;

/**
 * @author Axel
 */
public abstract class BooleanOperator extends Operator {

	public Object und(Object a, Object b) {
		if (!(a instanceof Boolean) | !(b instanceof Boolean))
			return new Object();
		return new Boolean(((Boolean)a).booleanValue() & ((Boolean)b).booleanValue());
	}

	public Object oder(Object a, Object b) {
		if (!(a instanceof Boolean) | !(b instanceof Boolean))
			return new Object();
		return new Boolean(((Boolean)a).booleanValue() | ((Boolean)b).booleanValue());
	}

	public abstract Object vereinigung(Object o1, Object o2);
	
	public abstract Object kettung(Object o1, Object o2);

    public abstract Object getNull();
}
