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
		this.seats = new EnumMap<SeatClass, Integer>(seats);
	}
	
	//builder method given a seat configuration
	public static final SeatConfiguration of(SeatConfiguration seatConfiguration) {
		return SeatConfiguration.of(seatConfiguration.seats);
	}
	
	//builder method given an enum map
<<<<<<< HEAD
	//TODO: Copy only in the builder or in constructor
=======
>>>>>>> parent of fa3ea9f... Comments
	public static final SeatConfiguration of(EnumMap<SeatClass, Integer> seats) {
		Objects.requireNonNull(seats,"Null input received.");
		return new SeatConfiguration(new EnumMap<SeatClass, Integer>(seats));
	}

	//returns number of seats
	public final Integer seats(SeatClass seatClass) {
		Objects.requireNonNull(seatClass,"Null input received.");
		if (seats.get(seatClass) == null || seats.get(seatClass) < 0) {
			return 0;
		} else {
			return seats.get(seatClass);
		}
		
	}
	
	//sets the seats to the given value
<<<<<<< HEAD
	public final int setSeats(SeatClass seatClass, Integer numSeats) {
		Objects.requireNonNull(seatClass,"Null input received.");
		Objects.requireNonNull(numSeats,"Null input received.");

=======
	public final Integer setSeats(SeatClass seatClass, Integer numSeats) {
		Helpers.nullCheck(seatClass, numSeats);
>>>>>>> parent of fa3ea9f... Comments
		return seats.put(seatClass, numSeats);
	}
	
	//checks if seats exist
<<<<<<< HEAD
	//TODO: .anyMatch();  | test this
=======
>>>>>>> parent of fa3ea9f... Comments
	public final boolean hasSeats() {
		return (seats.values().stream().anyMatch(n -> n>0));

	}

}
