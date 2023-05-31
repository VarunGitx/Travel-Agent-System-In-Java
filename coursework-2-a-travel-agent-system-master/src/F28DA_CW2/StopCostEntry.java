package F28DA_CW2;

public class StopCostEntry {
	private String code;
	private int stop;
	private int cost;

	/**
	 * Creates a new entry with the given meet up airport code, the number of stops
	 * or connections and the total cost in the journey to the meet up airport.
	 */
	public StopCostEntry(String cd, int s, int c) {
		this.code = cd;
		this.stop = s;
		this.cost = c;
	}
	

	
	public String getCode() {
		return code;
	}

	public int getStop() {
		return stop;
	}


	public int getCost() {
		return cost;
	}

	
	

}
