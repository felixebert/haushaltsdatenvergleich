package de.ifcore.hdv.converter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import de.ifcore.hdv.converter.data.BalanceItem;
import de.ifcore.hdv.converter.parser.AbstractCsvParser;

public class BalanceParser extends AbstractCsvParser<BalanceItem> {

	public BalanceParser(Collection<String[]> lines) {
		super(lines);
	}

	@Override
	protected List<BalanceItem> parseItem(String[] strings) {
		if (strings.length >= 6) {
			Long value = Long.valueOf(strings[5].trim());
			String areaKey = strings[0].trim();
			return Arrays.asList(new BalanceItem(areaKey.substring(0, areaKey.length() - 1), strings[2].trim(),
					strings[3].trim(), strings[4].trim(), value));
		}
		else
			return Collections.emptyList();
	}
}
