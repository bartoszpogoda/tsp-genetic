package test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import tsp.algorithm.crossover.CrossoverOperator;
import tsp.algorithm.crossover.PMXCrossoverOperator;
import tsp.algorithm.individual.PathIndividual;
import tsp.algorithm.util.RandomGenerator;

public class PMXCrossoverOperatorTest {

	@Test
	public void shouldPerformCrossoverCorrectly() {
		// given
		PathIndividual firstParent = new PathIndividual(10);
		firstParent.setStartCity(0);
		firstParent.setCity(1, 1);
		firstParent.setCity(2, 3);
		firstParent.setCity(3, 5);
		firstParent.setCity(4, 8);
		firstParent.setCity(5, 7);
		firstParent.setCity(6, 2);
		firstParent.setCity(7, 4);
		firstParent.setCity(8, 6);

		PathIndividual secondParent = new PathIndividual(10);
		secondParent.setStartCity(0);
		secondParent.setCity(1, 8);
		secondParent.setCity(2, 7);
		secondParent.setCity(3, 6);
		secondParent.setCity(4, 5);
		secondParent.setCity(5, 4);
		secondParent.setCity(6, 3);
		secondParent.setCity(7, 2);
		secondParent.setCity(8, 1);

		RandomGenerator randomMock = Mockito.mock(RandomGenerator.class);
		Mockito.when(randomMock.generateIntInRangeInclusive(Mockito.anyInt(), Mockito.anyInt())).thenReturn(4, 7);

		CrossoverOperator crossoverOperator = new PMXCrossoverOperator(randomMock);

		PathIndividual child = crossoverOperator.crossover(firstParent, secondParent);

		Assert.assertEquals(5, child.getCity(1));
		Assert.assertEquals(3, child.getCity(2));
		Assert.assertEquals(6, child.getCity(3));
		Assert.assertEquals(8, child.getCity(4));
		Assert.assertEquals(7, child.getCity(5));
		Assert.assertEquals(2, child.getCity(6));
		Assert.assertEquals(4, child.getCity(7));
		Assert.assertEquals(1, child.getCity(8));
		Assert.assertEquals(0, child.getCity(9));
		Assert.assertEquals(0, child.getCity(0));
	}

}
