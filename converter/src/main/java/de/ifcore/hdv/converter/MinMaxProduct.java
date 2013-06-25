package de.ifcore.hdv.converter;

import java.util.HashMap;
import java.util.Map;

import de.ifcore.hdv.converter.data.AccountsPerArea;

public class MinMaxProduct extends MinMax {

	private Map<Integer, MinMax> accounts = new HashMap<>();

	public MinMaxProduct(int key, String label) {
		super(key, label);
	}

	public Map<Integer, MinMax> getAccounts() {
		return accounts;
	}

	public void addAccount(int accountKey, String accountName) {
		accounts.put(accountKey, new MinMax(accountKey, accountName));
	}

	public void addValue(int accountKey, Long value, AccountsPerArea accountsPerArea) {
		MinMax minMax = accounts.get(accountKey);
		if (minMax == null) {
			minMax = new MinMax(accountKey, "nix");
			accounts.put(accountKey, minMax);
		}
		minMax.addValue(value, accountsPerArea);
	}
}
