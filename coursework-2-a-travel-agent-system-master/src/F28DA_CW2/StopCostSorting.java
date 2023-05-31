package F28DA_CW2;

import java.util.Comparator;

public class StopCostSorting implements Comparator <StopCostEntry> {

	@Override
	public int compare(StopCostEntry s1, StopCostEntry s2) {

		
		int stopCompare = Integer.compare(s1.getStop(), s2.getStop());
	    if (stopCompare != 0) {
	        return stopCompare;
	    }
	    return Integer.compare(s1.getCost(), s2.getCost());
	}
}
		

		