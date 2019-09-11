package airtravel;

import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.time.Duration;
import java.time.LocalTime;

public final class FlightGroup  {
	
	private final Airport origin;
	private final NavigableMap<LocalTime, Set<Flight>> flights;
	
	private FlightGroup(Airport origin, NavigableMap flights) {
		this.origin = origin;
		this.flights = flights;
	}
	
	/**
	 * Public constructor: Catches any null values
	 * @param departureTime: The departure time
	 * @param arrivalTime: The arrival time
	 * @return the created Flight Schedule
	 * @throws IllegalArgumentException if receives null values
	 */
	public static final FlightGroup of(Airport origin) {
		//Check for null inputs
		NavigableMap<LocalTime, Set<Flight>> flights =  new TreeMap<LocalTime, Set<Flight>>();
		if (origin == null) {
			throw new NullPointerException("Invalid Input Values");
		}
		//Create Instance of FlightSchedule
		FlightGroup retVal = new FlightGroup(origin, flights);
		return retVal;
	}
	
	//unfinished
	public final boolean add(Flight flight) {
		 
		 
		return false;
		 
	 }
	 
	//unfinished 
	public final boolean remove(Flight flight) {
		 
		 return false;
	 }
	 
	//unfinished
	public final Set<Flight> flightsAtOrAfter(LocalTime departureTime){
		
		 return null;
		 
	 }

}
