package de.ifcore.hdv.converter.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import de.ifcore.hdv.converter.data.Account;

public class AccountParser {

	private CSVReader reader;

	public AccountParser(InputStream in) {
		reader = new CSVReader(new InputStreamReader(in, Charset.forName("ISO-8859-1")), ';');
	}

	public List<Account> parse() {
		List<Account> result = new ArrayList<>();
		try {
			String[] strings = null;
			do {
				strings = reader.readNext();
				if (strings != null) {
					if (strings.length == 5) {
						String areaKey = strings[0];
						String accountKey = strings[2];
						String accountName = strings[3];
						String value = strings[4];
						Long convertedValue = parseLongSafe(value);
						if (hasText(areaKey) && areaKey.length() == 8 && hasText(accountKey)) {
							int parsedAccountKey = Integer.parseInt(accountKey);
							result.add(new Account(areaKey, parsedAccountKey, accountName, convertedValue));
						}
					}
				}
			} while (strings != null);
		}
		catch (IOException e) {
			return null;
		}
		return result;
	}

	private Long parseLongSafe(String value) {
		try {
			return Long.valueOf(value);
		}
		catch (NumberFormatException e) {
			//							System.err.println("Error converting: " + areaKey + "/" + accountKey + "/" + accountName
			//									+ " => " + value);
			return null;
		}
	}

	public static boolean hasText(String s) {
		return s != null && !s.trim().isEmpty();
	}
}
