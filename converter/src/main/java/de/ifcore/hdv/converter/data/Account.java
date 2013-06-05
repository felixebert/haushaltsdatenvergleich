package de.ifcore.hdv.converter.data;

public class Account {

	private String areaKey;
	private String accountKey;
	private String accountName;
	private long value;

	public Account(String areaKey, String accountKey, String accountName, long value) {
		this.areaKey = areaKey;
		this.accountKey = accountKey;
		this.accountName = accountName;
		this.value = value;
	}

	public String getAreaKey() {
		return areaKey;
	}

	public String getAccountKey() {
		return accountKey;
	}

	public String getAccountName() {
		return accountName;
	}

	public long getValue() {
		return value;
	}
}
