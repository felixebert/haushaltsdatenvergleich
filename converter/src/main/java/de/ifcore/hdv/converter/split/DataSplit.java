package de.ifcore.hdv.converter.split;

import java.util.HashMap;
import java.util.Map;

import de.ifcore.hdv.converter.data.MergedData;

public class DataSplit {

	private Map<Integer, Product> products = new HashMap<>();
	private MergedData mergedData;

	public DataSplit(MergedData mergedData) {
		this.mergedData = mergedData;
	}

	public MergedData getMergedData() {
		return mergedData;
	}

	public void addProduct(Integer productId, Product product) {
		products.put(productId, product);
	}

	public Map<Integer, Product> getProducts() {
		return products;
	}
}
