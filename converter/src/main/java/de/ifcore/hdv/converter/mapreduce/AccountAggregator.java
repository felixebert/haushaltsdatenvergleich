package de.ifcore.hdv.converter.mapreduce;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.ifcore.hdv.converter.data.Account;

public class AccountAggregator {

	public static List<Account> aggregate(Collection<Account> accounts) {
		Map<String, Map<Integer, Account>> countyMap = new HashMap<>();
		for (Account account : accounts) {
			String countyKey = extractCountyKey(account);
			if (countyKey != null) {
				Map<Integer, Account> accountsMap = countyMap.get(countyKey);
				if (accountsMap == null) {
					accountsMap = new HashMap<Integer, Account>();
					countyMap.put(countyKey, accountsMap);
				}
				Account aggregatedAccount = accountsMap.get(account.getAccountKey());
				accountsMap.put(account.getAccountKey(), aggragteAccounts(countyKey, account, aggregatedAccount));
			}
		}
		return flattenMaps(countyMap);
	}

	private static String extractCountyKey(Account account) {
		if (account.getAreaKey().length() >= 5)
			return account.getAreaKey().substring(0, 5);
		else
			return null;
	}

	private static List<Account> flattenMaps(Map<String, Map<Integer, Account>> maps) {
		List<Account> result = new ArrayList<>();
		for (Entry<String, Map<Integer, Account>> countyEntry : maps.entrySet()) {
			result.addAll(countyEntry.getValue().values());
		}
		return result;
	}

	public static Account aggragteAccounts(String newAreaKey, Account a1, Account a2) {
		if (a1 == null) {
			if (a2 == null)
				return null;
			else
				return new Account(newAreaKey, a2.getAccountKey(), a2.getAccountName(), a2.getValue());
		}
		else if (a2 == null) {
			return new Account(newAreaKey, a1.getAccountKey(), a1.getAccountName(), a1.getValue());
		}
		else {
			Long value = null;
			if (a1.getValue() != null) {
				value = a1.getValue();
				if (a2.getValue() != null)
					value = Long.valueOf(value.longValue() + a2.getValue());
			}
			else {
				value = a2.getValue();
			}
			return new Account(newAreaKey, a1.getAccountKey(), a1.getAccountName(), value);
		}
	}
}
