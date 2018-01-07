package tsp.algorithm.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {
	
	public int generateIntInRangeInclusive(int min, int max) {
		
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);

	    return randomNum;
	}
	
	/**
	 * Returns a pseudorandom double value between zero (inclusive) and one (exclusive).
	 * 
	 * @return a pseudorandom double value between zero (inclusive) and one (exclusive).
	 */
	public double nextDouble() {
		return ThreadLocalRandom.current().nextDouble();
	}
}
