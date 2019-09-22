package airtravel;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

//TODO DO WE NEED NULL CHECKS FOR THIS CLASS???
public final class RouteTime implements Comparable<RouteTime>{
	
	private final LocalTime routeTime;
	
	public RouteTime(LocalTime routeTime){
		Objects.requireNonNull(routeTime, "Route time cannot be null");

		this.routeTime = routeTime;
	}
	
	public static final RouteTime UNKNOWN(){
		return null;
	}
	
	public boolean isKnown() {
		return routeTime != null;
	}
	
	public LocalTime getTime() {
		if (isKnown()) {
			return routeTime;
		} else {
			throw new IllegalStateException ("Route Time is unknown");
		}
		
	}
	
	public RouteTime plus(Duration duration) {
		Objects.requireNonNull(duration, "Duration cannot be null");
		if (isKnown()) {
			return new RouteTime(routeTime.plus(duration));
		} else {
			return UNKNOWN();
		}
		
	}

	@Override
	public int compareTo(RouteTime routeTime) {
		if (isKnown() && routeTime.isKnown()) {
			return this.routeTime.compareTo(routeTime.getTime());
		} else if (isKnown()){
			return -1;
		} else {
			return 1;			
		}
	}

	

}
