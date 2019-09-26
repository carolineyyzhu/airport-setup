package airtravel;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

final class RouteState {
	
	private Map<Airport, RouteNode> airportNode = new HashMap<>();
	private final NavigableSet<RouteNode> unreached = new TreeSet<>();

	private RouteState(Set<Airport> airports, Airport origin, LocalTime departureTime) {
        Objects.requireNonNull(airports,"Airports set input cannot be null");
        Objects.requireNonNull(origin,"Arrival time be null");
        Objects.requireNonNull(departureTime,"Departure time cannot be null");

		RouteNode originNode = RouteNode.of(origin, new RouteTime(departureTime), null);
		this.airportNode.put(origin, originNode);
		airports.forEach(n -> this.airportNode.put(n, RouteNode.of(n)));
		airports.forEach(n -> this.unreached.add(RouteNode.of(n)));
		
	}
	
	static RouteState of(Set<Airport> airports,Airport origin, LocalTime departureTime) {
		return new RouteState(airports, origin, departureTime);
	}
	
	void replaceNode(RouteNode routeNode) {
		airportNode.put(routeNode.getAirport(), routeNode);
		unreached.remove(routeNode); //TODO: Do we need to remove this from unreaced?
		
	}
	
	boolean allReached() {
		return unreached.isEmpty();
		
	}
	
	RouteNode closestUnreached() {
		if (!allReached()) {
			return Collections.min(unreached);
		} else{
			throw new NoSuchElementException ("All nodes have been reached");
		}
		
	}
	
	RouteNode airportNode(Airport airport) {
		return airportNode.get(airport);
		
	}

}
