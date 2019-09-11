package airtravel;

import org.junit.Test;


import junit.framework.*;

import java.time.Duration;
import java.time.LocalTime;

import static junit.framework.TestCase.assertTrue;

/**
 * This class tests the current classes in the airtravel package.
 *
 */
class AirportTest {
	
	
	/**
	 * Test for buildAirport
	 */
    @Test
    public void buildAirport() {
        Airport airport = Airport.of("AA12", Duration.ofMinutes(30));
        boolean codeAccurate = airport.getCode().equals("AA12");
        boolean minTimeAccurate = airport.getConnectionTimeMin().equals(Duration.ofMinutes(30));
        assertTrue(codeAccurate && minTimeAccurate);
    }

    /**
     * Test for addFlight utilizing Airport, leg, flight, and schedule
     */
    @Test
    public void addFlight() {
        Airport airportA = Airport.of("AA12", Duration.ofMinutes(30));
        Airport airportB = Airport.of("BB8", Duration.ofMinutes(30));
        Leg leg = Leg.of(airportA, airportB);
        FlightSchedule fsched = FlightSchedule.of(LocalTime.of(4, 50), LocalTime.of(6, 40));
        Flight flight = SimpleFlight.of("UA197", leg, fsched);

        assertTrue(airportA.addFlight(flight));
    }

    /**
     * Test for removeFlight utilizing Airport, leg, flight, and schedule
     */
    @Test
    public void removeFlight() {
        Airport airportA = Airport.of("AA12", Duration.ofMinutes(30));
        Airport airportB = Airport.of("BB8", Duration.ofMinutes(30));
        Leg leg = Leg.of(airportA, airportB);
        FlightSchedule fsched = FlightSchedule.of(LocalTime.of(4, 50), LocalTime.of(6, 40));
        Flight flight = SimpleFlight.of("UA197", leg, fsched);

        assertTrue(airportA.removeFlight(flight));
    }
}