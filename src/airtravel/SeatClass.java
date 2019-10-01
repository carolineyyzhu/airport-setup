package airtravel;

import java.util.Objects;

/**
 * Seat Class enum map
 *
 */
public enum SeatClass {
	BUSINESS,
	PREMIUM_ECONOMY,
	ECONOMY;

	//helper method to find the class above the class that is being looked at
	static final SeatClass classAbove(SeatClass seatClass) {
		Objects.requireNonNull(seatClass,"Seat class cannot be null");

		SeatClass aboveClass = seatClass;
		aboveClass = SeatClass.values()[Math.max(seatClass.ordinal() - 1, 0)];
		return aboveClass;
	}

	//helper method to find the seat class ranking below the seat class passed in
	static final SeatClass classBelow(SeatClass seatClass) {
		Objects.requireNonNull(seatClass,"Seat class cannot be null");

		SeatClass belowClass = seatClass;
		belowClass = SeatClass.values()[Math.min(seatClass.ordinal() + 1, SeatClass.values().length - 1)];
		return belowClass;
	}
}
