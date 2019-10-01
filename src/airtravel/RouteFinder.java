package airtravel;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

/**
 * 
 * This method uses an algorithm to find the shortest route.
 *
 */
public final class RouteFinder {
	private final Set<Airport> airports;
	
	//private build method
	private RouteFinder(Set<Airport> airports) {
		this.airports = airports;
	}
	
	/**
	 * This method is the public constructor for route finder
	 * @param airports is a set of possible airports
	 * @return a new routeFinder 
	 */
	public static RouteFinder of(Set<Airport> airports){
		Objects.requireNonNull(airports, "The set of airports cannot be a null value");
		return new RouteFinder(airports);
	}
	
	/**
	 * This method finds the shortest route between two airports
	 * @param origin - the origin airport
	 * @param destination - the destination airport
	 * @param departureTime - the time to leave
	 * @param fareClass - what fareClass to use
	 * @return null if no route is found otherwise a routeNode with the shortest route.
	 */
	public final RouteNode route(Airport origin, Airport destination, LocalTime departureTime, FareClass fareClass) {
		Objects.requireNonNull(origin,"origin input cannot be null");
		Objects.requireNonNull(destination,"destination input cannot be null");
		Objects.requireNonNull(departureTime,"departureTime input cannot be null");
		Objects.requireNonNull(fareClass,"fareClass input cannot be null");
		
		return routeHelper(origin, destination, departureTime, fareClass);
	}
	
	//This method implements the route finding algorithm
	private final RouteNode routeHelper(Airport origin, Airport destination, LocalTime departureTime, FareClass fareClass){
		RouteState currentRoutes = RouteState.of(this.airports, origin, departureTime);
		while (!currentRoutes.allReached()) {
			RouteNode currentNode = currentRoutes.closestUnreached();
			if (currentNode.getAirport().equals(destination)) {
				return currentNode;
			}
			for (Flight currentFlight : currentNode.availableFlights(fareClass)) {
				RouteNode newNode = RouteNode.of(currentFlight, currentNode);
				if (newNode.compareTo(currentNode) < 0) {
					currentRoutes.replaceNode(newNode);
				}
			}
		}
		return null;
	}
}
