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
		Objects.requireNonNull(flight,"Flight input cannot be null");
		Objects.requireNonNull(policy,"Policy cannot be null");

		//Create new flight policy
		FlightPolicy newFlightPolicy = new FlightPolicy(flight, policy);

		//Replace flight at departure airport with this policy
		flight.origin().removeFlight(flight);
		flight.origin().addFlight(newFlightPolicy);
		return newFlightPolicy;
	}

	/**
	 * returns a FlightPolicy that is strict, and only makes seats from the class the passenger is
	 * currently in available to the passenger
	 * @param flight is the flight the policy is being applied to
	 * @return a new FlightPolicy object with a SeatConfiguration in which only seats from the passenger's
	 * class are available
	 */
	public static final Flight strict(Flight flight) {
		Objects.requireNonNull(flight,"Flight input cannot be null");
		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a, b) -> strictSeatConfig(a, b, flight);
		return FlightPolicy.of(flight, policy);
	}

	//helper method for strict to create a new SeatConfiguration
	private static final SeatConfiguration strictSeatConfig(SeatConfiguration oldSeatConfig, FareClass fareClass, Flight flight) {
		SeatConfiguration newSeatConfig = SeatConfiguration.of(oldSeatConfig);
			for (SeatClass section : SeatClass.values()) {
				if (!section.equals(fareClass.getSeatClass()))
					newSeatConfig.setSeats(section, 0);
			}
		return newSeatConfig;
	}

	/**
	 * Enacts a policy such that if the duration is less than the maximum duration, it enacts a strict policy,
	 * but otherwise, returns the same seat configuration as the inputted flight
	 * @param flight is the flight the policy is enacted upon
	 * @param durationMax the maximum duration of a flight
	 * @return a FlightPolicy that enacts the appropriate policy
	 */
	public static final Flight restrictedDuration(Flight flight, Duration durationMax) {
		Objects.requireNonNull(flight,"Flight input cannot be null");
		Objects.requireNonNull(durationMax,"Duration maximum cannot be null");

		Flight restrictedDurationFlight;
		//returns a strict policy on short flights
		if (flight.isShort(durationMax))
			restrictedDurationFlight = strict(flight);
		//returns the same seat configuration as on the underlying flight
		else {
			BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a, b) -> SeatConfiguration.of(a);
			restrictedDurationFlight = FlightPolicy.of(flight, policy);
		}
		return restrictedDurationFlight;
	}

	/**
	 * Enacts a policy so that passengers can switch to any class, so long as a certain number of seats are reserved
	 * for other passengers
	 * @param flight is the flight the policy is enacted upon
	 * @param reserve is the number of seats to be reserved
	 * @return a new FlightPolicy with a SeatConfiguration that has a certain number of seats reserved
	 */
	public static final Flight reserve(Flight flight, int reserve) {
		Objects.requireNonNull(flight,"Flight input cannot be null");

		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a, b) -> reserveSeatConfig(a, reserve);
		return FlightPolicy.of(flight, policy);
	}

	//private helper method to generate a SeatConfiguration that accounts for available seats while saving a reserve
	private static final SeatConfiguration reserveSeatConfig(SeatConfiguration seatConfig, int reserve) {
		Objects.requireNonNull(seatConfig,"Null input received.");

		SeatConfiguration newSeatConfig = SeatConfiguration.of(seatConfig);
		for (SeatClass section : SeatClass.values()) {
			if (newSeatConfig.seats(section) > reserve)
				newSeatConfig.setSeats(section, newSeatConfig.seats(section) - reserve);
			else
				newSeatConfig.setSeats(section, 0);
		}
		return newSeatConfig;
	}

	/**
	 * Enacts a policy such that a passenger has access to the seats in their class and in the class above them
	 * @param flight is the flight the policy is enacted upon
	 * @return a new FlightPolicy with a limited SeatConfiguration
	 */
	public static final Flight limited(Flight flight) {
		Objects.requireNonNull(flight,"Flight input cannot be null");
		//return limitedSeatConfig, no need for conditional
		BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = FlightPolicy::limitedSeatConfig;
		return FlightPolicy.of(flight, policy);
	}

	//private helper method to generate a SeatConfiguration
	private static final SeatConfiguration limitedSeatConfig(SeatConfiguration seatConfig, FareClass fareClass) {
		Objects.requireNonNull(seatConfig,"Null input received.");
		Objects.requireNonNull(fareClass,"Seat configuration cannot be null");

		SeatClass seatClass = fareClass.getSeatClass();
		SeatConfiguration limitedSeatConfig = SeatConfiguration.of(seatConfig);
		for (SeatClass section : SeatClass.values()) {
			if (section != seatClass && section != classAbove(seatClass))
				limitedSeatConfig.setSeats(section, 0);
		}
		return limitedSeatConfig;
	}

	//helper method to find the class above the class that is being looked at
	static final SeatClass classAbove(SeatClass seatClass) {
		Objects.requireNonNull(seatClass,"Seat class cannot be null");

		SeatClass aboveClass = seatClass;
		if(seatClass.ordinal() < SeatClass.values().length && seatClass.ordinal() != 0)
			aboveClass = SeatClass.values()[seatClass.ordinal() - 1];
		return aboveClass;
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
		Objects.requireNonNull(fareClass,"Fare class cannot be null");

		//Apply the flight policy here
		return this.policy.apply(this.flight.seatsAvailable(fareClass), fareClass);
	}
}
