package DataStructures;

import java.util.LinkedList;

public class SplitTree {
	
	private LinkedList<double[]> Su;
	private LinkedList<double[]> Ro;
	
	private LS_Collection LS;
	
	private int dimensions;
	
	private TreeNode root;
	
	public void FastSplitTree(LinkedList<double[]> S, LinkedList<double[]> R) {
		Su = S;
		Ro = R;
		dimensions = S.getFirst().length;
		
		preProcess();
		
		root = new TreeNode(Ro, LS);
		
		recursiveCall(root);
		
		root.getSu().printSet();
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
	private LinkedList<double[]> computeBoundingBox(LS_Collection LS) {
		int dimensions = LS.getDimensionSize();
		LinkedList<double[]> R = new LinkedList<double[]>();
		
		for(int i = 0; i < dimensions; i ++) {
			DoublyLinkedList LSi = LS.getLSi(i);
			
			double _min = LSi.getFirst().getCoordinateValueAt(i);
			double _max = LSi.getLast().getCoordinateValueAt(i);
			
			R.add(new double[] {_min, _max});
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
