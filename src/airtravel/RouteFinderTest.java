package airtravel;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

public class RouteFinderTest {
    @Test
    public void routeFinderTest() {
        //Creates airport
        Airport airportA = Airport.of("AA12", Duration.ofMinutes(30));
        Airport airportB = Airport.of("BB8", Duration.ofMinutes(150));
        Airport airportC = Airport.of("K2S0", Duration.ofMinutes(270));
        FareClass fareClass = FareClass.of(1, SeatClass.PREMIUM_ECONOMY);
        //creates the first leg of the trip, which connects airport A and airport B, from 4:50 to 6:40, with six seats in every seat class
        Leg legAToB = Leg.of(airportA, airportB);
        FlightSchedule fschedAToB = FlightSchedule.of(LocalTime.of(4, 50), LocalTime.of(6, 40));
        EnumMap<SeatClass, Integer> seatConfigEnumMapAToB = new EnumMap<SeatClass, Integer>(SeatClass.class);
        for (SeatClass section : SeatClass.values()) {
            seatConfigEnumMapAToB.put(section, 6);
        }
        SeatConfiguration seatConfigurationAToB = SeatConfiguration.of(seatConfigEnumMapAToB);
        Flight flightAToB = SimpleFlight.of("UA192", legAToB, fschedAToB, seatConfigurationAToB);

        //creates the second leg of the trip, which connects airport B and airport C, from 8:50 to 10:30, with 15 seats in every seat class
        Leg legBToC = Leg.of(airportB, airportC);
        FlightSchedule fschedBToC = FlightSchedule.of(LocalTime.of(8, 50), LocalTime.of(10,30));
        EnumMap<SeatClass, Integer> seatConfigEnumMapBToC = new EnumMap<SeatClass, Integer>(SeatClass.class);
        for (SeatClass section : SeatClass.values()) {
            seatConfigEnumMapAToB.put(section, 15);
        }
        SeatConfiguration seatConfigurationBToC = SeatConfiguration.of(seatConfigEnumMapAToB);
        Flight flightBToC = SimpleFlight.of("UA382", legBToC, fschedBToC, seatConfigurationBToC);

        Set<Airport> airports = new HashSet<Airport>();
        airports.add(airportA);
        airports.add(airportB);
        airports.add(airportC);

        RouteFinder routeFinder = RouteFinder.of(airports);
        RouteNode routeNode = routeFinder.route(airportA, airportC, flightAToB.getFlightSchedule().getDepartureTime(), fareClass);
        while(routeNode.getPrevious() != null) {
            System.out.println(routeNode.getAirport() + " arriving at " + routeNode.getArrivalTime());
            routeNode = routeNode.getPrevious();
        }

        assert(true);
    }
}
