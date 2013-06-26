package de.ifcore.hdv.converter.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.ifcore.hdv.converter.data.Account;
import de.ifcore.hdv.converter.utils.Utils;

public abstract class AccountParser extends AbstractColumnCsvParser<Account> {

	private static final int AREA_KEY = 1;
	private static final int ACCOUNT_KEY = 3;
	private static final int ACCOUNT_NAME = 4;
	private static final int VALUE_COLMN = 5;

	public AccountParser(Collection<String[]> lines) {
		super(lines, 7);
	}

	@Override
	protected List<Account> parseItem(String[] strings) {
		List<Account> result = new ArrayList<>();
		List<ColumnDefinition> columnDefinitions = getColumnDefinitions();
		if (strings.length == VALUE_COLMN + columnDefinitions.size()) {
			String areaKey = strings[AREA_KEY];
			if (isAreaKeyAcceptable(areaKey)) {
				String accountKey = strings[ACCOUNT_KEY];
				String accountName = strings[ACCOUNT_NAME];
				if (isAreaKeyValid(areaKey) && Utils.hasText(accountKey)) {
					int parsedAccountKey = Integer.parseInt(accountKey);
					for (int x = 0; x < columnDefinitions.size(); x++) {
						ColumnDefinition cd = columnDefinitions.get(x);
						String value = strings[VALUE_COLMN + x];
						Long convertedValue = Utils.parseLongSafe(value);
						result.add(new Account(areaKey, cd.getKey(), cd.getLabel(), parsedAccountKey, accountName,
								convertedValue));
					}
				}
			}
		}
		return result;
	}

	private boolean isAreaKeyValid(String areaKey) {
		return Utils.hasText(areaKey) && areaKey.length() == 8;
	}

	protected abstract boolean isAreaKeyAcceptable(String areaKey);
}
