package DataStructures;

import java.util.ArrayList;
import java.util.LinkedList;

public class TreeNode {
	TreeNode leftChild, rightChild;
	
	// Pointset Su
	DoublyLinkedList Su;
	
	// Rectangle
	LinkedList<double[]> Ro;
	
	// Bounding Rectangle
	LinkedList<double[]> Ru;
	
	// Collection of linkedlists of Su, sorted by dimension 
	LS_Collection LSu;
	LS_Collection CLS;
	
	int size;
	
	public TreeNode() {
		leftChild = null;
		rightChild = null;
	}
	
	// Rectangle Ro, Collection of DoublyLinkedList LSi
	public TreeNode(LinkedList<double[]> _Ro, LS_Collection _LS) {
		leftChild = null;
		rightChild = null;
		
		Su = _LS.getLSi(0).clone();
		Ro = _Ro;
		LSu = _LS;
		size = Su.size();
	}
	
	public TreeNode(LinkedList<double[]> _Ro) {
		leftChild = null;
		rightChild = null; 
		
		Ro = _Ro;
	}
	
	public void partialSplitTree() {
		// Check if required info is set
		if(Ro != null && LSu != null) {
			step1();
		} else {
			throw new NullPointerException("HyperRectangle and/or LSi is empty");
		}
	}
	
	private void step1() {
		// Step 1: Make copies CLSi of lists LSi
		CLS = LSu.clone();
		
		Su = LSu.getLSi(0);
		size = Su.size();
		
		step2();
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
		
		setLeftChild(vNode);
		setRightChild(wNode);
		
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
		
		vNode.setRo(R1);
		wNode.setRo(R2);
		
	}
	
	private void step5() {
		
	}
	
	private void step6() {
		
	}
	
	public void setRo(LinkedList<double[]> _Ro) { Ro = _Ro; }
	
	public void setRu(LinkedList<double[]> _Ru) { Ru = _Ru; }
	
	public void setLeftChild(TreeNode node) { leftChild = node; }
	
	public void setRightChild(TreeNode node) { rightChild = node; }
	
	public TreeNode getLeftChild() { return leftChild; }
	
	public TreeNode getRightChild() { return rightChild; }
}
