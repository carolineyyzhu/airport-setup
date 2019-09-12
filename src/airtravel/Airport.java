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
	
	//CHECK NOTE HERE : also override this, hashcode, and tostring
	//Override Equals method based on code
	public final boolean equals(Airport airport) {
		//This is repeated code: create a helper method
		if (airport == null) {
			throw new NullPointerException("Null inputs were received");
		}
		return this.code.equals(airport.code);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airport other = (Airport) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (connectionTimeMin == null) {
			if (other.connectionTimeMin != null)
				return false;
		} else if (!connectionTimeMin.equals(other.connectionTimeMin))
			return false;
		if (outFlights == null) {
			if (other.outFlights != null)
				return false;
		} else if (!outFlights.equals(other.outFlights))
			return false;
		return true;
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
