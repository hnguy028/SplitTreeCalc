package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import DataStructures.*;
import Main.Algorithms;

public class Main {
	
	static LinkedList<double[]> pointSet = null;
	
	public static void main(String[] args) {
		// Data
		double[] a = new double[]{0.0, 0.0};
		double[] b = new double[]{1.0, 10.0}; 
		double[] c = new double[]{3.0/2.0, 1.0}; 
		double[] d = new double[]{2.0, 5.0/2.0}; 
		double[] e = new double[]{8.0, 7.0}; 
		double[] f = new double[]{11.0, 2.0};
		double[] g = new double[]{11.0, 9.0};
		double[] h = new double[]{12.0, 0.0};
		
		LinkedList<double[]> pointSet = new LinkedList<double[]>(Arrays.asList(a, b, c, d, e, f, g, h));
		
		if(args.length > 0) {
			try {
				pointSet = loadData(args[0]);
			} catch (IOException e1) { e1.printStackTrace(); }
		}
		
		if (pointSet == null) {
			System.out.println("Pointset is empty");
			System.exit(0);
		}
		
		// Run splitTreeAlgorithm
		SplitTree tree = new SplitTree();
		tree.FastSplitTree(pointSet, null);
		
		System.out.println("Split Tree:");
		tree.print();
		
		// Run computeWSPD on tree
		if(pointSet.getFirst().length == 2) {
			System.out.println("WSPD:");
			
			Algorithms algorithms = new Algorithms();
		
			algorithms.ComputeWSPD(tree.getTreeRoot(), 4.00001);
		}
	}
	
	public static LinkedList<double[]> loadData(String filepath) throws IOException {
		
		File file = new File(filepath);
		
		if(file.isFile()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			LinkedList<double[]> res = new LinkedList<double[]>();  
			
			String line = "";
			LinkedList<Double> coords = new LinkedList<Double>();
			double[] tcoords;
			
			while((line = br.readLine()) != null) {
				coords = new LinkedList<Double>();
				for(String item : line.split(",")) { coords.add(Double.parseDouble(item)); }
					
				tcoords = new double[coords.size()];
				for(int i = 0; i < coords.size(); i++) { tcoords[i] = coords.get(i); }
				
				res.add(tcoords);
			}
			
			br.close();
			
			return res;
		}
		
		System.out.println("File not found");
		
		return null;
	}
}
