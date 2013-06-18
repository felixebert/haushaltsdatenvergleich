package de.ifcore.hdv.converter.data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BalanceSheet {

	private Map<String, Map<String, List<BalanceItem>>> assets = new LinkedHashMap<>();
	private Map<String, Map<String, List<BalanceItem>>> liabilities = new LinkedHashMap<>();

	public Map<String, Map<String, List<BalanceItem>>> getAssets() {
		return assets;
	}

	public Map<String, Map<String, List<BalanceItem>>> getLiabilities() {
		return liabilities;
	}
}
