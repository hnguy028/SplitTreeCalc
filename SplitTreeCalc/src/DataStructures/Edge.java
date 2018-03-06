package DataStructures;

import java.util.ArrayList;
import java.util.List;

public class Edge {
	String id;
	List<Double> v1;
	List<Double> v2;
	
	public Edge(String _id, List<Double> _v1, List<Double> _v2) {
		id = _id;
		v1 = new ArrayList<Double>(_v1);
		v2 = new ArrayList<Double>(_v2);
	}
	
	public Edge(List<Double> _v1, List<Double> _v2) {
		v1 = new ArrayList<Double>(_v1);
		v2 = new ArrayList<Double>(_v2);
		
		id = toString();
	}
	
	public String toString() {
		return "Edge(" + v1.toString().replaceAll("\\[", "(").replaceAll("\\]",")") + ", " + v2.toString().replaceAll("\\[", "(").replaceAll("\\]",")") + ")";
	}
	
	public void print() { System.out.println(toString()); }
}
