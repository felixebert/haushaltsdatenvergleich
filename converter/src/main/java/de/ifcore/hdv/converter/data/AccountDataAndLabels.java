package de.ifcore.hdv.converter.data;

import java.util.List;

import de.ifcore.hdv.converter.AccountLabels;

public class AccountDataAndLabels {

	private List<Account> accounts;
	private AccountLabels accountLabels;

	public AccountDataAndLabels(List<Account> accounts, AccountLabels accountLabels) {
		this.accounts = accounts;
		this.accountLabels = accountLabels;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public AccountLabels getAccountLabels() {
		return accountLabels;
	}
}
