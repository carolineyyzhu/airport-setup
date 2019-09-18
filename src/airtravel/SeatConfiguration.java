package airtravel;

import java.util.EnumMap;
import java.util.List;

public final class SeatConfiguration {
	
	private final EnumMap<SeatClass, Integer> seats;
	
	private SeatConfiguration(EnumMap<SeatClass, Integer> seats) {
		this.seats = new EnumMap<SeatClass, Integer>(seats);
	}
	
	public static final SeatConfiguration of(SeatConfiguration seatConfiguration) {
		Helpers.nullCheck(seatConfiguration);
		return new SeatConfiguration(seatConfiguration.seats);
	}
	
	public static final SeatConfiguration of(EnumMap<SeatClass, Integer> seats) {
		Helpers.nullCheck(seats);
		return new SeatConfiguration(new EnumMap<SeatClass, Integer>(seats));
	}

	public final int seats(SeatClass seatClass) {
		Helpers.nullCheck(seatClass);
		if (seats.get(seatClass) == null || seats.get(seatClass) < 0) {
			return 0;
		} else {
			return seats.get(seatClass);
		}
		
	}
	
	public final int setSeats(SeatClass seatClass, int numSeats) {
		Helpers.nullCheck(seatClass, numSeats);
		return seats.replace(seatClass, numSeats);
	}
	
	public final boolean hasSeats() {
		List <Integer> values = (List<Integer>) seats.values();
		return (values.stream().mapToInt(Integer::intValue).sum() > 0);

	}

}
