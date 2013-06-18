package de.ifcore.hdv.converter;

import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import de.ifcore.hdv.converter.data.AccountClass;
import de.ifcore.hdv.converter.utils.PropertyParser;
import de.ifcore.hdv.converter.utils.PropertyUtils;

public class AccountClasses {

	private SortedSet<AccountClass> mainAccountClasses = new TreeSet<>();
	private SortedSet<AccountClass> subAccountClasses = new TreeSet<>();
	private static final PropertyParser<AccountClass> accountClassParser = new PropertyParser<AccountClass>() {

		@Override
		public AccountClass parse(Entry<Object, Object> entry) {
			String key = (String)entry.getKey();
			String label = (String)entry.getValue();
			return new AccountClass(key, label);
		}
	};
	private static final AccountClasses INSTANCE = new AccountClasses();

	private AccountClasses() {
		mainAccountClasses.addAll(PropertyUtils.loadProperties("kontenklassen.properties", accountClassParser));
		subAccountClasses.addAll(PropertyUtils.loadProperties("nkf-kontenrahmen.properties", accountClassParser));
	}

	public static AccountClasses getInstance() {
		return INSTANCE;
	}

	public SortedSet<AccountClass> getMainAccountClasses() {
		return mainAccountClasses;
	}

	public SortedSet<AccountClass> getSubAccountClasses() {
		return subAccountClasses;
	}

	public static boolean isAsset(String prefix) {
		return prefix.equals("0") || prefix.equals("1");
	}
}
