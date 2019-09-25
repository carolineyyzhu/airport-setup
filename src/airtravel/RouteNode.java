package airtravel;

import java.util.Objects;
import java.util.Set;

public final class RouteNode implements Comparable<RouteNode>{
    private final Airport airport;
    private final RouteTime arrivalTime;
    private final RouteNode previous;

    private RouteNode(Airport airport, RouteTime arrivalTime, RouteNode previous) {
        this.airport = airport;
        this.arrivalTime = arrivalTime;
        this.previous = previous;
    }

    public static final RouteNode of(Airport airport, RouteTime arrivalTime, RouteNode previous) {
        Objects.requireNonNull(airport,"Airport input cannot be null");
        Objects.requireNonNull(arrivalTime,"Arrival time cannot be null");
        //Note that previous can be null
        return new RouteNode(airport, arrivalTime, previous);
    }

    public static final RouteNode of(Flight flight, RouteNode previous) {
        Objects.requireNonNull(flight,"Flight input cannot be null");
        return RouteNode.of(flight.destination(), new RouteTime(flight.arrivalTime()), previous);
    }

    public static final RouteNode of (Airport airport) {
        return RouteNode.of(airport, RouteTime.UNKNOWN(), null);
    }

    public final Boolean isArrivalTimeKnown() {
        return !this.getArrivalTime().equals(RouteTime.UNKNOWN());
    }

    public final RouteTime departureTime() {
        if(!isArrivalTimeKnown())
            throw new NullPointerException("Route time cannot be unknown");
        return this.getArrivalTime().plus(this.getAirport().getConnectionTimeMin());
    }

    public Set<Flight> availableFlights(FareClass fareClass) {
    	Objects.requireNonNull(fareClass,"fareClass cannot be null");
        if(!isArrivalTimeKnown())
            throw new NullPointerException("Route time cannot be unknown");
        return this.getAirport().availableFlights(this.departureTime().getTime(), fareClass);
    }

    public Airport getAirport() {
        return airport;
    }

    public RouteTime getArrivalTime() {
        return arrivalTime;
    }

    public RouteNode getPrevious() {
        return previous;
    }

    @Override
    public int compareTo(RouteNode o) {
    	Objects.requireNonNull(arrivalTime,"Arrival time cannot be null");
        return o.getArrivalTime().compareTo(this.getArrivalTime());
    }
}
