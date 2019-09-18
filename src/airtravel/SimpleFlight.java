package airtravel;

import java.time.Duration;

/**
 * This class creates a simple flight with basic requirements
 *
 */
public class SimpleFlight extends AbstractFlight {
    private final String code;
    private final FlightSchedule flightSchedule;
    private final Leg leg;

    /**
     * private constructor
     * @param code
     * @param leg
     * @param flightSchedule
     */

    private SimpleFlight(String code, Leg leg, FlightSchedule flightSchedule) {
        this.code = code;
        this.leg = leg;
        this.flightSchedule = flightSchedule;
    }

    /**
     * public build method
     * @param code
     * @param leg
     * @param flightSchedule
     * @return new SimpleFlight object
     */

    public static SimpleFlight of(String code, Leg leg, FlightSchedule flightSchedule) {
        if(code == null || leg == null || flightSchedule == null) {
            throw new NullPointerException("Null value entered");
        }
        SimpleFlight flight = new SimpleFlight(code, leg, flightSchedule);
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
		//Throws exception if null inputs are received
		if (durationMax == null) {
			throw new NullPointerException("Null inputs were received");
		}
		return this.getFlightSchedule().isShort(durationMax);
	}


}