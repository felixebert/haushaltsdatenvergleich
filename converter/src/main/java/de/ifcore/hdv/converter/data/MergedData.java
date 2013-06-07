package de.ifcore.hdv.converter.data;

import java.util.List;
import java.util.Map;

import de.ifcore.hdv.converter.MinMaxAccount;

public class MergedData {

	private List<AccountsPerArea> accountsPerAreas;
	private Map<Integer, MinMaxAccount> accountMap;
	private Map<Integer, List<Integer>> tree;

	public MergedData(List<AccountsPerArea> accountsPerAreas, Map<Integer, MinMaxAccount> accountMap,
			Map<Integer, List<Integer>> tree) {
		this.accountsPerAreas = accountsPerAreas;
		this.accountMap = accountMap;
		this.tree = tree;
	}

	public List<AccountsPerArea> getAccountsPerAreas() {
		return accountsPerAreas;
	}

	public Map<Integer, MinMaxAccount> getAccountMap() {
		return accountMap;
	}

	public Map<Integer, List<Integer>> getTree() {
		return tree;
	}
}
