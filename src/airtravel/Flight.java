package airtravel;
import java.time.Duration;
import java.time.LocalTime;

/**
 * Interface for the basic flight Flight containing starting methods.
 *
 */
public interface Flight {
    public String getCode();
    public Leg getLeg();
    public Airport origin();
    public Airport destination();
    public FlightSchedule getFlightSchedule();
    public LocalTime departureTime();
    public LocalTime arrivalTime();
    public boolean isShort(Duration maxDuration);
    public SeatConfiguration seatsAvailable(FareClass fareClass);
    public boolean hasSeats(FareClass fareClass);
}
