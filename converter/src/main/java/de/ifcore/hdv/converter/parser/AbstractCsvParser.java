package de.ifcore.hdv.converter.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractCsvParser<T> {

	private Collection<String[]> lines;

	protected AbstractCsvParser(Collection<String[]> lines) {
		this.lines = lines;
	}

	public List<T> parse() {
		List<T> result = new ArrayList<>();
		for (String[] line : lines) {
			T item = parseItem(line);
			if (item != null)
				result.add(item);
		}
		return result;
	}

	protected abstract T parseItem(String[] strings);
}
