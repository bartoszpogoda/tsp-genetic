package tsp.algorithm.util;

import tsp.algorithm.individual.PathIndividual;
import tsp.instance.Instance;

public class FitnessCalculator {
	private Instance instance;
	
	public FitnessCalculator() {
	}

	public FitnessCalculator(Instance instance) {
		this.instance = instance;
	}
	
	public void setInstance(Instance instance) {
		this.instance = instance;
	}
	
	public double calculateFitness(PathIndividual pathIndividual) {
		double totalDistance = 0;
		
		for(int i=0 ; i < pathIndividual.getLength() - 1 ; i++) {
			totalDistance += instance.getDistance(pathIndividual.getCity(i), pathIndividual.getCity(i+1));
		}
		
		return 1/totalDistance;
	}
}
