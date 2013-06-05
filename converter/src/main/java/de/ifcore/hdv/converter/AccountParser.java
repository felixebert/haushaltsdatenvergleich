package de.ifcore.hdv.converter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import de.ifcore.hdv.converter.data.Account;

public class AccountParser {

	private String countryCode;
	private CSVReader reader;

	public AccountParser(String countryCode, InputStream in) {
		this.countryCode = countryCode;
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
						if (hasText(areaKey) && areaKey.length() == 8 && hasText(accountKey)) {
							result.add(new Account(countryCode + areaKey, accountKey, accountName, Long
									.parseLong(value)));
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

	public static boolean hasText(String s) {
		return s != null && !s.trim().isEmpty();
	}
}
