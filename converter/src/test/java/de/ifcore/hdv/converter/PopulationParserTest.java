package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Population;
import de.ifcore.hdv.converter.parser.PopulationParser;

public class PopulationParserTest {

	@Test(expected = RuntimeException.class)
	public void itShouldThrowRuntimeExceptionOnFailure() throws Exception {
		new PopulationParser(null);
	}

	@Test
	public void itShouldParsePopulationAsMap() throws Exception {
		PopulationParser parser = new PopulationParser(ResourceUtils.getResourceAsStream("population.xls"));
		Map<String, Population> result = parser.parse();
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertTrue(result.containsKey("01001000"));
		assertPopulation(82801, result.get("01001000"));
		assertPopulation(756, result.get("01051021"));
		assertPopulation(2846, result.get("16077056"));
		assertEquals(11292, result.size());
	}

	private void assertPopulation(long expectedPopulation, Population population) {
		assertEquals(expectedPopulation, population.getPopulation());
	}
}
