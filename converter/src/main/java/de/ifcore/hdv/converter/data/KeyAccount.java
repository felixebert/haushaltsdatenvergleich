package de.ifcore.hdv.converter.data;

public class KeyAccount implements Comparable<KeyAccount> {

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
		int c = label.compareTo(o.label);
		if (c == 0) {
			c = key - o.key;
		}
		return c;
	}
}
