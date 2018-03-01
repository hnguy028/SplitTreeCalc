package TSpanner;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map.Entry;

public class TSpannerGraph {
	private LinkedHashSet<GraphNode> nodes;
	private LinkedHashSet<GraphNode> edges;
	
	public TSpannerGraph(LinkedList<double[]> S) {
		nodes = new LinkedHashSet<GraphNode>();
		edges = new LinkedHashSet<GraphNode>();
		
		for(double[] coord : S) {
			nodes.add(new GraphNode(coord));
		}
	}
	
	public void addNode(GraphNode g) { nodes.add(g); }
	
	public TSpannerGraph calculateShortestPathFromSource(TSpannerGraph graph, GraphNode source) {
	 
	    LinkedHashSet<GraphNode> settledNodes = new LinkedHashSet<>();
	    LinkedHashSet<GraphNode> unsettledNodes = new LinkedHashSet<>();
	 
	    unsettledNodes.add(source);
	 
	    while (unsettledNodes.size() != 0) {
	        GraphNode currentNode = getLowestDistanceNode(unsettledNodes);
	        unsettledNodes.remove(currentNode);
	        for (Entry < GraphNode, Double> adjacencyPair: 
	          currentNode.getAdjacencyList().entrySet()) {
	            GraphNode adjacentNode = adjacencyPair.getKey();
	            Double edgeWeight = adjacencyPair.getValue();
	            if (!settledNodes.contains(adjacentNode)) {
	                calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
	                unsettledNodes.add(adjacentNode);
	            }
	        }
	        settledNodes.add(currentNode);
	    }
	    return graph;
	}
	
	private GraphNode getLowestDistanceNode(LinkedHashSet<GraphNode> unsettledNodes) {
	    GraphNode lowestDistanceNode = null;
	    double lowestDistance = Double.MAX_VALUE;
	    for (GraphNode node: unsettledNodes) {
	        double nodeDistance = node.getDistance();
	        if (nodeDistance < lowestDistance) {
	            lowestDistance = nodeDistance;
	            lowestDistanceNode = node;
	        }
	    }
	    return lowestDistanceNode;
	}
	
	private void calculateMinimumDistance(GraphNode evaluationNode,
			  Double edgeWeigh, GraphNode sourceNode) {
			    Double sourceDistance = sourceNode.getDistance();
			    if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
			        evaluationNode.setDistance(sourceDistance + edgeWeigh);
			        LinkedList<GraphNode> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			        shortestPath.add(sourceNode);
			        evaluationNode.setShortestPath(shortestPath);
			    }
	}
}
