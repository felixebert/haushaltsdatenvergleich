package de.ifcore.hdv.converter.data;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.ifcore.hdv.converter.MinMaxProduct;

public class MergedData {

	private List<AccountsPerArea> areas;
	@JsonIgnore
	private Map<Integer, MinMaxProduct> products;
	private Map<Integer, List<Integer>> tree;
	private Set<KeyAccount> productLabels;
	private Set<KeyAccount> incomeLabels;
	private Set<KeyAccount> spendingsLabels;

	public MergedData(List<AccountsPerArea> areas, Map<Integer, MinMaxProduct> products,
			Map<Integer, List<Integer>> tree, Set<KeyAccount> productLabels, Set<KeyAccount> incomeLabels,
			Set<KeyAccount> spendingsLabels) {
		this.areas = areas;
		this.products = products;
		this.tree = tree;
		this.productLabels = productLabels;
		this.incomeLabels = incomeLabels;
		this.spendingsLabels = spendingsLabels;
	}

	public List<AccountsPerArea> getAreas() {
		return areas;
	}

	public Map<Integer, List<Integer>> getTree() {
		return tree;
	}

	public Map<Integer, MinMaxProduct> getProducts() {
		return products;
	}

	public Set<KeyAccount> getProductLabels() {
		return productLabels;
	}

	public Set<KeyAccount> getIncomeLabels() {
		return incomeLabels;
	}

	public Set<KeyAccount> getSpendingsLabels() {
		return spendingsLabels;
	}
}
