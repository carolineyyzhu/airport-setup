package airtravel;

import java.util.Objects;
import java.util.Set;

/**
 * This class creates route nodes which represents an airport in a route
 * from a departure to a destination
 *
 */
public final class RouteNode implements Comparable<RouteNode>{
    private final Airport airport;
    private final RouteTime arrivalTime;
    private final RouteNode previous;

    //Private constructor for RouteNode
    private RouteNode(Airport airport, RouteTime arrivalTime, RouteNode previous) {
        this.airport = airport;
        this.arrivalTime = arrivalTime;
        this.previous = previous;
    }
    
    /**
     * This is the public build method for RouteNode
     * @param airport the airport
     * @param arrivalTime the time to arrive
     * @param previous the previous node
     * @return a new RouteNode
     */
    public static final RouteNode of(Airport airport, RouteTime arrivalTime, RouteNode previous) {
        Objects.requireNonNull(airport,"Airport input cannot be null");
        Objects.requireNonNull(arrivalTime,"Arrival time cannot be null");
        //Note that previous can be null
        return new RouteNode(airport, arrivalTime, previous);
    }

    /**
     * This is a public build method for RouteNode
     * @param flight the flight to add to the node
     * @param previous the previous flight
     * @return a new RouteNode
     */
    public static final RouteNode of(Flight flight, RouteNode previous) {
        Objects.requireNonNull(flight,"Flight input cannot be null");
        return RouteNode.of(flight.destination(), new RouteTime(flight.arrivalTime()), previous);
    }

    /**
     * This is a public build method for RouteNode
     * @param airport the airport to add
     * @return returns a route through this airport with an unknown arrival time and a null previous airport
     */
    public static final RouteNode of (Airport airport) {
        return RouteNode.of(airport, RouteTime.UNKNOWN, null);
    }

    //checks to see if the arrival time is known. True if is known
    public final Boolean isArrivalTimeKnown() {
        return !this.getArrivalTime().equals(RouteTime.UNKNOWN);
    }

    //returns the departure time
    //assumes that the connection time must be incurred even at the original departure airport since it takes time to reach the flight from the booking counter
    public final RouteTime departureTime() {
        if(!isArrivalTimeKnown())
            throw new NullPointerException("Route time cannot be unknown");
        return this.getArrivalTime().plus(this.getAirport().getConnectionTimeMin());
    }

    //returns the available flights given a fareClass
    public Set<Flight> availableFlights(FareClass fareClass) {
    	Objects.requireNonNull(fareClass,"fareClass cannot be null");
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
    public int compareTo(RouteNode routeNode) {
        Objects.requireNonNull(routeNode,"Route node input cannot be null");
        int compareValue = routeNode.getArrivalTime().compareTo(this.getArrivalTime());
        if(compareValue == 0) {
            compareValue = this.getAirport().compareTo(routeNode.getAirport());
        }
        return compareValue;
    }
}
