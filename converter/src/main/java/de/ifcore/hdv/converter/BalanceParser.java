package de.ifcore.hdv.converter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import de.ifcore.hdv.converter.data.BalanceItem;
import de.ifcore.hdv.converter.data.BalanceValue;
import de.ifcore.hdv.converter.parser.AbstractCsvParser;

public class BalanceParser extends AbstractCsvParser<BalanceItem> {

	private static final String[] SUFFIXES_TO_REMOVE = { "des Haushaltsjahres", "des Vorjahres" };

	public BalanceParser(Collection<String[]> lines) {
		super(lines);
	}

	@Override
	protected List<BalanceItem> parseItem(String[] strings) {
		if (strings.length >= 6) {
			Long value = Long.valueOf(strings[5].trim());
			String areaKey = strings[0].trim();
			String accountLabel = strings[4].trim();
			boolean currentYear = true;
			if (accountLabel.endsWith("des Vorjahres")) {
				currentYear = false;
			}
			for (String str : SUFFIXES_TO_REMOVE) {
				if (accountLabel.endsWith(str)) {
					accountLabel = accountLabel.substring(0, accountLabel.length() - str.length()).trim();
				}
			}
			BalanceItem bi = new BalanceItem(areaKey.substring(0, areaKey.length() - 1), strings[2].trim(),
					accountLabel);
			BalanceValue balanceValue = new BalanceValue(strings[3].trim(), value);
			if (currentYear)
				bi.setCurrent(balanceValue);
			else
				bi.setPrevious(balanceValue);
			return Arrays.asList(bi);
		}
		else
			return Collections.emptyList();
	}
}
