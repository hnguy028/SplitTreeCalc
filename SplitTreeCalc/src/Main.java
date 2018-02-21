import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import DataStructures.*;

public class Main {
	public static void main(String[] args) {
		int dimension_size = 2;

		double[] a = new double[]{0.0, 0.0};
		double[] b = new double[]{1.0, 10.0}; 
		double[] c = new double[]{3.0/2.0, 1.0}; 
		double[] d = new double[]{2.0, 5.0/2.0}; 
		double[] e = new double[]{8.0, 7.0}; 
		double[] f = new double[]{11.0, 2.0};
		double[] g = new double[]{11.0, 9.0};
		double[] h = new double[]{12.0 ,0.0};
		
		LinkedList<double[]> pointSet = new LinkedList<double[]>(Arrays.asList(a, b, c, d, e, f, g, h));
		
		DoublyLinkedList LS1 = new DoublyLinkedList(2);
		LS1.add(a);
		LS1.add(b);
		LS1.add(c);
		LS1.add(d);
		LS1.add(e);
		LS1.add(f);
		LS1.add(g);
		LS1.add(h);
		
		DoublyLinkedList LS2 = LS1.clone();
		
		LS_Collection coll = new LS_Collection(pointSet, 2);
		LS_Collection coll2 = coll.clone();
		
		coll.print();
		coll2.print();
		System.out.println();
		
		TreeNode treeNode = new TreeNode(computeBoundingBox(coll), coll);
		treeNode.partialSplitTree();
//		System.out.println(treeNode.getRightChild().CLS);
//		System.out.println(treeNode.getRightChild().LSu.getLSi(0).get(0).getCrossPointersCLS());
		treeNode.getLeftChild().partialSplitTree();
		treeNode.getRightChild().partialSplitTree();
		System.out.println("---------------------------------");
		treeNode.print();
//		DoublyLinkedListIterator iterator = treeNode.CLS.getLSi(0).iterator();
//		
//		while(iterator.hasNext()) {
//			System.out.println(iterator.next().getCrossPointers());
//		}
//		
//		System.out.println();
//		DoublyLinkedListIterator iterator2 = treeNode.getLeftChild().LSu.getLSi(0).iterator();
//		
//		while(iterator2.hasNext()) {
//			System.out.println(iterator2.next().getCrossPointersCLS());
//		}
		
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
	
	public void partialSplitTree(LinkedList<double[]> S, HyperRectangle R, LS_Collection LS) {
		// Step 1: Make copies CLSi of lists LSi
		LS_Collection CLS = LS.clone();
		int size = S.size();
		
		// Create a node u, which will be the root of the final partial split tree
		//TreeNode uNode = new TreeNode(S, R.getRo(), LS);
	}
	
	public void preProcess(LinkedList<double[]> _pointSet, int _dimension) {
		// create d, linkedlists from point set each sorted wrt to the dimension
		LS_Collection LS = new LS_Collection(_pointSet, _dimension);
		LS_Collection CLS = LS.clone();
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
