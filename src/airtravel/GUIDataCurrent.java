package airtravel;

import java.util.ArrayList;
import java.util.List;

public class GUIDataCurrent {
	static List<Airport> airports = new ArrayList<Airport>();
	
	static Boolean addAirport(Airport airport) {
		GUIHome.addAirportTextUpdate(airport);
		return airports.add(airport);
	}
	
	static Boolean removeAirport(Airport airport) {
		
		Boolean isSuccessful = airports.remove(airport);
		GUIHome.removeAirportTextUpdate();
		return isSuccessful;
	}
	
	static Boolean containsAirport (Airport airport) {
		return airports.contains(airport);
	}
	
}
