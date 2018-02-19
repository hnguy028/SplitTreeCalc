package DataStructures;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import javax.lang.model.type.NullType;

public class DoublyLinkedList {
	private PointNode head;
	private PointNode tail;
	private int size;
	private PointNodeComparator comparator;
	private int dimensions;
	
	public DoublyLinkedList(int _dimensions) {
		head = null;
		tail = null;
		size = 0;
		comparator = new PointNodeComparator();
		dimensions = _dimensions;
	}
	
	public void add(double[] coords) {
		PointNode temp = new PointNode(this, coords);
		if(head == null || tail == null) {
			head = temp; 
			tail = temp;
		} else { 
			tail.setNext_Prev(temp);
			tail = temp;
		}
		size++;
	}
	
	public void add(PointNode p) {
		if(head == null || tail == null) {
			head = p; 
			tail = p;
		} else { 
			tail.setNext_Prev(p);
			tail = p;
		}
		size++;
	}
	
	public void add(double[] coords, int index) throws Exception {
		throw new Exception("Should implement");
	}
	
	public PointNode get(int index) {
		if(index < 0 || size < 1 || index >= size) { throw new ArrayIndexOutOfBoundsException(); }
		
		PointNode temp = head;
		
		for(int i = 0; i < index; i++) { temp = temp.getNext(); }
		
		return temp;
	}
	
	public PointNode getFirst() {
		return head;
	}
	
	public PointNode getLast() {
		return tail;
	}
	
	public void remove(int index) {
		if(index < 0 || size < 1 || index >= size) { throw new IndexOutOfBoundsException(); }
		
		PointNode temp = head;
		
		if(index == 0) { head = temp.getNext(); }
		if(index == size-1) {tail = temp.getPrev(); }
		
		for(int i = 0; i < index; i++) { temp = temp.getNext(); }
		
		PointNode _prev = temp.getPrev();
		PointNode _next = temp.getNext();
		
		if(_prev != null) { _prev.setNext(_next); }
		if(_next != null) { _next.setPrev(_prev); }
		
		this.size--;
	}
	
	public PointNode removeFirst() {
		PointNode temp = null;
		if(head != null) {
			temp = head;
			if(head == tail) { tail = null; }
			if(head.getNext() != null) {
				head = head.getNext();
				head.setPrev(null);
				size--;
			}
		}
		return temp;
	}
	
	public PointNode removeLast() {
		PointNode temp = null;
		if(tail != null) {
			temp = tail;
			if(head == tail) { head = null; }
			if(tail.getPrev() != null) {
				tail = tail.getPrev();
				tail.setNext(null);
				size--;
			}
		}
		return temp;
	}
	
	public int dimensions() { return dimensions; }
	
	public void decrementSize() { size--; }
	
	public int size() { return size; }
	
	public void sort(int dimension) {
		if(dimension > dimensions || dimension < 0) { throw new IndexOutOfBoundsException(); }
		
		if (this.size <= 0) { return; }
		
		LinkedList<PointNode> temp = toLinkedList(); 
		
		Collections.sort(temp, comparator.setSortDimension(dimension));
		
		loadList(temp);
	}
	
	private LinkedList<PointNode> toLinkedList() {
		LinkedList<PointNode> result = new LinkedList<PointNode>();
		PointNode temp = head;
		
		for(int i = 0; i < size; i++) {
			result.add(temp);
			temp = temp.getNext(); 
		}
		return result;
	}
	
	private void loadList(LinkedList<PointNode> l) {
		head = null;
		tail = null;
		size = 0;
		
		for(PointNode p : l) { add(p); }
		
		head.setPrev(null);
		tail.setNext(null);
	}
	
	public DoublyLinkedListIterator iterator() {
		return new DoublyLinkedListIterator(head);
	}
	
	public DoublyLinkedList clone() {
		DoublyLinkedList dll = new DoublyLinkedList(this.dimensions);
		
		PointNode temp = head;
		
		// temp.clone()
		while(temp != null) { dll.add(temp.getCoordinates()); temp = temp.getNext(); }
		
		return dll;
	}
	
	public String toString() {
		String out = "[";
		
		PointNode temp = head;
		
		for(int i = 0; i < size; i++) { 
			out += (i == size-1) ? temp.toString() : temp.toString() + ", "; 
			temp = temp.getNext(); 
		}
		
		return out + "]";
	}
	
	public void print() { System.out.println(this.toString()); }
}
