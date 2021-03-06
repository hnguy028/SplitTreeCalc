package WSPD;

import java.util.LinkedList;

import DataStructures.*;

public class WSPD {
	
	// Init recurse call
	public LinkedList<Pair> ComputeWSPD(TreeNode T, double s) {
		return DFSComputeWSPD(T, s);
	}
	
	// Recursive DFS to iterate all nodes in the splitTree
	public LinkedList<Pair> DFSComputeWSPD(TreeNode root, double s) {
		LinkedList<Pair> wspd = new LinkedList<Pair>();
		
		// continue recursion only for internal nodes
		if(!isLeaf(root)) {
			wspd = handleInternalNode(root, s);
			
			wspd.addAll(DFSComputeWSPD(root.getLeftChild(), s));
			wspd.addAll(DFSComputeWSPD(root.getRightChild(), s));
		}
		
		return wspd;
	}
	
	// Run find pairs algorithm with the given internal node as the root
	private LinkedList<Pair> handleInternalNode(TreeNode internalNode, double s) {
		if(internalNode != null) {
			return FindPairs(internalNode.getLeftChild(), internalNode.getRightChild(), s);
		}
		return new LinkedList<Pair>();
	}
	
	private boolean isLeaf(TreeNode node) { return (node.getLeftChild() == null && node.getRightChild() == null); }
	
	public LinkedList<Pair> FindPairs(TreeNode v, TreeNode w, double s) {
		if(isWellSeparated(v, w, s)) {
			LinkedList<Pair> res = new LinkedList<Pair>();
			res.add(new Pair(v.getSu(), w.getSu()));
			return res;
		} else {
			if(Math.abs(v.getHyperRectangle().getLmax()) <= Math.abs(w.getHyperRectangle().getLmax())) {
				TreeNode wl = w.getLeftChild();
				TreeNode wr = w.getRightChild();
				
				LinkedList<Pair> res = FindPairs(v, wl, s);
				res.addAll(FindPairs(v, wr, s));
				
				return res;
			} else {
				TreeNode vl = v.getLeftChild();
				TreeNode vr = v.getRightChild();
				
				LinkedList<Pair> res = FindPairs(vl, w, s);
				res.addAll(FindPairs(vr, w, s));
				
				return res;
			}
		}
	}
	
	/*
	 *	Compute ball on the hyperspheres of each node and determine if they are well separated wrt to s 
	 */
	private boolean isWellSeparated(TreeNode v, TreeNode w, double s) {
		Ball ball1 = new Ball(v.getHyperRectangle());
		Ball ball2 = new Ball(w.getHyperRectangle());
		
		double max_radius = Math.max(ball1.getRadius(), ball2.getRadius());
		
		double delta = ball1.getDistance(ball2) - ball1.getRadius() - ball2.getRadius();
		
		return delta >= s * max_radius;
	}
}
