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
		Helpers.nullCheck(origin);
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
		Helpers.nullCheck(flight);
		//Throws exception if flight originated from a different airport
		if (!flight.origin().equals(this.origin)) {
			throw new IllegalArgumentException("This flight did not originate from this airport.");
		}
		LocalTime deptTime = flight.getFlightSchedule().getDepartureTime();
		flights.computeIfAbsent(deptTime, flightList -> new HashSet<Flight>()).add(flight);
		return flights.get(deptTime).contains(flight);
	}

	/**
	 * Removes flight from flight group if it exists in this flight group
	 * If the flight does not exist, an IllegalArgumentException is thrown
	 * @param flight is the flight to be removed
	 * @return true if flight was removed, throws error if the flight was not
	 */
	public final boolean remove(Flight flight) {
		//Throws exception if inputs are null
		Helpers.nullCheck(flight);
		//Throws exception if flight originated from a different airport
		if (!flight.origin().equals(this.origin)) {
			throw new IllegalArgumentException("This flight does not originate from this airport.");
		}

		LocalTime deptTime = flight.getFlightSchedule().getDepartureTime();
		BiFunction<LocalTime, Set<Flight>, Set<Flight>> removeFlight = (depart, flightSet) -> flightSet.remove(flight) ? flightSet : null;
		if(flights.computeIfPresent(deptTime, removeFlight) == null)
			throw new IllegalArgumentException("This flight does not exist in this flight group.");
		return !flights.get(deptTime).contains(flight);
	}

	/**
	 * Method returns a set of all flights departing at or after a certain departure time
	 * @param departureTime is the time when the flight leaves the airport
	 * @return returnSet, a HashSet of flights containing all the flights leaving after a given time
	 */

	public final Set<Flight> flightsAtOrAfter(LocalTime departureTime){
		//Throws exception if null inputs are received
		Helpers.nullCheck(departureTime);
			
		HashSet<Flight> returnSet = new HashSet<Flight>();

		//new hashset of the flightsets
		HashSet<Set<Flight>> setOfFlightSets = new HashSet<Set<Flight>>();

		//retrieves all sets for departure times after the departure time inputted, inclusive
		setOfFlightSets = (HashSet<Set<Flight>>) flights.tailMap(departureTime, true);
		//map.values (check later)

		//iterator runs through the sets within the Hashset and adds all of the entries into the returnSet
		Iterator<Set<Flight>> flightSets = setOfFlightSets.iterator();
		while(flightSets.hasNext()) {
			HashSet<Flight> indFlights = (HashSet<Flight>) flightSets.next();
			returnSet.addAll(indFlights);
		}
		return returnSet;
	}

}