package de.ifcore.hdv.converter.data;

public class BalanceValue {

	private String no;
	private Long value;

	public BalanceValue(String no, Long value) {
		this.no = no;
		this.value = value;
	}

	public String getNo() {
		return no;
	}

	public Long getValue() {
		return value;
	}
}
