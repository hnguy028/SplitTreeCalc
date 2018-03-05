package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import DataStructures.*;
import WSPD.*;
import TSpanner.*;

public class Main {
	
	static LinkedList<double[]> pointSet = null;
	
	public static void main(String[] args) {
		// GUI
		Graph graph = new SingleGraph("Tutorial 1");
		graph.addNode("B").addAttribute("xy", 1, 1);
		
		// probably use grey overlay for ball
		Node aNode = graph.addNode("A");
		aNode.addAttribute("xy", 1, 1);
		aNode.addAttribute("ui.style", "fill-color: grey; size: 20px; stroke-mode: plain; stroke-color: black;");
		graph.display(false);
		
		// Data
		double[] a = new double[]{0.0, 0.0};
		double[] b = new double[]{1.0, 10.0}; 
		double[] c = new double[]{3.0/2.0, 1.0}; 
		double[] d = new double[]{2.0, 5.0/2.0}; 
		double[] e = new double[]{8.0, 7.0}; 
		double[] f = new double[]{11.0, 2.0};
		double[] g = new double[]{11.0, 9.0};
		double[] h = new double[]{12.0, 0.0};
		double[] neg1 = new double[]{-12.0, 0.0};
		double[] dup = new double[]{1.0, 10.0};
		double[] neg2 = new double[]{1.0, -10.0};
		
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
		
		LinkedList<Pair> resultSet = null;
		
		// Run computeWSPD on tree
		if(pointSet.getFirst().length == 2) {
			System.out.println("WSPD:");
			
			WSPD algorithms = new WSPD(); 
		
			resultSet = algorithms.ComputeWSPD(tree.getTreeRoot(), 4.00001);
			
			for(int i = 0; i < resultSet.size(); i++) {
				String out = (i == resultSet.size() - 1) ? resultSet.get(i).toString() : resultSet.get(i).toString() + ","; 
				System.out.println(out);
			}
		}
		
		// Run build t-spanner
		new TSpanner().BuildSpannerFromWSPD(resultSet, tree.getTreeRoot().getSu(), 2);
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
