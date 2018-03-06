package DataStructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SplitTree {
	
	private LinkedList<List<Double>> Su;
	private LinkedList<List<Double>> Ro;
	
	private LS_Collection LS;
	
	private int dimensions;
	
	private TreeNode root;
	
	public void FastSplitTree(LinkedList<List<Double>> pointSet, LinkedList<List<Double>> R) {
		Su = pointSet;
		Ro = R;
		dimensions = pointSet.getFirst().size();
		
		preProcess();
		
		root = new TreeNode(Ro, LS);
		
		recursiveCall(root);
		
		System.out.print("Input Dataset: ");
		root.getSu().printSet();
		System.out.println();
	}
	
	// DFS style
	private void recursiveCall(TreeNode node) {
		if(node.size() > 1) {
			if(node.isLeafNode()) { node.partialSplitTree(); }
		
			recursiveCall(node.getLeftChild());
			recursiveCall(node.getRightChild());
		} else {
			node.computeBoundingBoxLeaf();
		}
	}
	
	// Used to find the initial rectangle that bounds the point set
	private LinkedList<List<Double>> computeBoundingBox(LS_Collection LS) {
		int dimensions = LS.getDimensionSize();
		LinkedList<List<Double>> R = new LinkedList<List<Double>>();
		
		for(int i = 0; i < dimensions; i ++) {
			DoublyLinkedList LSi = LS.getLSi(i);
			
			double _min = LSi.getFirst().getCoordinateValueAt(i);
			double _max = LSi.getLast().getCoordinateValueAt(i);
			
			R.add(new ArrayList<Double> (Arrays.asList(_min, _max)));
		}
		return R;
	}
	
	private void preProcess() {
		// create d, linkedlists from point set each sorted wrt to the dimension
		LS = new LS_Collection(Su, dimensions);
		
		// if rectangle is not given, the initial rectangle is set to the bounding box
		if(Ro == null) {
			Ro = computeBoundingBox(LS);
		}
	}
	
	public TreeNode getTreeRoot() { return root; }
	
	public void print() { root.print(); }
}
