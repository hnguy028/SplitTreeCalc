import java.util.Arrays;
import java.util.LinkedList;

import DataStructures.*;

public class Main {
	public static void main(String[] args) {
		int dimensionSize = 2;

		double[] a = new double[]{0.0, 0.0};
		double[] b = new double[]{1.0, 10.0}; 
		double[] c = new double[]{3.0/2.0, 1.0}; 
		double[] d = new double[]{2.0, 5.0/2.0}; 
		double[] e = new double[]{8.0, 7.0}; 
		double[] f = new double[]{11.0, 2.0};
		double[] g = new double[]{11.0, 9.0};
		double[] h = new double[]{12.0 ,0.0};
		
		LinkedList<double[]> pointSet = new LinkedList<double[]>(Arrays.asList(a, b, c, d, e, f, g, h));
		
		SplitTree tree = new SplitTree();
		tree.FastSplitTree(pointSet, null, dimensionSize);
		tree.print();
	}
}
