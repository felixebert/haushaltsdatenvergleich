package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Population;

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
		assertTrue(result.containsKey("0101001000"));
		assertPopulation("Flensburg, Stadt", 82801, result.get("0101001000"));
		assertPopulation("Diekhusen-Fahrstedt", 756, result.get("0101051021"));
		assertPopulation("Saara", 2846, result.get("1616077056"));
		assertEquals(11292, result.size());
	}

	private void assertPopulation(String areaName, long expectedPopulation, Population population) {
		assertEquals(areaName, population.getAreaName());
		assertEquals(expectedPopulation, population.getPopulation());
	}
}
