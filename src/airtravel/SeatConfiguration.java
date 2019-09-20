package airtravel;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Function;

/**
 * This class creates a seat configuration using enum maps
 *
 */
public final class SeatConfiguration {
	
	private final EnumMap<SeatClass, Integer> seats;
	
	//private constructor
	//TODO: shorten line
	private SeatConfiguration(EnumMap<SeatClass, Integer> seats) {
		this.seats = new EnumMap<SeatClass, Integer>(seats);
	}
	
	//builder method given a seat configuration
	public static final SeatConfiguration of(SeatConfiguration seatConfiguration) {
		Helpers.nullCheck(seatConfiguration);
		return new SeatConfiguration(seatConfiguration.seats);
	}
	
	//builder method given an enum map
	//TODO: Copy only in the builder or in constructor
	//TODO: build methods can call each other
	public static final SeatConfiguration of(EnumMap<SeatClass, Integer> seats) {
		Helpers.nullCheck(seats);
		return new SeatConfiguration(new EnumMap<SeatClass, Integer>(seats));
	}

	//returns number of seats
	public final Integer seats(SeatClass seatClass) {
		Helpers.nullCheck(seatClass);
		if (seats.get(seatClass) == null || seats.get(seatClass) < 0) {
			return 0;
		} else {
			return seats.get(seatClass);
		}
		
	}
	
	//sets the seats to the given value
	//change back to primitive int
	public final Integer setSeats(SeatClass seatClass, Integer numSeats) {
		Helpers.nullCheck(seatClass, numSeats);
		return seats.put(seatClass, numSeats);
	}
	
	//checks if seats exist
	//TODO: .anyMatch(); 
	public final boolean hasSeats() {
		List <Integer> values = (List<Integer>) seats.values();
		return (values.stream().mapToInt(Integer::intValue).sum() > 0);

	}

}
