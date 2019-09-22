package airtravel;

import java.util.Objects;

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

        return new RouteNode(airport, arrivalTime, previous);
    }

    public static final RouteNode of(Flight flight, RouteNode previous) {
        Objects.requireNonNull(flight,"Flight input cannot be null");

        return new RouteNode(flight.destination(), new RouteTime(flight.arrivalTime()), previous);
    }

    public static final RouteNode of (Airport airport) {
        Objects.requireNonNull(airport,"Airport input cannot be null");

        return new RouteNode(airport, RouteTime.UNKNOWN(), null);
    }

    public final Boolean isArrivalTimeKnown() {
        return !this.getArrivalTime().equals(RouteTime.UNKNOWN());
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
        Objects.requireNonNull(airport,"Route node input cannot be null");

        return o.getArrivalTime().compareTo(this.getArrivalTime());
    }
}
