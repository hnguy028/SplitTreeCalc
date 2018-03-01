package TSpanner;

import java.util.HashMap;
import java.util.LinkedList;

public class GraphNode {
	private HashMap<GraphNode, Double> adjacencyList;
	private LinkedList<GraphNode> shortestPath;
	private double[] coords;
	private double distance;
	
	public GraphNode(double[] _coords) {
		adjacencyList = new HashMap<GraphNode, Double>();
		shortestPath = new LinkedList<GraphNode>();
		coords = _coords;
		distance = 0.0;
	}
	
	public GraphNode(double[] _coords, double dist) {
		adjacencyList = new HashMap<GraphNode, Double>();
		shortestPath = new LinkedList<GraphNode>();
		coords = _coords;
		distance = dist;
	}
	
//	public double getDistance(GraphNode g) {
//		double delta = 0.0;
//		for(int i = 0; i < coords.length; i++) {
//			delta += Math.pow(g.getCoords()[i], 2) + Math.pow(coords[i], 2);
//		}
//		return Math.sqrt(delta);
//	}
	
	public void addDestination(GraphNode g, double distance) { adjacencyList.put(g, distance); }
	
	//public void connect(GraphNode g) { insertGraphNode(g); g.insertGraphNode(this); }
	
	//private void insertGraphNode(GraphNode g) { adjacencyList.add(g); }
	
	public HashMap<GraphNode, Double> getAdjacencyList() { return adjacencyList; }
	
	public double[] getCoords() { return coords; }
	
	public double getDistance() { return distance; }
	
	public void setDistance(double _distance) { distance = _distance; }

	public LinkedList<GraphNode> getShortestPath() { return shortestPath; }
	
	public void setShortestPath(LinkedList<GraphNode> sp) { shortestPath = sp; }
}
