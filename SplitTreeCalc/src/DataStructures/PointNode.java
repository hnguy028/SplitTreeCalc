package DataStructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class PointNode{
	
	private DoublyLinkedList doublyLinkedList;
	
	private double[] coords;
	private int dimensions;
	
	private ArrayList<PointNode> crossPointersLS;
	private ArrayList<PointNode> crossPointersCLS;
	
	private PointNode next;
	private PointNode prev;
	
	public PointNode(DoublyLinkedList dll, double[] _coords) {
		
		this.doublyLinkedList = dll;
		
		this.coords = _coords;
		this.dimensions = this.coords.length;
		
		this.crossPointersLS = new ArrayList<PointNode>(dimensions);
		this.crossPointersCLS = new ArrayList<PointNode>(dimensions);
		
		this.next = null;
		this.prev = null;
	}
	
	public void setCrossPointer(PointNode pointer, int dimension) { crossPointersLS.set(dimension, pointer); }
	public void setCrossPointers(ArrayList<PointNode> pointers) { crossPointersLS = pointers; }
	public ArrayList<PointNode> getCrossPointers() { return crossPointersLS; }
	
	public void setCrossPointersCLS(ArrayList<PointNode> pointers) { crossPointersCLS = pointers; }
	public ArrayList<PointNode> getCrossPointersCLS() { return crossPointersCLS; }
	
	//public int getIndexAt(int dimension) { return LS_indices[dimension]; }
	
	public PointNode getNext() { return next; }
	public void setNext(PointNode _next) { next = _next; }
	public void setNext_Prev(PointNode _next) { next = _next; _next.prev = this; }
	
	public PointNode getPrev() { return this.prev; }
	public void setPrev(PointNode _prev) { this.prev = _prev; }
	public void setPrev_Next(PointNode _prev) { prev = _prev; _prev.next = this; }
	
	public void removeCrossPointers_LSi(int excludeDimension) {
		for(int i = 0; i < crossPointersLS.size(); i++) {
			if(i != excludeDimension) { crossPointersLS.get(i).remove(); }
		}
	}
	
	public void removeCrossPointers_CLSi() {
		for(int i = 0; i < crossPointersCLS.size(); i++) {
			crossPointersCLS.get(i).remove();
		}
	}
	
	public void remove() {
		if(prev != null) { prev.setNext(next); }
		if(next != null) { next.setPrev(prev); }
		next = null; prev = null;
		doublyLinkedList.decrementSize();
	}
	
	public double[] getCoordinates() { return coords; }
	
	public int getDimensions() { return dimensions; }
	
	public double getCoordinateValueAt(int _dimension) { return coords[_dimension]; }
	
	public int compareTo(PointNode _point, int _dimension) {
		if(_dimension >= _point.getDimensions() || _dimension >= dimensions) { throw new IndexOutOfBoundsException(); }
		
		if(coords[_dimension] == _point.getCoordinateValueAt(_dimension)) { return 0; }
		if(coords[_dimension] > _point.getCoordinateValueAt(_dimension)) { return 1; }
		return -1;
	}
	
	public String toString() { return Arrays.toString(coords).replace("[", "(").replace("]", ")"); }
	
	public void printString() { System.out.println(Arrays.toString(coords).replace("[", "(").replace("]", ")")); }
}
