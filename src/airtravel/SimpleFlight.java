package airtravel;

import java.time.Duration;

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

    public SimpleFlight of(String code, Leg leg, FlightSchedule flightSchedule) {
        if(code == null || leg == null || flightSchedule == null) {
            throw new NullPointerException("Null value entered");
        }
        Airport departureAirport = leg.getOrigin();
        departureAirport.addFlight(flight);
        return new SimpleFlight(code, leg, flightSchedule);
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

    @Override
	public boolean isShort(Duration durationMax) {
		return this.getFlightSchedule().isShort(durationMax);
	}


}