package de.ifcore.hdv.converter;

public class ValueCalculator {

	private Long value;
	private long population;
	private double size;

	public ValueCalculator(Long value, long population, double size) {
		this.value = value;
		this.population = population;
		this.size = size;
	}

	public Long getValue() {
		return value;
	}

	private Long divideSafe(Long value1, long value2) {
		if (value1 != null)
			return Long.valueOf(value1 / value2);
		else
			return null;
	}

	public Long getValuePerArea() {
		return divideSafe(value, Math.round(size));
	}

	public Long getValuePerPopulation() {
		return divideSafe(value, population);
	}
}
