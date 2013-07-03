package de.ifcore.hdv.converter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import de.ifcore.hdv.converter.data.KeyAccount;

public class AccountLabels {

	private Set<KeyAccount> keyAccounts = new TreeSet<>();

	public void add(int key, String label) {
		keyAccounts.add(new KeyAccount(key, label));
	}

	public Set<KeyAccount> getKeyAccounts() {
		return keyAccounts;
	}

	public Map<Integer, String> asSortedMap() {
		Map<Integer, String> result = new LinkedHashMap<>(keyAccounts.size());
		for (KeyAccount keyAccount : keyAccounts) {
			result.put(keyAccount.getKey(), keyAccount.getLabel());
		}
		return result;
	}
}
