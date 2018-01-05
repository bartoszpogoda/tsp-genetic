package test;

import tsp.algorithm.individual.PathIndividual;
import tsp.instance.Instance;

public class PredefinedTestObjects {
	// naive
	public static Instance createPredefinedInstanceA() {
		Instance instance = new Instance(5);

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				instance.setDistance(i, j, 1);
			}
		}

		instance.setDistance(0, 1, 5);
		instance.setDistance(1, 2, 4);
		instance.setDistance(2, 3, 3);
		instance.setDistance(3, 4, 7);
		instance.setDistance(4, 0, 20);
		
		return instance;
	}
	
	public static Instance createPredefinedInstanceB() {
		Instance instance = new Instance(5);
		
		instance.setDistance(0, 1, 2);
		instance.setDistance(0, 2, 1);
		instance.setDistance(0, 3, 8);
		instance.setDistance(0, 4, 6);
	
		instance.setDistance(1, 0, 3);
		instance.setDistance(1, 2, 3);
		instance.setDistance(1, 3, 5);
		instance.setDistance(1, 4, 6);
		
		instance.setDistance(2, 0, 6);
		instance.setDistance(2, 1, 4);
		instance.setDistance(2, 3, 8);
		instance.setDistance(2, 4, 1);
		
		instance.setDistance(3, 0, 3);
		instance.setDistance(3, 1, 4);
		instance.setDistance(3, 2, 9);
		instance.setDistance(3, 4, 10);
		
		instance.setDistance(4, 0, 3);
		instance.setDistance(4, 1, 2);
		instance.setDistance(4, 2, 2);
		instance.setDistance(4, 3, 7);
		
		return instance;
	}
	
	public static PathIndividual createPredefinedPath() {
		PathIndividual path = new PathIndividual(5 + 1);

		path.setStartCity(0);
		path.setCity(1, 1);
		path.setCity(2, 2);
		path.setCity(3, 3);
		path.setCity(4, 4);
		
		return path;
	}
}
