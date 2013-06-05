package de.ifcore.hdv.converter.data;

import java.util.List;
import java.util.Map;

import de.ifcore.hdv.converter.MinMaxAccount;

public class MergedData {

	private List<AccountsPerArea> accountsPerAreas;
	private Map<String, MinMaxAccount> accountMap;

	public MergedData(List<AccountsPerArea> accountsPerAreas, Map<String, MinMaxAccount> accountMap) {
		this.accountsPerAreas = accountsPerAreas;
		this.accountMap = accountMap;
	}

	public List<AccountsPerArea> getAccountsPerAreas() {
		return accountsPerAreas;
	}

	public Map<String, MinMaxAccount> getAccountMap() {
		return accountMap;
	}
}
