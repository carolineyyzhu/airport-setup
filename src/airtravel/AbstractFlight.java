package airtravel;
import java.time.Duration;
import java.time.LocalTime;

public abstract class AbstractFlight implements Flight {
    public Airport origin() {
        return this.getLeg().getOrigin();
    }

    public Airport destination() {
        return this.getLeg().getDestination();
    }

    public LocalTime departureTime() {
        return this.getFlightSchedule().getDepartureTime();
    }

    public LocalTime arrivalTime() {
        return this.getFlightSchedule().getArrivalTime();
    }

    public boolean isShort(Duration maxDuration) {
        //Placeholder method; issue of duration must be solved because this duration is an empty object
        return this.getFlightSchedule().isShort(maxDuration);
    }

}
