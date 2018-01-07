package tsp.algorithm.crossover;

import tsp.algorithm.individual.PathIndividual;
import tsp.algorithm.util.RandomGenerator;

public class PMXCrossoverOperator implements CrossoverOperator {

	RandomGenerator randomGenerator;

	public PMXCrossoverOperator() {
		this.randomGenerator = new RandomGenerator();
	}

	public PMXCrossoverOperator(RandomGenerator randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

	class MatchingSectionBounds {
		int lowerBound;
		int upperBound;
	}

	@Override
	public PathIndividual crossover(PathIndividual firstParent, PathIndividual secondParent) {
		
		PathIndividual child = new PathIndividual(firstParent.getLength());

		// generate matching section bounds
		MatchingSectionBounds matchingSectionBounds = generateRandomMatchingSectionBounds(firstParent);
		
		// copy cities from first parent's matching section to the child
		for(int i = matchingSectionBounds.lowerBound ; i <= matchingSectionBounds.upperBound ; i++) {
			child.setCity(i, firstParent.getCity(i));
		}
		
		// search for cities in seconds parent's matching section that aren't already in child
		for(int i = matchingSectionBounds.lowerBound ; i <= matchingSectionBounds.upperBound ; i++) {
			
			int cityToCheck = secondParent.getCity(i);
			boolean cityAlreadyInChild = false;
			
			for(int j = matchingSectionBounds.lowerBound ; j <= matchingSectionBounds.upperBound && !cityAlreadyInChild ; j++) {
				if(child.getCity(j) == cityToCheck) {
					cityAlreadyInChild = true;
				}
			}
			
			if(!cityAlreadyInChild) {
				int foundPosition = findPositionForValueInSecondParent(matchingSectionBounds, firstParent, secondParent, i);
				secondParent.setCity(foundPosition, cityToCheck);
			}
		}
		
		// copy remaining cities from second parent
		copyRemainingCities(firstParent, secondParent, child, matchingSectionBounds);
		
		return child;
	}

	private int findPositionForValueInSecondParent(MatchingSectionBounds matchingSectionBounds, PathIndividual firstParent, PathIndividual secondParent, int currentPositionInSecondParent) {
		int correspondingValueInFirstParent = firstParent.getCity(currentPositionInSecondParent);
		
		// find value in second parent
		boolean valueFound = false;
		int foundIndex = -1;
		for(int j = 1 ; j <= secondParent.getLength() - 2 && !valueFound ; j++) {
			if(secondParent.getCity(j) == correspondingValueInFirstParent) {
				valueFound = true;
				foundIndex = j;
			}
		}
		
		if(matchingSectionBounds.lowerBound <= foundIndex && matchingSectionBounds.upperBound >= foundIndex) {
			return findPositionForValueInSecondParent(matchingSectionBounds, firstParent, secondParent, foundIndex);
		} else {
			return foundIndex;
		}
	}

	private void copyRemainingCities(PathIndividual firstParent, PathIndividual secondParent, PathIndividual child,
			MatchingSectionBounds matchingSectionBounds) {
		for(int i = 1 ; i < matchingSectionBounds.lowerBound ; i++) {
			if(child.getCity(i) == 0) {
				child.setCity(i, secondParent.getCity(i));
			}
		}
		
		for(int i = matchingSectionBounds.upperBound ; i < firstParent.getLength() - 1 ; i++) {
			if(child.getCity(i) == 0) {
				child.setCity(i, secondParent.getCity(i));
			}
		}
	}
	
	private MatchingSectionBounds generateRandomMatchingSectionBounds(PathIndividual anyParent) {
		MatchingSectionBounds matchingSectionBounds = new MatchingSectionBounds();

		matchingSectionBounds.lowerBound = randomGenerator.generateIntInRangeInclusive(1, anyParent.getLength() - 2);

		if (matchingSectionBounds.lowerBound == 1) {
			// prevent full section selection
			matchingSectionBounds.upperBound = randomGenerator
					.generateIntInRangeInclusive(matchingSectionBounds.lowerBound, anyParent.getLength() - 3);

		} else {
			matchingSectionBounds.upperBound = randomGenerator
					.generateIntInRangeInclusive(matchingSectionBounds.lowerBound, anyParent.getLength() - 2);
		}

		return matchingSectionBounds;
	}
}
