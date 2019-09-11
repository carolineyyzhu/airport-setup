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
	 * @return new FlightGroup object
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
		//Throws exception if flight originated from a different airport
		if (!flight.origin().equals(this.origin)) {
			throw new IllegalArgumentException("This flight originate from this airport.");
		}
		//Throws exception if flight is null
		if (flight == null) {
			throw new NullPointerException("Null inputs were received");
		}
			
		
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
		//Throws exception if flight originated froma different airport
		if (!flight.origin().equals(this.origin)) {
			throw new IllegalArgumentException("This flight originate from this airport.");
		}
		//Throws exception if inputs are null
		if (flight == null) {
			throw new NullPointerException("Null inputs were received");
		}
		
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

	/**
	 * Method returns a set of all flights departing at or after a certain departure time
	 * @param departureTime is the time when the flight leaves the airport
	 * @return returnSet, a HashSet of flights containing all the flights leaving after a given time
	 */

	public final Set<Flight> flightsAtOrAfter(LocalTime departureTime){
		//Throws exception if null inputs are recieved
		if (departureTime == null) {
			throw new NullPointerException("Null inputs were received");
		}
			
		HashSet setOfFlightSets = new HashSet<Set<Flight>>();
		setOfFlightSets = (HashSet) flights.tailMap(departureTime, true);
		Iterator flightSets = setOfFlightSets.iterator();
		HashSet returnSet = new HashSet<Flight>();
		while(flightSets.hasNext()) {
			HashSet indFlights = (HashSet) flightSets.next();
			returnSet.addAll(indFlights);
		}
		return returnSet;
	}

}