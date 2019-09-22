package airtravel;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

final class RouteState {
	
	private Map<Airport, RouteNode> airportNode;
	private final NavigableSet<RouteNode> unreached;
	
	private RouteState(Set<Airport> airports, Airport origin, LocalTime departureTime) {
		this.airportNode = Collections.EMPTY_MAP;
		RouteNode originNode = RouteNode.of(origin, new RouteTime(departureTime), null);
		this.airportNode.put(origin, originNode);
		airports.stream().forEach(n -> RouteNode.of(n, new RouteTime(), null) );
		
		this.unreached = 
	}

}
