package tsp;

import java.io.IOException;

import tsp.algorithm.Algorithm;
import tsp.algorithm.crossover.PMXCrossoverOperator;
import tsp.algorithm.mutation.InvertMutationOperator;
import tsp.algorithm.thread.AlgorithmTerminator;
import tsp.algorithm.thread.BestDistanceSampler;
import tsp.instance.Instance;
import tsp.instance.reader.InstanceFileReader;

public class Main {

	public static void main(String[] args) throws IOException {
		// CommandLineInterface cli = new CommandLineInterface();
		// cli.enter();

		InstanceFileReader instanceFileReader = new InstanceFileReader();
		Instance instance = instanceFileReader.read("input/rbg403.atsp");

		AlgorithmTerminator algorithmTerminator = new AlgorithmTerminator();
		algorithmTerminator.setTimeLimitMs(600000);

		BestDistanceSampler bestDistanceSampler = new BestDistanceSampler();

		Algorithm algorithm = new Algorithm.AlgorithmBuilder().populationSize(200).tournamentSize(2).crossoverRate(0.8)
				.mutationRate(0.01).crossoverOperator(new PMXCrossoverOperator())
				.mutationOperator(new InvertMutationOperator()).algorithmTerminator(algorithmTerminator)
				.bestDistanceSampler(bestDistanceSampler).create();

		algorithm.execute(instance);
	}
}
