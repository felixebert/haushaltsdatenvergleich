package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Population;
import de.ifcore.hdv.converter.mapreduce.Aggregator;
import de.ifcore.hdv.converter.mapreduce.PopulationMapReduce;

public class AggregatorTest {

	@Test
	public void itShouldAggregatePopulation() throws Exception {
		Map<String, Population> map = new HashMap<>();
		map.put("01234345", new Population(250));
		map.put("01234344", new Population(200));
		map.put("04344353", new Population(100));
		Map<String, Population> aggregated = Aggregator.aggregateMap(map, new PopulationMapReduce());
		assertNotNull(aggregated);
		assertEquals(450, aggregated.get("01234").getPopulation());
		assertEquals(100, aggregated.get("04344").getPopulation());
	}
}
