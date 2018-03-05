package DataStructures;

import java.util.Arrays;
import java.util.LinkedList;

public class Ball {
	private double radius;
	private double[] centerCoordinate;
	private int dimensions;
	
	public Ball(HyperRectangle R) {
		
		LinkedList<double[]> points = R.getVertices();
		
		dimensions = points.getFirst().length;
		
		centerCoordinate = new double[dimensions];
		
		computeCircle(points);
	}
	
	public Ball(LinkedList<double[]> points) {
		
		dimensions = points.getFirst().length;
		
		centerCoordinate = new double[dimensions];
		
		computeCircle(points);
	}
	
	public void computeCircle(LinkedList<double[]> points) {
		rittersAlgorithm(points);
	}
	
	/*
	 * Ritter's algorithm for smallest bounding hypersphere over a set of points in "d" dimensions
	 */
	public void rittersAlgorithm(LinkedList<double[]> points) {
		double[] pmin = points.getFirst().clone();
		double[] pmax = points.getFirst().clone();
		
		double[] coord = null;
		
		for(int i = 0; i < points.size(); i++) {
			coord = points.get(i);
			for(int d = 0; d < dimensions; d++) {
				pmin[d] = Math.min(pmin[d], coord[d]);
				pmax[d] = Math.max(pmax[d], coord[d]);
			}
		}
		
		double[] cdiff = new double[dimensions];
		double diameter = 0.0;
		
		for(int i = 0; i < dimensions; i++) {
			cdiff[i] = Math.abs(pmax[i] - pmin[i]);
			diameter = Math.max(diameter, cdiff[i]);
		}
		
		for(int i = 0; i < dimensions; i++) { centerCoordinate[i] = (pmax[i] + pmin[i]) / 2.0; }
		
		radius = diameter / 2.0;
		double sq_radius = Math.pow(radius, 2);
		
		for(int i = 0; i < points.size(); i++) {
			double[] direction = new double[dimensions];
			
			double[] point = points.get(i);
			
			for(int d = 0; d < dimensions; d++) {
				direction[d] = point[d] - centerCoordinate[d];
			}
			
			double sq_distance = length2(direction);
			
			if(sq_distance > sq_radius) {
				double distance = Math.sqrt(sq_distance);
				
				double difference = distance - radius;

				double new_diameter = 2 * radius + difference;
				radius = new_diameter / 2.0;
				
				sq_radius = radius * radius;
				difference /= 2;
				
				for(int d = 0; d < dimensions; d++) {
					centerCoordinate[d] += difference * direction[d];
				}
			}
		}
	}
	
	public double getDistance(Ball _b) {
		double[] c = _b.getCenter();
		double res = 0.0;
		
		// euclidean distance
		for(int i = 0; i < dimensions; i++) {
			res += Math.pow(c[i] - centerCoordinate[i], 2);
		}
		return Math.sqrt(res);
	}
	
	// squared length - euclidean distance
	public double length2(double[] vec) {
		double res = 0.0;
		
		for(int i = 0; i < dimensions; i++) {
			res += Math.pow(vec[i], 2);
		}
		return Math.sqrt(res);
	}
	
	public double getRadius() { return radius; }
	
	public double[] getCenter() { return centerCoordinate; }
	
	public String toString() { return "center: " + Arrays.toString(centerCoordinate) + ", radius: " + radius; }
	
	public void print() { System.out.println(toString()); }
}
