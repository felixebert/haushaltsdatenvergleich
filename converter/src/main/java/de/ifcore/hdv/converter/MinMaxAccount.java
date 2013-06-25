package de.ifcore.hdv.converter;

import java.util.HashMap;
import java.util.Map;

public class MinMaxAccount extends MinMax {

	private Map<Integer, MinMax> accounts = new HashMap<>();

	public MinMaxAccount(int key, String label) {
		super(key, label);
	}

	public Map<Integer, MinMax> getAccounts() {
		return accounts;
	}
}
