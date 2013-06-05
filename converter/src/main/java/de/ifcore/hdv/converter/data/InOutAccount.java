package de.ifcore.hdv.converter.data;

public class InOutAccount {

	private String key;
	private Long in;
	private Long out;

	public InOutAccount(String key, Long in, Long out) {
		this.key = key;
		this.in = in;
		this.out = out;
	}

	public String getKey() {
		return key;
	}

	public Long getIn() {
		return in;
	}

	public Long getOut() {
		return out;
	}

	public void incIncome(Long value) {
		if (in == null)
			in = value;
		else if (value != null)
			in += value;
	}

	public void incSpendings(Long value) {
		if (out == null)
			out = value;
		else if (value != null)
			out += value;
	}
}
