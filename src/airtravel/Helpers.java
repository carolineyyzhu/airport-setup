package airtravel;

import java.util.Objects;

final class Helpers {
	
	static <T> void nullCheck(T test) {
		Objects.requireNonNull(test,"Null input resived in method header");
	}
	
	static <T> void nullCheck(T test1, T test2) {
		nullCheck(test1);
		nullCheck(test2);
	}
	

	static <T> void nullCheck(T test1, T test2, T test3) {
		nullCheck(test1, test2);
		nullCheck(test3);
	}

	static <T> void nullCheck(T test1, T test2, T test3, T test4) {
		nullCheck(test1, test2);
		nullCheck(test3, test4);
	}

}
