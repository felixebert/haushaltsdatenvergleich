package de.ifcore.hdv.converter.mapreduce;

public class SizeMapReduce implements MapReduce<Double> {

	@Override
	public String extractKey(String key) {
		return key.substring(0, 5);
	}

	@Override
	public Double reduce(Double value1, Double value2) {
		return Double.valueOf(value1 + value2);
	}
}
