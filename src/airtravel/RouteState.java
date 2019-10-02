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
		Set<Airport> airportsNoOrigin= new TreeSet<Airport>(airports);
		airportsNoOrigin.remove(origin);
		RouteNode originNode = RouteNode.of(origin, new RouteTime(departureTime), null);
		this.airportNode.put(origin, originNode);

		airportsNoOrigin.forEach(n -> this.airportNode.put(n, RouteNode.of(n)));
		airportsNoOrigin.forEach(n -> this.unreached.add(RouteNode.of(n)));
	}
	
	/**
	 * Package private constructor
	 * @param airports a set of airports to search
	 * @param origin the airport of origin
	 * @param departureTime the time to depart
	 * @return a new RouteState
	 */
	static RouteState of(Set<Airport> airports, Airport origin, LocalTime departureTime) {
	    Objects.requireNonNull(airports,"Airports set input cannot be null");
	    Objects.requireNonNull(origin,"Arrival time be null");
	    Objects.requireNonNull(departureTime,"Departure time cannot be null");
        
		return new RouteState(airports, origin, departureTime);
	}
	
	//replace a node when reached: remove it from unreached and add it to the known map.
	//assumes  that  the  corresponding  airport  is  known  to  the  RouteState and unreached.
	void replaceNode(RouteNode routeNode) {
		Objects.requireNonNull(routeNode, "Route Node cannot be a null value");
		
		//Airport airport = routeNode.getAirport();
		//unreached.remove(airportNode(airport));
		RouteNode closestNode = unreached.pollFirst();
		unreached.add(routeNode);
		airportNode.replace(closestNode.getAirport(), routeNode);
		
	}
	
	//True if all nodes are reached.
	boolean allReached() {
		return unreached.isEmpty();
		
	}
	
	//returns the closes un-reached node
	RouteNode closestUnreached() {
		if (!allReached()) {
			RouteNode temp = unreached.pollFirst();
			return temp;
		} else{
			throw new NoSuchElementException ("All nodes have been reached");
		}
		
	}
	
	//returns an airport node for a given airport
	RouteNode airportNode(Airport airport) {
		return airportNode.get(airport);
		
	}

}
