package F28DA_CW2;

import java.util.List;
import java.util.Scanner;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleGraph;

public class FlyingPlannerMainPartA {
 
	
	
	public static void main(String[] args) {
		
		// The following code is from HelloJGraphT.java of the org.jgrapth.demo package
		
		System.err.println("The example code is from HelloJGraphT.java from the org.jgrapt.demo package.");
		System.err.println("Use similar code to build the small graph from Part A by hand.");
		System.err.println("Note that you will need to use a different graph class as your graph is not just a Simple Graph.");
		System.err.println("Once you understand how to build such graph by hand, move to Part B to build a more substantial graph.");
		// Code is from HelloJGraphT.java of the org.jgrapth.demo package (start)
		
		Graph<String, DefaultWeightedEdge> g = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

		// add the vertices
		g.addVertex("DXB");
		g.addVertex("EDI");
		g.addVertex("KUL");
		g.addVertex("SYD");
		g.addVertex("LHR");

		// add edges
		g.addEdge("EDI", "LHR"); // Edinburgh -> Heathrow
		g.setEdgeWeight(g.getEdge("EDI", "LHR"), 80);

		g.addEdge("LHR", "DXB"); // Heathrow -> Dubai
		g.setEdgeWeight(g.getEdge("LHR", "DXB"), 130);

		g.addEdge("LHR", "SYD"); // Heathrow -> Sydney
		g.setEdgeWeight(g.getEdge("LHR", "SYD"), 570);

		g.addEdge("EDI", "DXB"); // Edinburgh -> Dubai
		g.setEdgeWeight(g.getEdge("EDI", "DXB"), 150);

		g.addEdge("DXB", "KUL"); // Dubai -> Kuala Lumpur
		g.setEdgeWeight(g.getEdge("DXB", "KUL"), 170);

		g.addEdge("KUL", "SYD"); // Kuala Lumpur -> Sydney
		g.setEdgeWeight(g.getEdge("KUL", "SYD"), 150);

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Departure Airport: ");
		String departure = scanner.nextLine();
		System.out.print("Enter Destination Airport: ");
		String destination = scanner.nextLine();

		DijkstraShortestPath<String, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(g);
		List<DefaultWeightedEdge> path = dijkstra.getPath(departure, destination).getEdgeList();

		double cost = 0;
		for(DefaultWeightedEdge e: path) {
		    cost += g.getEdgeWeight(e);
		}

		System.out.println("Cheapest Route Path: ");
		for(int i = 0; i < path.size(); i++ ) {
		    String source = g.getEdgeSource(path.get(i));
		    String target = g.getEdgeTarget(path.get(i));
		    System.out.println((i+1) + ", " + source + " -> " + target);
		}
		System.out.println("Number of plane changes: " + (path.size()-1));
		System.out.println("Cheapest path = " + cost);

		

        
        
        
        
        
        
        
	}

}
