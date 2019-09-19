package airtravel;

import java.time.Duration;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiFunction;

public final class FlightPolicy extends AbstractFlight {
	
	private final Flight flight;
	private final BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy;

	//Private constructor for FlightPolicy
	private FlightPolicy(Flight flight, BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy) {
		this.flight = flight;
		this.policy = policy;
	}

	/**
	 * Build method for FlightPolicy that accesses private constructor
	 * @param flight flight that policy is being applied to
	 * @param policy BiFunction that defines the policy
	 * @return
	 */
	public static final FlightPolicy of(Flight flight, BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy) {
		//Create instance of Airport
		return new FlightPolicy(flight, policy);
	}

	//Applies the strict BiFunction to a specific flight
	public static final Flight strict(Flight flight) {
		Helpers.nullCheck(flight);
		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a,b) ->
				flight.hasSeats(b) ? putSeat(generateEmptySeatConfig(), b.getSeatClass(), a.seats(b.getSeatClass())) : generateEmptySeatConfig();;
		return FlightPolicy.of(flight, policy).flight;
	}

	public static final Flight restrictedDuration(Flight flight, Duration durationMax) {
		Helpers.nullCheck(flight, durationMax);
		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy;
		if (flight.isShort(durationMax))
			//returns a strict policy on short flights
			 policy = (a,b) -> flight.hasSeats(b) ? flight.seatsAvailable(b) : generateEmptySeatConfig();
		else
			//returns the same seat configuration as on the underlying flight
			policy = (a,b) -> SeatConfiguration.of(a);
		return FlightPolicy.of(flight, policy).flight;
	}
	
	public static final Flight reserve(Flight flight, int reserve) {
		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a,b) -> a.seats(b.getSeatClass()) - reserve > 0 ?  : a;
		return FlightPolicy.of(flight, policy).flight;
	}

	private static final BiFunction<SeatConfiguration, FareClass, SeatConfiguration> reserveBiFunction(Flight flight, int reserve) {
		Helpers.nullCheck(flight);
		return (a,b) -> ((a.seats(b.getSeatClass()) - reserve) > 0) ? : SeatConfiguration.of(a);
	}
	
	public static final Flight limited(Flight flight) {
		
	}

	//private helper method to add a key and a value to a given SeatConfiguration, and return the new SeatConfiguration
	private static final SeatConfiguration putSeat(SeatConfiguration seatConfig, SeatClass seatClass, Integer numSeats) {
		Helpers.nullCheck(seatConfig, seatClass, numSeats);
		seatConfig.setSeats(seatClass, numSeats);
		return seatConfig;
	}

	//private helper method to create a new seat configuration where every enum value has a key of 0
	private static SeatConfiguration generateEmptySeatConfig() {
		SeatConfiguration newSeatConfig = SeatConfiguration.of(new EnumMap<SeatClass, Integer>(SeatClass.class));
		SeatClass[] seatClasses = SeatClass.values();
		for(SeatClass section:seatClasses) {
			newSeatConfig.setSeats(section, 0);
		}
		return newSeatConfig;
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
