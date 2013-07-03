package de.ifcore.hdv.converter.split;

import java.util.HashMap;
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
		DataSplit dataSplit = new DataSplit(mergedData);
		for (Entry<Integer, List<Integer>> entry : tree.entrySet()) {
			for (Integer productId : entry.getValue()) {
				Product product = createProduct(productId);
				dataSplit.addProduct(productId, product);
			}
		}
		return dataSplit;
	}

	private Product createProduct(Integer productId) {
		Map<String, Map<Integer, AccountValue>> areas = new HashMap<>();
		Map<Integer, AccountValue> emptyAccountMap = new HashMap<>();
		Map<Integer, MinMax> emptyMinMaxAccounts = new HashMap<>();
		for (AccountsPerArea apa : mergedData.getAreas()) {
			Map<Integer, AccountValue> accounts = emptyAccountMap;
			if (apa.getProducts().containsKey(productId)) {
				accounts = apa.getProducts().get(productId).getAccounts();
			}
			areas.put(apa.getKey(), accounts);
		}
		Map<Integer, MinMax> minMaxAccounts = mergedData.getProductsMap().containsKey(productId) ? mergedData
				.getProductsMap().get(productId).getAccounts() : emptyMinMaxAccounts;
		return new Product(areas, minMaxAccounts);
	}
}
