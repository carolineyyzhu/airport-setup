package airtravel;

import java.time.Duration;
import java.time.LocalTime;

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
		//Check for null inputs
		if (code == null || connectionTimeMin == null) {
			throw new NullPointerException("Null inputs were received");
		}
		//Create Instance of Airport
		Airport newAirport = new Airport(code, connectionTimeMin);
		return newAirport;
	}
	
	//Adds a flight: returns true if flight was added
	public final boolean addFlight(Flight flight) {
		if (flight == null) {
			throw new NullPointerException("Null inputs were received");
		}
		return outFlights.add(flight);
	}
	
	//Removes a flight: returns true if the flight was removed
	public final boolean removeFlight(Flight flight) {
		if (flight == null) {
			throw new NullPointerException("Null inputs were received");
		}
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
	
	//Overrides equals method
	@Override
	public boolean equals(Object obj) {
		if(obj == null || code == null || connectionTimeMin == null || outFlights == null) {
			throw new NullPointerException("Null values processed, invalid");
		}
		boolean equal = false;
		Airport other = (Airport) obj;
		if (this == obj)
			equal = true;
		if(code.equals(other.code) && connectionTimeMin.equals(other.connectionTimeMin) && outFlights.equals(other.outFlights))
			equal = true;
		return equal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((connectionTimeMin == null) ? 0 : connectionTimeMin.hashCode());
		result = prime * result + ((outFlights == null) ? 0 : outFlights.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Airport code =" + code;
	}

	@Override
	public int compareTo(Airport airport) {
		return code.compareTo(airport.getCode());
	}
}
