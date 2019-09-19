package airtravel;

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
		SeatClass aboveClass = seatClass;
		if(seatClass.ordinal() < SeatClass.values().length - 1 && seatClass.ordinal() != 0)
			aboveClass = SeatClass.values()[seatClass.ordinal() - 1];
		return aboveClass;
	}

	//helper method to find the class below the class being looked at
	static final SeatClass classBelow(SeatClass seatClass) {
		SeatClass belowClass = seatClass;
		if(seatClass.ordinal() != SeatClass.values().length - 1 && seatClass.ordinal() > 0)
			belowClass = SeatClass.values()[seatClass.ordinal() + 1];
		return belowClass;
	}
}
