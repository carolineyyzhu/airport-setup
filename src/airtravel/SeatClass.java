package airtravel;

public enum SeatClass {
	BUSINESS,
	PREMIUM_ECONOM,
	ECONOMY;


	public static final SeatClass classAbove(SeatClass seatClass) {
		SeatClass aboveClass = seatClass;
		if(seatClass.ordinal() < SeatClass.values().length - 1 && seatClass.ordinal() != 0)
			aboveClass = SeatClass.values()[seatClass.ordinal() - 1];
		return aboveClass;
	}

	public static final SeatClass classBelow(SeatClass seatClass) {
		SeatClass belowClass = seatClass;
		if(seatClass.ordinal() != SeatClass.values().length - 1 && seatClass.ordinal() > 0)
			belowClass = SeatClass.values()[seatClass.ordinal() + 1];
		return belowClass;
	}
}
