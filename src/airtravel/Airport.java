package airtravel;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class creates airport objects with codes and min connection times.
 *
 */
public final class Airport implements Comparable<Airport> {
	private final String code;
	private final Duration connectionTimeMin;
	private final FlightGroup outFlights;

	/**
	 * Constructor
	 * @param code: A string representation of the airport code
	 * @param connectionTimeMin: the min connection time of a flight
	 */
	private Airport(String code, Duration connectionTimeMin) {
		this.code = code;
		this.connectionTimeMin = connectionTimeMin;
		this.outFlights = FlightGroup.of(this);
	}
	
	/**
	 * Public constructor: Catches any null values
	 * @param code: A string representation of the airport code
	 * @param connectionTimeMin: the min connection time of a flight
	 * @return the created airport
	 */
	public static final Airport of(String code, Duration connectionTimeMin) {
		Objects.requireNonNull(code,"Airport code cannot be null");
		Objects.requireNonNull(connectionTimeMin,"Connection time minimum cannot be null");

		return new Airport(code, connectionTimeMin);
	}
	
	//Adds a flight: returns true if flight was added
	public final boolean addFlight(Flight flight) {
		Objects.requireNonNull(flight,"Flight cannot be null");
		return outFlights.add(flight);
	}
	
	//Removes a flight: returns true if the flight was removed
	public final boolean removeFlight(Flight flight) {
		Objects.requireNonNull(flight,"Flight cannot be null");
		return outFlights.remove(flight);
	}
	
	//Getter for Code
	public final String getCode() {
		return code;
	}
	
	//Getter for connectionTimeMin
	public final Duration getConnectionTimeMin() {
		return connectionTimeMin;
	}
	
	/**
	 * Method to find the set of available flights 
	 * @param departureTime is the departure time
	 * @param fareClass is the fare class
	 * @return a set of flights
	 */
	public Set<Flight> availableFlights(LocalTime departureTime, FareClass fareClass){
		Objects.requireNonNull(departureTime,"Departure time cannot be null");
		Objects.requireNonNull(fareClass,"Fare class cannot be null");

		return outFlights.flightsAtOrAfter(departureTime)
				.stream()
				.filter(n -> n.hasSeats(fareClass))
				.collect(Collectors.toSet());
		
	}
	
	//Overrides equals method
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		Airport other = (Airport) obj;
		return (this == obj || code.equals(other.code));
	}

	//Overrides hashCode
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		return prime * result + ((code == null) ? 0 : code.hashCode());
	}

	//Overrides toString
	@Override
	public String toString() {
		return "Airport code =" + code;
	}

	//Overrides compareTo
	@Override
	public int compareTo(Airport airport) {
		return code.compareTo(airport.getCode());
	}
}
