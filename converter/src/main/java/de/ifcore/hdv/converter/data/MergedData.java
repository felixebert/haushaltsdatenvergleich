package de.ifcore.hdv.converter.data;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.ifcore.hdv.converter.MinMaxProduct;

public class MergedData {

	private List<AccountsPerArea> areas;
	@JsonIgnore
	private Map<Integer, MinMaxProduct> products;
	private Map<Integer, List<Integer>> tree;
	private Map<Integer, String> productLabels;
	private Map<Integer, String> incomeLabels;
	private Map<Integer, String> spendingsLabels;

	public MergedData(List<AccountsPerArea> areas, Map<Integer, MinMaxProduct> products,
			Map<Integer, List<Integer>> tree, Map<Integer, String> productLabels, Map<Integer, String> incomeLabels,
			Map<Integer, String> spendingsLabels) {
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

	public Map<Integer, String> getProductLabels() {
		return productLabels;
	}

	public Map<Integer, String> getSpendingsLabels() {
		return spendingsLabels;
	}

	public Map<Integer, String> getIncomeLabels() {
		return incomeLabels;
	}
}
