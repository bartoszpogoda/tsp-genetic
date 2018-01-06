package tsp.algorithm.mutation;

import java.util.Random;

import tsp.algorithm.individual.PathIndividual;

public class SwapMutationOperator implements MutationOperator {

	Random randomGenerator;

	public SwapMutationOperator() {
		randomGenerator = new Random();
	}

	public SwapMutationOperator(Random randomGenerator) {
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

		swapPositions.smallerPosition = randomGenerator.nextInt(pathIndividual.getLength() - 3) + 1;
		swapPositions.biggerPosition = randomGenerator
				.nextInt((pathIndividual.getLength() - 1 - (swapPositions.smallerPosition + 1)))
				+ swapPositions.smallerPosition + 1;

		return swapPositions;
	}
}
