package de.ifcore.hdv.converter.data;

public class LongValue {

	public static final LongValue NA = new LongValue(null, "-");
	private Long value;
	private String orgValue;

	private LongValue(Long value, String orgValue) {
		this.value = value;
		this.orgValue = orgValue;
	}

	public boolean isValid() {
		return value != null;
	}

	public boolean isValidForOutput() {
		return isValid() || (orgValue != null && !orgValue.equals("-"));
	}

	public Long getValue() {
		return value;
	}

	public String getOrgValue() {
		return orgValue;
	}

	public static LongValue valueOf(String value) {
		try {
			return new LongValue(Long.valueOf(value), null);
		}
		catch (NumberFormatException e) {
			return new LongValue(null, value);
		}
	}

	public static LongValue valueOf(long value) {
		return new LongValue(Long.valueOf(value), null);
	}
}
