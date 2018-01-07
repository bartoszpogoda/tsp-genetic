package tsp.algorithm.individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tsp.instance.Instance;

public class PathIndividualGenerator {
	public PathIndividual generateRandomPath(Instance instance) {
		PathIndividual randomPath = new PathIndividual(instance.getNumberOfCities() + 1);
		randomPath.setStartCity(0);

		Random random = new Random(System.currentTimeMillis());

		List<Integer> availableCities = new ArrayList<Integer>();
		for (int i = 1; i < instance.getNumberOfCities(); i++) {
			availableCities.add(i);
		}

		int pathIndex = 1;
		while (!availableCities.isEmpty()) {
			int randomAvailableCitiesPosition = random.nextInt(availableCities.size());
			randomPath.setCity(pathIndex, availableCities.remove(randomAvailableCitiesPosition));
			pathIndex++;
		}
		
		return randomPath;
	}

	public PathIndividual generateGreedyPath(Instance instance) {
		PathIndividual greedyPath = new PathIndividual(instance.getNumberOfCities() + 1);
		greedyPath.setStartCity(0);

		List<Integer> availableCities = new ArrayList<Integer>();
		for (int i = 1; i < instance.getNumberOfCities(); i++) {
			availableCities.add(i);
		}

		int pathIndex = 1;
		int lastCity = 0;
		while (!availableCities.isEmpty()) {
			int bestCity = -1;
			double bestDistance = Double.MAX_VALUE;

			for (int i = 0; i < availableCities.size(); i++) {
				double distance = instance.getDistance(lastCity, availableCities.get(i));
				if (distance < bestDistance) {
					bestDistance = distance;
					bestCity = availableCities.get(i);
				}
			}

			greedyPath.setCity(pathIndex, bestCity);
			availableCities.remove((Integer) bestCity);

			pathIndex += 1;
		}

		return greedyPath;
	}
}
