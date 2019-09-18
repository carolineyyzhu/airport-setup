package airtravel;

import java.time.Duration;
import java.util.function.BiFunction;

public final class FlightPolicy extends AbstractFlight {
	
	private final Flight flight;
	private final BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy;
	
	private FlightPolicy(Flight flight, BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy) {
		this.flight = flight;
		this.policy = policy;
	}
	
	public static final FlightPolicy of(Flight flight, BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy) {

		//Create Instance of Airport
		return new FlightPolicy(flight, policy);
	}
	
	public static final Flight strict(Flight flight) {
		
	}
	
	public static final Flight restrictedDuration(Flight flight, Duration durationMax) {
		
	}
	
	public static final Flight reserve(Flight flight, int reserve) {
		
	}
	
	public static final Flight limited(Flight flight) {
		
	}

	@Override
	public String getCode() {
		return flight.getCode();
	}

	@Override
	public Leg getLeg() {
		return flight.getLeg();
	}

	@Override
	public FlightSchedule getFlightSchedule() {
		return flight.getFlightSchedule();
	}

	@Override
	public SeatConfiguration seatsAvailable(FareClass fareClass) {
		//TODO
		return null;
	}

}
