package de.ifcore.hdv.converter.split;

import java.util.List;
import java.util.Map;

public class Labels {

	private Map<Integer, List<Integer>> tree;
	private Map<Integer, String> productLabels;
	private Map<Integer, String> incomeLabels;
	private Map<Integer, String> spendingsLabels;

	public Labels(Map<Integer, List<Integer>> tree, Map<Integer, String> productLabels,
			Map<Integer, String> incomeLabels, Map<Integer, String> spendingsLabels) {
		this.tree = tree;
		this.productLabels = productLabels;
		this.incomeLabels = incomeLabels;
		this.spendingsLabels = spendingsLabels;
	}

	public Map<Integer, List<Integer>> getTree() {
		return tree;
	}

	public Map<Integer, String> getProductLabels() {
		return productLabels;
	}

	public Map<Integer, String> getIncomeLabels() {
		return incomeLabels;
	}

	public Map<Integer, String> getSpendingsLabels() {
		return spendingsLabels;
	}
}
