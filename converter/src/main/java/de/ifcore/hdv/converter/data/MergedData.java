package de.ifcore.hdv.converter.data;

import java.util.List;
import java.util.Map;

public class MergedData {

	private List<AccountsPerArea> accountsPerAreas;
	private Map<String, String> accountMap;

	public MergedData(List<AccountsPerArea> accountsPerAreas, Map<String, String> accountMap) {
		this.accountsPerAreas = accountsPerAreas;
		this.accountMap = accountMap;
	}

	public List<AccountsPerArea> getAccountsPerAreas() {
		return accountsPerAreas;
	}

	public Map<String, String> getAccountMap() {
		return accountMap;
	}
}
