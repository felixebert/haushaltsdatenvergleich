package de.ifcore.hdv.converter.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountValue {

	@JsonIgnore
	private int key;
	private Long value;

	public AccountValue(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
}