package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import DataStructures.*;
import WSPD.*;
import TSpanner.*;

public class Main {
	
	static Random rng = new Random();
	static LinkedList<double[]> pointSet = null;
	
	public static void main(String[] args) {
		
		// Default Data
		List<Double> a = new LinkedList<Double>(Arrays.asList(0.0, 0.0));
		List<Double> b = new LinkedList<Double>(Arrays.asList(1.0, 10.0)); 
		List<Double> c = new LinkedList<Double>(Arrays.asList(3.0/2.0, 1.0)); 
		List<Double> d = new LinkedList<Double>(Arrays.asList(2.0, 5.0/2.0)); 
		List<Double> e = new LinkedList<Double>(Arrays.asList(8.0, 7.0)); 
		List<Double> f = new LinkedList<Double>(Arrays.asList(11.0, 2.0));
		List<Double> g = new LinkedList<Double>(Arrays.asList(11.0, 9.0));
		List<Double> h = new LinkedList<Double>(Arrays.asList(12.0, 0.0));
		
		LinkedList<List<Double>> pointSet = new LinkedList<List<Double>>(Arrays.asList(a, b, c, d, e, f, g, h));
		
		// Checks for filepath for pointset
		if(args.length > 0) {
			if(args[0].equals("-h")) {
				System.out.println("[filepath] | [-random numPoints dimension minPoint maxPoint [pointPrecision]] ");
				System.exit(1);
			}
			if(new File(args[0]).isFile()) {
				try {
					pointSet = loadData(args[0]);
				} catch (IOException e1) { e1.printStackTrace(); }
			} else if(args[0].equalsIgnoreCase("-random")) {
				int precision = 2;
				if(args.length >= 5) {
					if (args.length >= 6) { 
						precision = Integer.parseInt(args[5]); 
						precision = (precision >= 15) ? 2 : precision;
					}
					pointSet = randomDataSet(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]), precision);
				} else {
					pointSet = randomDataSet(rng.nextInt(50) + 1, rng.nextInt(5) + 1, 100.0, -100.0, precision);
				}
			}
		}
		
		if (pointSet == null) {
			System.out.println("Pointset is empty");
			System.exit(0);
		}
		
		// check for duplicates, and different dimension size
		checkConstraints(pointSet);
		
		int dimensions = pointSet.get(0).size();
		
		// Run splitTreeAlgorithm
		SplitTree tree = new SplitTree();
		tree.FastSplitTree(pointSet, null);
		
		// Print Tree
		System.out.println("Split Tree:");
		tree.print();
		
		System.out.println("\n\n----------------------------------------------------------------\n\n");
		
		System.out.println("WSPD:");
		
		LinkedList<Pair> resultSet = null;
			
		WSPD algorithms = new WSPD(); 
		
		// Run computeWSPD on tree
		resultSet = algorithms.ComputeWSPD(tree.getTreeRoot(), 4.00001);
			
		// Print WSPD
		for(int i = 0; i < resultSet.size(); i++) {
			String out = (i == resultSet.size() - 1) ? resultSet.get(i).toString() : resultSet.get(i).toString() + ","; 
			System.out.println(out);
		}
		
		System.out.println("\n\n----------------------------------------------------------------\n\n");
		
		System.out.println("t-Spanner:");
		
		// Run build t-spanner
		TSpanner tSpanner = new TSpanner();
		tSpanner.BuildSpannerFromWSPD(resultSet, tree.getTreeRoot().getSu(), dimensions);
		
		// t-Spanner graph G = (S,E)
		DoublyLinkedList S = tSpanner.getPointSet();
		HashMap<String, Edge> E = tSpanner.getEdgeList();
		
		// Print point set
		System.out.print("S: ");
		S.printSet();
		
		// Print edge list
		System.out.print("E: ");
		for(String edge : E.keySet()) {
			System.out.print(E.get(edge).toString() + ", ");
		}
	}
	
	/*
	 * Perform some checks on the input data
	 */
	private static void checkConstraints(LinkedList<List<Double>> input) {
		HashSet<String> stringInput = new HashSet<String>();
		int dimension = input.getFirst().size();
		
		for(List<Double> list : input) {
			if(list.size() != dimension) {
				try {
					throw new Exception("Error: incorrect dimension size(s) - " + input.getFirst().toString() + ", "+ list.toString());
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
			
			if(!stringInput.add(list.toString()))
				try {
					throw new Exception("Error: duplicate coordinate in input set - " + list.toString());
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
		}
	}
	
	/*
	 *  Point set loader from csv file
	 */
	public static LinkedList<List<Double>> loadData(String filepath) throws IOException {
		
		File file = new File(filepath);
		
		if(file.isFile()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			LinkedList<List<Double>> res = new LinkedList<List<Double>>();  
			
			String line = "";
			List<Double> coords = new LinkedList<Double>();
			
			// Parse and store each line as a coordinate
			while((line = br.readLine()) != null) {
				coords = new LinkedList<Double>();
				for(String item : line.split(",")) { coords.add(Double.parseDouble(item)); }
				
				res.add(coords);
			}
			
			br.close();
			
			return res;
		}
		
		// If path is not a file
		System.out.println("File not found");
		
		return null;
	}
	
	/*
	 *	Methods for randomly generated point sets 
	 */
	public static LinkedList<List<Double>> randomDataSet(int numPoints, int dimensionSize, double min, double max, int precision) {
		LinkedList<List<Double>> pointSet = new LinkedList<List<Double>>();
		
		for(int i = 0; i < numPoints; i++) {
			ArrayList<Double> coordinate = new ArrayList<Double>();
			for(int d = 0; d < dimensionSize; d++) {
				coordinate.add(randDouble(min, max, precision));
			}
			pointSet.add(coordinate);
		}
		return pointSet;
	}
	
	public static double randFloat(float min, float max) {
	    return rng.nextFloat() * (max - min) + min;
	}

	public static double randDouble(double min, double max) {
	    return rng.nextDouble() * (max - min) + min;
	}
	
	public static double randDouble(double min, double max, int precision) {
	    return Double.parseDouble(String.format("%." + precision + "f", rng.nextDouble() * (max - min) + min));
	}
}
