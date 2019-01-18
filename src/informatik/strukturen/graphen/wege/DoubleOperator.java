package informatik.strukturen.graphen.wege;

/**
 * @author Axel
 */
public abstract class DoubleOperator extends Operator {

	public Object plus(Object a, Object b) {
		if (!(a instanceof Double) | !(b instanceof Double))
			return new Double(Double.NaN);
		return new Double(((Double)a).doubleValue() + ((Double)b).doubleValue());
	}

	public Object minus(Object a, Object b) {
		if (!(a instanceof Double) | !(b instanceof Double))
			return new Double(Double.NaN);
		return new Double(((Double)a).doubleValue() - ((Double)b).doubleValue());
	}

	public Object mal(Object a, Object b) {
		if (!(a instanceof Double) | !(b instanceof Double))
			return new Double(Double.NaN);
		return new Double(((Double)a).doubleValue() * ((Double)b).doubleValue());
	}

	public Object durch(Object a, Object b) {
		if (!(a instanceof Double) | !(b instanceof Double))
			return new Double(Double.NaN);
		return new Double(((Double)a).doubleValue() / ((Double)b).doubleValue());
	}

	public Object min(Object a, Object b) {
		if (!(a instanceof Double) | !(b instanceof Double))
			return new Double(Double.NaN);
		return new Double(Math.min(((Double)a).doubleValue(), ((Double)b).doubleValue()));
	}

	public Object max(Object a, Object b) {
		return new Double(Math.max(((Double)a).doubleValue(), ((Double)b).doubleValue()));
	}

	public abstract Object vereinigung(Object o1, Object o2);
		
	public abstract Object kettung(Object o1, Object o2);

	
}
