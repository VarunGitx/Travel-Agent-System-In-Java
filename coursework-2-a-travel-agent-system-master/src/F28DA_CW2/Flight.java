package F28DA_CW2;

public class Flight implements IFlight {
	

		private String flightCode;
		private Airport toAP;
		private Airport fromAP;
		private String toGMTime;
		private String fromGMTime;
		private int cost;
		
		
		public Flight(String fc, Airport from, String fromTime, Airport to, String toTime, int c) {
			this.flightCode = fc;
			this.fromAP = from;
			this.fromGMTime = fromTime;
			this.toAP = to;
			this.toGMTime = toTime;
			this.cost = c;
		}
		

		@Override
		public String getFlightCode() {
			// TODO Auto-generated method stub
			return flightCode;
		}

		@Override
		public Airport getTo() {
			// TODO Auto-generated method stub
			return toAP;
		}

		@Override
		public Airport getFrom() {
			// TODO Auto-generated method stub
			return fromAP;
		}

		
		@Override
		public String getFromGMTime() {
			// TODO Auto-generated method stub
			return fromGMTime.substring(0, 2) + ":" + fromGMTime.substring(2);
		}

		@Override
		public String getToGMTime() {
			// TODO Auto-generated method stub
			return toGMTime.substring(0, 2) + ":" + toGMTime.substring(2);
		}

		@Override
		public int getCost() {
			// TODO Auto-generated method stub
			return cost;


	}
		
		
	}