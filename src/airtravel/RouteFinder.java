package airtravel;

import java.time.LocalTime;
import java.util.Set;

public final class RouteFinder {
    private final Set<Airport> airports;

    private RouteFinder(Set<Airport> airports) {
        this.airports = airports;
    }

    public static final RouteFinder of(Set<Airport> airports) {
        return new RouteFinder(airports);
    }

    public final RouteNode route(Airport origin, Airport destination, LocalTime departureTime, FareClass fareClass) {
        RouteState routeState = new RouteState(this.getAirports(), )
    }

    public Set<Airport> getAirports() {
        return airports;
    }
}
