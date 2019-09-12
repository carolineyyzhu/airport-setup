package airtravel;

import java.util.*;
import java.time.LocalTime;
import java.util.function.BiFunction;

public final class FlightGroup  {

	private final Airport origin;
	private final NavigableMap<LocalTime, Set<Flight>> flights;

	private FlightGroup(Airport origin, NavigableMap<LocalTime, Set<Flight>> flights) {
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
			throw new IllegalArgumentException("This flight did not originate from this airport.");
		}
		//Throws exception if flight is null
		if (flight == null) {
			throw new NullPointerException("Null inputs were received");
		}
		
		LocalTime deptTime = flight.getFlightSchedule().getDepartureTime();

		return flights.computeIfAbsent(deptTime, k -> new HashSet<Flight>()).add(flight);
		
	}

	/**
	 * Removes flight from flight group if it exists in this flight group
	 * If the flight does not exist, an IllegalArgumentException is thrown
	 * @param flight is the flight to be removed
	 * @return true if flight was removed, throws error if the fligth was not
	 */
	public final boolean remove(Flight flight) {
		//Throws exception if flight originated from a different airport
		if (!flight.origin().equals(this.origin)) {
			throw new IllegalArgumentException("This flight does not originate from this airport.");
		}
		//Throws exception if inputs are null
		if (flight == null) {
			throw new NullPointerException("Null inputs were received");
		}
		
		
		LocalTime deptTime = flight.getFlightSchedule().getDepartureTime();
		BiFunction<LocalTime, Set<Flight>, Set<Flight>> removeFlight = (depart, flightSet) -> flightSet.remove(flight) ? flightSet : null;
		if(flights.computeIfPresent(deptTime, removeFlight) == null) {
			throw new IllegalArgumentException("This flight does not exist in this flight group.");
		} else {
			//If the flight successfully removed the value and did not throw an error, then there is no need for an else statement
		}
		
		return true;

		
	}

	/**
	 * Method returns a set of all flights departing at or after a certain departure time
	 * @param departureTime is the time when the flight leaves the airport
	 * @return returnSet, a HashSet of flights containing all the flights leaving after a given time
	 */

	
	@SuppressWarnings("unchecked")
	public final Set<Flight> flightsAtOrAfter(LocalTime departureTime){
		//Throws exception if null inputs are received
		if (departureTime == null) {
			throw new NullPointerException("Null inputs were received");
		}
			
		HashSet<Flight> returnSet = new HashSet<Flight>();
		
		HashSet<Set<Flight>> setOfFlightSets = new HashSet<Set<Flight>>();
		setOfFlightSets = (HashSet<Set<Flight>>) flights.tailMap(departureTime, true);
		Iterator<Set<Flight>> flightSets = setOfFlightSets.iterator();
		
		while(flightSets.hasNext()) {
			HashSet<Flight> indFlights = (HashSet<Flight>) flightSets.next();
			returnSet.addAll(indFlights);
		}
		return returnSet;
	}

}