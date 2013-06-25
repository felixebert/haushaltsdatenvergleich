package de.ifcore.hdv.converter.data;

import java.util.List;
import java.util.Map;

import de.ifcore.hdv.converter.MinMaxProduct;

public class MergedData {

	private List<AccountsPerArea> areas;
	private Map<Integer, MinMaxProduct> accounts;
	private Map<Integer, List<Integer>> tree;

	public MergedData(List<AccountsPerArea> areas, Map<Integer, MinMaxProduct> accounts,
			Map<Integer, List<Integer>> tree) {
		this.areas = areas;
		this.accounts = accounts;
		this.tree = tree;
	}

	public List<AccountsPerArea> getAreas() {
		return areas;
	}

	public Map<Integer, MinMaxProduct> getAccounts() {
		return accounts;
	}

	public Map<Integer, List<Integer>> getTree() {
		return tree;
	}
}
