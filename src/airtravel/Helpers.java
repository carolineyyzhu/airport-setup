package airtravel;

import java.util.Objects;

/**
 * This class creates helper methods to deal with repeated code.
 *
 */
final class Helpers {
	//methods that can take multiple amounts of arguments
	
	static void nullCheck(Object ... test) {
		for (Object check: test) {
			Objects.requireNonNull(check,"Null input resived in method header");
		}
	}

}
