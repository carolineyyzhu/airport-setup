package airtravel;

import java.time.Duration;
import java.util.EnumMap;
import java.util.function.BiFunction;

public final class FlightPolicy extends AbstractFlight {
	
	private final Flight flight;
	private final BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy;
	
	private FlightPolicy(Flight flight, BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy) {
		this.flight = flight;
		this.policy = policy;
	}
	
	public static final FlightPolicy of(Flight flight, BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy) {
		return new FlightPolicy(flight, policy);
	}
	
	public static final Flight strict(Flight flight) {
		//thres some more stuff to get the enum map to cooperate but you got the bulk of it holy shit
		//dab dab I have no idea what im doing help me fix big brain
		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a,b) -> flight.hasSeats(b) ? flight.seatsAvailable(b) : a;
		return FlightPolicy.of(flight, policy).flight;
	}
	
	public static final Flight restrictedDuration(Flight flight, Duration durationMax) {
		if (flight.isShort(durationMax)){
			//returns a strict policy on short flights,
			BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a,b) -> flight.hasSeats(b) ? flight.seatsAvailable(b) : a;
			return FlightPolicy.of(flight, policy).flight;
		} else {
			//returns the same seat configuration as on the underlying flight
			BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a,b) -> a;
			return FlightPolicy.of(flight, policy).flight;
			
		}
		
		
	}
	
	public static final Flight reserve(Flight flight, int reserve) {
		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a,b) -> (flight.hasSeats(b)) ? (flight.seatsAvailable(b) - reserve) : a;
		return FlightPolicy.of(flight, policy).flight;
		
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
		this.flight.seatsAvailable(fareClass);
		return null;
	}

}
