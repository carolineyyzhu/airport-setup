package airtravel;

import java.util.Objects;

/**
 * This class defines the fare class which breaks the seat class into smaller increments
 *
 */
public final class FareClass {
	
	private final int identifier;
	private final SeatClass seatClass;
	
	/**
	 * Private constructor for fare class
	 * @param identifier - the unique identifier for the fare class
	 * @param seatClass - the seat class corresponding to the fare class
	 */
	private FareClass(int identifier, SeatClass seatClass) {
		this.identifier = identifier;
		this.seatClass = seatClass;
	}
	
	/**
	 * A builder method for fare class
	 * @param identifier - the unique identifier for fare class
	 * @param seatClass - the seat class corresponding to the fare class
	 * @return a new fareClass
	 */
	public static final FareClass of(int identifier, SeatClass seatClass) {
		Objects.requireNonNull(identifier,"Identifier cannot be null");
		Objects.requireNonNull(seatClass,"seatClass cannot be null");
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

	//Overrides the hash code method according to the identifier. 
	@Override
	public int hashCode() {
		final int prime = 31;
		return prime + (((Integer) identifier).hashCode());
	}
	
	//returns the identifier
	public final int getIdentifier() {
		return identifier;
	}
	
	//returns the seat class
	public final SeatClass getSeatClass() {
		return seatClass;
	}
	

}
