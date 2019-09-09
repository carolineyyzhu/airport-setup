package airtravel;

public interface Flight {
    public String getCode();
    public Leg getLeg();
    public Airport origin();
    public Airport destination();
    public FlightSchedule getFlightSchedule();
    public LocalTime departureTime();
    public LocalTime arrivalTime();
    public boolean isShort();
}