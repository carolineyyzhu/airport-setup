package airtravel;

import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

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
		NavigableMap<LocalTime, Set<Flight>> flights =  new TreeMap<LocalTime, Flight>();
		if (departureTime == null || arrivalTime == null) {
			throw new NullPointerException("Invalid Input Values");
		}
		//Check if arrivalTime precedes departureTime
		if (arrivalTime.precedes(departureTime)) {
			throw new IllegalArgumentException("Arrival time precedes departure time");
		}
		//Create Instance of FlightSchedule
		FlightSchedule retVal = new FlightSchedule(departureTime, arrivalTime);
		return retVal;
	}
	

}
