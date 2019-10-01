package airtravel;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Objects;
import java.util.function.BiFunction;

import org.assertj.core.api.JUnitSoftAssertions;

import static airtravel.SeatClass.classBelow;

public class FlightPolicyTest {
    private final JUnitSoftAssertions softAssert = new JUnitSoftAssertions();

    /**
     * Test to apply the strict policy to a flight
     */
    @Test
    public void strictPolicyFlight() {
        //airport setup
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

        //
        boolean seatsAccurate = true;
        SeatConfiguration flightClassSeats = FlightPolicy.strict(flight).seatsAvailable(fareClass);
        for (SeatClass section : SeatClass.values()) {
            int classSeats = flightClassSeats.seats(section);
            System.out.println(section + " " + classSeats);
            softAssert.assertThat(!section.equals(fareClass.getSeatClass()) && classSeats == 0);
            softAssert.assertThat(section.equals(fareClass.getSeatClass()) && classSeats == flight.seatsAvailable(fareClass).seats(section));
        }
    }


    /**
     * Test to apply the limited and reserve policies to a flight
     */
    @Test
    public void limitedReservePolicyFlight() {
        //airport setup
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
        SeatClass seatClass = fareClass.getSeatClass();
        for (SeatClass section : SeatClass.values()) {
            System.out.println(section + " " + flightClassSeats.seats(section));
            softAssert.assertThat(!section.equals(seatClass) && !section.equals(SeatClass.classAbove(seatClass)) && flightClassSeats.seats(section).equals(0));
            softAssert.assertThat((section.equals(seatClass) || section.equals(SeatClass.classAbove(seatClass))) && flightClassSeats.seats(section).equals(flight.seatsAvailable(fareClass).seats(section) - reserve));
        }
    }

    /**
     * Passenger can go to their class or the class below them
     */

    @Test
    public void classBelowPolicyTest() {
        //airport setup
        Airport airportA = Airport.of("AA12", Duration.ofMinutes(30));
        Airport airportB = Airport.of("BB8", Duration.ofMinutes(30));
        Leg leg = Leg.of(airportA, airportB);
        FlightSchedule fsched = FlightSchedule.of(LocalTime.of(4, 50), LocalTime.of(6, 40));
        EnumMap<SeatClass, Integer> seatConfigEnumMap = new EnumMap<SeatClass, Integer>(SeatClass.class);
        for (SeatClass section : SeatClass.values()) {
            seatConfigEnumMap.put(section, 6);
        }
        SeatConfiguration seatConfiguration = SeatConfiguration.of(seatConfigEnumMap);
        FareClass fareClass = FareClass.of(15, SeatClass.BUSINESS);
        Flight flight = SimpleFlight.of("UA197", leg, fsched, seatConfiguration);
        airportA.addFlight(flight);

        boolean seatsAccurate = true;
        BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = FlightPolicyTest::belowSeatConfig;
        FlightPolicy fp = FlightPolicy.of(flight, policy);
        SeatConfiguration flightClassSeats = fp.seatsAvailable(fareClass);
        SeatClass seatClass = fareClass.getSeatClass();
        for (SeatClass section : SeatClass.values()) {
            System.out.println(section + " " + flightClassSeats.seats(section));
            softAssert.assertThat(!section.equals(seatClass) && !section.equals(classBelow(seatClass)) && flightClassSeats.seats(section).equals(0));
            softAssert.assertThat((section.equals(seatClass) || section.equals(classBelow(seatClass))) && flightClassSeats.seats(section).equals(flight.seatsAvailable(fareClass).seats(section)));
        }
    }

    //private helper method to create a seat configuration that includes the passenger's SeatClass and the SeatClass below that
    private static SeatConfiguration belowSeatConfig(SeatConfiguration seatConfig, FareClass fareClass) {
        Objects.requireNonNull(seatConfig,"Seat configuration cannot be null");
        Objects.requireNonNull(fareClass,"Fare class cannot be null");

        SeatClass seatClass = fareClass.getSeatClass();
        SeatConfiguration belowClassConfig = SeatConfiguration.of(seatConfig);
        for (SeatClass section : SeatClass.values()) {
            if (section != seatClass && section != classBelow(seatClass))
                belowClassConfig.setSeats(section, 0);
        }
        return belowClassConfig;
    }

    /**
     * Passengers in business class and above can go to their class and premium economy
     */
    @Test
    public void premiumEconomyBusiness() {
        //airport setup
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
        BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy = FlightPolicyTest::premEconBusConfig;
        FlightPolicy fp = FlightPolicy.of(flight, policy);
        SeatConfiguration flightClassSeats = fp.seatsAvailable(fareClass);
        SeatClass seatClass = fareClass.getSeatClass();
        for (SeatClass section : SeatClass.values()) {
            System.out.println(section + " " + flightClassSeats.seats(section));
            softAssert.assertThat(!section.equals(seatClass) && !section.equals(SeatClass.PREMIUM_ECONOMY) && flightClassSeats.seats(section).equals(0));
            softAssert.assertThat(section.equals(seatClass) && flightClassSeats.seats(section).equals(flight.seatsAvailable(fareClass).seats(section)));
        }
    }

    //private helper method to generate a SeatConfiguration that accounts for available seats while saving a reserve
    private static SeatConfiguration premEconBusConfig(SeatConfiguration seatConfig, FareClass fareClass) {
        SeatClass seatClass = fareClass.getSeatClass();
        SeatConfiguration newSeatConfig = SeatConfiguration.of(seatConfig);
        for (SeatClass section : SeatClass.values()) {
            if(section.ordinal() >= SeatClass.BUSINESS.ordinal() && section != SeatClass.PREMIUM_ECONOMY)
                newSeatConfig.setSeats(section, 0);
        }
        return newSeatConfig;
    }
}
