package de.ifcore.hdv.converter.utils;

import java.util.HashSet;
import java.util.Set;

import de.ifcore.hdv.converter.data.Account;

public class AreaUtils {

	public static Set<String> createUniqueAreaKeys(Iterable<Account> income, Iterable<Account> spendings) {
		Set<String> areaKeys = new HashSet<String>();
		collectAreaKeys(areaKeys, income);
		collectAreaKeys(areaKeys, spendings);
		return areaKeys;
	}

	private static void collectAreaKeys(Set<String> areaKeys, Iterable<Account> accounts) {
		for (Account account : accounts) {
			areaKeys.add(account.getAreaKey());
		}
	}
}
