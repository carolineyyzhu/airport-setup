package airtravel;

/**
 * This class represents a non-stop route between two airports
 */
public final class Leg {

    private Airport origin;
    private Airport destination;

    /**
     * Constructor for a leg of the trip
     * @param origin where the flight originated
     * @param destination where the flight is headed
     */
    private Leg(Airport origin, Airport destination) {
        origin = this.origin;
        destination = this.destination;
    }

    /**
     * Public constructor/build method
     * @param origin where the flight originated
     * @param destination where the flight is headed
     * @return generated Leg object with a non-null origin and destination
     */
    public static final Leg of(Airport origin, Airport destination) {
        if(origin == null || destination == null) {
            throw new NullPointerException("Null input values");
        }
        return new Leg(origin, destination);
    }

    /**
     * getter for the origin airport
     * @return origin
     */

    public Airport getOrigin() {
        return this.origin;
    }

    /**
     * getter for the destination airport
     * @return destination
     */
    public Airport getDestination() {
        return this.destination;
    }
}
