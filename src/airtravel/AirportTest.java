package airtravel;

import org.junit.Test;


import junit.framework.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

/**
 * This class tests the current classes in the airtravel package.
 *
 */
public class AirportTest {

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
     * Test for buildLeg
     */
    @Test
    public void buildLeg() {
        Airport airportA = Airport.of("AA12", Duration.ofMinutes(30));
        Airport airportB = Airport.of("BB8", Duration.ofMinutes(60));
        Leg leg = Leg.of(airportA, airportB);
        boolean orginAccurate = leg.getOrigin().equals(airportA);
        boolean destinationAccurate = leg.getDestination().equals(airportB);
        assertTrue(orginAccurate && destinationAccurate);
    }

    /**
     * Test for building a flight utilizing Airport, leg, flight, schedule, and seatConfiguration
     */
    @Test
    public void buildSimpleFlight() {
        Airport airportA = Airport.of("AA12", Duration.ofMinutes(30));
        Airport airportB = Airport.of("BB8", Duration.ofMinutes(30));
        Leg leg = Leg.of(airportA, airportB);
        FlightSchedule fsched = FlightSchedule.of(LocalTime.of(4, 50), LocalTime.of(6, 40));
        EnumMap<SeatClass, Integer> seatConfigEnumMap = new EnumMap<SeatClass, Integer>(SeatClass.class);
        for (SeatClass section : SeatClass.values()) {
            seatConfigEnumMap.put(section, 6);
        }
        SeatConfiguration seatConfiguration = SeatConfiguration.of(seatConfigEnumMap);
        Flight flight = SimpleFlight.of("UA192", leg, fsched, seatConfiguration);
        assertTrue(flight.getCode().equals("UA192"));
    }

    /**
     * Test for removeFlight utilizing Airport, leg, flight, schedule, and seatConfiguration
     */
    @Test
    public void removeFlight() {
        Airport airportA = Airport.of("AA12", Duration.ofMinutes(30));
        Airport airportB = Airport.of("BB8", Duration.ofMinutes(30));
        Leg leg = Leg.of(airportA, airportB);
        FlightSchedule fsched = FlightSchedule.of(LocalTime.of(4, 50), LocalTime.of(6, 40));
        EnumMap<SeatClass, Integer> seatConfigEnumMap = new EnumMap<SeatClass, Integer>(SeatClass.class);
        for (SeatClass section : SeatClass.values()) {
            seatConfigEnumMap.put(section, 6);
        }
        SeatConfiguration seatConfiguration = SeatConfiguration.of(seatConfigEnumMap);
        Flight flight = SimpleFlight.of("UA197", leg, fsched, seatConfiguration);
        airportA.addFlight(flight);
        assertTrue(airportA.removeFlight(flight));
    }

    /**
     * Test to apply the strict policy to a flight
     */
    @Test
    public void strictPolicyFlight() {
        Airport airportA = Airport.of("AA12", Duration.ofMinutes(30));
        Airport airportB = Airport.of("BB8", Duration.ofMinutes(30));
        Leg leg = Leg.of(airportA, airportB);
        FlightSchedule fsched = FlightSchedule.of(LocalTime.of(4, 50), LocalTime.of(6, 40));
        EnumMap<SeatClass, Integer> seatConfigEnumMap = new EnumMap<SeatClass, Integer>(SeatClass.class);
        for (SeatClass section : SeatClass.values()) {
            seatConfigEnumMap.put(section, 6);
        }
        SeatConfiguration seatConfiguration = SeatConfiguration.of(seatConfigEnumMap);
        FareClass fareClass = FareClass.of(15, SeatClass.ECONOMY);
        Flight flight = SimpleFlight.of("UA197", leg, fsched, seatConfiguration);
        airportA.addFlight(flight);

        boolean seatsAccurate = true;
        SeatConfiguration flightClassSeats = FlightPolicy.strict(flight).seatsAvailable(fareClass);
        for (SeatClass section : SeatClass.values()) {
            System.out.println(section + " " + flightClassSeats.seats(section));
            if (section != fareClass.getSeatClass() && !flightClassSeats.seats(section).equals(0))
                seatsAccurate = false;
            if (section == fareClass.getSeatClass() && !flightClassSeats.seats(section).equals(flight.seatsAvailable(fareClass).seats(section)))
                seatsAccurate = false;
        }
        assertTrue(seatsAccurate);
    }
}