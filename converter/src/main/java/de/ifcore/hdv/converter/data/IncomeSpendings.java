package de.ifcore.hdv.converter.data;

public class IncomeSpendings {

	private Long income;
	private Long spending;

	public IncomeSpendings(Long income, Long spending) {
		this.income = income;
		this.spending = spending;
	}

	public Long getIncome() {
		return income;
	}

	public Long getSpending() {
		return spending;
	}
}
