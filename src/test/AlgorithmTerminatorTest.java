package test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import tsp.algorithm.Algorithm;
import tsp.algorithm.thread.AlgorithmTerminator;

public class AlgorithmTerminatorTest {

	private AlgorithmTerminator algorithmTerminator = null;

	@Before
	public void setUp() {
		algorithmTerminator = new AlgorithmTerminator();
	}

	@Test
	public void shouldTerminateAlgorithm() throws InterruptedException {
		// given
		Algorithm spyAlgorithm = spy(new Algorithm());
		algorithmTerminator.setAlgorithm(spyAlgorithm);

		// when
		algorithmTerminator.setTimeLimitMs(2000);
		algorithmTerminator.start();
		Thread.sleep(2050);

		// then
		verify(spyAlgorithm).terminate();
	}

}
