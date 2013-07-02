package de.ifcore.hdv.converter.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountValue {

	@JsonIgnore
	private int key;
	private LongValue value;

	public AccountValue(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}

	public LongValue getValue() {
		return value;
	}

	public void setValue(LongValue value) {
		this.value = value;
	}
}
