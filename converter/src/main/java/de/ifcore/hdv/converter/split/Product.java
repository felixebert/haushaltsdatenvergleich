package de.ifcore.hdv.converter.split;

import java.util.List;
import java.util.Map;

import de.ifcore.hdv.converter.MinMax;

public class Product {

	private List<AccountsPerAreaSplit> areas;
	private Map<Integer, MinMax> minmax;

	public Product(List<AccountsPerAreaSplit> areas, Map<Integer, MinMax> minmax) {
		this.areas = areas;
		this.minmax = minmax;
	}

	public List<AccountsPerAreaSplit> getAreas() {
		return areas;
	}

	public Map<Integer, MinMax> getMinmax() {
		return minmax;
	}
}
