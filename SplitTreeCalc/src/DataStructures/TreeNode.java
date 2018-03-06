package DataStructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class TreeNode {
	private	TreeNode leftChild, rightChild;
	
	// Pointset Su
	private DoublyLinkedList Su;
	
	// 
	private HyperRectangle rectangle;
	
	// Rectangle
	private	LinkedList<List<Double>> Ro;
	
	// Bounding Rectangle
	private LinkedList<List<Double>> Ru;
	
	// Collection of linkedlists of Su, sorted by dimension 
	private LS_Collection LSu;
	private LS_Collection CLS;
	
	private int n;
	
	// For printing purposes
	private String[] setBrackets = null;
	
	public TreeNode() {
		leftChild = null;
		rightChild = null;
	}
	
	// Rectangle Ro, Collection of DoublyLinkedList LSi
	public TreeNode(LinkedList<List<Double>> ro2, LS_Collection _LS) {
		leftChild = null;
		rightChild = null;
		
		Ro = ro2;
		LSu = _LS;
		Su = _LS.getLSi(0).clone();
		n = Su.size();
	}
	
	public TreeNode(LinkedList<List<Double>> _Ro) {
		leftChild = null;
		rightChild = null; 
		
		Ro = _Ro;
		n = -1;
	}
	
	public void partialSplitTree() {
		// Check if required info is set
		if(Ro != null && LSu != null) {
			step1();
		} else {
			throw new NullPointerException("HyperRectangle and/or LSi is empty");
		}
		
		step6();
	}
	
	private void step1() {
		// Step 1: Make copies CLSi of lists LSi
		CLS = LSu.clone();
		
		Su = LSu.getLSi(0).clone();
		n = Su.size();
		
		step2();
	}
	
	private void savePointSet() {
		// if(Su != null) {}
		Su = LSu.getLSi(0).clone();
	}
	
	private void step2() {
		savePointSet();
		int size = LSu.getLSi(0).size();
		if (n < 0) { n = CLS.getLSi(0).size(); }
		
		if(size <= n / 2.0) {	
			DoublyLinkedListIterator walk = LSu.getLSi(0).iterator();
			PointNode z;
			
			while(walk.hasNext()) {
				z = walk.next();
				for(PointNode cls_occurrence : z.getCrossPointersCLS()) {
					cls_occurrence.storeTreeNodePointer(this);	
				}
			}
			
			LSu = new LS_Collection(LSu.getDimensionSize());
			
			// step6 will be called when by recursion after this step is complete
			
		} else { // size > Su.size / 2.0
			step3();
		}
	}
	
	private void step3() {
		// Compute bounding box
		rectangle = new HyperRectangle(LSu, Ro);
		
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
			q_ = q_.getPrev();
			
			size_++;
		}
		
		if(p_.getCoordinateValueAt(xi) > h) {
			step4(p, xi, h, size_);
		} else { // p_.getCoordinateValueAt(xi) < h
			step5(q, xi, h, size_);
		}
	}
	
	private void step4(PointNode p, int xi, double h, int size_) {
		LinkedList<List<Double>> R1 = new LinkedList<List<Double>>();
		LinkedList<List<Double>> R2 = new LinkedList<List<Double>>();
		
		for(int i = 0; i < Ru.size(); i++) {
			if(xi == i) {
				R1.add(new ArrayList<Double> (Arrays.asList(Ru.get(i).get(0), h)));
				R2.add(new ArrayList<Double> (Arrays.asList(h, Ru.get(i).get(1))));
			} else {
				R1.add(Ru.get(i));
				R2.add(Ru.get(i));
			}
		}
		
		TreeNode vNode = new TreeNode(R1);
		TreeNode wNode = new TreeNode(R2);
		
		setLeftChild(vNode);
		setRightChild(wNode);
		
		// 4.1, 4.2, 4.3
		DoublyLinkedListIterator walk = LSu.getLSi(xi).iterator();
		PointNode z;
		
		boolean pReached = false;
		while(walk.hasNext()) {
			z = walk.next();
			
			if(!pReached) {
				// stop after p is found
				if (z == p) { pReached = true; }
			
				// 4.1
				for(PointNode cls_occurrence : z.getCrossPointersCLS()) {
					cls_occurrence.storeTreeNodePointer(vNode);	
				}
			
				// 4.2
				z.removeCrossPointers_LSi();
			
				// 4.3
				z.remove();
			}
		}
		wNode.setLS(LSu, CLS);
		wNode.step2();
		
		vNode.setLSu(new LS_Collection(LSu.getDimensionSize()));
	}
	
	private void step5(PointNode q, int xi, double h, int size_) {
		LinkedList<List<Double>> R1 = new LinkedList<List<Double>>();
		LinkedList<List<Double>> R2 = new LinkedList<List<Double>>();
		
		for(int i = 0; i < Ru.size(); i++) {
			if(xi == i) {
				R1.add(new ArrayList<Double> (Arrays.asList(Ru.get(i).get(0), h)));
				R2.add(new ArrayList<Double> (Arrays.asList(h, Ru.get(i).get(1))));
			} else {
				R1.add(Ru.get(i));
				R2.add(Ru.get(i));
			}
		}
		
		TreeNode vNode = new TreeNode(R1);
		TreeNode wNode = new TreeNode(R2);
		
		setLeftChild(vNode);
		setRightChild(wNode);
		
		// 5.1, 5.2, 5.3
		DoublyLinkedListIterator walk = LSu.getLSi(xi).iterator_reverse();
		PointNode z;
		
		boolean qReached = false;
		while(walk.hasNext()) {
			z = walk.next();
			
			if(!qReached) {
				// stop after p is found
				if (z == q) { qReached = true; }
			
				// 5.1
				for(PointNode cls_occurrence : z.getCrossPointersCLS()) {
					cls_occurrence.storeTreeNodePointer(wNode);	
				}
			
				// 5.2
				z.removeCrossPointers_LSi();
			
				// 5.3
				z.remove();
			}
		}
		vNode.setLS(LSu, CLS);
		vNode.step2();
		
		wNode.setLSu(new LS_Collection(LSu.getDimensionSize()));
	}
	
	// only called at the root of the partial split tree, where all points in CLS have a pointer to a lead node
	private void step6() {
		DoublyLinkedListIterator iterator;
		PointNode pointNode;
		int dimensions = CLS.getDimensionSize();
		
		LinkedHashSet<TreeNode> leafSets = new LinkedHashSet<TreeNode>();
		
		for(int i = 0; i < dimensions; i++) {
			iterator = CLS.getLSi(i).iterator();
			
			while(iterator.hasNext()) {
				pointNode = iterator.next();
				
				// store reference to all leaf LSi sets
				leafSets.add(pointNode.getTreeNode());
				
				pointNode.getTreeNode().LSu.append(pointNode, i);
			}
		}
		
		// Connect cross pointers between each subset list, and connect cross pointers between CLS and LS subset
		for(TreeNode node : leafSets) { node.LSu.connectCrossPointers(CLS); node.savePointSet(); }
	}
	
	public void computeBoundingBoxLeaf() {
		// Compute bounding box
		rectangle = new HyperRectangle(LSu, Ro);
				
		// set R(u) = R(Su)
		Ru = rectangle.getR();
	}

	private void setLSu(LS_Collection _LS) { LSu = _LS; }
	
	private void setLS(LS_Collection _LS, LS_Collection _CLS) { LSu = _LS; CLS = _CLS; }
	
	public DoublyLinkedList getSu() { return Su; }
	
	public void setRo(LinkedList<List<Double>> _Ro) { Ro = _Ro; }
	
	public void setRu(LinkedList<List<Double>> _Ru) { Ru = _Ru; }
	
	public HyperRectangle getHyperRectangle() { return rectangle; }
	
	public LinkedList<List<Double>> getRu() { if(Ru == null) { return new LinkedList<List<Double>>(Arrays.asList(Su.getFirst().getCoordinates())); } return Ru; }
	
	public void setLeftChild(TreeNode node) { leftChild = node; }
	
	public void setRightChild(TreeNode node) { rightChild = node; }
	
	public boolean isLeafNode() { return leftChild == null && rightChild == null; }
	
	public int size() { return Su.size(); }
	
	public TreeNode getLeftChild() { return leftChild; }
	
	public TreeNode getRightChild() { return rightChild; }
	
	public String toString(int lvl, String[] brackets) { 
		if(brackets != null) { setBrackets = brackets; } 
		return toString(lvl); 
	}
	
	public String toString(int lvl) {
		String brk = "";
		for(int i = 0; i < lvl; i++) { brk += "\t"; }
		
		String lChild = (leftChild == null) ? brk + "\tDepth" + (lvl + 1) + ": []" : leftChild.toString(lvl + 1);
		String rChild = (rightChild == null) ? brk + "\tDepth" + (lvl + 1) + ": []" : rightChild.toString(lvl + 1);

		String output = brk + "Depth" + lvl + ": " + Su.toString(setBrackets) + " :\n" + lChild + "\n" + rChild + "\n";// + "Depth" + lvl + ":" + getRu() + "--";

		return output;
	}
	
	//public void print() { System.out.print(toString(0)); }
	public void print() { printNavigationTree("", true); }
	
	private void printNavigationTree(String prefix, boolean isTail) {
		System.out.println(prefix + (isTail ? "+-- " : "|-- ") + Su.toString(setBrackets));
		
		if(leftChild != null || rightChild != null) {
			if(rightChild == null) {
				leftChild.printNavigationTree(prefix + (isTail ? "    " : "|   "), true);
			} else if(leftChild == null) {
				rightChild.printNavigationTree(prefix + (isTail ? "    " : "|   "), true);
			} else {
				leftChild.printNavigationTree(prefix + (isTail ? "    " : "|   "), false);
				rightChild.printNavigationTree(prefix + (isTail ? "    " : "|   "), true);
			}
		}
	}
}
