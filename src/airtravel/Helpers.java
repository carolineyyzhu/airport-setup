package airtravel;

final class Helpers {
	
	static <T> void nullCheck(T test) {
		if (test == null){
			throw new NullPointerException("Null inputs were received");
		}
	}
	
	static <T> void nullCheck(T test1, T test2) {
		if (test1 == null || test2 == null){
			throw new NullPointerException("Null inputs were received");
		}
	}

	static <T> void nullCheck(T test1, T test2, T test3) {
		if (test1 == null || test2 == null || test3 == null){
			throw new NullPointerException("Null inputs were received");
		}
	}

}
