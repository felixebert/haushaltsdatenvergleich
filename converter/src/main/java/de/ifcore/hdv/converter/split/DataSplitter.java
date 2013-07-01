package de.ifcore.hdv.converter.split;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.ifcore.hdv.converter.MinMax;
import de.ifcore.hdv.converter.data.AccountValue;
import de.ifcore.hdv.converter.data.AccountsPerArea;
import de.ifcore.hdv.converter.data.MergedData;

public class DataSplitter {

	private MergedData mergedData;

	public DataSplitter(MergedData mergedData) {
		this.mergedData = mergedData;
	}

	public DataSplit split() {
		Map<Integer, List<Integer>> tree = mergedData.getTree();
		Labels labels = new Labels(tree, mergedData.getProductLabels(), mergedData.getIncomeLabels(),
				mergedData.getSpendingsLabels());
		DataSplit dataSplit = new DataSplit(labels);
		for (Entry<Integer, List<Integer>> entry : tree.entrySet()) {
			for (Integer productId : entry.getValue()) {
				Product product = createProduct(productId);
				dataSplit.addProduct(productId, product);
			}
		}
		return dataSplit;
	}

	private Product createProduct(Integer productId) {
		List<AccountsPerAreaSplit> areas = new ArrayList<>();
		for (AccountsPerArea apa : mergedData.getAreas()) {
			Map<Integer, AccountValue> accounts = null;
			if (apa.getProducts().containsKey(productId)) {
				accounts = apa.getProducts().get(productId).getAccounts();
			}
			AccountsPerAreaSplit productApa = new AccountsPerAreaSplit(apa.getKey(), apa.getPopulation(),
					apa.getSize(), accounts);
			areas.add(productApa);
		}
		Map<Integer, MinMax> minMaxAccounts = mergedData.getProducts().containsKey(productId) ? mergedData
				.getProducts().get(productId).getAccounts() : null;
		return new Product(areas, minMaxAccounts);
	}
}
