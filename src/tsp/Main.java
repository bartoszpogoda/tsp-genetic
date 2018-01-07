package tsp;

import java.io.IOException;

import cli.CommandLineInterface;
import tsp.algorithm.crossover.CrossoverOperator;
import tsp.algorithm.crossover.PMXCrossoverOperator;
import tsp.algorithm.individual.PathIndividual;
import tsp.algorithm.mutation.MutationOperator;
import tsp.algorithm.mutation.SwapMutationOperator;
import tsp.algorithm.population.PathPopulation;
import tsp.algorithm.util.FitnessCalculator;
import tsp.instance.Instance;
import tsp.instance.reader.InstanceFileReader;

public class Main {

	public static void main(String[] args) throws IOException {
//		CommandLineInterface cli = new CommandLineInterface();
//		cli.enter();
		
		PathIndividual path = new PathIndividual(5 + 1);

//		path.setStartCity(0);
//		path.setCity(1, 1);
//		path.setCity(2, 2);
//		path.setCity(3, 3);
//		path.setCity(4, 4);
//		
//		MutationOperator mutationOperator = new SwapMutationOperator();
//		mutationOperator.mutate(path);
		
//		CrossoverOperator crossoverOperator = new PMXCrossoverOperator();
//		crossoverOperator.crossover(path, path);
		
		InstanceFileReader instanceFileReader = new InstanceFileReader();
		Instance instance = instanceFileReader.read("input/test/bays29.tsp");
	
		PathPopulation pathPopulation = PathPopulation.generateInitialPopulation(10, instance);
		FitnessCalculator fitnessCalculator = new FitnessCalculator(instance);
		
		PathIndividual theFittest = pathPopulation.findTheFittest(fitnessCalculator);
		
		System.out.println(fitnessCalculator.calculateFitness(theFittest));
		
	}

}
