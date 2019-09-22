package airtravel;

import java.time.Duration;
import java.util.Objects;

/**
 * This class creates a simple flight with basic requirements
 *
 */
public class SimpleFlight extends AbstractFlight {
    private final String code;
    private final FlightSchedule flightSchedule;
    private final Leg leg;
    private final SeatConfiguration seatsAvailable;

    /**
     * private constructor
     * @param code
     * @param leg
     * @param flightSchedule
     */

    private SimpleFlight(String code, Leg leg, FlightSchedule flightSchedule, SeatConfiguration seatsAvailable) {
        this.code = code;
        this.leg = leg;
        this.flightSchedule = flightSchedule;
        this.seatsAvailable = seatsAvailable; 
    }

    /**
     * public build method
     * @param code
     * @param leg
     * @param flightSchedule
     * @return new SimpleFlight object
     */

    public static SimpleFlight of(String code, Leg leg, FlightSchedule flightSchedule, SeatConfiguration seatsAvailable) {
        Objects.requireNonNull(code,"Null input received.");
        Objects.requireNonNull(leg,"Null input received.");
        Objects.requireNonNull(flightSchedule,"Null input received.");
        Objects.requireNonNull(seatsAvailable,"Null input received.");

        SimpleFlight flight = new SimpleFlight(code, leg, flightSchedule, SeatConfiguration.of(seatsAvailable));
        Airport departureAirport = leg.getOrigin();
        departureAirport.addFlight(flight);
        return flight;
    }

    //getter for code
    public String getCode() {
        return this.code;
    }

    //getter for leg
    public Leg getLeg() {
        return this.leg;
    }

    //getter for flightSchedule
    public FlightSchedule getFlightSchedule() {
        return this.flightSchedule;
    }

    //returns true if the flight is short
    @Override
	public boolean isShort(Duration durationMax) {
        Objects.requireNonNull(durationMax,"Null input received.");
		return this.getFlightSchedule().isShort(durationMax);
	}

	//returns the SeatConfiguration
    public SeatConfiguration seatsAvailable(FareClass fareClass) {
        Objects.requireNonNull(fareClass,"Null input received.");

        return SeatConfiguration.of(seatsAvailable);
    }
}