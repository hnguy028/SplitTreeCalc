package DataStructures;

import java.util.Iterator;

public class DoublyLinkedListIterator implements Iterator<PointNode>{

	private PointNode current;
	private boolean reverse;
	
	public DoublyLinkedListIterator(PointNode _p) {
		current = _p;
		reverse = false;
	}
	
	public DoublyLinkedListIterator(PointNode _p, boolean _reverse) {
		current = _p;
		reverse = _reverse;
	}
	
	@Override
	public boolean hasNext() {
		return current != null;
	}

	@Override
	public PointNode next() {
		PointNode temp = current;
		
		if (reverse) { current = current.getPrev(); } 
		else { current = current.getNext(); }
		
		return temp;
	}
}
