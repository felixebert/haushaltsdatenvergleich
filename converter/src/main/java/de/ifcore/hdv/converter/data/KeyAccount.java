package de.ifcore.hdv.converter.data;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class KeyAccount implements Comparable<KeyAccount> {

	@JsonIgnore
	private static final Collator collator = Collator.getInstance(Locale.GERMAN);
	private int key;
	private String label;
	@JsonIgnore
	private CollationKey collationKey;

	public KeyAccount(int key, String label) {
		this.key = key;
		this.label = label;
		collationKey = collator.getCollationKey(label);
	}

	public int getKey() {
		return key;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public int compareTo(KeyAccount o) {
		int c = collationKey.compareTo(o.collationKey);
		if (c == 0) {
			c = key - o.key;
		}
		return c;
	}
}
