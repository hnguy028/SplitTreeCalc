package DataStructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Ball {
	private double radius;
	private List<Double> centerCoordinate;
	private int dimensions;
	
	public Ball(HyperRectangle R) {
		
		// List of vertices from the hyperrectangle
		LinkedList<List<Double>> points = R.getVertices();
		
		dimensions = points.getFirst().size();
		
		centerCoordinate = new ArrayList<Double>(Collections.nCopies(dimensions, 0.0));
		
		computeCircle(points);
	}
	
	public Ball(LinkedList<List<Double>> points) {
		
		dimensions = points.getFirst().size();
		
		centerCoordinate = new ArrayList<Double>(Collections.nCopies(dimensions, 0.0));
		
		computeCircle(points);
	}
	
	public void computeCircle(LinkedList<List<Double>> points) {
		rittersAlgorithm(points);
	}
	
	/*
	 * Ritter's algorithm for smallest bounding hypersphere over a set of points in "d" dimensions
	 */
	public void rittersAlgorithm(LinkedList<List<Double>> points) {
		List<Double> pmin = new ArrayList<Double>(points.getFirst());
		List<Double> pmax = new ArrayList<Double>(points.getFirst());
		
		List<Double> coord = null;
		
		// Find the min and max points for each dimension in our point set
		for(int i = 0; i < points.size(); i++) {
			coord = points.get(i);
			for(int d = 0; d < dimensions; d++) {
				pmin.set(d, Math.min(pmin.get(d), coord.get(d)));
				pmax.set(d, Math.max(pmax.get(d), coord.get(d)));
			}
		}
		
		// calculate the domain/bounds of our points
		List<Double> cdiff = new ArrayList<Double>(Collections.nCopies(dimensions, 0.0));
		double diameter = 0.0;
		
		for(int i = 0; i < dimensions; i++) {
			cdiff.set(i, Math.abs(pmax.get(i) - pmin.get(i)));
			diameter = Math.max(diameter, cdiff.get(i));
		}
		
		for(int i = 0; i < dimensions; i++) { centerCoordinate.set(i, (pmax.get(i) + pmin.get(i)) / 2.0); }
		
		radius = diameter / 2.0;
		double sq_radius = Math.pow(radius, 2);
		
		// Check if there are any point encapsulated in the ball above
		for(int i = 0; i < points.size(); i++) {
			List<Double> direction = new ArrayList<Double>(Collections.nCopies(dimensions, 0.0));
			
			List<Double> point = points.get(i);
			
			for(int d = 0; d < dimensions; d++) {
				direction.set(d, point.get(d) - centerCoordinate.get(d));
			}
			
			double sq_distance = length2(direction);
			
			// If any points exist outside the ball update the ball
			if(sq_distance > sq_radius) {
				double distance = Math.sqrt(sq_distance);
				
				double difference = distance - radius;

				double new_diameter = 2 * radius + difference;
				radius = new_diameter / 2.0;
				
				sq_radius = radius * radius;
				difference /= 2;
				
				for(int d = 0; d < dimensions; d++) {
					centerCoordinate.set(d, centerCoordinate.get(d) + (difference * direction.get(d)));
				}
			}
		}
	}
	
	// Euclidean distance between the center point of 2 balls
	public double getDistance(Ball _b) {
		List<Double> c = _b.getCenter();
		double res = 0.0;
		
		for(int i = 0; i < dimensions; i++) {
			res += Math.pow(c.get(i) - centerCoordinate.get(i), 2);
		}
		return Math.sqrt(res);
	}
	
	// squared length - euclidean distance of 2 coordinates
	public double length2(List<Double> vec) {
		double res = 0.0;
		
		for(int i = 0; i < dimensions; i++) {
			res += Math.pow(vec.get(i), 2);
		}
		return Math.sqrt(res);
	}
	
	public double getRadius() { return radius; }
	
	public List<Double> getCenter() { return centerCoordinate; }
	
	public String toString() { return "center: " + centerCoordinate.toString() + ", radius: " + radius; }
	
	public void print() { System.out.println(toString()); }
}
