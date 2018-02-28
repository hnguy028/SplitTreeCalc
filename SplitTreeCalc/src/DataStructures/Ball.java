package DataStructures;

import java.awt.List;
import java.time.chrono.MinguoChronology;
import java.util.Arrays;
import java.util.LinkedList;

public class Ball {
	private double radius;
	private double[] centerCoordinate;
	private int dimensions;
	
	private final double zero = 0.000000001;
	
	public Ball(HyperRectangle R) {
		if(R.getDimension() != 2) { throw new IllegalArgumentException("Only implemented for 2 dimensions"); }
		
		LinkedList<double[]> points = R.getVertices();
		
		if(points.size() != 4) { throw new NullPointerException("Rectangle out of bounds"); }
		
		computeCircle(points.get(0), points.get(1), points.get(2));
	}
	
	public Ball (double[] p1, double[] p2, double[] p3) {
		computeCircle(p1, p2, p3);
	}
	
	public void computeCircle(double[] A, double[] B, double[] C) {
		
		if(Arrays.equals(A, B) && Arrays.equals(B, C)) {
			radius = 0.0;
			centerCoordinate = A;
			return;
		}
		
		//if(Arrays.equals(p1, p2) || Arrays.equals(p2, p3) || Arrays.equals(p1, p3)) {}
		
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
		radius = Math.sqrt( Math.pow(A[0] - centerCoordinate[0], 2) + Math.pow(A[1] - centerCoordinate[1], 2) );
	}
	
	// 2D
	public void computeCircle(double[] p1, double[] p2, double[] p3, boolean p) {
		
		if(Arrays.equals(p1, p2) && Arrays.equals(p2, p3)) {
			radius = 0.0;
			centerCoordinate = p1;
			return;
		}
		
		if(Arrays.equals(p1, p2) || Arrays.equals(p2, p3) || Arrays.equals(p1, p3)) {
			
		}
		
		double offset = Math.pow(p2[0], 2) + Math.pow(p2[1], 2);
		
		double bc = ( Math.pow(p1[0], 2) + Math.pow(p1[1], 2) ) / 2.0;
		double cd = ( offset - Math.pow(p3[0], 2) - Math.pow(p3[1], 2) ) / 2.0;
		
		double det = (p1[0] - p2[0]) * (p2[1] - p3[1]) - (p2[0] - p3[0]) * (p1[1] - p2[1]); 
		
		if (Math.abs(det) < zero) {throw new IllegalArgumentException("Division by zero " + Arrays.toString(p1) + "," + Arrays.toString(p2) + "," + Arrays.toString(p3)); }
		
		double identity = 1 / det;
		
		double x = ( bc * (p2[1] - p3[1]) - cd * (p1[1] - p2[1]) ) * identity;
		double y = ( cd * (p1[0] - p2[0]) - bc * (p2[0] - p3[0]) ) * identity;
		
		centerCoordinate = new double[] {x, y};
		
		radius = Math.sqrt( Math.pow(p2[0] - x, 2) + Math.pow(p2[1] - y, 2));
	}
	
	public void computeCircle(List points) {
		
	}
	
	/*
	 * Ritter's algorithm for smallest bounding hypersphere over a set of points in "d" dimensions
	 */
	public void rittersAlgorithm(LinkedList<double[]> points) {
		double[] pmin = points.getFirst();
		double[] pmax = points.getFirst();
		
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
		
		centerCoordinate = new double[dimensions];
		
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
	
	// for d dimensions
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
}
