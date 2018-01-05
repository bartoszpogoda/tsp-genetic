package tsp.algorithm.crossover;

import tsp.algorithm.individual.PathIndividual;

public interface CrossoverOperator {
	PathIndividual crossover(PathIndividual firstParent, PathIndividual secondParent);
}
