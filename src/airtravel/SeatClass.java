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


	//helper method to find the class below the class being looked at
	static final SeatClass classBelow(SeatClass seatClass) {
		Objects.requireNonNull(seatClass,"Seat class cannot be null");

		SeatClass belowClass = seatClass;
		if(seatClass.ordinal() != SeatClass.values().length - 1 && seatClass.ordinal() > 0)
			belowClass = SeatClass.values()[seatClass.ordinal() + 1];
		return belowClass;
	}
}
