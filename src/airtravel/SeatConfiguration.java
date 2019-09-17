package airtravel;

import java.time.Duration;
import java.util.EnumMap;

public final class SeatConfiguration {
	
	private final EnumMap<SeatClass, Integer> seats;
	
	private SeatConfiguration(EnumMap<SeatClass, Integer> seats) {
		this.seats = new EnumMap<SeatClass, Integer>(seats);
	}
	
	public static final SeatConfiguration of(EnumMap<SeatClass, Integer> seats) {
		return new SeatConfiguration(new EnumMap<SeatClass, Integer>(seats));
	}
	
	public static final SeatConfiguration of(SeatConfiguration seatConfiguration) {
		return new SeatConfiguration(seatConfiguration.seats);
	}
	
	public final int seats(SeatClass seatClass) {
		if (seats.get(seatClass) == null || seats.get(seatClass) < 0) {
			return 0;
		} else {
			return seats.get(seatClass);
		}
		
	}
	
	
	public final int setSeats(SeatClass seatClass, int seats) {
		return this.seats.replace(seatClass, seats);
	}
	
	public final boolean hasSeats() {
		for (EnumMap<SeatClass, Integer> seat : this.seats) { 
		    if (seats.get(seat)) {
		    	
		    }
		}
	}

}
