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
        Airport airportC = Airport.of("CC7", Duration.ofMinutes(20));
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

        Leg legBToC = Leg.of(airportA, airportB);
        FlightSchedule fSchedBToC = FlightSchedule.of(LocalTime.of(7, 00), LocalTime.of(8, 50));
        EnumMap<SeatClass, Integer> seatConfigEnumMapBToC = new EnumMap<SeatClass, Integer>(SeatClass.class);
        for (SeatClass section : SeatClass.values()) {
            seatConfigEnumMapAToB.put(section, 6);
        }
        SeatConfiguration seatConfigurationBToC = SeatConfiguration.of(seatConfigEnumMapBToC);
        Flight flightBToC = SimpleFlight.of("NZ142", legBToC, fSchedBToC, seatConfigurationBToC);

        Set<Airport> airports = new HashSet<Airport>();
        airports.add(airportA);
        airports.add(airportB);
        airports.add(airportC);

        RouteFinder routeFinder = RouteFinder.of(airports);
        RouteNode routeNode = routeFinder.route(airportA, airportC, flightAToB.getFlightSchedule().getDepartureTime(), fareClass);
        System.out.println(routeNode.getAirport());
        while(routeNode.getPrevious() != null) {
            routeNode = routeNode.getPrevious();
        }
        assert(routeNode.getAirport().equals(airportA));
    }
}
