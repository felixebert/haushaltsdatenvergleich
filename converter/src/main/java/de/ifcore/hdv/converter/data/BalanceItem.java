package de.ifcore.hdv.converter.data;

public class BalanceItem {

	private String ks;
	private String no;
	private String label;
	private Long value;

	public BalanceItem(String ks, String no, String label, Long value) {
		this.ks = ks;
		this.no = no;
		this.label = label;
		this.value = value;
	}

	public String getKs() {
		return ks;
	}

	public String getNo() {
		return no;
	}

	public String getLabel() {
		return label;
	}

	public Long getValue() {
		return value;
	}
}
