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
	
	public TSpanner() {
	}
	
	public void BuildSpannerFromWSPD(LinkedList<Pair> wspd, DoublyLinkedList pointSet, int dimensions) {
		
		S = pointSet.clone();
		
		if(dimensions > 0 && dimensions < 4) {
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
			
			if(dimensions > 0 && dimensions < 4) {
				// add the edge to the gui graph
				graph.addEdge(firstRepNode + secondRepNode, firstRepNode, secondRepNode);
			}
		}
		
		if(dimensions > 0 && dimensions < 4) {
			graph.display(false);
		}
	}
	
	private void plotNode(Graph graph, PointNode node, int dimensions) throws Exception {
		if(dimensions < 1 || dimensions > 3) { throw new Exception(); }
		
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
