package de.ifcore.hdv.converter.split;

import java.util.Map;

import de.ifcore.hdv.converter.MinMax;
import de.ifcore.hdv.converter.data.AccountValue;

public class Product {

	private Map<String, Map<Integer, AccountValue>> areas;
	private Map<Integer, MinMax> minmax;

	public Product(Map<String, Map<Integer, AccountValue>> areas, Map<Integer, MinMax> minmax) {
		this.areas = areas;
		this.minmax = minmax;
	}

	public Map<String, Map<Integer, AccountValue>> getAreas() {
		return areas;
	}

	public Map<Integer, MinMax> getMinmax() {
		return minmax;
	}
}
