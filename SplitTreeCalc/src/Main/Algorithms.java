package Main;

import java.util.HashMap;
import java.util.LinkedList;

import DataStructures.*;

public class Algorithms {
	
	/*
	 * Should return a T-spanner GRAPH 
	 */
	public void BuildSpannerFromWSPD(LinkedList<Pair> wspd) {
		HashMap<String, DoublyLinkedList> set = new HashMap<String, DoublyLinkedList>();
		
		for(Pair pair : wspd) {
			if(!set.containsKey(pair.getFirst().toString())) set.put(pair.getFirst().toString(),pair.getFirst());
			if(!set.containsKey(pair.getLast().toString())) set.put(pair.getLast().toString(),pair.getLast());
		}
		
	}
	
	/*
	 *	Given a pair, ie a list of points connect the points in some way 
	 */
	private void connectPair(Pair pair, String method) {
		switch (method) {
		case "tspanner":
			// perform t-spanner algorithm on this subset of points
			break;
		case "":
		default:
			// first element as representative, connect all other in the set to it
			
			break;
		}
	}
}
