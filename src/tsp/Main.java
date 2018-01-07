package tsp;

import java.io.IOException;

import cli.CommandLineInterface;
import tsp.algorithm.Algorithm;
import tsp.algorithm.crossover.CrossoverOperator;
import tsp.algorithm.crossover.PMXCrossoverOperator;
import tsp.algorithm.individual.PathIndividual;
import tsp.algorithm.mutation.MutationOperator;
import tsp.algorithm.mutation.SwapMutationOperator;
import tsp.algorithm.population.PathPopulation;
import tsp.algorithm.thread.AlgorithmTerminator;
import tsp.algorithm.thread.BestDistanceSampler;
import tsp.algorithm.util.FitnessCalculator;
import tsp.instance.Instance;
import tsp.instance.reader.InstanceFileReader;

public class Main {

	public static void main(String[] args) throws IOException {
		// CommandLineInterface cli = new CommandLineInterface();
		// cli.enter();

		PathIndividual path = new PathIndividual(5 + 1);

		// path.setStartCity(0);
		// path.setCity(1, 1);
		// path.setCity(2, 2);
		// path.setCity(3, 3);
		// path.setCity(4, 4);
		//
		// MutationOperator mutationOperator = new SwapMutationOperator();
		// mutationOperator.mutate(path);

		// CrossoverOperator crossoverOperator = new PMXCrossoverOperator();
		// crossoverOperator.crossover(path, path);

		InstanceFileReader instanceFileReader = new InstanceFileReader();
		Instance instance = instanceFileReader.read("input/eil101.tsp");

		// PathPopulation pathPopulation =
		// PathPopulation.generateInitialPopulation(10, instance);
		FitnessCalculator fitnessCalculator = new FitnessCalculator(instance);

		// PathIndividual theFittest =
		// pathPopulation.findTheFittest(fitnessCalculator);

		// System.out.println(fitnessCalculator.calculateFitness(theFittest));

		AlgorithmTerminator algorithmTerminator = new AlgorithmTerminator();
		algorithmTerminator.setTimeLimitMs(600000);

		BestDistanceSampler bestDistanceSampler = new BestDistanceSampler();

		Algorithm algorithm = new Algorithm.AlgorithmBuilder().populationSize(50).tournamentSize(2).crossoverRate(0.8)
				.mutationRate(0.2).crossoverOperator(new PMXCrossoverOperator())
				.mutationOperator(new SwapMutationOperator()).algorithmTerminator(algorithmTerminator)
				.bestDistanceSampler(bestDistanceSampler).create();

		algorithm.execute(instance);

	}

}
