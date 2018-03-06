package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import DataStructures.*;
import WSPD.*;
import TSpanner.*;

public class Main {
	
	static LinkedList<double[]> pointSet = null;
	
	public static void main(String[] args) {
		// Data
		List<Double> a = new LinkedList<Double>(Arrays.asList(0.0, 0.0));
		List<Double> b = new LinkedList<Double>(Arrays.asList(1.0, 10.0)); 
		List<Double> c = new LinkedList<Double>(Arrays.asList(3.0/2.0, 1.0)); 
		List<Double> d = new LinkedList<Double>(Arrays.asList(2.0, 5.0/2.0)); 
		List<Double> e = new LinkedList<Double>(Arrays.asList(8.0, 7.0)); 
		List<Double> f = new LinkedList<Double>(Arrays.asList(11.0, 2.0));
		List<Double> g = new LinkedList<Double>(Arrays.asList(11.0, 9.0));
		List<Double> h = new LinkedList<Double>(Arrays.asList(12.0, 0.0));
		List<Double> ee = new LinkedList<Double>(Arrays.asList(12.0, 0.0));
		
		LinkedList<List<Double>> pointSet = new LinkedList<List<Double>>(Arrays.asList(a, b, c, d, e, f, g, h, ee));
		
		if(args.length > 0) {
			try {
				pointSet = loadData(args[0]);
			} catch (IOException e1) { e1.printStackTrace(); }
		}
		
		if (pointSet == null) {
			System.out.println("Pointset is empty");
			System.exit(0);
		}
		
		// check for duplicates
		checkConstraints(pointSet);
		
		// Run splitTreeAlgorithm
		SplitTree tree = new SplitTree();
		tree.FastSplitTree(pointSet, null);
		
		System.out.println("Split Tree:");
		tree.print();
		
		System.out.println("\n\n----------------------------------------------------------------\n\n");
		
		LinkedList<Pair> resultSet = null;
		
		// Run computeWSPD on tree
		if(pointSet.getFirst().size() == 2) {
			System.out.println("WSPD:");
			
			WSPD algorithms = new WSPD(); 
		
			resultSet = algorithms.ComputeWSPD(tree.getTreeRoot(), 4.00001);
			
			for(int i = 0; i < resultSet.size(); i++) {
				String out = (i == resultSet.size() - 1) ? resultSet.get(i).toString() : resultSet.get(i).toString() + ","; 
				System.out.println(out);
			}
		}
		
		System.out.println("\n\n----------------------------------------------------------------\n\n");
		
		System.out.println("t-Spanner:");
		
		// Run build t-spanner
		TSpanner tSpanner = new TSpanner();
		tSpanner.BuildSpannerFromWSPD(resultSet, tree.getTreeRoot().getSu(), 2);
		
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
	
	public static LinkedList<List<Double>> loadData(String filepath) throws IOException {
		
		File file = new File(filepath);
		
		if(file.isFile()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			LinkedList<List<Double>> res = new LinkedList<List<Double>>();  
			
			String line = "";
			List<Double> coords = new LinkedList<Double>();
			
			while((line = br.readLine()) != null) {
				coords = new LinkedList<Double>();
				for(String item : line.split(",")) { coords.add(Double.parseDouble(item)); }
				
				res.add(coords);
			}
			
			br.close();
			
			return res;
		}
		
		System.out.println("File not found");
		
		return null;
	}
	
	private static void checkConstraints(LinkedList<List<Double>> input) {
		HashSet<String> stringInput = new HashSet<String>();
		
		for(List<Double> list : input) {
			if(!stringInput.add(list.toString()))
				try {
					throw new Exception("Error: duplicate coordinate in input set - " + list.toString());
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
		}
		
	}
}
