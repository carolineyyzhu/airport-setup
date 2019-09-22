package airtravel;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

public final class RouteFinder {
	private final Set<Airport> airports;
	
	private RouteFinder(Set<Airport> airports) {
		this.airports = airports;
	}
	
	public RouteFinder of(Set<Airport> airports){
		Objects.requireNonNull(airports, "Set<Airport> is required to be non null");
		return new RouteFinder(airports);
	}
	
	public final RouteNode route(Airport origin,
				 				 Airport destination,
				 				 LocalTime departureTime,
				 				 FareClass fareClass) {
		
		return routeHelper(origin, destination, departureTime, fareClass);
		
	}
	
	private final RouteNode routeHelper(Airport origin,
			 							Airport destination,
			 							LocalTime departureTime,
			 							FareClass fareClass){
	RouteState currentRoutes = RouteState.of(this.airports, origin, departureTime);
	while (!currentRoutes.allReached()) {
		RouteNode currentNode = currentRoutes.closestUnreached();
		if (currentNode.getAirport().equals(destination)) {
			return currentNode;
		}
		for (Flight currentFlight: currentNode.availableFlights(fareClass)) {
			Duration length = Duration.between(currentFlight.departureTime(), currentFlight.arrivalTime());
			RouteTime arrivalTime = new RouteTime(currentFlight.arrivalTime());
			
			if (arrivalTime.compareTo(arrivalTime.plus(length)) < 0) {
				RouteNode newNode = RouteNode.of(currentFlight, currentNode);
				currentRoutes.replaceNode(newNode);	
			}
		}

	}
	return null;
}
}
