package de.ifcore.hdv.converter.data;

public class Account {

	private String areaKey;
	private int productKey;
	private String productName;
	private int accountKey;
	private String accountName;
	private LongValue value;

	public Account(String areaKey, int productKey, String productName, int accountKey, String accountName,
			LongValue value) {
		this.areaKey = areaKey;
		this.productKey = productKey;
		this.productName = productName;
		this.accountKey = accountKey;
		this.accountName = accountName;
		this.value = value;
	}

	public String getAreaKey() {
		return areaKey;
	}

	public int getAccountKey() {
		return accountKey;
	}

	public String getAccountName() {
		return accountName;
	}

	public LongValue getValue() {
		return value;
	}

	public int getProductKey() {
		return productKey;
	}

	public String getProductName() {
		return productName;
	}
}
