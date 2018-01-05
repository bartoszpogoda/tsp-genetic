package tsp.algorithm.mutation;

import tsp.algorithm.individual.PathIndividual;

public interface MutationOperator {
	void mutate(PathIndividual individual);
}
