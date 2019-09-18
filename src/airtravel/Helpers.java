package airtravel;

public final class Helpers {
	
	protected static <T> void nullCheck(T test) {
		if (test == null){
			throw new NullPointerException("Null inputs were received");
		}
	}
	
	protected static <T> void nullCheck(T test1, T test2) {
		if (test1 == null || test2 == null){
			throw new NullPointerException("Null inputs were received");
		}
	}

}
