package de.ifcore.hdv.converter.data;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.ifcore.hdv.converter.MinMaxProduct;

public class MergedData {

	private List<AccountsPerArea> areas;
	@JsonIgnore
	private Map<Integer, MinMaxProduct> productsMap;
	private Map<Integer, List<Integer>> tree;
	private Set<KeyAccount> products;
	private Set<KeyAccount> incomeAccounts;
	private Set<KeyAccount> spendingsAccounts;

	public MergedData(List<AccountsPerArea> areas, Map<Integer, MinMaxProduct> productsMap,
			Map<Integer, List<Integer>> tree, Set<KeyAccount> products, Set<KeyAccount> incomeAccounts,
			Set<KeyAccount> spendingsAccounts) {
		this.areas = areas;
		this.productsMap = productsMap;
		this.tree = tree;
		this.products = products;
		this.incomeAccounts = incomeAccounts;
		this.spendingsAccounts = spendingsAccounts;
	}

	public List<AccountsPerArea> getAreas() {
		return areas;
	}

	public Map<Integer, List<Integer>> getTree() {
		return tree;
	}

	public Map<Integer, MinMaxProduct> getProductsMap() {
		return productsMap;
	}

	public Set<KeyAccount> getProducts() {
		return products;
	}

	public Set<KeyAccount> getIncomeAccounts() {
		return incomeAccounts;
	}

	public Set<KeyAccount> getSpendingsAccounts() {
		return spendingsAccounts;
	}
}
