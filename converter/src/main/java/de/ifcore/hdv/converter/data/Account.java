package de.ifcore.hdv.converter.data;

public class Account {

	private String areaKey;
	private String accountKey;
	private String accountName;
	private Long value;

	public Account(String areaKey, String accountKey, String accountName, Long value) {
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

	public Long getValue() {
		return value;
	}
}
