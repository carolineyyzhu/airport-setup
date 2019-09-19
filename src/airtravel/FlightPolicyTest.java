package airtravel;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.function.BiFunction;

import static junit.framework.TestCase.assertTrue;

public class FlightPolicyTest {

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

    /**
     * Test to apply the limited policy to a flight
     */
    @Test
    public void limitedReservePolicyFlight() {
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
        SeatConfiguration flightClassSeats = FlightPolicy.limited(FlightPolicy.reserve(flight, 2)).seatsAvailable(fareClass);
        int reserve = 2;
        for (SeatClass section : SeatClass.values()) {
            System.out.println(section + " " + flightClassSeats.seats(section));
            if (section != fareClass.getSeatClass() && section != SeatClass.classAbove(fareClass.getSeatClass()) && !flightClassSeats.seats(section).equals(0))
                seatsAccurate = false;
            if (section == fareClass.getSeatClass() && !flightClassSeats.seats(section).equals(flight.seatsAvailable(fareClass).seats(section) - reserve))
                seatsAccurate = false;
            if(section == SeatClass.classAbove(fareClass.getSeatClass()) && !flightClassSeats.seats(section).equals(flight.seatsAvailable(fareClass).seats(section) - reserve))
                seatsAccurate = false;
        }
        assertTrue(seatsAccurate);
    }
    /**
     * Passenger can go to their class or the class below them
     */

    @Test
    public void classBelowPolicyTest() {
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
        BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a,b) ->
                    flight.hasSeats(b) || flight.hasSeats(FareClass.of(0, SeatClass.classBelow(b.getSeatClass()))) ? belowSeatConfig(a, b) : FlightPolicy.emptySeatConfig();
        FlightPolicy fp = FlightPolicy.of(flight, policy);
        SeatConfiguration flightClassSeats = fp.seatsAvailable(fareClass);
        for (SeatClass section : SeatClass.values()) {
            System.out.println(section + " " + flightClassSeats.seats(section));
            if (section != fareClass.getSeatClass() && section != SeatClass.classBelow(fareClass.getSeatClass()) && !flightClassSeats.seats(section).equals(0))
                seatsAccurate = false;
            if (section == fareClass.getSeatClass() && !flightClassSeats.seats(section).equals(flight.seatsAvailable(fareClass).seats(section)))
                seatsAccurate = false;
            if(section == SeatClass.classBelow(fareClass.getSeatClass()) && !flightClassSeats.seats(section).equals(flight.seatsAvailable(fareClass).seats(section)))
                seatsAccurate = false;
        }
        assertTrue(seatsAccurate);
    }

    //private helper method to generate a SeatConfiguration that accounts for available seats while saving a reserve
    private static SeatConfiguration belowSeatConfig(SeatConfiguration seatConfig, FareClass fareClass) {
        SeatClass seatClass = fareClass.getSeatClass();
        SeatConfiguration belowClassConfig = SeatConfiguration.of(seatConfig);
        for (SeatClass section : SeatClass.values()) {
            if (section != seatClass && section != SeatClass.classBelow(seatClass))
                belowClassConfig.setSeats(section, 0);
        }
        return belowClassConfig;
    }

    /**
     * Passengers in business class and above can go to their class and premium economy
     */

    @Test
    public void premiumEconomyBusiness() {
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
        BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = (a,b) ->
                flight.hasSeats(b) || flight.hasSeats(FareClass.of(0, SeatClass.PREMIUM_ECONOMY)) ? premEconBusConfig(a, b) : FlightPolicy.emptySeatConfig();
        FlightPolicy fp = FlightPolicy.of(flight, policy);
        SeatConfiguration flightClassSeats = fp.seatsAvailable(fareClass);
        for (SeatClass section : SeatClass.values()) {
            System.out.println(section + " " + flightClassSeats.seats(section));
            if (section != fareClass.getSeatClass() && section != SeatClass.classBelow(fareClass.getSeatClass()) && !flightClassSeats.seats(section).equals(0))
                seatsAccurate = false;
            if (section == fareClass.getSeatClass() && !flightClassSeats.seats(section).equals(flight.seatsAvailable(fareClass).seats(section)))
                seatsAccurate = false;
            if(section == SeatClass.classBelow(fareClass.getSeatClass()) && !flightClassSeats.seats(section).equals(flight.seatsAvailable(fareClass).seats(section)))
                seatsAccurate = false;
        }
        assertTrue(seatsAccurate);
    }

    //private helper method to generate a SeatConfiguration that accounts for available seats while saving a reserve
    private static SeatConfiguration premEconBusConfig(SeatConfiguration seatConfig, FareClass fareClass) {
        SeatClass seatClass = fareClass.getSeatClass();
        SeatConfiguration newSeatConfig = SeatConfiguration.of(seatConfig);
        for (SeatClass section : SeatClass.values()) {
            if(section.ordinal() >= SeatClass.values().length - 3) {
                if (section != seatClass && section != SeatClass.PREMIUM_ECONOMY)
                    newSeatConfig.setSeats(section, 0);
            }
        }
        return newSeatConfig;
    }
}
