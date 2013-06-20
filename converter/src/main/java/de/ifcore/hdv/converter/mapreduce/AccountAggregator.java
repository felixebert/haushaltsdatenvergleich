package de.ifcore.hdv.converter.mapreduce;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.ifcore.hdv.converter.data.Account;

public class AccountAggregator {

	public static List<Account> reduceAreaKeyForCounty(Collection<Account> accounts) {
		List<Account> result = new ArrayList<Account>();
		for (Account account : accounts) {
			String countyKey = extractCountyKey(account);
			if (countyKey != null)
				result.add(new Account(countyKey, account.getAccountKey(), account.getAccountName(), account.getValue()));
		}
		return result;
	}

	private static String extractCountyKey(Account account) {
		if (account.getAreaKey().length() >= 5)
			return account.getAreaKey().substring(0, 5);
		else
			return null;
	}
}
