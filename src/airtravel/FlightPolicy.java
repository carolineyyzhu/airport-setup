package airtravel;

import java.time.Duration;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

public final class FlightPolicy extends AbstractFlight {

	private final Flight flight;
	private final BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy;

	//Private constructor for FlightPolicy
	private FlightPolicy(Flight flight, BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy) {
		this.policy = policy;
		this.flight = flight;
	}

	/**
	 * Build method for FlightPolicy that accesses private constructor
	 *
	 * @param flight flight that policy is being applied to
	 * @param policy BiFunction that defines the policy
	 * @return
	 */
	public static final FlightPolicy of(Flight flight, BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy) {
		Objects.requireNonNull(flight,"Null input received.");
		Objects.requireNonNull(policy,"Null input received.");

		//Create new flight policy
		//This is the desired seat configuration
<<<<<<< HEAD
		FlightPolicy newFlightPolicy = new FlightPolicy(flight, policy); //variable name
=======
		FlightPolicy tmp = new FlightPolicy(flight, policy);
>>>>>>> parent of fa3ea9f... Comments

		//Replace flight at departure airport with this policy
		flight.origin().removeFlight(flight);
		flight.origin().addFlight(newFlightPolicy);
		return newFlightPolicy;
	}

	//Applies the strict BiFunction to a specific flight
	public static final Flight strict(Flight flight) {
		Objects.requireNonNull(flight,"Null input received.");
		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a, b) ->
				flight.hasSeats(b) ? putSeat(emptySeatConfig(), b.getSeatClass(), a.seats(b.getSeatClass())) : emptySeatConfig();
		return FlightPolicy.of(flight, policy);
	}

	public static final Flight restrictedDuration(Flight flight, Duration durationMax) {
		Objects.requireNonNull(flight,"Null input received.");
		Objects.requireNonNull(durationMax,"Null input received.");

		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy;
		if (flight.isShort(durationMax))
			//returns a strict policy on short flights
			policy = (a, b) -> flight.hasSeats(b) ? flight.seatsAvailable(b) : emptySeatConfig();
		else
			//returns the same seat configuration as on the underlying flight
			policy = (a, b) -> SeatConfiguration.of(a);
		return FlightPolicy.of(flight, policy);
	}

	//method to calculate
	public static final Flight reserve(Flight flight, int reserve) {
		Objects.requireNonNull(flight,"Null input received.");
		Objects.requireNonNull(reserve,"Null input received.");

		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a, b) ->
				((a.seats(b.getSeatClass()) - reserve) > 0) ? reserveSeatConfig(a, reserve) : emptySeatConfig();
		return FlightPolicy.of(flight, policy);
	}

	//private helper method to generate a SeatConfiguration that accounts for available seats while saving a reserve
	private static final SeatConfiguration reserveSeatConfig(SeatConfiguration seatConfig, int reserve) {
		Objects.requireNonNull(seatConfig,"Null input received.");
		Objects.requireNonNull(reserve,"Null input received.");

		SeatConfiguration newSeatConfig = SeatConfiguration.of(seatConfig);
		for (SeatClass section : SeatClass.values()) {
			if (newSeatConfig.seats(section) > reserve)
				newSeatConfig.setSeats(section, newSeatConfig.seats(section) - reserve);
			else
				newSeatConfig.setSeats(section, 0);
		}
		return newSeatConfig;
	}

	public static final Flight limited(Flight flight) {
<<<<<<< HEAD
		Objects.requireNonNull(flight,"Null input received.");
		//return limitedSeatConfig, no need for conditional
=======
		Helpers.nullCheck(flight);
>>>>>>> parent of fa3ea9f... Comments
		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a, b) ->
				flight.hasSeats(b) || flight.hasSeats(FareClass.of(0, SeatClass.classAbove(b.getSeatClass()))) ? limitedSeatConfig(a, b) : emptySeatConfig();
		return FlightPolicy.of(flight, policy);
	}

	private static final SeatConfiguration limitedSeatConfig(SeatConfiguration seatConfig, FareClass fareClass) {
		Objects.requireNonNull(seatConfig,"Null input received.");
		Objects.requireNonNull(fareClass,"Null input received.");

		SeatClass seatClass = fareClass.getSeatClass();
		SeatConfiguration limitedSeatConfig = SeatConfiguration.of(seatConfig);
		for (SeatClass section : SeatClass.values()) {
			if (section != seatClass && section != SeatClass.classAbove(seatClass))
				limitedSeatConfig.setSeats(section, 0);
		}
		return limitedSeatConfig;
	}

	//private helper method to add a key and a value to a given SeatConfiguration, and return the new SeatConfiguration
	private static final SeatConfiguration putSeat(SeatConfiguration seatConfig, SeatClass seatClass, Integer numSeats) {
		Objects.requireNonNull(seatConfig,"Null input received.");
		Objects.requireNonNull(seatClass,"Null input received.");

		seatConfig.setSeats(seatClass, numSeats);
		return seatConfig;
	}

	//private helper method to create a new seat configuration where every enum value has a key of 0
	static SeatConfiguration emptySeatConfig() {
		SeatConfiguration newSeatConfig = SeatConfiguration.of(new EnumMap<SeatClass, Integer>(SeatClass.class));
		for (SeatClass section : SeatClass.values()) {
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
		Objects.requireNonNull(fareClass,"Null input received.");

		//Apply the flight policy here
		return this.policy.apply(this.flight.seatsAvailable(fareClass), fareClass);
	}
}
