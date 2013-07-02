package de.ifcore.hdv.converter.split;

import java.util.HashMap;
import java.util.Map;

import de.ifcore.hdv.converter.data.AccountValue;

public class AccountsPerAreaSplit {

	private String key;
	private long population;
	private double size;
	private Map<Integer, AccountValue> accounts = new HashMap<>();

	public AccountsPerAreaSplit(String key, long population, double size, Map<Integer, AccountValue> accounts) {
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

	public Map<Integer, AccountValue> getAccounts() {
		return accounts;
	}
}
