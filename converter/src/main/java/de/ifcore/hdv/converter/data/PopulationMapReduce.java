package de.ifcore.hdv.converter.data;

import de.ifcore.hdv.converter.MapReduce;

public class PopulationMapReduce implements MapReduce<Population> {

	@Override
	public String extractKey(String key) {
		return key.substring(0, 6);
	}

	@Override
	public Population reduce(Population value1, Population value2) {
		return new Population(value1.getPopulation() + value2.getPopulation());
	}
}
