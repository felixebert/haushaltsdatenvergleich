package de.ifcore.hdv.converter.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public abstract class AbstractCsvParser<T> {

	protected CSVReader reader;

	protected AbstractCsvParser(InputStream in) {
		reader = new CSVReader(new InputStreamReader(in, Charset.forName("ISO-8859-1")), ';');
	}

	public List<T> parse() {
		List<T> result = new ArrayList<>();
		try {
			String[] strings = null;
			do {
				strings = reader.readNext();
				if (strings != null) {
					T item = parseItem(strings);
					if (item != null)
						result.add(item);
				}
			} while (strings != null);
		}
		catch (IOException e) {
			return null;
		}
		return result;
	}

	protected abstract T parseItem(String[] strings);

	protected Long parseLongSafe(String value) {
		try {
			return Long.valueOf(value);
		}
		catch (NumberFormatException e) {
			return null;
		}
	}

	public static boolean hasText(String s) {
		return s != null && !s.trim().isEmpty();
	}
}
