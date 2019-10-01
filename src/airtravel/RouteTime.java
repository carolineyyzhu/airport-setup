package airtravel;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Class to compute the route time 
 *
 */
public final class RouteTime implements Comparable<RouteTime>{
	
	private final LocalTime routeTime;
    public static final RouteTime UNKNOWN = new RouteTime(null);
	
	/**
	 * This constructor creates a new RouteTime given a localTime
	 * @param routeTime: the time it takes for a route to occur
	 */
	public RouteTime(LocalTime routeTime){
		//No need for null check, methods handle nulls
		this.routeTime = routeTime;
	}

	//checks to see if the route time is known
	public boolean isKnown() {
		return routeTime != null;
	}
	
	//returns the route time if the time is known
	public LocalTime getTime() {
		if (isKnown()) {
			return routeTime;
		} else {
			throw new IllegalStateException ("Route Time is unknown");
		}
		
	}
	
	//adds the flight duration to the route time
	public RouteTime plus(Duration duration) {
		Objects.requireNonNull(duration, "Duration cannot be null");
		if (isKnown()) {
			return new RouteTime(routeTime.plus(duration));
		} else {
			return UNKNOWN;
		}
		
	}

	//compare routes based on their times with unknown routes being the largest.
	@Override
	public int compareTo(RouteTime routeTime) {
		if (this.isKnown() && routeTime.isKnown())
			return this.routeTime.compareTo(routeTime.getTime());
		else if (!this.isKnown())
			return -1;
		else
			return 1;
	}

	

}
