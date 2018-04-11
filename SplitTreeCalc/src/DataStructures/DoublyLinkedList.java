package DataStructures;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DoublyLinkedList {
	private PointNode head;
	private PointNode tail;
	
	private int size;
	
	private PointNodeComparator comparator;
	
	// dimension of point set
	private int dimensions;
	
	// dimension on which this doublylinkedlist is sorted on
	private int dimensionRep;
	
	// bracket format for output
	private String[] setBrackets = new String[]{"[","]"};
	
	public DoublyLinkedList(int _dimensions) {
		head = null;
		tail = null;
		size = 0;
		comparator = new PointNodeComparator();
		dimensions = _dimensions;
		dimensionRep = -1;
	}
	
	public void add(List<Double> point) {
		PointNode temp = new PointNode(this, point);
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
	
	// private method used for cloning (deep copy) a doublylinkedlist
	private void add_clone(PointNode p, List<Double> coords, int sortDimension) throws Exception {
		
		if(sortDimension < 0) { throw new Exception("sortDimension < 0 : Error"); }
		
		PointNode temp = new PointNode(this, p, coords, sortDimension);
		if(head == null || tail == null) {
			head = temp; 
			tail = temp;
		} else { 
			tail.setNext_Prev(temp);
			tail = temp;
		}
		size++;
	}
	
	// Get node at given index in the doublylinkedlist
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
		
		// find pointnode of at the given index
		PointNode temp = head;
		
		if(index == 0) { head = temp.getNext(); }
		if(index == size-1) {tail = temp.getPrev(); }
		
		for(int i = 0; i < index; i++) { temp = temp.getNext(); }
		
		// set pointers of neighbours to each other
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
	
	public void incrementHead() { head = head.getNext(); }
	public void decrementTail() { tail = tail.getPrev(); }
	
	// set the dimensions this doublylinkedlist is sorted on
	public void setDimensionRep(int _rep) { 
		dimensionRep = _rep;
		
		DoublyLinkedListIterator iter = iterator();
		while(iter.hasNext()) {
			iter.next().setSortDimension(dimensionRep);
		}
	}
	
	public int getDimensionRep() { return dimensionRep; }
	
	public int dimensions() { return dimensions; }
	
	public void decrementSize() { size--; }
	
	public int size() { return size; }
	
	// Sort doublylinkedlist with respect to the given dimension
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
	
	// load doublylinkedlist from a linkedlist of pointnodes
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

	public DoublyLinkedListIterator iterator_reverse() {
		return new DoublyLinkedListIterator(tail, true);
	}
	
	/*
	 * Set cross pointers for LSi, 0 < i <= dimension
	 * 
	 * At this point LS and CLS 
	 */
	public void loadCrossPointersCLS_init() {
		DoublyLinkedListIterator LS_iter = iterator();

		while(LS_iter.hasNext()) {
			PointNode p = LS_iter.next();
			p.setCrossPointers(p.getCrossPointersCLS().get(dimensionRep).getCopyCrossPointers());
		}
	}
	
	public void loadCrossPointersCLS() {
		DoublyLinkedListIterator LS_iter = iterator();

		while(LS_iter.hasNext()) {
			PointNode p = LS_iter.next();
			p.setCrossPointersCLS(p.getCrossPointersCLS().get(dimensionRep).getCrossPointers());
		}
	}
	
	/* 
	 * Clone data structure with references - only used once sorted
	 * Note that only 2 doublylinkedlist will ever have reference to each other
	 */
	public DoublyLinkedList cloneReference() throws Exception {
		
		if(dimensionRep < 0) { throw new Exception("DoublyLinkedList has not been sorted"); }
		
		DoublyLinkedList dll = new DoublyLinkedList(this.dimensions);
		dll.setDimensionRep(dimensionRep);
		
		PointNode temp = head;
		
		while(temp != null) { try { dll.add_clone(temp, temp.getCoordinates(), dimensionRep); } catch(Exception e) {System.out.println( e.getMessage() ); } temp = temp.getNext(); }
		
		return dll;
	}

	// deep copy - clone
	public DoublyLinkedList clone() {
		DoublyLinkedList dll = new DoublyLinkedList(this.dimensions);
		
		PointNode temp = head;
		
		while(temp != null) { dll.add(temp.getCoordinates()); temp = temp.getNext(); }
		
		return dll;
	}
	
	public String toString(String[] brackets) {
		if(brackets != null) { setBrackets = brackets; }
		return toString();
	}
	
	public String toString() {
		String out = setBrackets[0];
		
		PointNode temp = head;
		
		for(int i = 0; i < size; i++) { 
			out += (i == size-1) ? temp.toString() : temp.toString() + ", "; 
			temp = temp.getNext(); 
		}
		
		return out + setBrackets[1];
	}
	
	public void print() { System.out.println(this.toString()); }
	public void printSet() { System.out.println(this.toString(new String[] {"{", "}"})); }
}
