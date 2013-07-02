package de.ifcore.hdv.converter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.ifcore.hdv.converter.data.AccountsPerArea;
import de.ifcore.hdv.converter.data.LongValue;

public class MinMaxProduct {

	@JsonIgnore
	private int key;
	@JsonIgnore
	private String label;
	private Map<Integer, MinMax> accounts = new HashMap<>();

	public MinMaxProduct(int key, String label) {
		this.key = key;
		this.label = label;
	}

	public int getKey() {
		return key;
	}

	public String getLabel() {
		return label;
	}

	public Map<Integer, MinMax> getAccounts() {
		return accounts;
	}

	public void addAccount(int accountKey, String accountName) {
		accounts.put(accountKey, new MinMax(accountKey));
	}

	public void addValue(int accountKey, LongValue value, AccountsPerArea accountsPerArea) {
		MinMax minMax = accounts.get(accountKey);
		if (minMax == null) {
			minMax = new MinMax(accountKey);
			accounts.put(accountKey, minMax);
		}
		minMax.addValue(value, accountsPerArea);
	}

	public void filterNullValues() {
		Iterator<MinMax> it = accounts.values().iterator();
		while (it.hasNext()) {
			if (!it.next().hasData())
				it.remove();
		}
	}
}
