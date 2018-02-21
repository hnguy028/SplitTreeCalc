import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.DocumentListener;

import DataStructures.DoublyLinkedList;
import DataStructures.DoublyLinkedListIterator;
import DataStructures.HyperRectangle;
import DataStructures.LS_Collection;
import DataStructures.PointNode;
import DataStructures.TreeNode;

public class SplitTree {
	
	
	
	LinkedList<double[]> Su;
	LinkedList<double[]> Ro;
	
	LS_Collection LS;
	
	int dimensions;
	
	TreeNode root;
	
	public void FastSplitTree(LinkedList<double[]> S, LinkedList<double[]> R, int _dimensions) {
		Su = S;
		Ro = R;
		dimensions = _dimensions;
		
		preProcess();
		
		root = new TreeNode(Ro, LS);
//		treeNode.partialSplitTree();
	} 
	
	// Used to find the initial rectangle that bounds the point set
	public static LinkedList<double[]> computeBoundingBox(LS_Collection LS) {
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
	
	public void preProcess() {
		// create d, linkedlists from point set each sorted wrt to the dimension
		LS = new LS_Collection(Su, dimensions);
		
		if(Ro == null) {
			Ro = computeBoundingBox(LS);
		}
	}
	
	
	public void splitTreeCardOne(List<PointNode> S, HyperRectangle R) {
		// if |S| == 1
		
		
		// create a node u
		// Node u;
		
		// bounding box R(u) = R(S)
		
		// rectangle containing bounding box Ro(u) = R
		
		// Store with u the only point of S, Ro(u), R(u)
		
		// set u's pointers to null
		
		// return u;
	}
}
