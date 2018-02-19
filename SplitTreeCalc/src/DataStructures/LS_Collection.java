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
		for(int i = 0; i < dimensionSize; i++) { LS.get(i).sort(i); }
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
		LS_Collection CLS = new LS_Collection(pointSet, dimensionSize);

		// set crosspointers between LS and CLSi
		for(int i = 0; i < dimensionSize; i++) {
			DoublyLinkedListIterator LS_iter = LS.get(i).iterator();
			DoublyLinkedListIterator CLS_iter = CLS.getLSi(i).iterator();

			while(LS_iter.hasNext()) {
				PointNode p1 = LS_iter.next();
				PointNode p2 = CLS_iter.next();
				
				p1.setCrossPointersCLS(p2.getCrossPointers());
				p2.setCrossPointersCLS(p1.getCrossPointers());
			}
		}
		return CLS;
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
	
	
