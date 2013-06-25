package de.ifcore.hdv.converter.data;


public class InOutAccount {

	private int key;
	private Long income;
	private Long spending;

	public InOutAccount(int key, Long income, Long spending) {
		this.key = key;
		this.income = income;
		this.spending = spending;
	}

	public int getKey() {
		return key;
	}

	public Long getIncome() {
		return income;
	}

	public Long getSpending() {
		return spending;
	}

	public void incIncome(Long value) {
		if (income == null)
			income = value;
		else if (value != null)
			income += value;
	}

	public void incSpendings(Long value) {
		if (spending == null)
			spending = value;
		else if (value != null)
			spending += value;
	}
}
