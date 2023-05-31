package F28DA_CW2;

import java.util.Set;

public class Airport implements IAirportPartB, IAirportPartC {
	

private String code;
	private String city;
	private String name;
	private Set<Airport> directlyConnectedAirport;
	private int directlyConnectedOrder;
	
	
	public Airport(String c, String ct, String n) {
		this.code = c;
		this.city = ct;
		this.name = n;
		this.directlyConnectedOrder = 0;
		this.directlyConnectedAirport = null;
		
	}
	
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}
	
	public String getCity() {
		return city;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void setDicrectlyConnected(Set<Airport> dicrectlyConnected) {
		// TODO Auto-generated method stub
		this.directlyConnectedAirport = dicrectlyConnected;
		
	}

	
	@Override
	public Set<Airport> getDicrectlyConnected() {
		// TODO Auto-generated method stub
		return directlyConnectedAirport;

	}


	@Override
	public void setDicrectlyConnectedOrder(int order) {
		// TODO Auto-generated method stub
		this.directlyConnectedOrder = order;
	}

	@Override
	public int getDirectlyConnectedOrder() {
		// TODO Auto-generated method stub
		return directlyConnectedOrder;
}
	
	
	}