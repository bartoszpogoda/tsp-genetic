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
//		run("input/eil101.tsp", timeLimitMs, 10, fixedTournamentSize, 0.8, 0.02, fixedCrossoverOperator,
//				new InvertMutationOperator(), "101_02");
//		run("input/eil101.tsp", timeLimitMs, 10, fixedTournamentSize, 0.8, 0.05, fixedCrossoverOperator,
//				new InvertMutationOperator(), "101_05");
//		run("input/eil101.tsp", timeLimitMs, 10, fixedTournamentSize, 0.8, 0.1, fixedCrossoverOperator,
//				new InvertMutationOperator(), "101_1");

//		// rbg403.atsp
//		run("input/rbg403.atsp", timeLimitMs, 40, fixedTournamentSize, 0.8, 0.02, fixedCrossoverOperator,
//				new SwapMutationOperator(), "403_02");
//		run("input/rbg403.atsp", timeLimitMs, 40, fixedTournamentSize, 0.8, 0.05, fixedCrossoverOperator,
//				new SwapMutationOperator(), "403_05");
//		run("input/rbg403.atsp", timeLimitMs, 40, fixedTournamentSize, 0.8, 0.1, fixedCrossoverOperator,
//				new SwapMutationOperator(), "403_1");

		// eil101.tsp
		run("input/d1291.tsp", timeLimitMs, 129, fixedTournamentSize, 0.8, 0.02, fixedCrossoverOperator,
				new InvertMutationOperator(), "1291_02");
		run("input/d1291.tsp", timeLimitMs, 129, fixedTournamentSize, 0.8, 0.05, fixedCrossoverOperator,
				new InvertMutationOperator(), "1291_05");
		run("input/d1291.tsp", timeLimitMs, 129, fixedTournamentSize, 0.8, 0.1, fixedCrossoverOperator,
				new InvertMutationOperator(), "1291_1");
//		run("input/d1291.tsp", timeLimitMs, 645, fixedTournamentSize, 0.8, 0.01, fixedCrossoverOperator,
//				new SwapMutationOperator(), "1291_swap_pop645");
//		run("input/d1291.tsp", timeLimitMs, 1290, fixedTournamentSize, 0.8, 0.01, fixedCrossoverOperator,
//				new SwapMutationOperator(), "1291_swap_pop1290");
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
				.populationSize(populationSize).tournamentSize(tournamentSize).build();

		PathIndividual path = algorithm.execute(instance);

		System.out.println(path);
		System.out.println("Finished: " + instanceFilename);
	}
}
