package airtravel;

import java.time.Duration;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiFunction;

public final class FlightPolicy extends AbstractFlight {
	
	private final Flight flight;
	private final BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy;

	//Private constructor for FlightPolicy
	private FlightPolicy(Flight flight, FareClass fareClass) {
		this.policy = policy.apply(flight.seatsAvailable(fareClass), fareClass);
		this.flight = flight;
	}

	/**
	 * Build method for FlightPolicy that accesses private constructor
	 * @param flight flight that policy is being applied to
	 * @param policy BiFunction that defines the policy
	 * @return
	 */
	public static final FlightPolicy of(Flight flight) {
		//Create new flight policy
		
		
		//Replace flight at departure airport with this policy
		flight.getLeg().getOrigin().removeFlight(flight);
		flight.getLeg().getOrigin().addFlight(flightPolicy);
		return flightPolicy;
	}

	//Applies the strict BiFunction to a specific flight
	public static final Flight strict(Flight flight) {
		Helpers.nullCheck(flight);
		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a,b) ->
				flight.hasSeats(b) ? putSeat(emptySeatConfig(), b.getSeatClass(), a.seats(b.getSeatClass())) : emptySeatConfig();
		//Step 1: get the seats available
		//Step 2: Then apply the policy to the flight
		//Step 3: return the new configuration
		
		return FlightPolicy.of(policy.apply(flight.seatsAvailable(), ), policy);
	}

	public static final Flight restrictedDuration(Flight flight, Duration durationMax) {
		Helpers.nullCheck(flight, durationMax);
		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy;
		if (flight.isShort(durationMax))
			//returns a strict policy on short flights
			policy = (a,b) -> flight.hasSeats(b) ? flight.seatsAvailable(b) : emptySeatConfig();
		else
			//returns the same seat configuration as on the underlying flight
			policy = (a,b) -> SeatConfiguration.of(a);
		return FlightPolicy.of(flight, policy, fareClass);
	}
	
	public static final Flight reserve(Flight flight, int reserve) {
		Helpers.nullCheck(flight, reserve);
		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a,b) ->
				((a.seats(b.getSeatClass()) - reserve) > 0) ? reserveSeatConfig(a, reserve): emptySeatConfig();
		return FlightPolicy.of(flight, policy);
	}

	//private helper method to generate a SeatConfiguration that accounts for available seats while saving a reserve
	private static final SeatConfiguration reserveSeatConfig(SeatConfiguration seatConfig, int reserve) {
		Helpers.nullCheck(seatConfig, reserve);
		SeatConfiguration newSeatConfig = SeatConfiguration.of(seatConfig);
		for(SeatClass section:SeatClass.values()) {
			if(newSeatConfig.seats(section) > reserve)
				newSeatConfig.setSeats(section, newSeatConfig.seats(section) - reserve);
			else
				newSeatConfig.setSeats(section, 0);
		}
		return newSeatConfig;
	}
	
	public static final Flight limited(Flight flight) {
		Helpers.nullCheck(flight);
		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a,b) ->
				flight.hasSeats(b) || flight.hasSeats(FareClass.of(0, SeatClass.classAbove(b.getSeatClass()))) ? limitedSeatConfig(a, b): emptySeatConfig();
		return FlightPolicy.of(flight, policy);
	}

	private static final SeatConfiguration limitedSeatConfig(SeatConfiguration seatConfig, FareClass fareClass) {
		SeatClass seatClass = fareClass.getSeatClass();
		SeatConfiguration limitedSeatConfig = SeatConfiguration.of(seatConfig);
		for(SeatClass section:SeatClass.values()) {
			if(section != seatClass && section != SeatClass.classAbove(seatClass))
				limitedSeatConfig.setSeats(section, 0);
		}
		return limitedSeatConfig;
	}

	//private helper method to add a key and a value to a given SeatConfiguration, and return the new SeatConfiguration
	private static final SeatConfiguration putSeat(SeatConfiguration seatConfig, SeatClass seatClass, Integer numSeats) {
		Helpers.nullCheck(seatConfig, seatClass, numSeats);
		seatConfig.setSeats(seatClass, numSeats);
		return seatConfig;
	}

	//private helper method to create a new seat configuration where every enum value has a key of 0
	private static SeatConfiguration emptySeatConfig() {
		SeatConfiguration newSeatConfig = SeatConfiguration.of(new EnumMap<SeatClass, Integer>(SeatClass.class));
		for(SeatClass section:SeatClass.values()) {
			newSeatConfig.setSeats(section, 0);
		}
		return newSeatConfig;
	}

	@Override
	public String getCode() {
		return this.flight.getCode();
	}

	@Override
	public Leg getLeg() {
		return this.flight.getLeg();
	}

	@Override
	public FlightSchedule getFlightSchedule() {
		return this.flight.getFlightSchedule();
	}

	@Override
	public SeatConfiguration seatsAvailable(FareClass fareClass) {
		return this.flight.seatsAvailable(fareClass);
	}

}
