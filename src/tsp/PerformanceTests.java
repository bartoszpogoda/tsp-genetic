package tsp;

import java.io.IOException;

import tsp.algorithm.Algorithm;
import tsp.algorithm.crossover.CrossoverOperator;
import tsp.algorithm.crossover.PMXCrossoverOperator;
import tsp.algorithm.individual.PathIndividual;
import tsp.algorithm.mutation.InvertMutationOperator;
import tsp.algorithm.mutation.MutationOperator;
import tsp.algorithm.mutation.SwapMutationOperator;
import tsp.algorithm.thread.AlgorithmTerminator;
import tsp.algorithm.thread.BestDistanceSampler;
import tsp.instance.Instance;
import tsp.instance.reader.InstanceFileReader;

public class PerformanceTests {
	public static void main(String[] args) throws IOException {

		int fixedTournamentSize = 2;
		CrossoverOperator fixedCrossoverOperator = new PMXCrossoverOperator();

		// eil101.tsp
		long timeLimitMs = 6 * 60 * 1000;
//		run("input/eil101.tsp", timeLimitMs, 10, fixedTournamentSize, 0.8, 0.01, fixedCrossoverOperator,
//				new InvertMutationOperator(), "101_invert_pop10");
//		run("input/eil101.tsp", timeLimitMs, 50, fixedTournamentSize, 0.8, 0.01, fixedCrossoverOperator,
//				new InvertMutationOperator(), "101_invert_pop50");
//		run("input/eil101.tsp", timeLimitMs, 100, fixedTournamentSize, 0.8, 0.01, fixedCrossoverOperator,
//				new InvertMutationOperator(), "101_invert_pop100");

		// rbg403.atsp
//		run("input/rbg403.atsp", timeLimitMs, 40, fixedTournamentSize, 0.8, 0.01, fixedCrossoverOperator,
//				new SwapMutationOperator(), "403_swap_pop40");
		run("input/rbg403.atsp", timeLimitMs, 200, 10, 0.8, 0.01, fixedCrossoverOperator,
				new SwapMutationOperator(), "403_swap_pop200");
		run("input/rbg403.atsp", timeLimitMs, 400, fixedTournamentSize, 0.8, 0.01, fixedCrossoverOperator,
				new SwapMutationOperator(), "403_swap_pop400");

		// eil101.tsp
//		run("input/d1291.tsp", timeLimitMs, 129, fixedTournamentSize, 0.8, 0.01, fixedCrossoverOperator,
//				new InvertMutationOperator(), "1291_invert_pop129");
//		run("input/d1291.tsp", timeLimitMs, 645, fixedTournamentSize, 0.8, 0.01, fixedCrossoverOperator,
//				new InvertMutationOperator(), "1291_invert_pop645");
//		run("input/d1291.tsp", timeLimitMs, 1290, fixedTournamentSize, 0.8, 0.01, fixedCrossoverOperator,
//				new InvertMutationOperator(), "1291_invert_pop1290");
	}

	private static void run(String instanceFilename, long timeLimitMs, int populationSize, int tournamentSize,
			double crossoverRate, double mutationRate, CrossoverOperator crossoverOperator,
			MutationOperator mutationOperator, String outputFilename) throws IOException {
		InstanceFileReader ifr = new InstanceFileReader();
		Instance instance = ifr.read(instanceFilename);

		AlgorithmTerminator algorithmTerminator = new AlgorithmTerminator();
		algorithmTerminator.setTimeLimitMs(timeLimitMs);

		BestDistanceSampler bestDistanceSampler = new BestDistanceSampler();
		bestDistanceSampler.setFilename(outputFilename);

		Algorithm algorithm = new Algorithm.AlgorithmBuilder().algorithmTerminator(algorithmTerminator)
				.bestDistanceSampler(bestDistanceSampler).crossoverOperator(crossoverOperator)
				.crossoverRate(crossoverRate).mutationOperator(mutationOperator).mutationRate(mutationRate)
				.populationSize(populationSize).tournamentSize(tournamentSize).create();

		PathIndividual path = algorithm.execute(instance);

		System.out.println(path);
		System.out.println("Finished: " + instanceFilename);
	}
}
