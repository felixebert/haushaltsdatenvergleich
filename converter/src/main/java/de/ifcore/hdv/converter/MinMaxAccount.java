package de.ifcore.hdv.converter;

public class MinMaxAccount {

	private String label;
	private Long iMin;
	private Long iMax;
	private Long sMin;
	private Long sMax;
	private Long dMin;
	private Long dMax;

	public MinMaxAccount(String label) {
		this.label = label;
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
		if (income != null && spendings != null) {
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
