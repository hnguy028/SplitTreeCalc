package DataStructures;

import java.util.Arrays;

public class Edge {
	String id;
	double[] v1;
	double[] v2;
	
	public Edge(String _id, double[] _v1, double[] _v2) {
		id = _id;
		v1 = _v1;
		v2 = _v2;
	}
	
	public Edge(double[] _v1, double[] _v2) {
		id = toString();
		v1 = _v1;
		v2 = _v2;
	}
	
	public String toString() {
		return "Edge(" + Arrays.toString(v1).replaceAll("\\[", "(").replaceAll("\\]",")") + ", " + Arrays.toString(v2).replaceAll("\\[", "(").replaceAll("\\]",")") + ")";
	}
	
	public void print() { System.out.println(toString()); }
}
