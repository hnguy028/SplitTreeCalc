package DataStructures;

import java.util.Comparator;

public class PointNodeComparator implements Comparator<PointNode> {
	private int dimension = 0;
	
	public PointNodeComparator() {
		this.dimension = 0;
	}
	
	public PointNodeComparator(int _dimension) {
		this.dimension = _dimension;
	}
	
	public PointNodeComparator setSortDimension(int _dimension) {
		this.dimension = _dimension;
		return this;
	}

	@Override
	public int compare(PointNode p1, PointNode p2) {
		return p1.compareTo(p2, this.dimension);
	}

}
