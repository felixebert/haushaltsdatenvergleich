package de.ifcore.hdv.converter.data;

public class AccountClass implements Comparable<AccountClass> {

	private String prefix;
	private String label;

	public AccountClass(String prefix, String label) {
		this.prefix = prefix;
		this.label = label;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public int compareTo(AccountClass o) {
		return prefix.compareTo(o.prefix);
	}
}
