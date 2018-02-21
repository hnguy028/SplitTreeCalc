import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.event.DocumentListener;

import DataStructures.DoublyLinkedListIterator;
import DataStructures.HyperRectangle;
import DataStructures.LS_Collection;
import DataStructures.PointNode;
import DataStructures.TreeNode;

public class SplitTree {
	
	LinkedList<double[]> Su;
	LinkedList<double[]> Ro;
	LinkedList<double[]> Ru;
	LS_Collection CLS;
	LS_Collection LSu;
	int size;
	
	TreeNode uNode;
	
	//public void FastSplitTree(LinkedList<Point> S, HyperRectangle R) {} 
	
	// private void partialSplitTree(LinkedList<double[]> _S, LinkedList<double[]> _R, LS_Collection _LS) {
	private TreeNode partialSplitTree(LinkedList<double[]> _R, LS_Collection _LS) {
//		Su = _S;
		Ro = _R;
		LSu = _LS;
		
		// Step 1: Make copies CLSi of lists LSi
		CLS = _LS.clone();
		size = _LS.getLSi(0).size();
		
		// Create a node u, which will be the root of the final partial split tree
		uNode = null;//new TreeNode(null, Ro, _LS);
		
		step2();
		
		return uNode;
	}
	
	private void step2() {
		if(size <= Su.size() / 2.0) {
			DoublyLinkedListIterator iter = null;
			
			// probably only need to do one dimension since we are accessing the crosspointers for CLSi
			for(int i = 0; i < LSu.getDimensionSize(); i++) {
				iter = LSu.getLSi(i).iterator();
				
				while(iter.hasNext()) {
					ArrayList<PointNode> crossPointers = iter.next().getCrossPointersCLS();
					// for(PointNode p : crossPointers) { p.addPointerToNode(u); }
				}
			}
			
			step6();
		} else { // size > Su.size / 2.0
			step3();
		}
	}
	
	private void step3() {
		// Compute bounding box
		HyperRectangle rectangle = new HyperRectangle(LSu, Ro);
		
		// set R(u) = R(Su)
		Ru = rectangle.getR();
		
		// find index i such that Lmax(R(u)) = Li(R(u))
		int xi = rectangle.getHyperplaneIndex();
		double h = rectangle.getHyperplanePoint();
		
		// Run the described procedure
		step3_procedure(xi, h);
	}
	
	private void step3_procedure(int xi, double h) {
		PointNode p = LSu.getLSi(xi).getFirst();
		PointNode p_ = p.getNext();
		
		PointNode q = LSu.getLSi(xi).getLast();
		PointNode q_ = q.getPrev();
		
		int size_ = 1;
		
		while(p_.getCoordinateValueAt(xi) <= h && q_.getCoordinateValueAt(xi) >= h) {
			p = p_;
			p_ = p_.getNext();
			
			q = q_;
			q_ = q_.getNext();
			
			size_++;
		}
		
		if(p_.getCoordinateValueAt(xi) > h) {
			step4(xi, h);
		} else { // p_.getCoordinateValueAt(xi) < h
			step5();
		}
	}
	
	private void step4(int xi, double h) {
		TreeNode vNode = new TreeNode();
		TreeNode wNode = new TreeNode();
		
//		uNode.setLeftChild(vNode);
//		uNode.setRightChild(wNode);
		
		LinkedList<double[]> R1 = new LinkedList<double[]>();
		LinkedList<double[]> R2 = new LinkedList<double[]>();
		
		for(int i = 0; i < Ru.size(); i++) {
			if(xi == i) {
				R1.add(new double[] {Ru.get(i)[0], h});
				R2.add(new double[] {h, Ru.get(i)[1]});
			} else {
				R1.add(Ru.get(i));
				R2.add(Ru.get(i));
			}
		}
		
//		vNode.setRo(R1);
//		wNode.setRo(R2);
		
	}
	
	private void step5() {
		
	}
	
	private void step6() {
		
	}
	//private void SplitTreeOne(LinkedList<Point> S, HyperRectangle R, Collection_LS LS) {
		// if |S| == 1 }
}
