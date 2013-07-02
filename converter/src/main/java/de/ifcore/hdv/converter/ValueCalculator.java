package de.ifcore.hdv.converter;

import de.ifcore.hdv.converter.data.LongValue;

public class ValueCalculator {

	private LongValue value;
	private long population;
	private double size;

	public ValueCalculator(LongValue value, long population, double size) {
		this.value = value;
		this.population = population;
		this.size = size;
	}

	public LongValue getValue() {
		return value;
	}

	private LongValue divideSafe(LongValue value1, long value2) {
		if (value1.isValid()) {
			long newValue = Long.valueOf(value1.getValue() / value2);
			return LongValue.valueOf(newValue);
		}
		else
			return value1;
	}

	public LongValue getValuePerArea() {
		return divideSafe(value, Math.round(size));
	}

	public LongValue getValuePerPopulation() {
		return divideSafe(value, population);
	}
}
