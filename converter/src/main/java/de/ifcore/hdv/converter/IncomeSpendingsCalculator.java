package de.ifcore.hdv.converter;

public class IncomeSpendingsCalculator {

	private Long income;
	private Long spendings;
	private long population;
	private double size;

	public IncomeSpendingsCalculator(Long income, Long spendings, long population, double size) {
		this.income = income;
		this.spendings = spendings;
		this.population = population;
		this.size = size;
	}

	public Long getIncome() {
		return income;
	}

	public Long getSpendings() {
		return spendings;
	}

	private Long divideSafe(Long value1, long value2) {
		if (value1 != null)
			return Long.valueOf(value1 / value2);
		else
			return null;
	}

	public Long getIncomePerArea() {
		return divideSafe(income, Math.round(size));
	}

	public Long getSpendingsPerArea() {
		return divideSafe(spendings, Math.round(size));
	}

	public Long getIncomePerPopulation() {
		return divideSafe(income, population);
	}

	public Long getSpendingsPerPopulation() {
		return divideSafe(spendings, population);
	}
}
