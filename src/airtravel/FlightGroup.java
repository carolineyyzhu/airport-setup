package airtravel;

import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeSet;

public final class FlightGroup {
    private final Airport origin;

    private FlightGroup(Airport origin) {
        this.origin = origin;
    }

    public static final FlightGroup of(Airport origin) {
        if(origin == null)
            throw new NullPointerException("Null value entered");
        return new FlightGroup(origin);
    }

    public Airport getOrigin() {
        return this.origin;
    }

    private final NavigableMap<LocalTime, Set<Flight>> bst = new BinarySearchTree();

    public final boolean add(Flight flight) {
        if(!flight.getLeg().getOrigin().equals(this.origin)) {
            throw new IllegalArgumentException("This flight does not leave from the same airport as this flight group");
        }
        //add flight into map
        return true;
    }

    public final boolean remove(Flight flight) {
        boolean flightRemoved = false;
        if (bst.containsValue(flight)) {
            //bst.remove() object key
            flightRemoved = true;
        } else {
            throw new IllegalArgumentException("This flight does not exist in this flight group")
        }
        return flightRemoved;
    }

    public final Set<Flight> flightsAtOrAfter(LocalTime departureTime) {
        //returns flights leaving at or after a certain time
        //below is a placeholder
    }



}
