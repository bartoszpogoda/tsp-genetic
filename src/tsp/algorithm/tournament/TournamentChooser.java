package tsp.algorithm.tournament;

import tsp.algorithm.individual.PathIndividual;
import tsp.algorithm.population.PathPopulation;
import tsp.algorithm.util.FitnessCalculator;
import tsp.algorithm.util.RandomGenerator;

public class TournamentChooser {

	private FitnessCalculator fitnessCalculator;
	private RandomGenerator randomGenerator;

	private int tournamentSize;
	
	public TournamentChooser(FitnessCalculator fitnessCalculator, int tournamentSize) {
		this.randomGenerator = new RandomGenerator();
		this.fitnessCalculator = fitnessCalculator;
		this.tournamentSize = tournamentSize;
	}

	public TournamentChooser(RandomGenerator randomGenerator, FitnessCalculator fitnessCalculator, int tournamentSize) {
		this.randomGenerator = randomGenerator;
		this.fitnessCalculator = fitnessCalculator;
		this.tournamentSize = tournamentSize;
	}

	public PathIndividual choose(PathPopulation population) {
		PathIndividual bestParticipant = null;
		double bestParticipantFitness = 0.0;
		
		for (int i = 0; i < tournamentSize; i++) {
			int randomIndex = randomGenerator.generateIntInRangeInclusive(0, population.getSize() - 1);
			
			PathIndividual participant = population.getPathIndividual(randomIndex);
			double participalFitness = fitnessCalculator.calculateFitness(participant);
			
			if(participalFitness > bestParticipantFitness) {
				bestParticipant = participant;
				bestParticipantFitness = participalFitness;
			}
			
		}

		return bestParticipant;
	}

}
