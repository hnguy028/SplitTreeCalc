package TSpanner;

import java.util.HashMap;
import java.util.LinkedList;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import DataStructures.*;

public class TSpanner {
	
	private DoublyLinkedList S;
	private HashMap<String, Edge> E = new HashMap<String, Edge>();
	
	private Graph graph;
	private int min_dim = 2;
	private int max_dim = 3;
	private boolean displayGraph;
	
	public TSpanner(boolean _displayGraph) { displayGraph = _displayGraph; }
	
	public void BuildSpannerFromWSPD(LinkedList<Pair> wspd, DoublyLinkedList pointSet, int dimensions) {
		
		boolean drawGraph = dimensions >= min_dim && dimensions <= max_dim && displayGraph;
		
		S = pointSet.clone();
		
		if(drawGraph) {
			graph = new SingleGraph("T-Spanner");
			
			DoublyLinkedListIterator iterator = pointSet.iterator();
			
			while (iterator.hasNext()) {
				PointNode node = iterator.next();
				try {
					plotNode(graph, node, dimensions);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		for(Pair pair : wspd) {
			// Take the first point as the representative node
			String firstRepNode = pair.getFirst().getFirst().toString();
			String secondRepNode = pair.getLast().getFirst().toString();
			
			// add to edge list
			E.put(firstRepNode + secondRepNode, new Edge(pair.getFirst().getFirst().getCoordinates(), pair.getLast().getFirst().getCoordinates()));
			
			if(drawGraph) {
				// add the edge to the gui graph
				graph.addEdge(firstRepNode + secondRepNode, firstRepNode, secondRepNode);
			}
		}
		
		if(drawGraph) {
			graph.display(false);
		}
	}
	
	/*
	 *	Create graph if the dimensions are within required range 
	 */
	private void plotNode(Graph graph, PointNode node, int dimensions) throws Exception {
		if(dimensions < min_dim || dimensions > max_dim) { throw new Exception(); }
		
		Node gNode;
		
		switch (dimensions) {
		case 1:
			gNode = graph.addNode(node.toString());
			gNode.addAttribute("xy", node.getCoordinates().get(0));
			gNode.addAttribute("ui.label", node.toString());
			break;
		case 2:
			gNode = graph.addNode(node.toString());
			gNode.addAttribute("xy", node.getCoordinates().get(0), node.getCoordinates().get(1));
			gNode.addAttribute("ui.label", node.toString());
			break;
		case 3:
			gNode = graph.addNode(node.toString());
			gNode.addAttribute("xy", node.getCoordinates().get(0), node.getCoordinates().get(1), node.getCoordinates().get(2));
			gNode.addAttribute("ui.label", node.toString());
			break;
		default:
			break;
		}
	}
	
	public DoublyLinkedList getPointSet() { return S; }
	public HashMap<String, Edge> getEdgeList() { return E; }
}
