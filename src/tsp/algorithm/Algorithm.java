package tsp.algorithm;

import tsp.algorithm.individual.PathIndividualGenerator;
import tsp.algorithm.individual.PathIndividual;
import tsp.algorithm.thread.AlgorithmTerminator;
import tsp.algorithm.thread.BestDistanceSampler;
import tsp.instance.Instance;

public class Algorithm {
	private volatile boolean running = true;
	
	private double currentDiversificationBestDistance = Double.MAX_VALUE;

	private PathIndividualGenerator pathGenerator;

	// dependencies
	private AlgorithmTerminator algorithmTerminator;
	private BestDistanceSampler bestDistanceSampler;


	public void setAlgorithmTerminator(AlgorithmTerminator algorithmTerminator) {
		this.algorithmTerminator = algorithmTerminator;
		algorithmTerminator.setAlgorithm(this);
	}
	

	public void setBestDistanceSampler(BestDistanceSampler bestDistanceSampler) {
		this.bestDistanceSampler = bestDistanceSampler;
	}

	public Algorithm() {
		pathGenerator = new PathIndividualGenerator();
	}

	public double getCurrentBestDistance() {
		return 0;
	}
	
	public double getCurrentDiversificationBestDistance() {
		return currentDiversificationBestDistance;
	}

	public synchronized PathIndividual execute(Instance instance) {
		algorithmTerminator.start();

		bestDistanceSampler.start();

		evolutionLoop(instance);

		bestDistanceSampler.terminate();

		return null;
	}


	public void evolutionLoop(Instance instance) {
		while (running) {
			// TODO Implement algorithm
		}
	}

	public void terminate() {
		running = false;
	}
}
