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
		Objects.requireNonNull(origin,"Origin airport cannot be null");

		NavigableMap<LocalTime, Set<Flight>> flights =  new TreeMap<LocalTime, Set<Flight>>();
		//Create Instance of FlightSchedule
		return new FlightGroup(origin, flights);
	}

	/**
	 * Adds a flight to the flight group
	 * If the flight time already exists, the flight is appended onto the set at that index
	 * Otherwise, the flight is added to a new set and put into the map
	 * @param flight
	 * @return true if flight was added, false if flight was not
	 */
	public final boolean add(Flight flight) {
		checkErrors(flight);
		LocalTime deptTime = flight.getFlightSchedule().getDepartureTime();
		return flights.computeIfAbsent(deptTime, flightList -> new HashSet<Flight>()).add(flight);
	}

	/**
	 * Removes flight from flight group if it exists in this flight group
	 * If the flight does not exist, an IllegalArgumentException is thrown
	 * @param flight is the flight to be removed
	 * @return true if flight was removed, throws error if the flight was not
	 */
	//TODO use get or default | im so confuesd
	public final boolean remove(Flight flight) {
		checkErrors(flight);
		LocalTime deptTime = flight.getFlightSchedule().getDepartureTime();
		
		BiFunction<LocalTime, Set<Flight>, Set<Flight>> removeFlight = (depart, flightSet) -> flightSet.remove(flight) ? flightSet : null;
		
		if(flights.computeIfPresent(deptTime, removeFlight) == null) {
			return false;
		} else {
			return !flights.get(deptTime).contains(flight);
		}
		
	}
	
	/**
	 * This method checks for errors in the add and remove flight method.
	 * @param flight
	 */
	private final void checkErrors(Flight flight) {
		//Throws exception if inputs are null
		Objects.requireNonNull(flight,"Flight cannot be null");
		//Throws exception if flight originated from a different airport
		if (!flight.origin().equals(this.origin)) {
			throw new IllegalArgumentException("This flight does not originate from this airport.");
		}
		
	}

	/**
	 * Method returns a set of all flights departing at or after a certain departure time
	 * @param departureTime is the time when the flight leaves the airport
	 * @return returnSet, a HashSet of flights containing all the flights leaving after a given time
	 */

	public final Set<Flight> flightsAtOrAfter(LocalTime departureTime){
		//Throws exception if null inputs are received
		Objects.requireNonNull(departureTime,"Departure time cannot be null");

		Set<Flight> returnSet = new HashSet<Flight>();

		//retrieves all sets for departure times after the departure time inputed, inclusive
		flights.tailMap(departureTime, true)
				.values()
				.stream()
				.forEach(returnSet::addAll);

		return returnSet;
	}

}