package tsp.algorithm.mutation;

import tsp.algorithm.individual.PathIndividual;
import tsp.algorithm.util.RandomGenerator;

public class SwapMutationOperator implements MutationOperator {

	RandomGenerator randomGenerator;

	public SwapMutationOperator() {
		randomGenerator = new RandomGenerator();
	}

	public SwapMutationOperator(RandomGenerator randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

	class SwapPositions {
		int smallerPosition;
		int biggerPosition;
	}

	@Override
	public void mutate(PathIndividual pathIndividual) {
		SwapPositions swapPositions = generateRandomPositions(pathIndividual);
		
		int cityA = pathIndividual.getCity(swapPositions.smallerPosition);
		pathIndividual.setCity(swapPositions.smallerPosition, pathIndividual.getCity(swapPositions.biggerPosition));
		pathIndividual.setCity(swapPositions.biggerPosition, cityA);
	}

	private SwapPositions generateRandomPositions(PathIndividual pathIndividual) {
		SwapPositions swapPositions = new SwapPositions();

		swapPositions.smallerPosition = randomGenerator.generateIntInRangeInclusive(1, pathIndividual.getLength() - 3);
		swapPositions.biggerPosition = randomGenerator.generateIntInRangeInclusive(swapPositions.smallerPosition + 1, pathIndividual.getLength() - 2);

		return swapPositions;
	}
}
