package airtravel;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.EnumMap;

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
    public void limitedPolicyFlight() {
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
        for (SeatClass section : SeatClass.values()) {
            System.out.println(section + " " + flightClassSeats.seats(section));
            if (section != fareClass.getSeatClass() && section != SeatClass.classAbove(fareClass.getSeatClass()) && !flightClassSeats.seats(section).equals(0))
                seatsAccurate = false;
            if ((section == fareClass.getSeatClass() || section == SeatClass.classAbove(fareClass.getSeatClass())) && !flightClassSeats.seats(section).equals(flight.seatsAvailable(fareClass).seats(section)))
                seatsAccurate = false;
        }
        assertTrue(seatsAccurate);
    }

    /**
     *
     */

}
