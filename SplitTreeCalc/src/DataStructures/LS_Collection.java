package DataStructures;

import java.util.ArrayList;
import java.util.LinkedList;

public class LS_Collection {
	private LinkedList<DoublyLinkedList> LS;	// LSi
	private LinkedList<double[]> pointSet;
	private int n;						// |pointSet|
	private int dimensionSize;
	
	public LS_Collection(LinkedList<double []> _pointSet, int _dimension) {
		LS = new LinkedList<DoublyLinkedList>();
		pointSet = _pointSet;
		n = pointSet.size();
		dimensionSize = _dimension;
		
		DoublyLinkedList temp = new DoublyLinkedList(dimensionSize);
		LinkedList<DoublyLinkedListIterator> iterators = new LinkedList<DoublyLinkedListIterator>();
		
		// add points to LS
		for(double[] point : pointSet) { temp.add(point); }
		
		// set LSi's, and LSi iterators
		for(int i=0; i < dimensionSize; i++) {
			LS.add(temp.clone());
			iterators.add(LS.get(i).iterator());
		}
		
		DoublyLinkedListIterator iter = iterators.getFirst();
		
		// iterate pointset, and set cross pointers
		while(iter.hasNext()) {
			
			// cross pointers list
			ArrayList<PointNode> pointers = new ArrayList<PointNode>(); 
			
			for(DoublyLinkedListIterator dlli : iterators) {
				
				// add point from each dimension to pointers list
				pointers.add(dlli.next());
			}
			
			// for each dimension set cross pointers list
			for(PointNode pNode : pointers) { pNode.setCrossPointers(pointers); }
		}
		
		// sort each LSi by dimension
		for(int i = 0; i < dimensionSize; i++) { LS.get(i).sort(i); LS.get(i).setDimensionRep(i); }
	}
	
	public LS_Collection(LinkedList<DoublyLinkedList> _LS, LinkedList<double[]> _pointSet, int _dimension) {
		LS = _LS;
		pointSet = _pointSet;
		n = pointSet.size();
		dimensionSize = _dimension;
	}
	
	public DoublyLinkedList getLSi(int i) { return LS.get(i); }
	
	public LinkedList<DoublyLinkedList> getLS() { return LS; }
	
	public int getDimensionSize() { return this.dimensionSize; }
	
	// Given the dimension of the hyperplane
	public void splitCollection(int hyperplaneIndex) {
			
	}
		
	// Remove 
	public void remove() {
			
	}
	
	public LS_Collection clone() {

		LinkedList<DoublyLinkedList> _LS = new LinkedList<DoublyLinkedList>();
		
		// create copy of LSi	O(dn)
		// and create (partial) cross pointers between LS and CLS
		// partial as in LSi only has a pointer to CLSj ONLY where i==j
		for(int i = 0; i < dimensionSize; i++) {
			try {
				_LS.add(LS.get(i).cloneReference());
			} catch (Exception e) { e.printStackTrace(); }
		}

		// set cross pointers between CLSi's	O(2dn)
		for(int i = 0; i < dimensionSize; i++) {
			_LS.get(i).loadCrossPointersCLS_init();
		}
		
		// At this point we have 2 collections LS and CLS
		// (1) They have internal cross pointers between each LSi, where 1 >= i >= dimension
		// (2) Each point in the LS has one pointer to its node in CLS (and vice versa)
		
		// connect the remaining cross pointers between LS and CLS
		for(int i = 0; i < dimensionSize; i++) {
			_LS.get(i).loadCrossPointersCLS();
			LS.get(i).loadCrossPointersCLS();
		}
		
		// pointSet??
		return new LS_Collection(_LS, pointSet, dimensionSize);
	}
	
	public String toString() {
		String out = "{ ";
		for(int i = 0; i < dimensionSize; i++) { 
			out += (i == dimensionSize - 1) ? LS.get(i).toString() : LS.get(i).toString() + ",\n"; 
		}
		return out + " }";
	}
	
	public void print() {
		System.out.println(toString());
	}
}
	
	
