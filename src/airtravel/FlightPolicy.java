package airtravel;

import java.time.Duration;
import java.util.EnumMap;
import java.util.Map;
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
		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a,b) ->
				flight.hasSeats(b) ? SeatConfiguration.of(generateEmptySeatConfig().put(b.getSeatClass(), a.seats(b.getSeatClass()))) : a;
		return FlightPolicy.of(flight, policy).flight;
	}


	private static EnumMap<SeatClass, Integer> generateEmptySeatConfig() {
		EnumMap<SeatClass, Integer> newConfigMap = new EnumMap<SeatClass, Integer>(SeatClass.class);
		SeatClass[] seatClasses = SeatClass.values();
		for(SeatClass section:seatClasses) {
			newConfigMap.put(section, 0);
		}
		return newConfigMap;
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
