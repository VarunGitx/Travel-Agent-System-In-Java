package F28DA_CW2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;


public class FlyingPlanner implements IFlyingPlannerPartB<Airport,Flight>, IFlyingPlannerPartC<Airport,Flight> {

	
	private Graph<Airport, FlightEdge> costWeightedGraph = new SimpleDirectedWeightedGraph<>(FlightEdge.class);
	private HashMap<String, Airport> airports = new HashMap<>();
	private HashMap<String, Flight> flights = new HashMap<>();
	private Graph<Airport, FlightEdge> hopUnweightedGraph = new SimpleDirectedGraph<>(FlightEdge.class);
	private DirectedAcyclicGraph<Airport, FlightEdge> directedAcyclicGraph;
	private DirectedAcyclicGraph<Airport, FlightEdge> betterdirectedAcyclicGraph;
	private HashMap<Airport, Integer> directConnections = new HashMap<Airport, Integer>();
	
	
	
	
	private boolean populate(HashMap<String, Airport> airports, HashMap<String, Flight> flights, Graph<Airport, FlightEdge> graph,boolean weight){
		
		for (Airport airport : airports.values()) {
			graph.addVertex(airport);
			}
		
		for (Flight flight : flights.values()) {
			Airport from = flight.getFrom();
			Airport to = flight.getTo();
			graph.addEdge(from, to, new FlightEdge(flight));
			if (weight) {
				graph.setEdgeWeight(from, to, flight.getCost());
			}
		}
		return true;
	}
	
	
	//--------done
	
	@Override
	public boolean populate(FlightsReader flightsreader) {
		
		return populate(flightsreader.getAirports(),flightsreader.getFlights());
	}


	
	//..done
	@Override
	public boolean populate(HashSet<String[]> airportList, HashSet<String[]> flightList) {
		for (String[] a : airportList) {
			Airport airport = new Airport(a[0], a[1], a[2]);
			airports.put(a[0], airport);
			hopUnweightedGraph.addVertex(airport);
			costWeightedGraph.addVertex(airport);
			
		}

		for (String[] f : flightList) {
			Airport from = airports.get(f[1]);
			Airport to = airports.get(f[3]);
			Flight flight = new Flight(f[0], from, f[2], to, f[4], Integer.parseInt(f[5]));
			flights.put(f[0], flight);
			hopUnweightedGraph.addEdge(from, to, new FlightEdge(flight));
			costWeightedGraph.addEdge(from, to, new FlightEdge(flight));
			costWeightedGraph.setEdgeWeight(from, to, flight.getCost());
			
		}
		return true;
				
}
	
	@Override
	public Airport airport(String code) {
		// TODO Auto-generated method stub
		return airports.get(code);
	}

	@Override
	public Flight flight(String code) {
		// TODO Auto-generated method stub
		return flights.get(code);
	}
	
	
	
	@Override
	public Journey leastCost(String from, String to) throws FlyingPlannerException {
		// TODO Auto-generated method stub
		return leastCost(from, to, costWeightedGraph);
	}
	
	
	private Journey leastCost(String from, String to, Graph<Airport, FlightEdge> graph) throws FlyingPlannerException {
		Airport fromA = airport(from);
		Airport toA = airport(to);
		GraphPath<Airport, FlightEdge> dijkstraShortestPath = DijkstraShortestPath.findPathBetween(graph, fromA, toA);

		if (dijkstraShortestPath == null) {
			throw new FlyingPlannerException("Journey not available.");}
		else {
			Journey leastCostJ = new Journey(fromA, toA, dijkstraShortestPath);
			Journey leastHopJ = leastHop(from, to);

			if (leastCostJ.totalCost() == leastHopJ.totalCost()) {
				return leastHopJ;
				}
			return leastCostJ;
			}}
	
	
	private Journey leastHop(String from, String to, Graph<Airport, FlightEdge> weightedGraph, Graph<Airport, FlightEdge> unweightedGraph)
			throws FlyingPlannerException {
		Airport fromA = airport(from);
		Airport toA = airport(to);
		GraphPath<Airport, FlightEdge> shortestPath = DijkstraShortestPath.findPathBetween(unweightedGraph, fromA, toA);

		if (shortestPath == null) {
			throw new FlyingPlannerException("Journey not available.");
		} 

		AllDirectedPaths<Airport, FlightEdge> kwPaths = new AllDirectedPaths<Airport, FlightEdge>(weightedGraph);
		List<GraphPath<Airport, FlightEdge>> kwList = kwPaths.getAllPaths(fromA, toA, true, shortestPath.getLength());
		List<Double> costList = new LinkedList<>();

		for (GraphPath<Airport, FlightEdge> graphPath : kwList) {
			costList.add(graphPath.getWeight());
		}

		double minCost = Collections.min(costList);

		List<GraphPath<Airport, FlightEdge>> minList = new LinkedList<>();
		List<Integer> timeList = new LinkedList<>();
		for (GraphPath<Airport, FlightEdge> path : kwList) {
			Journey j = new Journey(fromA, toA, path);
			if (j.totalCost() == minCost) {
				minList.add(path);
				timeList.add(j.totalTime());
			}
		}

		Journey leastHopJ;
		if (!timeList.isEmpty()) {
			int minTime = Collections.min(timeList);
			int index = timeList.indexOf(minTime);
			GraphPath<Airport, FlightEdge> leastHopLeastCostLeastTimePath = minList.get(index);
			leastHopJ = new Journey(fromA, toA, leastHopLeastCostLeastTimePath);
		} else {
			int index = costList.indexOf(minCost);
			GraphPath<Airport, FlightEdge> shortestLeastCostPath = kwList.get(index);
			leastHopJ = new Journey(fromA, toA, shortestLeastCostPath);
		}

		return leastHopJ;
		}
	
		   
	
	

	@Override
	public Journey leastHop(String from, String to) throws FlyingPlannerException {
		// TODO Auto-generated method stub
		return leastHop(from, to, costWeightedGraph, hopUnweightedGraph);
		
	}

	@Override
	public Journey leastCost(String from, String to, List<String> excluding)
			throws FlyingPlannerException {
		// TODO Auto-generated method stub
		Graph<Airport, FlightEdge> cloneGraph = new SimpleDirectedWeightedGraph<>(FlightEdge.class);
		populate(airports, flights, cloneGraph, true);

		for (String code : excluding) {
		    cloneGraph.removeVertex(airport(code));
		}

		return leastCost(from, to, cloneGraph);}
	

// done
	@Override
	public Journey leastHop(String from, String to, List<String> excluding)
			throws FlyingPlannerException {
		// TODO Auto-generated method stub
		Graph<Airport, FlightEdge> cloneWeightedGraph = new SimpleDirectedWeightedGraph<>(FlightEdge.class);
		Graph<Airport, FlightEdge> cloneUnweightedGraph = new SimpleDirectedGraph<>(FlightEdge.class);
		populate(airports, flights, cloneWeightedGraph, true);
		populate(airports, flights, cloneUnweightedGraph, false);

		for (String code : excluding) {
		    cloneWeightedGraph.removeVertex(airport(code));
		    cloneUnweightedGraph.removeVertex(airport(code));
		}

		return leastHop(from, to, cloneWeightedGraph, cloneUnweightedGraph);}
		
	

	@Override
	public Set<Airport> directlyConnected(Airport airport) {
		// TODO Auto-generated method stub
	
		
		
		directedAcyclicGraph = new DirectedAcyclicGraph<Airport, FlightEdge>(FlightEdge.class);
		Set<Airport> directAirport = new HashSet<Airport>();
		Collection<Airport> airportList = airports.values();
		for (Airport airports : airportList) {
			directedAcyclicGraph.addVertex(airports);
		}
		Collection<Flight> flightList = flights.values();
		for (Flight flights : flightList) {
			Airport fromA = flights.getFrom();
			Airport toA = flights.getTo();
			if (fromA.getCode().equalsIgnoreCase(airport.getCode())|| toA.getCode().equalsIgnoreCase(airport.getCode())) {
				
				try { directedAcyclicGraph.addEdge(fromA, toA, new FlightEdge(flights));
				} catch (IllegalArgumentException e) {
					if (!fromA.getCode().equalsIgnoreCase(airport.getCode())) {
						if (!directAirport.contains(fromA)) { directAirport.add(fromA);
						}
					} else if (!toA.getCode().equalsIgnoreCase(airport.getCode())) {
						
						if (!directAirport.contains(toA)) { directAirport.add(toA);
			}
			}
			}
			}
			} airport.setDicrectlyConnected(directAirport);
			return directAirport;
	}

	@Override
	public int setDirectlyConnected() {
		// TODO Auto-generated method stub
		int sum = 0;
		for (Airport airPort : airports.values()) {
			int size = directlyConnected(airPort).size();
			directConnections.put(airPort, size);
			sum += size;
		}
		return sum;}
	
	
	@Override
	public int setDirectlyConnectedOrder() {
		// TODO Auto-generated method stub
		betterdirectedAcyclicGraph = new DirectedAcyclicGraph<Airport, FlightEdge>(FlightEdge.class);
		for (Airport airport : airports.values()) {
			betterdirectedAcyclicGraph.addVertex(airport);
			
		}int edgeCount = 0;
		for (Flight flights : flights.values()) {
			Airport from = flights.getFrom();
			Airport to = flights.getTo();
			if (directConnections.get(to) > directConnections.get(from)) {
				betterdirectedAcyclicGraph.addEdge(from, to, new FlightEdge(flights)); edgeCount++;
			}
		}
		return edgeCount;}
		
	
//??????????
	@Override
	public Set<Airport> getBetterConnectedInOrder(Airport airport) {
		// TODO Auto-generated method stub
		
		Set<Airport> betterConnected = betterdirectedAcyclicGraph.getDescendants(airport);
		airport.setDicrectlyConnectedOrder(betterConnected.size());
		return betterConnected;
	}
	
	///??
	private List<String> leastCostFixedHop(String from, String to, Graph<Airport, FlightEdge> weightedGraph, int number) {
		Airport fromA = airport(from);
		Airport toA = airport(to);
		AllDirectedPaths<Airport, FlightEdge> FPaths = new AllDirectedPaths<>(weightedGraph);
		List<GraphPath<Airport, FlightEdge>> FList = FPaths.getAllPaths(fromA, toA, true, number);

		List<String> codeList = new LinkedList<>();
		for (GraphPath<Airport, FlightEdge> graphPath : FList) {
			if (graphPath.getEdgeList().size() != 1) {
				List<Airport> list = graphPath.getVertexList();
				for (int i = 1; i < list.size() - 1; i++) {
					String airportString = list.get(i).getCode();
					if (!airportString.equalsIgnoreCase(from) && !airportString.equalsIgnoreCase(to) && !codeList.contains(airportString)) 
					{
					codeList.add(airportString);
					}}
				}
		}
		return codeList;
	}
	
	@Override
	public String leastCostMeetUp(String at1, String at2) throws FlyingPlannerException {
		// TODO Auto-generated method stub
		List<String> codeList = new LinkedList<String>();
		int num = 0;
		int j = 0;

		do {
			codeList = leastCostFixedHop(at1, at2, costWeightedGraph, j);
			num = codeList.size();
			j++;
		} while (num < 30 && j < 6);

		List<Integer> costList = new LinkedList<Integer>();
		for (String code : codeList) {
			Journey j1 = leastCost(at1, code);
			Journey j2 = leastCost(at2, code);
			int cost = j1.totalCost() + j2.totalCost();
			costList.add(cost);
		}

		int minCost = Collections.min(costList);
		int index = costList.indexOf(minCost);
		String meetStop = codeList.get(index);

		return meetStop;
	}
	
	
	
	private Journey leastTime(String from, String to, Graph<Airport, FlightEdge> weightedGraph, int number)
			throws FlyingPlannerException {
		Airport toA = airport(to);
		Airport fromA = airport(from);
		
		AllDirectedPaths<Airport, FlightEdge> kPaths = new AllDirectedPaths<Airport, FlightEdge>(weightedGraph);
		List<GraphPath<Airport, FlightEdge>> kList = kPaths.getAllPaths(fromA, toA, true, number);

		if (kList.isEmpty()) {
			throw new FlyingPlannerException("Journey not available.");
		}

		GraphPath<Airport, FlightEdge> leastTimeLeastCostPath = null;
		int minTime = Integer.MAX_VALUE;
		int minCost = Integer.MAX_VALUE;

		for (GraphPath<Airport, FlightEdge> path : kList) {
			Journey j = new Journey(fromA, toA, path);
			int duration = j.totalTime();
			int cost = j.totalCost();

			if (duration < minTime || (duration == minTime && cost < minCost)) {
				leastTimeLeastCostPath = path;
				minTime = duration;
				minCost = cost;
			}
		}

		if (leastTimeLeastCostPath == null) {
			throw new FlyingPlannerException("Journey not available.");
		}

		return new Journey(fromA, toA, leastTimeLeastCostPath);
	}
		
	
	
	public Journey leastTime(String from, String to, int num) throws FlyingPlannerException {
		return leastTime(from, to, costWeightedGraph, num);
	}
	
	
	
 
	
	@Override
	public String leastTimeMeetUp(String at1, String at2, String startTime) throws FlyingPlannerException {
		// TODO Auto-generated method stub
		List<String> codeList = new LinkedList<>();
		int j = 0;

		do {
		    codeList = leastCostFixedHop(at1, at2, costWeightedGraph, j++);
		} while (codeList.size() < 30 && j < 6);

		List<Integer> timeList = new LinkedList<>();
		String startTimeF = startTime.substring(0, 2) + ":" + startTime.substring(2);

		for (String code : codeList) {
		    Journey j1 = leastTime(at1, code, 4);
		    Journey j2 = leastTime(at2, code, 4);

		    int time1 = j1.calculateTime(startTimeF, flight(j1.getFlights().get(0)).getFromGMTime()) + j1.totalTime();
		    int time2 = j2.calculateTime(startTimeF, flight(j2.getFlights().get(0)).getFromGMTime()) + j2.totalTime();

		    timeList.add(Math.max(time1, time2));
		}

		return codeList.get(timeList.indexOf(Collections.min(timeList)));}
		

	@Override
	
	
	public String leastHopMeetUp(String at1, String at2) throws FlyingPlannerException {
		// TODO Auto-generated method stub
		List<String> codeList = new LinkedList<>();
		int num = 0;
		int j = 0;

		do {
			codeList = leastCostFixedHop(at1, at2, costWeightedGraph, j);
			num = codeList.size();
			j++;
		} while (num < 30 && j < 6);

		List<StopCostEntry> stopCostList = new ArrayList<>();
		for (String code : codeList) {
			Journey j1 = leastHop(at1, code);
			Journey j2 = leastHop(at2, code);
			int cost = j1.totalCost() + j2.totalCost();
			int stop = j1.totalHop() + j2.totalHop();
			
			stopCostList.add(new StopCostEntry(code, stop, cost));
		}

		stopCostList.sort(new StopCostSorting());
		return  stopCostList.get(0).getCode();

	}
	
	public Set<String> getAirportList() {
		return airports.keySet();
	}
	
	}