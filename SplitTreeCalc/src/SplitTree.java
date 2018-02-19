import java.util.ArrayList;
import java.util.LinkedList;

import DataStructures.DoublyLinkedListIterator;
import DataStructures.HyperRectangle;
import DataStructures.LS_Collection;
import DataStructures.PointNode;
import DataStructures.TreeNode;

public class SplitTree {
	
	LinkedList<double[]> S;
	LinkedList<double[]> Ro;
	LinkedList<double[]> Ru;
	LS_Collection CLS;
	LS_Collection LS;
	int size;
	
	TreeNode uNode;
	
	//public void FastSplitTree(LinkedList<Point> S, HyperRectangle R) {} 
	
	private void partialSplitTree(LinkedList<double[]> _S, LinkedList<double[]> _R, LS_Collection _LS) {
		// Step 1: Make copies CLSi of lists LSi
		S = _S;
		Ro = _R;
		LS = _LS;
		CLS = _LS.clone();
		size = _S.size();
		
		// Create a node u, which will be the root of the final partial split tree
		uNode = new TreeNode(_S, Ro, _LS);
		
		step2();
	}
	
	private void step2() {
		if(size <= S.size()) {
			DoublyLinkedListIterator iter = null;
			
			// probably only need to do one dimension since we are accessing the crosspointers for CLSi
			for(int i = 0; i < LS.getDimensionSize(); i++) {
				iter = LS.getLSi(i).iterator();
				
				while(iter.hasNext()) {
					ArrayList<PointNode> crossPointers = iter.next().getCrossPointersCLS();
					// for(PointNode p : crossPointers) { p.addPointerToNode(u); }
				}
			}
			
			step6();
		} else {
			step3();
		}
	}
	
	private void step3() {
		HyperRectangle rectangle = new HyperRectangle(LS, Ro);
		Ru = rectangle.getR();
		
		int xi = rectangle.getHyperplaneIndex();
	}
	
	private void step3_procedure(int xi, double h) {
		PointNode p = LS.getLSi(xi).getFirst();
		PointNode p_ = p.getNext();
		
		PointNode q = LS.getLSi(xi).getLast();
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
		} else {
			step5();
		}
	}
	
	private void step4(int xi, double h) {
		TreeNode vNode = new TreeNode();
		TreeNode wNode = new TreeNode();
		
		uNode.setLeftChild(vNode);
		uNode.setRightChild(wNode);
		
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
	}
	
	private void step5() {
		
	}
	
	private void step6() {
		
	}
	//private void SplitTreeOne(LinkedList<Point> S, HyperRectangle R, Collection_LS LS) {
		// if |S| == 1 }
}
