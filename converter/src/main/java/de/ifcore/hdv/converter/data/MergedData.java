package de.ifcore.hdv.converter.data;

import java.util.List;
import java.util.Map;

import de.ifcore.hdv.converter.MinMaxAccount;

public class MergedData {

	private List<AccountsPerArea> areas;
	private Map<Integer, MinMaxAccount> accounts;
	private Map<Integer, List<Integer>> tree;

	public MergedData(List<AccountsPerArea> areas, Map<Integer, MinMaxAccount> accounts,
			Map<Integer, List<Integer>> tree) {
		this.areas = areas;
		this.accounts = accounts;
		this.tree = tree;
	}

	public List<AccountsPerArea> getAreas() {
		return areas;
	}

	public Map<Integer, MinMaxAccount> getAccounts() {
		return accounts;
	}

	public Map<Integer, List<Integer>> getTree() {
		return tree;
	}
}
