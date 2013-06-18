package de.ifcore.hdv.converter.parser;

import java.io.InputStream;

import de.ifcore.hdv.converter.data.Account;

public class AccountParser extends AbstractCsvParser<Account> {

	public AccountParser(InputStream in) {
		super(in);
	}

	@Override
	protected Account parseItem(String[] strings) {
		Account item = null;
		if (strings.length == 5) {
			String areaKey = strings[0];
			String accountKey = strings[2];
			String accountName = strings[3];
			String value = strings[4];
			Long convertedValue = parseLongSafe(value);
			if (hasText(areaKey) && areaKey.length() == 8 && hasText(accountKey)) {
				int parsedAccountKey = Integer.parseInt(accountKey);
				item = new Account(areaKey, parsedAccountKey, accountName, convertedValue);
			}
		}
		return item;
	}
}
