package tsp.algorithm.mutation;

import tsp.algorithm.individual.PathIndividual;
import tsp.algorithm.util.RandomGenerator;

public class InvertMutationOperator implements MutationOperator {

	RandomGenerator randomGenerator;

	public InvertMutationOperator() {
		randomGenerator = new RandomGenerator();
	}

	public InvertMutationOperator(RandomGenerator randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

	class InversionRange {
		int start;
		int end;
	}

	@Override
	public void mutate(PathIndividual pathIndividual) {
		InversionRange inversionRange = generateRandomInversionRange(pathIndividual);

		for (int i = inversionRange.start; i < (inversionRange.start + inversionRange.end + 1) / 2; i++) {
			int citySwapBuffer = pathIndividual.getCity(i);
			pathIndividual.setCity(i, pathIndividual.getCity(inversionRange.end + inversionRange.start - i));
			pathIndividual.setCity(inversionRange.end + inversionRange.start - i, citySwapBuffer);
		}

	}

	private InversionRange generateRandomInversionRange(PathIndividual pathIndividual) {
		InversionRange inversionRange = new InversionRange();

		inversionRange.start = randomGenerator.generateIntInRangeInclusive(1, pathIndividual.getLength() - 3);
		inversionRange.end = randomGenerator.generateIntInRangeInclusive(inversionRange.start + 1,
				pathIndividual.getLength() - 2);

		return inversionRange;
	}

	@Override
	public String toString() {
		return "Invert Mutation Operator";
	}
}
