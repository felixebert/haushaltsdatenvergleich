package de.ifcore.hdv.converter.utils;

import java.util.HashSet;
import java.util.Set;

import de.ifcore.hdv.converter.data.Account;
import de.ifcore.hdv.converter.data.AreaLabel;

public class AreaUtils {

	public static Set<AreaLabel> createUniqueAreaKeys(Iterable<Account> income, Iterable<Account> spendings) {
		Set<AreaLabel> areaKeys = new HashSet<>();
		collectAreaKeys(areaKeys, income);
		collectAreaKeys(areaKeys, spendings);
		return areaKeys;
	}

	private static void collectAreaKeys(Set<AreaLabel> areaKeys, Iterable<Account> accounts) {
		for (Account account : accounts) {
			areaKeys.add(new AreaLabel(account.getAreaKey(), account.getAreaLabel()));
		}
	}
}
