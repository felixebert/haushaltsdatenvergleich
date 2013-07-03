package de.ifcore.hdv.converter.data;

import java.text.Collator;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class KeyAccount implements Comparable<KeyAccount> {

	@JsonIgnore
	private static final Collator collator = Collator.getInstance(Locale.GERMAN);
	private int key;
	private String label;

	public KeyAccount(int key, String label) {
		this.key = key;
		this.label = label;
	}

	public int getKey() {
		return key;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public int compareTo(KeyAccount o) {
		int c = collator.compare(label, o.label);
		if (c == 0) {
			c = key - o.key;
		}
		return c;
	}
}
