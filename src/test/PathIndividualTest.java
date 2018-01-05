package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tsp.algorithm.individual.PathIndividual;
import tsp.instance.Instance;

public class PathIndividualTest {

	private Instance instance = null;
	private PathIndividual path = null;

	@Before
	public void setUp() {
		instance = PredefinedTestObjects.createPredefinedInstanceA();
		path = PredefinedTestObjects.createPredefinedPath();
	}

	@Test
	public void totalDistanceTest() {
		double totalDistance = instance.calculateTotalDistance(path);
		
		assertEquals(39, totalDistance, 0.01);
	}
	
	@Test
	public void copiedPathShouldntBeReferenced() {
		PathIndividual copiedPath = new PathIndividual(path);
		
		// City at position 1 is originally 1
		path.setCity(1, 3);
		
		assertNotEquals(path.getCity(1), copiedPath.getCity(1));
	}

}
