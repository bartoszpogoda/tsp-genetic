package tsp.algorithm.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {
	
	public int generateIntInRangeInclusive(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);

	    return randomNum;
	}
}
