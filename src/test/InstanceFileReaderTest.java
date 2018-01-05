package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import tsp.instance.Instance;
import tsp.instance.reader.InstanceFileReader;

public class InstanceFileReaderTest {

	private InstanceFileReader instanceFileReader;

	@Before
	public void setup() {
		// given
		instanceFileReader = new InstanceFileReader();
	}

	@Test
	public void shouldReadNumberOfCities() {
		// when
		Instance instance = null;
		try {
			instance = instanceFileReader.read("input/test/bays29.tsp");
		} catch (IOException e) {
			fail();
		}

		// then
		assertEquals(29, instance.getNumberOfCities());
	}

	@Test
	public void shouldReadDistanceValue() {
		// when
		Instance instance = null;
		try {
			instance = instanceFileReader.read("input/test/bays29.tsp");
		} catch (IOException e) {
			fail();
		}

		// then
		assertEquals(124.0, instance.getDistance(0, 4), 0.001);
	}

}
