package Main;

import java.util.LinkedList;

import DataStructures.*;

public class Algorithms {
	
	/*
	 * Should return a T-spanner GRAPH 
	 */
	public void BuildSpannerFromWSPD(LinkedList<Pair> wspd) {
		// create some sort of dictionary set? for the edge list?
		
		for(Pair pair : wspd) {
			pair.getFirst();
			pair.getLast();
		}
		
		
		// we need to add an edge between every point in the set to the representative?
		// otherwise how else would we do this? 
		//		we could implement the normal t-spanner algo within the set
		//		todo : implement this so it could easily be overridden depending on how users would like to connect them
		//		most likely though, this doesn't really matter since a single set is well-separated from other sets
		
		
		// since all pairs represent an edge for the t-spanner
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
