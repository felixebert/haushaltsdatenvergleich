package de.ifcore.hdv.converter.data;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountsPerArea {

	private String key;
	private long population;
	private double size;
	@JsonIgnore
	private Map<Integer, InOutProduct> products;
	private String label;

	public AccountsPerArea(String key, String label, long population, double size, Map<Integer, InOutProduct> products) {
		this.key = key;
		this.label = label;
		this.population = population;
		this.size = size;
		this.products = products;
	}

	public String getKey() {
		return key;
	}

	public long getPopulation() {
		return population;
	}

	public double getSize() {
		return size;
	}

	public String getLabel() {
		return label;
	}

	public Map<Integer, InOutProduct> getProducts() {
		return products;
	}
}
