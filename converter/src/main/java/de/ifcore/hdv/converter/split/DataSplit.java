package de.ifcore.hdv.converter.split;

import java.util.HashMap;
import java.util.Map;

public class DataSplit {

	private Map<Integer, Product> products = new HashMap<>();
	private Labels labels;

	public DataSplit(Labels labels) {
		this.labels = labels;
	}

	public void addProduct(Integer productId, Product product) {
		products.put(productId, product);
	}

	public Map<Integer, Product> getProducts() {
		return products;
	}

	public Labels getLabels() {
		return labels;
	}
}
