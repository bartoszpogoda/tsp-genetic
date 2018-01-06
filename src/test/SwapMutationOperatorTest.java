package test;

import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import tsp.algorithm.individual.PathIndividual;
import tsp.algorithm.mutation.MutationOperator;
import tsp.algorithm.mutation.SwapMutationOperator;

public class SwapMutationOperatorTest {
	private PathIndividual path = null;

	@Before
	public void setUp() {
		path = PredefinedTestObjects.createPredefinedPath();
	}

	@Test
	public void shouldSwapTwoCitiesOnMutation() {
		// given
		Random randomMock = Mockito.mock(Random.class);
		Mockito.when(randomMock.nextInt()).thenReturn(1, 2);
		MutationOperator mutationOperator = new SwapMutationOperator(randomMock);
		
		// when
		mutationOperator.mutate(path);
		
		// then
		Assert.assertEquals(2, path.getCity(1));
		Assert.assertEquals(1, path.getCity(2));
		
	}
}
