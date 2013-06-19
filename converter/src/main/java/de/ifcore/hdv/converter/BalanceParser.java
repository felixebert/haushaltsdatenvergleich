package de.ifcore.hdv.converter;

import java.util.Collection;

import de.ifcore.hdv.converter.data.BalanceItem;
import de.ifcore.hdv.converter.parser.AbstractCsvParser;

public class BalanceParser extends AbstractCsvParser<BalanceItem> {

	public BalanceParser(Collection<String[]> lines) {
		super(lines);
	}

	@Override
	protected BalanceItem parseItem(String[] strings) {
		if (strings.length >= 6) {
			Long value = Long.valueOf(strings[5].trim());
			return new BalanceItem(strings[0].trim(), strings[3].trim(), strings[4].trim(), value);
		}
		else
			return null;
	}
}
