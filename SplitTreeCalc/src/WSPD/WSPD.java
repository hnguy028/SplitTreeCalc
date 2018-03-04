package WSPD;

import java.util.Arrays;
import java.util.LinkedList;

import DataStructures.*;

public class WSPD {
	public LinkedList<Pair> ComputeWSPD(TreeNode T, double s) {
		TreeNode vNode = T.getLeftChild();
		TreeNode wNode = T.getRightChild();
		
		LinkedList<Pair> resultSet = FindPairs(vNode, wNode, s);
		
		return resultSet;
	}
	
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
	
	private boolean isWellSeparated(TreeNode v, TreeNode w, double s) {
		Ball ball1 = new Ball(v.getHyperRectangle());
		Ball ball2 = new Ball(w.getHyperRectangle());
		
		double max_radius = Math.max(ball1.getRadius(), ball2.getRadius());
		
		double delta = ball1.getDistance(ball2) - ball1.getRadius() - ball2.getRadius();
		
		Arrays.toString(v.getHyperRectangle().getRo().toArray());
		ball1.print();
		ball2.print();
		System.out.println("delta: " + delta + "\n");
		
		return delta >= s * max_radius;
	}
}
