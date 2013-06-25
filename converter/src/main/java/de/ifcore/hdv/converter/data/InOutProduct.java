package de.ifcore.hdv.converter.data;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class InOutProduct {

	private Map<Integer, AccountValue> accounts = new HashMap<>();
	@JsonIgnore
	private int productKey;

	public InOutProduct(int productKey) {
		this.productKey = productKey;
	}

	public int getProductKey() {
		return productKey;
	}

	public Map<Integer, AccountValue> getAccounts() {
		return accounts;
	}

	public void setValue(Account account) {
		AccountValue inOut = getInOutAccount(account);
		inOut.setValue(account.getValue());
	}

	private AccountValue getInOutAccount(Account account) {
		AccountValue inOut = accounts.get(account.getAccountKey());
		if (inOut == null) {
			inOut = new AccountValue(account.getAccountKey());
			accounts.put(account.getAccountKey(), inOut);
		}
		return inOut;
	}
}
