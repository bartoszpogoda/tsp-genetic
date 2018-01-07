package tsp.algorithm;

import tsp.algorithm.mutation.MutationOperator;
import tsp.algorithm.mutation.SwapMutationOperator;
import tsp.algorithm.population.PathPopulation;

import tsp.algorithm.crossover.CrossoverOperator;
import tsp.algorithm.crossover.PMXCrossoverOperator;
import tsp.algorithm.individual.PathIndividual;
import tsp.algorithm.thread.AlgorithmTerminator;
import tsp.algorithm.thread.BestDistanceSampler;
import tsp.algorithm.tournament.TournamentChooser;
import tsp.algorithm.util.FitnessCalculator;
import tsp.algorithm.util.RandomGenerator;
import tsp.instance.Instance;

public class Algorithm {

	private volatile boolean running = true;

	// dependencies
	private AlgorithmTerminator algorithmTerminator;
	private BestDistanceSampler bestDistanceSampler;
	
	private FitnessCalculator fitnessCalculator;
	private TournamentChooser tournamentChooser;
	private CrossoverOperator crossoverOperator;
	private MutationOperator mutationOperator;
	private RandomGenerator randomGenerator;

	// parameters
	private int populationSize = 100;
	private int tournamentSize = 2;
	private double crossoverRate = 0.8;
	private double mutationRate = 0.01;

	// best fittest tracking
	private PathIndividual currentBest = null;
	private double currentBestDistance = Double.MAX_VALUE;
	
	public Algorithm() {
		randomGenerator = new RandomGenerator();
	}

	public double getCurrentBestDistance() {
		return currentBestDistance;
	}

	public synchronized PathIndividual execute(Instance instance) {
		algorithmTerminator.start();
		bestDistanceSampler.start();
		
		fitnessCalculator = new FitnessCalculator(instance);
		tournamentChooser = new TournamentChooser(fitnessCalculator, tournamentSize);

		PathPopulation initialPopulation = PathPopulation.generateInitialPopulation(populationSize, instance);
		
		startEvolution(instance, initialPopulation);

		bestDistanceSampler.terminate();
		return currentBest;
	}
	
	public void startEvolution(Instance instance, PathPopulation initialPopulation) {
		PathPopulation population = initialPopulation;
		
		while (running) {
			population = evolve(instance, population);
		}
	}
	
	private PathPopulation evolve(Instance instance, PathPopulation population) {

		// finding the fittest one to be preserved (incubated)
		PathIndividual fittestToIncubate = population.findTheFittest(fitnessCalculator);
		
		// update current best for result tracking/sampling purposes
		updateCurrestBestPath(fittestToIncubate);

		PathPopulation evolvedPopulation = new PathPopulation(populationSize); 
		
		// preserve (incubate) best fit individual
		evolvedPopulation.savePathIndividual(0, fittestToIncubate);

		// for remaining population slots find parents (tournament based) and crossover them
		for (int i = 1; i < evolvedPopulation.getSize(); i++) {
			if(randomGenerator.nextDouble() < crossoverRate) {
				PathIndividual firstParent = tournamentChooser.choose(population);
				PathIndividual secondParent = tournamentChooser.choose(population);

				PathIndividual child = crossoverOperator.crossover(firstParent, secondParent);
				evolvedPopulation.savePathIndividual(i, child);
			} else {
				evolvedPopulation.savePathIndividual(i, population.getPathIndividual(i));
			}
		}
		
		// mutate next generation except for incubated one
		for (int i = 1; i < evolvedPopulation.getSize(); i++) {
			if(randomGenerator.nextDouble() < mutationRate) {
				mutationOperator.mutate(evolvedPopulation.getPathIndividual(i));
			}
		}

		return evolvedPopulation;
	}
	
	private void updateCurrestBestPath(PathIndividual currentFittest) {
		currentBestDistance = 1 / fitnessCalculator.calculateFitness(currentFittest);
		currentBest = currentFittest;
	}

	public void terminate() {
		running = false;
	}

	public static class AlgorithmBuilder {
		private Algorithm builtAlgorithm;

		public AlgorithmBuilder() {
			this.builtAlgorithm = new Algorithm();
		}

		public AlgorithmBuilder populationSize(int populationSize) {
			builtAlgorithm.populationSize = populationSize;
			return this;
		}

		public AlgorithmBuilder tournamentSize(int tournamentSize) {
			builtAlgorithm.tournamentSize = tournamentSize;
			return this;
		}

		public AlgorithmBuilder crossoverRate(double crossoverRate) {
			builtAlgorithm.crossoverRate = crossoverRate;
			return this;
		}

		public AlgorithmBuilder mutationRate(double mutationRate) {
			builtAlgorithm.mutationRate = mutationRate;
			return this;
		}
		
		public AlgorithmBuilder crossoverOperator(CrossoverOperator crossoverOperator) {
			builtAlgorithm.crossoverOperator = crossoverOperator;
			return this;
		}
		
		public AlgorithmBuilder mutationOperator(MutationOperator mutationOperator) {
			builtAlgorithm.mutationOperator = mutationOperator;
			return this;
		}
		
		public AlgorithmBuilder algorithmTerminator(AlgorithmTerminator algorithmTerminator) {
			builtAlgorithm.algorithmTerminator = algorithmTerminator;
			algorithmTerminator.setAlgorithm(builtAlgorithm);
			return this;
		}
		
		public AlgorithmBuilder bestDistanceSampler(BestDistanceSampler bestDistanceSampler) {
			builtAlgorithm.bestDistanceSampler = bestDistanceSampler;
			bestDistanceSampler.setAlgorithm(builtAlgorithm);
			return this;
		}
		
		public Algorithm create() {
			return builtAlgorithm;
		}

	}
}
