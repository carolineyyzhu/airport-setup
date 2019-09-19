package airtravel;

public final class FareClass {
	
	private final int identifier;
	private final SeatClass seatClass;
	
	private FareClass(int identifier, SeatClass seatClass) {
		this.identifier = identifier;
		this.seatClass = seatClass;
	}
	
	public static final FareClass of(int identifier, SeatClass seatClass) {
		return new FareClass(identifier, seatClass);
	}
	
	//Overrides equals method
	@Override
	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		FareClass other = (FareClass) obj;
		return (this == obj || other.getIdentifier() == identifier);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime + (((Integer) identifier).hashCode());
	}
	
	public final int getIdentifier() {
		return identifier;
	}
	
	public final SeatClass getSeatClass() {
		return seatClass;
	}
	

}
