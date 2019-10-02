package airtravel;

import java.util.ArrayList;
import java.util.List;

public class GUIDataCurrent {
	static List<Airport> airports = new ArrayList<Airport>();
	static List<Flight> flights = new ArrayList<Flight>();
	
	static Airport findAirportByName(String name) {
		for (Airport airport: airports) {
			if (airport.getCode().equals(name)){
				return airport;
			}
		}
		return null;
	}
	
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
	
	static Boolean addFlight(Flight flight) {
		GUIHome.addFlightTextUpdate(flight);
		return flights.add(flight);
	}
	
	static Boolean removeFlight(Flight flight) {
		Boolean isSuccessful = flights.remove(flight);
		GUIHome.removeFlightTextUpdate();
		return isSuccessful;
	}
	
}
