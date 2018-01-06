package tsp;

import java.io.IOException;

import cli.CommandLineInterface;
import tsp.algorithm.individual.PathIndividual;
import tsp.algorithm.mutation.MutationOperator;
import tsp.algorithm.mutation.SwapMutationOperator;

public class Main {

	public static void main(String[] args) throws IOException {
//		CommandLineInterface cli = new CommandLineInterface();
//		cli.enter();
		
		PathIndividual path = new PathIndividual(5 + 1);

		path.setStartCity(0);
		path.setCity(1, 1);
		path.setCity(2, 2);
		path.setCity(3, 3);
		path.setCity(4, 4);
		
		MutationOperator mutationOperator = new SwapMutationOperator();
		mutationOperator.mutate(path);
	}

}
