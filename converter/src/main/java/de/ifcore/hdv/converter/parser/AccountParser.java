package de.ifcore.hdv.converter.parser;

import java.util.Collection;

import de.ifcore.hdv.converter.data.Account;
import de.ifcore.hdv.converter.utils.Utils;

public abstract class AccountParser extends AbstractCsvParser<Account> {

	private static final int REQ_COLUMNS = 7;
	private static final int ACCOUNT_KEY = 2;
	private static final int ACCOUNT_NAME = 3;
	private static final int PRODUCT_KEY = 4;
	private static final int PRODUCT_NAME = 5;
	private static final int VALUE = 6;

	public AccountParser(Collection<String[]> lines) {
		super(lines);
	}

	@Override
	protected Account parseItem(String[] strings) {
		Account item = null;
		if (strings.length == REQ_COLUMNS) {
			String areaKey = strings[0];
			if (isAreaKeyAcceptable(areaKey)) {
				String accountKey = strings[ACCOUNT_KEY];
				String accountName = strings[ACCOUNT_NAME];
				String productKey = strings[PRODUCT_KEY];
				String productName = strings[PRODUCT_NAME];
				String value = strings[VALUE];
				Long convertedValue = Utils.parseLongSafe(value);
				if (isAreaKeyValid(areaKey) && Utils.hasText(accountKey) && Utils.hasText(productKey)) {
					int parsedProductKey = Integer.parseInt(productKey);
					int parsedAccountKey = Integer.parseInt(accountKey);
					item = new Account(areaKey, parsedProductKey, productName, parsedAccountKey, accountName,
							convertedValue);
				}
			}
		}
		return item;
	}

	private boolean isAreaKeyValid(String areaKey) {
		return Utils.hasText(areaKey) && areaKey.length() == 8;
	}

	protected abstract boolean isAreaKeyAcceptable(String areaKey);
}
