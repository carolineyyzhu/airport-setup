package airtravel;
import java.time.Duration;
import java.time.LocalTime;

/**
 * Abstract flight class containing requirements of a basic flight of any type
 * 
 */
public abstract class AbstractFlight implements Flight {
	
	//return Where the flight comes from
    public Airport origin() {
        return this.getLeg().getOrigin();
    }

    //return Where the flight is going
    public Airport destination() {
        return this.getLeg().getDestination();
    }

    //return When the flight departs
    public LocalTime departureTime() {
        return this.getFlightSchedule().getDepartureTime();
    }

    //return When the flight arrives
    public LocalTime arrivalTime() {
        return this.getFlightSchedule().getArrivalTime();
    }

    //return True if the flight is short
    public boolean isShort(Duration maxDuration) {
        return this.getFlightSchedule().isShort(maxDuration);
    }

}
