package DataStructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HyperRectangle {
	private LinkedList<List<Double>> Ro; // Ro(u) := R 		Rectangle containing the bounding box
	private LinkedList<List<Double>> R; // R(u) := R(Su)		Bounding box
	
	// dimension of the hyperplane
	private int hyperplane_index;
	
	// split point of the hyperplane
	private double hyperplaneSplitPoint;
	
	private LinkedList<List<Double>> vertices;
	
	public HyperRectangle(LS_Collection _LS, LinkedList<List<Double>> _Ro) {
		this.Ro = _Ro;
		this.R = new LinkedList<List<Double>>();
		this.vertices = new LinkedList<List<Double>>();
		
		computeBoundingBox(_LS);
	}
	
	/* 
	 * Compute hyoerplane and bounding box given LSi
	 */
	private void computeBoundingBox(LS_Collection LS) {
		int dimensions = LS.getDimensionSize();
		int _hyperplane_index = 0;
		double max_range = -1;
		
		for(int i = 0; i < dimensions; i ++) {
			DoublyLinkedList LSi = LS.getLSi(i);
			
			// add vertex of the hyper rectangle to list of vertices
			vertices.add(new ArrayList<Double>(LSi.getFirst().getCoordinates()));
			vertices.add(new ArrayList<Double>(LSi.getLast().getCoordinates()));
			
			double _min = LSi.getFirst().getCoordinateValueAt(i);
			double _max = LSi.getLast().getCoordinateValueAt(i);
			
			R.add(new ArrayList<Double> (Arrays.asList(_min, _max)));
			
			// Find Lmax(R(u))
			if(_max - _min > max_range) { max_range = _max - _min; hyperplaneSplitPoint=(_min + _max) / 2.0; _hyperplane_index = i; }
		}
		this.hyperplane_index = _hyperplane_index;
	}
	
	/*
	 *	Return the length of the longest dimensions of the hyperrectangle 
	 */
	public double getLmax() {
		List<Double> l = R.get(hyperplane_index);
		return l.get(1) - l.get(0);
	}
	
	public int getDimension() { return R.getFirst().size(); }
	
	public int getHyperplaneIndex() { return this.hyperplane_index; }
	
	public double getHyperplanePoint() { return hyperplaneSplitPoint; }
	
	public LinkedList<List<Double>> getRo() { return Ro; }
	
	public LinkedList<List<Double>> getR() { return R; }
	
	public LinkedList<List<Double>> getVertices() { return vertices; }
	
	public void print() {
		System.out.println("Ro(u)" + Arrays.toString(Ro.toArray()));
		System.out.println("R(u)" + Arrays.toString(R.toArray()));
	}
}
