package airtravel;

public final class RouteNode implements Comparable<RouteNode>{
    private final Airport airport;
    private final RouteTime arrivalTime;
    private final RouteNode previous;

    private RouteNode(Airport airport, RouteTime arrivalTime, RouteNode previous) {
        this.airport = airport;
        this.arrivalTime = arrivalTime;
        this.previous = previous;
    }

    public static final of(Airport airport, RouteTime arrivalTime, RouteNode previous) {
        
    }


    public Airport getAirport() {
        return airport;
    }

    public RouteTime getArrivalTime() {
        return arrivalTime;
    }

    public RouteNode getPrevious() {
        return previous;
    }

    @Override
    public int compareTo(RouteNode o) {
        return 0;
    }
}
