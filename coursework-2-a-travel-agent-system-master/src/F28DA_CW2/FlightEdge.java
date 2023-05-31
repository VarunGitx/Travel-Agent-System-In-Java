package F28DA_CW2;

import org.jgrapht.graph.DefaultEdge;

public class FlightEdge extends DefaultEdge {
	private Flight flight;


	public FlightEdge(Flight f) {
		this.flight = f;
	}

	public Flight getFlight() {
		return flight;
	}

	

	

}