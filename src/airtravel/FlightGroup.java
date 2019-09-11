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
	 * Adds a flight to the flight group
	 * If the flight time already exists, the flight is appended onto the set at that index
	 * Otherwise, the flight is added to a new set and put into the map
	 * @param flight
	 * @return true if flight was added, false if flight was not
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

	/**
	 * Removes flight from flight group if it exists in this flight group
	 * If the flight does not exist, an IllegalArgumentException is thrown
	 * @param flight is the flight to be removed
	 * @return true if flight was removed, false if not
	 */
	public final boolean remove(Flight flight) {
		boolean retVal = false;
		LocalTime deptTime = flight.getFlightSchedule().getDepartureTime();
		if(flights.containsKey(deptTime)) {
			HashSet currentSet = (HashSet) flights.get(deptTime);
			if(currentSet.contains(flight)) {
				currentSet.remove(flight);
				retVal = true;
			}
		} else {
			throw new IllegalArgumentException("This flight does not exist in this flight group.");
		}
		return retVal;
	}

	//unfinished
	public final Set<Flight> flightsAtOrAfter(LocalTime departureTime){
		return null;
	}

}