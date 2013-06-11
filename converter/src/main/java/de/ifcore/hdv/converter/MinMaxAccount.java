package de.ifcore.hdv.converter;

public class MinMaxAccount {

	private int key;
	private String label;
	private Long iMin;
	private Long iMax;
	private Long sMin;
	private Long sMax;
	private Long dMin;
	private Long dMax;

	public MinMaxAccount(int key, String label) {
		this.key = key;
		this.label = label;
	}

	public int getKey() {
		return key;
	}

	public String getLabel() {
		return label;
	}

	public void addValue(Long income, Long spendings) {
		if (income != null) {
			if (iMin == null)
				iMin = income;
			if (iMax == null)
				iMax = income;
			iMin = Math.min(iMin, income);
			iMax = Math.max(iMax, income);
		}
		if (spendings != null) {
			if (sMin == null)
				sMin = spendings;
			if (sMax == null)
				sMax = spendings;
			sMin = Math.min(sMin, spendings);
			sMax = Math.max(sMax, spendings);
		}
		if (income != null || spendings != null) {
			if (income == null)
				income = Long.valueOf(0);
			if (spendings == null)
				spendings = Long.valueOf(0);
			if (dMin == null)
				dMin = income - spendings;
			if (dMax == null)
				dMax = income - spendings;
			dMin = Math.min(dMin, income - spendings);
			dMax = Math.max(dMax, income - spendings);
		}
	}

	public Long getIMin() {
		return iMin;
	}

	public Long getIMax() {
		return iMax;
	}

	public Long getSMin() {
		return sMin;
	}

	public Long getSMax() {
		return sMax;
	}

	public Long getDMin() {
		return dMin;
	}

	public Long getDMax() {
		return dMax;
	}
}
