package airtravel;

import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * This class creates a seat configuration using enum maps
 *
 */
public final class SeatConfiguration {
	
	private final EnumMap<SeatClass, Integer> seats;
	
	//private constructor
	private SeatConfiguration(EnumMap<SeatClass, Integer> seats) {
		this.seats = seats;
	}
	
	/**
	 * This build method returns a new SeatConfiguration given a seatConfiguration
	 * @param seatConfiguration: seat configuration to copy
	 * @return new seat configuration
	 */
	public static final SeatConfiguration of(SeatConfiguration seatConfiguration) {
        Objects.requireNonNull(seatConfiguration,"seatConfiguration input cannot be null");

		return SeatConfiguration.of(seatConfiguration.seats);
	}
	
	/**
	 * This build method creates a new seat configuration
	 * @param seats enum map of seats to use to create the configuration
	 * @return a new seat configuration
	 */
	public static final SeatConfiguration of(EnumMap<SeatClass, Integer> seats) {
		Objects.requireNonNull(seats,"EnumMap seats cannot be null");
		return new SeatConfiguration(new EnumMap<SeatClass, Integer>(seats));
	}

	//returns number of seats
	public final Integer seats(SeatClass seatClass) {
		Objects.requireNonNull(seatClass,"Seat class cannot be null");
		Integer seats = this.seats.getOrDefault(seatClass, 0);
		if (seats < 0) {
			seats = 0;
		}
		return seats;
	}
	
	//sets the seats to the given value
	public final int setSeats(SeatClass seatClass, Integer numSeats) {
		if (seatClass == null || numSeats == null) {
			return 0;
		}

		return seats.put(seatClass, numSeats);
	}
	
	//checks if seats exist
	public final boolean hasSeats() {
		return (seats.values().stream().anyMatch(n -> n>0));

	}

}
