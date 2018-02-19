package DataStructures;

import java.util.LinkedList;

public class TreeNode {
	TreeNode leftChild, rightChild;
	
	LinkedList<double[]> Su;
	LinkedList<double[]> Ro;
	LinkedList<double[]> R;
	LS_Collection LS;
	
	public TreeNode() {
		leftChild = null;
		rightChild = null;
	}
	
	public TreeNode(LinkedList<double[]> _Su, LinkedList<double[]> _Ro, LS_Collection _LS) {
		leftChild = null;
		rightChild = null;
		
		Su = _Su;
		Ro = _Ro;
		LS = _LS;
	}
	
	public void setRo(LinkedList<double[]> _Ro) { Ro = _Ro; }
	
	public void setRu(LinkedList<double[]> _R) { R = _R; }
	
	public void setLeftChild(TreeNode node) { leftChild = node; }
	
	public void setRightChild(TreeNode node) { rightChild = node; }
	
	public TreeNode getLeftChild() { return leftChild; }
	
	public TreeNode getRightChild() { return rightChild; }
}
