package tsp.algorithm.population;

import tsp.algorithm.individual.PathIndividual;
import tsp.algorithm.individual.PathIndividualGenerator;
import tsp.algorithm.util.FitnessCalculator;
import tsp.instance.Instance;

public class PathPopulation {

	private PathIndividual[] population;

	public PathPopulation(int populationSize) {
		this.population = new PathIndividual[populationSize];
	}
	
    public void savePathIndividual(int index, PathIndividual pathIndividual) {
    	population[index] = pathIndividual;
    }
    
    public PathIndividual getPathIndividual(int index) {
        return population[index];
    }
    
    public PathIndividual findTheFittest(FitnessCalculator fitnessCalculator) {
    	PathIndividual currentBestPath = null;
    	double currentBestPathFitness = 0;
    	
    	for (int i = 0; i < population.length; i++) {
    		double currentPathFitness = fitnessCalculator.calculateFitness(population[i]);
    		
			if(currentPathFitness > currentBestPathFitness) {
				currentBestPath = population[i];
				currentBestPathFitness = currentPathFitness;
			}
		}
    	
    	return currentBestPath;
    }

	public static PathPopulation generateInitialPopulation(int populationSize, Instance instance) {
		PathPopulation pathPopulation = new PathPopulation(populationSize);

		PathIndividualGenerator pathIndividualGenerator = new PathIndividualGenerator();

		// TODO consider one greedy?
		for (int i = 0; i < pathPopulation.population.length; i++) {
			pathPopulation.population[i] = pathIndividualGenerator.generateRandomPath(instance);
		}

		return pathPopulation;
	}
}
