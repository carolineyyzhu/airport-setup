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
	private Airport(String code,Duration connectionTimeMin, FlightGroup outFlights) {
		this.code = code;
		this.connectionTimeMin = connectionTimeMin;
		this.outFlights = outFlights;
		
	}
	
	/**
	 * Public constructor: Catches any null values
	 * @param code: A string representation of the airport code
	 * @param connectionTimeMin: the min connection time of a flight
	 * @return the created airport
	 */
	public static final Airport of(String code, Duration connectionTimeMin, FlightGroup outFlights) {
		//Check for null inputs
		if (code == null | connectionTimeMin == null | outFlights == null) {
			throw new NullPointerException("Null inputs were recieved");
		}
		//Create Instance of Airpoirt
		Airport retVal = new Airport(code, connectionTimeMin, outFlights);
		return retVal;
	}
	
	//unfinished
	public final boolean addFlight(Flight flight) {
		
		return true;
	}
	
	//unfinished
	public final boolean removeFlight(Flight flight) {
		return true;
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
		return this.code.compareTo(airport.code);
	}

}
