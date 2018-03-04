package DataStructures;

import java.util.Arrays;
import java.util.LinkedList;

public class HyperRectangle {
	private LinkedList<double[]> Ro; // Ro(u) := R 		Rectangle containing the bounding box
	private LinkedList<double[]> R; // R(u) := R(Su)		Bounding box
	
	private int hyperplane_index;
	private double hyperplaneSplitPoint;
	
	private LinkedList<double[]> vertices;
	
	public HyperRectangle(LS_Collection _LS, LinkedList<double[]> _Ro) {
		this.Ro = _Ro;
		this.R = new LinkedList<double[]>();
		this.vertices = new LinkedList<double[]>();
		
		computeBoundingBox(_LS);
	}
	
	private void computeBoundingBox(LS_Collection LS) {
		int dimensions = LS.getDimensionSize();
		int _hyperplane_index = 0;
		double max_range = -1;
		
		for(int i = 0; i < dimensions; i ++) {
			DoublyLinkedList LSi = LS.getLSi(i);
			
			// add vertex of the hyper rectangle to list of vertices
			vertices.add(LSi.getFirst().getCoordinates().clone());
			vertices.add(LSi.getLast().getCoordinates().clone());
			
			double _min = LSi.getFirst().getCoordinateValueAt(i);
			double _max = LSi.getLast().getCoordinateValueAt(i);
			
			// Using point here as a range object
			R.add(new double[] {_min, _max});
			
			// Find Lmax(R(u))
			if(_max - _min > max_range) { max_range = _max - _min; hyperplaneSplitPoint=(_min + _max) / 2.0; _hyperplane_index = i; }
		}
		this.hyperplane_index = _hyperplane_index;
	}
	
	/*
	 *	Return the length of the max dimensions of the hyperrectangle 
	 */
	public double getLmax() {
		double[] l = R.get(hyperplane_index);
		return l[1] - l[0];
	}
	
	public int getDimension() { return R.getFirst().length; }
	
	public int getHyperplaneIndex() { return this.hyperplane_index; }
	
	public double getHyperplanePoint() { return hyperplaneSplitPoint; }
	
	public LinkedList<double[]> getRo() { return Ro; }
	
	public LinkedList<double[]> getR() { return R; }
	
	public LinkedList<double[]> getVertices() { return vertices; }
	
	public void print() {
		System.out.println("Ro(u)" + Arrays.toString(Ro.toArray()));
		System.out.println("R(u)" + Arrays.toString(R.toArray()));
	}
}
