package airtravel;

import java.util.*;
import java.time.LocalTime;

public final class FlightGroup  {

	private final Airport origin;
	private final NavigableMap<LocalTime, Set<Flight>> flights;

	private FlightGroup(Airport origin, NavigableMap flights) {
		this.origin = origin;
		this.flights = flights;
	}

	/**
	 * Builder method
	 * @param origin is the airport of origin
	 * @return
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

	/**
	 * Adds
	 * @param flight
	 * @return
	 */
	public final boolean add(Flight flight) {
		boolean retVal = false;
		LocalTime deptTime = flight.getFlightSchedule().getDepartureTime();
		if(flights.containsKey(deptTime)) {
			flights.get(deptTime).add(flight);
			retVal = true;
		} else {
			Set<Flight> newFlight = new HashSet<Flight>();
			newFlight.add(flight);
			flights.put(deptTime, newFlight);
			retVal = true;
		}
		return retVal;
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