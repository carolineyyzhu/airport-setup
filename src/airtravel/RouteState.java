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

/**
 * Intermediate route class to assist the routeFinder
 *
 */
final class RouteState {
	
	private Map<Airport, RouteNode> airportNode = new HashMap<>();
	private final NavigableSet<RouteNode> unreached = new TreeSet<>();

	//private constructor
	private RouteState(Set<Airport> airports, Airport origin, LocalTime departureTime) {
		
		RouteNode originNode = RouteNode.of(origin, new RouteTime(departureTime), null);
		this.airportNode.put(origin, originNode);
		airports.stream().forEach(n -> this.airportNode.put(n, RouteNode.of(n, RouteTime.UNKNOWN(), null)));
		airports.stream().forEach(n -> this.unreached.add(RouteNode.of(n, RouteTime.UNKNOWN(), null)));
		
	}
	
	/**
	 * Package private constructor
	 * @param airports a set of airports to search
	 * @param origin the airport of origin
	 * @param departureTimethe time to depart
	 * @return a new RouteState
	 */
	static RouteState of(Set<Airport> airports, Airport origin, LocalTime departureTime) {
        
		return new RouteState(airports, origin, departureTime);
	}
	
	//replace a node when reached: remove it from unreached and add it to the known map.
	void replaceNode(RouteNode routeNode) {
		airportNode.put(routeNode.getAirport(), routeNode);
		unreached.remove(routeNode);
		
	}
	
	//True if all nodes are reached.
	boolean allReached() {
		return unreached.isEmpty();
		
	}
	
	//returns the closest unreached node
	RouteNode closestUnreached() {
		if (!allReached()) {
			return Collections.min(unreached);
		} else{
			throw new NoSuchElementException ("All nodes have been reached");
		}
		
	}
	
	//returns an airport node for a given airport
	RouteNode airportNode(Airport airport) {
		return airportNode.get(airport);
		
	}

}
