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
		Helpers.nullCheck(obj);
		boolean equal = false;
		FareClass other = (FareClass) obj;
		if (this == obj)
			equal = true;
		if(other.getIdentifier() == identifier)
			equal = true;
		return equal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (((Integer) identifier).hashCode());
		return result;
	}
	
	public final int getIdentifier() {
		return identifier;
	}
	
	public final SeatClass getSeatClass() {
		return seatClass;
	}
	

}
