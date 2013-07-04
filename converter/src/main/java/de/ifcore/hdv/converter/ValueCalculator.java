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

	public Double getValue() {
		return value.isValid() ? value.getValue() : Double.NaN;
	}

	private Double divideSafe(LongValue value1, long value2) {
		if (value1.isValid()) {
			double newValue = (double)value1.getValue() / value2;
			return Double.valueOf(newValue);
		}
		else
			return Double.NaN;
	}

	public Double getValuePerArea() {
		return divideSafe(value, Math.round(size));
	}

	public Double getValuePerPopulation() {
		return divideSafe(value, population);
	}
}
