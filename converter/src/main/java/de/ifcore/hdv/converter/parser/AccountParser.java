package de.ifcore.hdv.converter.parser;

import java.util.Collection;

import de.ifcore.hdv.converter.data.Account;
import de.ifcore.hdv.converter.utils.Utils;

public abstract class AccountParser extends AbstractCsvParser<Account> {

	public AccountParser(Collection<String[]> lines) {
		super(lines);
	}

	@Override
	protected Account parseItem(String[] strings) {
		Account item = null;
		if (strings.length == 5) {
			String areaKey = strings[0];
			if (isAreaKeyAcceptable(areaKey)) {
				String accountKey = strings[2];
				String accountName = strings[3];
				String value = strings[4];
				Long convertedValue = Utils.parseLongSafe(value);
				if (Utils.hasText(areaKey) && areaKey.length() == 8 && Utils.hasText(accountKey)) {
					int parsedAccountKey = Integer.parseInt(accountKey);
					item = new Account(areaKey, parsedAccountKey, accountName, convertedValue);
				}
			}
		}
		return item;
	}

	protected abstract boolean isAreaKeyAcceptable(String areaKey);
}
