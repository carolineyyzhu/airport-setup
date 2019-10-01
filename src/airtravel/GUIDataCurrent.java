package airtravel;

import java.util.ArrayList;
import java.util.List;

public class GUIDataCurrent {
	static List<Airport> airports = new ArrayList<Airport>();
	
	static Boolean addAirport(Airport airport) {
		GUIHome.addAirportTextUpdate(airport);
		return airports.add(airport);
	}
	
	static Boolean containsAirport (Airport airport) {
		return airports.contains(airport);
	}

}
