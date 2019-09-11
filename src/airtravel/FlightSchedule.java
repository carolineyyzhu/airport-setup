/**
 * 
 */
package airtravel;

import java.time.Duration;
import java.time.LocalTime;

/**
 * This class creates flight schedules 
 *
 */
public final class FlightSchedule {
	
	private final LocalTime departureTime;
	private final LocalTime arrivalTime;
	
	/**
	 * Constructor
	 * @param departureTime: The departure time
	 * @param arrivalTime: The arrival time
	 */
	private FlightSchedule(LocalTime departureTime, LocalTime arrivalTime) {
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}
	
	/**
	 * Public constructor: Catches any null values
	 * @param departureTime: The departure time
	 * @param arrivalTime: The arrival time
	 * @return the created Flight Schedule
	 * @throws IllegalArgumentException if receives null values
	 */
	public static final FlightSchedule of(LocalTime departureTime, LocalTime arrivalTime) {
		//Check for null inputs
		if (departureTime == null || arrivalTime == null) {
			throw new NullPointerException("Invalid Input Values");
		}
		//Check if arrivalTime precedes departureTime
		if (arrivalTime.isBefore(departureTime)) {
			throw new IllegalArgumentException("Arrival time precedes departure time");
		}
		//Create Instance of FlightSchedule
		FlightSchedule retVal = new FlightSchedule(departureTime, arrivalTime);
		return retVal;
	}
	/**
	 * Method to determine id the flight is short
	 * @param durationMax the max duration of the flight
	 * @return True if the flight is short and false if it is long
	 */
	public final boolean isShort(Duration durationMax) {
		
		//Return val
		Boolean isShort = false;
		
		//Find the flight duration of this flight
		Duration duration = Duration.between(departureTime, arrivalTime);
		
		//Compare max duration to this duration
		if (duration.compareTo(durationMax) < 0) {
			isShort = true;
		}
		
		return isShort;
	}
	
	//Getter for departure time
	public final LocalTime getDepartureTime() {
		return departureTime;
	}
	
	//Getter for arrival time
	public final LocalTime getArrivalTime() {
		return arrivalTime;
	}
}
