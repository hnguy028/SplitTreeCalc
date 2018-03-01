package DataStructures;

import java.awt.List;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.ProgressMonitorInputStream;

public class Ball {
	private double radius;
	private double[] centerCoordinate;
	private int dimensions;
	
	public Ball(HyperRectangle R) {
		
		if(R.getDimension() != 2) { throw new IllegalArgumentException("Only implemented for 2 dimensions"); }
		
		LinkedList<double[]> points = R.getVertices();
		
		if(points.size() != 4) { throw new NullPointerException("Rectangle out of bounds"); }
		
		for(double[] ar : points) {
			System.out.print(Arrays.toString(ar) + ", ");
		}
		System.out.println();
		dimensions = points.getFirst().length;
		centerCoordinate = new double[dimensions];
		computeCircle(points);
		//computeCircle(points.get(0), points.get(1), points.get(2));
	}

	public void computeCircle(double[] A, double[] B, double[] C) {		
		// Case where the bounding box is over a single point
		if(Arrays.equals(A, B) && Arrays.equals(B, C)) {
			radius = 0.0;
			centerCoordinate = A;
			return;
		}
		
		// Case where the bounding box if over a line
		if(Arrays.equals(A, B)) {
			radius = Math.sqrt( Math.pow(A[0] - C[0], 2) + Math.pow(A[1] - C[1], 2) ) / 2.0;
			centerCoordinate = new double[] { (A[0] + C[0]) / 2.0, (A[1] + C[1]) / 2.0 };
			return;
		} else if(Arrays.equals(B, C)) {
			radius = Math.sqrt( Math.pow(A[0] - C[0], 2) + Math.pow(A[1] - C[1], 2) ) / 2.0;
			centerCoordinate = new double[] { (A[0] + C[0]) / 2.0, (A[1] + C[1]) / 2.0 };
			return;
		} else if(Arrays.equals(A, C)) {
			radius = Math.sqrt( Math.pow(A[0] - B[0], 2) + Math.pow(A[1] - B[1], 2) ) / 2.0;
			centerCoordinate = new double[] { (A[0] + B[0]) / 2.0, (A[1] + B[1]) / 2.0 };
			return;
		}
		
		
		double yDelta_a = B[1] - A[1];
		double xDelta_a = B[0] - A[0];
		double yDelta_b = C[1] - B[1];
		double xDelta_b = C[0] - B[0];
		
		double[] center = new double[] {0,0};

		double aSlope = yDelta_a/xDelta_a;
		double bSlope = yDelta_b/xDelta_b;

		double[] AB_Mid = new double[] { (A[0] + B[0]) / 2, (A[1] + B[1]) / 2 };
		double[] BC_Mid = new double[] { (B[0] + C[0]) / 2, (B[1] + C[1]) / 2 };

		if(yDelta_a == 0) { //aSlope == 0
		    
			center[0] = AB_Mid[0];
		    
		    if (xDelta_b == 0) { center[1] = BC_Mid[1]; } //bSlope == INFINITY
		    else { center[1] = BC_Mid[1] + (BC_Mid[0] - center[0]) / bSlope; }
		    
		} else if (yDelta_b == 0) { //bSlope == 0
			
		    center[0] = BC_Mid[0];
		    if (xDelta_a == 0) { center[1] = AB_Mid[1]; } //aSlope == INFINITY
		    else { center[1] = AB_Mid[1] + (AB_Mid[0] - center[0]) / aSlope; }
		    
		} else if (xDelta_a == 0) { //aSlope == INFINITY
		    center[1] = AB_Mid[1];
		    center[0] = bSlope * (BC_Mid[1] - center[1]) + BC_Mid[0];
		}
		else if (xDelta_b == 0) { //bSlope == INFINITY
		    center[1] = BC_Mid[1];
		    center[0] = aSlope * (AB_Mid[1] - center[1]) + AB_Mid[0];
		} else {
		    center[0] = (aSlope * bSlope * (AB_Mid[1] - BC_Mid[1]) - aSlope * BC_Mid[0] + bSlope * AB_Mid[0]) / (bSlope - aSlope);
		    center[1] = AB_Mid[1] - (center[0] - AB_Mid[0]) / aSlope;
		}
		
		centerCoordinate = center;
		radius = Math.sqrt( Math.pow(A[0] - centerCoordinate[0], 2) + Math.pow(A[1] - centerCoordinate[1], 2) ) / 2.0;
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
			cdiff[i] = pmax[i] - pmin[i];
			diameter = Math.max(diameter, cdiff[i]);
		}
		
		for(int i = 0; i < dimensions; i++) {
			centerCoordinate[i] = (pmax[i] + pmin[i]) / 2.0;
		}
		
		radius = diameter / 2.0;
		double sq_radius = Math.pow(radius, 2);

		double[] direction = new double[dimensions];
		for(int i = 0; i < points.size(); i++) {
			coord = points.get(i);
			
			for(int d = 0; d < dimensions; d++) {
				direction[d] = coord[d] - centerCoordinate[d];
			}
			
			double sq_distance = length2(direction);
			
			if(sq_distance > sq_radius) {
				double distance = Math.sqrt(sq_distance);
				
				double difference = distance - radius;

				double new_diameter = 2 * radius + difference;
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
		
		for(int i = 0; i < dimensions; i++) {
			res += Math.pow(c[i] - centerCoordinate[i], 2);
		}
		return Math.sqrt(res);
	}
	
	public double length2(double[] vec) {
		double res = 0.0;
		
		for(int i = 0; i < dimensions; i++) {
			res += Math.pow(vec[i], 2);
		}
		return res;
	}
	
	public double getRadius() { return radius; }
	
	public double[] getCenter() { return centerCoordinate; }
	
	public String toString() { return "c: " + Arrays.toString(centerCoordinate) + ", r:" + radius; }
	
	public void print() { System.out.println(toString()); }
}
