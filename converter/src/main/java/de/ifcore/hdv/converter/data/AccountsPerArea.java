package de.ifcore.hdv.converter.data;

import java.util.Map;

public class AccountsPerArea {

	private String key;
	private long population;
	private double size;
	private Map<Integer, Long[]> accounts;

	public AccountsPerArea(String key, long population, double size, Map<Integer, Long[]> accounts) {
		this.key = key;
		this.population = population;
		this.size = size;
		this.accounts = accounts;
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

	public Map<Integer, Long[]> getAccounts() {
		return accounts;
	}
}
