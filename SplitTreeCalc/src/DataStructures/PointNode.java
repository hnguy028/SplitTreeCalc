package DataStructures;

import java.util.ArrayList;
import java.util.List;

public class PointNode{
	
	private DoublyLinkedList doublyLinkedList;
	
	private List<Double> coords;
	
	// dimension of the point set
	private int dimensions;
	
	// dimensions of which this point is sorted on in the doublylinkedlist
	private int dimensionSortedOn;
	
	// cross pointers to other doublylinkedlists within LS
	private ArrayList<PointNode> crossPointersLS;
	
	// cross pointer to copy LSi
	private ArrayList<PointNode> crossPointersCLS;
	
	private PointNode next;
	private PointNode prev;
	
	// used in the split tree algorithm
	private TreeNode treeNode;
	
	public PointNode(DoublyLinkedList dll, List<Double> point) {
		
		this.doublyLinkedList = dll;
		
		this.coords = point;
		this.dimensions = this.coords.size();
		this.dimensionSortedOn = -1;
		
		this.crossPointersLS = new ArrayList<PointNode>(dimensions);
		this.crossPointersCLS = new ArrayList<PointNode>(dimensions);
		
		for(int i = 0; i < dimensions; i++) {
			crossPointersLS.add(null);
			crossPointersCLS.add(null);
		}
		
		this.next = null;
		this.prev = null;
	}
	
	public PointNode(DoublyLinkedList dll, PointNode _p, List<Double> _coords, int _sortDim) {
		
		this.doublyLinkedList = dll;
		
		this.coords = _coords;
		this.dimensions = this.coords.size();
		this.dimensionSortedOn = _sortDim;
		
		this.crossPointersLS = new ArrayList<PointNode>(dimensions);
		this.crossPointersCLS = new ArrayList<PointNode>(dimensions);
		
		for(int i = 0; i < dimensions; i++) {
			crossPointersLS.add(null);
			crossPointersCLS.add(null);
		}
		
		this.crossPointersCLS.set(_sortDim, _p);
		_p.setCrossPointersCLS(this, _sortDim);
		
		this.next = null;
		this.prev = null;
	}
	
	public void storeTreeNodePointer(TreeNode node) { treeNode = node; }
	public TreeNode getTreeNode() { return treeNode; }
	
	public void setCrossPointer(PointNode pointer, int dimension) { crossPointersLS.set(dimension, pointer); }
	public void setCrossPointers(ArrayList<PointNode> pointers) { crossPointersLS = pointers; }
	public ArrayList<PointNode> getCrossPointers() { return crossPointersLS; }
	
	public void setCrossPointersCLS(PointNode pointer, int dimension) { crossPointersCLS.set(dimension, pointer); }
	public void setCrossPointersCLS(ArrayList<PointNode> pointers) { crossPointersCLS = pointers; }
	public ArrayList<PointNode> getCrossPointersCLS() { return crossPointersCLS; }
	
	public PointNode getNext() { return next; }
	public void setNext(PointNode _next) { next = _next; }
	public void setNext_Prev(PointNode _next) { next = _next; _next.prev = this; }
	
	public PointNode getPrev() { return this.prev; }
	public void setPrev(PointNode _prev) { this.prev = _prev; }
	public void setPrev_Next(PointNode _prev) { prev = _prev; _prev.next = this; }
	
	/* 
	 * this function traverses it's LS cross pointers to PointNode p' , and then traverses to p's CLS cross pointers 
	 */
	public ArrayList<PointNode> getCopyCrossPointers() {
		ArrayList<PointNode> res = new ArrayList<>(dimensions);
		for(int i = 0; i < dimensions; i++) {
			res.add(crossPointersLS.get(i).crossPointersCLS.get(i));
		}
		return res;
	}
	
	public void removeCrossPointers_LSi() {
		for(int i = 0; i < crossPointersLS.size(); i++) {
			if(i != dimensionSortedOn) {  if(crossPointersLS.get(i) != null) { crossPointersLS.get(i).remove(); } }
		}
	}
	
	public void removeCrossPointers_CLSi() {
		for(int i = 0; i < crossPointersCLS.size(); i++) { 
			if(crossPointersCLS.get(i) != null) { crossPointersCLS.get(i).remove(); } 
		}
	}
	
	// remove this pointnode and pointers to it in LS and CLS
	public void remove() {
		if(doublyLinkedList.getFirst() == this) { doublyLinkedList.incrementHead(); }
		if(doublyLinkedList.getLast() == this) { doublyLinkedList.decrementTail(); }
		
		if(prev != null) { prev.setNext(next); }
		if(next != null) { next.setPrev(prev); }
		
		next = null;
		prev = null;
		
		doublyLinkedList.decrementSize();
		
		// clean up cross pointers to "this" object
		for(PointNode pointNode : crossPointersLS) { if(pointNode != null) pointNode.setCrossPointer(null, dimensionSortedOn); }
		for(PointNode pointNode : crossPointersCLS) { if(pointNode != null) pointNode.setCrossPointersCLS(null, dimensionSortedOn); }
	}
	
	public List<Double> getCoordinates() { return coords; }
	
	public int getDimensions() { return dimensions; }
	
	public int getSortDimension() { return dimensionSortedOn; }
	
	public void setSortDimension(int _dim) { dimensionSortedOn = _dim; }
	
	public double getCoordinateValueAt(int _dimension) { return coords.get(_dimension); }
	
	/*
	 *	Get pointer the pointnode equivalent to "this" in CLS, at the same sorted dimension 
	 */
	public PointNode getOccurrenceInCLS() { return crossPointersCLS.get(dimensionSortedOn); }
	
	/*
	 * Compares 2 point nodes given the dimension
	 * ie p1 = (x1, x2, x3), p2 = (y1, y2, y3), dimension = 2
	 * compare x2 to y2, and if they're equal check the other dimensions 
	 */
	public int compareTo(PointNode _point, int _dimension) {
		if(_dimension >= _point.getDimensions() || _dimension >= dimensions) { throw new IndexOutOfBoundsException(); }
		
		if(coords.get(_dimension) == _point.getCoordinateValueAt(_dimension)) {
			// iterate all other dimension excluding the given dimension to check equivalence
			for(int i = 0; i < dimensions; i++) {
				if(i != _dimension) {
					int comparison = this.compareToStrict(_point, i);
					if(0 != comparison) { return comparison; }
				}
			}
			return 0; 
		}
		if(coords.get(_dimension) > _point.getCoordinateValueAt(_dimension)) { return 1; }
		return -1;
	}
	
	/*
	 *  strictly limit the comparison of two points the the specified dimension
	 *  ie p1 = (x1, x2, x3), p2 = (y1, y2, y3), dimension = 2
	 *  compare x2 to y2
	 */
	private int compareToStrict(PointNode _point, int _dimension) {
		if(_dimension >= _point.getDimensions() || _dimension >= dimensions) { throw new IndexOutOfBoundsException(); }
		
		if(coords.get(_dimension) == _point.getCoordinateValueAt(_dimension)) { return 0;  }
		if(coords.get(_dimension) > _point.getCoordinateValueAt(_dimension)) { return 1; }
		return -1;
	}
	
	public String toString() { return coords.toString().replace("[", "(").replace("]", ")"); }
	
	public void printString() { System.out.println(coords.toString().replace("[", "(").replace("]", ")")); }
}
