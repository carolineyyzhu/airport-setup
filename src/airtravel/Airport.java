package airtravel; /**
 * 
 */

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
		Airport retVal = new Airport(code, connectionTimeMin);
		return retVal;
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
	
	//Override Equals method based on code
	public final boolean equals(Airport airport) {
		if (airport == null) {
			throw new NullPointerException("Null inputs were received");
		}
		return this.code.equals(airport.code);
	}
	
	//Override hash code based on code
	public final int hashCode() {
		return this.code.hashCode();
	}
	
	//Override to string based on code
	public final String toString() {
		return this.code.toString();
	}

	//Override compare to based on code
	@Override
	public final int compareTo(Airport airport) {
		if (airport == null) {
			throw new NullPointerException("Null inputs were received");
		}
		return this.code.compareTo(airport.code);
	}

}
