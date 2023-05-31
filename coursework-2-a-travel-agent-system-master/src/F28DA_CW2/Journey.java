package F28DA_CW2;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;


public class Journey implements IJourneyPartB<Airport, Flight>, IJourneyPartC<Airport, Flight> {
	private Airport fromAirport;
	private Airport toAirport;
	private GraphPath<Airport, FlightEdge> graphPath;
	
	public Journey(Airport from, Airport to, GraphPath<Airport, FlightEdge> gp) {
		this.fromAirport = from;
		this.toAirport = to;
		this.graphPath = gp;
	}

	@Override
	public List<String> getStops() {
		// TODO Auto-generated method stub
		List<Airport> airportList = graphPath.getVertexList();
		List<String> strList = new LinkedList<>();
		for (Airport a : airportList) { strList.add(a.getCode());
		}
		return strList;
		
	}

	//**done
	@Override
	public List<String> getFlights() {
		// TODO Auto-generated method stub
		List<FlightEdge> flightList = graphPath.getEdgeList();
	    List<String> flightCodes = new LinkedList<>();
	    for (FlightEdge flightEdge : flightList) {
	        flightCodes.add(flightEdge.getFlight().getFlightCode());
	    }
	    return flightCodes;
	}
	
	

	@Override
	public int totalHop() {
		// TODO Auto-generated method stub
		return getFlights().size();
	}

	
	@Override
	public int totalCost() {
		// TODO Auto-generated method stub
		List<FlightEdge> flightList = graphPath.getEdgeList();
	    int totalCost = 0;
	    for (FlightEdge flightEdge : flightList) {
	        totalCost += flightEdge.getFlight().getCost();
	    }
	    return totalCost;
	}
		
		


	@Override
	public int airTime() {
		// TODO Auto-generated method stub
		int totalMin = 0;
	    for (FlightEdge flightEdge : graphPath.getEdgeList()) {
	        totalMin += calculateTime(flightEdge.getFlight().getFromGMTime(), flightEdge.getFlight().getToGMTime());
	    }
	    return totalMin;
	}
		
	
	
	
	@Override
	public int connectingTime() {
		// TODO Auto-generated method stub
		List<FlightEdge> flightList = graphPath.getEdgeList();
		int totalMin = 0;

		for (int i = 0; i < flightList.size() - 1; i++) {
		    Flight ff1 = flightList.get(i).getFlight();
		    Flight ff2 = flightList.get(i + 1).getFlight();
		    totalMin += calculateTime(ff1.getToGMTime(), ff2.getFromGMTime());
		}

		return totalMin;}
		
		
	

	int calculateTime(String from, String to) {
		// TODO Auto-generated method stub
		
		
		int h1 = Integer.parseInt(from.substring(0, 2));
	    int m1 = Integer.parseInt(from.substring(from.length() - 2));
	    int h2 = Integer.parseInt(to.substring(0, 2));
	    int m2 = Integer.parseInt(to.substring(to.length() - 2));

	    int diff = (h2 * 60 + m2) - (h1 * 60 + m1);

	    if (diff < 0) {
	        diff += 1440; // 24 hour in min
	    }

	    return diff;
	}
		
		
	@Override
	public int totalTime() {
		// TODO Auto-generated method stub
		return airTime() + connectingTime();
	}

	@Override
	public int totalAirmiles() {
		// TODO Auto-generated method stub
		int totalCost = this.totalCost();
		int airTime = this.airTime();
		
		int airmiles = (int) (totalCost * 0.03 * airTime);
		return airmiles;
	}

	
	//
	public String printTime(int minute) {
		int hours = minute / 60;
		int minutes = minute % 60;
		String time = hours + " Hours " + minutes + " Min";
		return time;
	}

	
	
public void printJourney() {
	
	System.out.printf("Journey for %s (%s) to %s (%s)%n",
	        fromAirport.getCity(), fromAirport.getCode(), toAirport.getCity(), toAirport.getCode());
	System.out.println("                                                   ");
	String format = "%-4s %-20s %-6s %-8s %-20s %-6s %n";
	System.out.printf(format, "Leg", "Leave", "At", "On", "Arrive", "At");
	int i = 1;
	for (FlightEdge flightEdge : graphPath.getEdgeList()) {
	    Flight flight = flightEdge.getFlight();
	    String from = flight.getFrom().getCity() + " -" + flight.getFrom().getCode() ;
	    String to = flight.getTo().getCity() + " -" + flight.getTo().getCode() ;
	    System.out.printf(format, i++, from, flight.getFromGMTime(), flight.getFlightCode(), to, flight.getToGMTime());
	}
	System.out.println();
	System.out.printf("%-22s - £%s%n", "Total Journey Cost", totalCost());
	System.out.printf("%-22s - %s%n", "Total Time in the Air", printTime(airTime()));
	if (connectingTime() != 0) {
	    System.out.printf("%-22s - %s%n", "Total Connecting Time", printTime(connectingTime()));
	}
}
	


private void printJourney(Journey jor) {
	Airport fromA = jor.fromAirport;
	Airport toA = jor.toAirport;
	String from = fromA.getCity() + " - " + fromA.getCode() ;
	String to = toA.getCity() + " - " + toA.getCode() ;
	List<FlightEdge> flightList = jor.graphPath.getEdgeList();

	if (!flightList.isEmpty()) {
		System.out.printf("Journey for %s to %s%n", from, to);
		System.out.println("                                          ");
		System.out.printf("%-4s %-20s %-6s %-8s %-20s %-6s %n", "Leg", "Leave", "At", "On", "Arrive", "At");
		
		int i = 1;
		
		for (FlightEdge f : flightList) {
			Flight ff = f.getFlight();
			Airport aFrom = ff.getFrom();
			Airport aTo = ff.getTo();
			String fromAP = aFrom.getCity() + " - " + aFrom.getCode() ;
			String toAP = aTo.getCity() + " - " + aTo.getCode() ;
			
			System.out.printf("%-4s %-20s %-6s %-8s %-20s %-6s %n", i, fromAP, ff.getFromGMTime(), ff.getFlightCode(), toAP, ff.getToGMTime());
			
			i++;
		}
		
		
		System.out.println();
		System.out.printf("%-22s - £%s%n", "Total Journey Cost", jor.totalCost());
		System.out.printf("%-22s - %s%n", "Total Time in the Air", jor.printTime(jor.airTime()));
		
		if (jor.connectingTime() != 0) {
			System.out.printf("%-22s - %s%n", "Total Connecting Time", jor.printTime(jor.connectingTime()));
		}
		
		System.out.printf("%-22s - %s%n", "Total Journey Time", jor.printTime(jor.totalTime()));
		System.out.println("                                                  ");
	}
	System.out.printf("%-22s = %s\n", "Total Air Miles", totalAirmiles());
}

	
}












