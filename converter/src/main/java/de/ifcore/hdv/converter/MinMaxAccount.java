package de.ifcore.hdv.converter;

import de.ifcore.hdv.converter.data.AccountsPerArea;
import de.ifcore.hdv.converter.data.IncomeSpendings;

public class MinMaxAccount {

	public static final int MIN_INCOME = 0;
	public static final int MAX_INCOME = 1;
	public static final int MIN_SPENDINGS = 2;
	public static final int MAX_SPENDINGS = 3;
	public static final int MIN_POS_DIFF = 4;
	public static final int MAX_POS_DIFF = 5;
	public static final int MIN_NEG_DIFF = 6;
	public static final int MAX_NEG_DIFF = 7;
	public static final int MIN_INCOME_PER_AREA = 8;
	public static final int MAX_INCOME_PER_AREA = 9;
	public static final int MIN_SPENDINGS_PER_AREA = 10;
	public static final int MAX_SPENDINGS_PER_AREA = 11;
	public static final int MIN_POS_DIFF_PER_AREA = 12;
	public static final int MAX_POS_DIFF_PER_AREA = 13;
	public static final int MIN_NEG_DIFF_PER_AREA = 14;
	public static final int MAX_NEG_DIFF_PER_AREA = 15;
	public static final int MIN_INCOME_PER_POP = 16;
	public static final int MAX_INCOME_PER_POP = 17;
	public static final int MIN_SPENDINGS_PER_POP = 18;
	public static final int MAX_SPENDINGS_PER_POP = 19;
	public static final int MIN_POS_DIFF_PER_POP = 20;
	public static final int MAX_POS_DIFF_PER_POP = 21;
	public static final int MIN_NEG_DIFF_PER_POP = 22;
	public static final int MAX_NEG_DIFF_PER_POP = 23;
	private int key;
	private String label;
	private Long[] data = new Long[3 * 8];

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

	public void addValue(Long income, Long spendings, AccountsPerArea accountsPerArea) {
		IncomeSpendingsCalculator calc = new IncomeSpendingsCalculator(income, spendings,
				accountsPerArea.getPopulation(), accountsPerArea.getSize());
		IncomeSpendings[] incomeSpendings = new IncomeSpendings[3];
		incomeSpendings[0] = new IncomeSpendings(income, spendings);
		incomeSpendings[1] = new IncomeSpendings(calc.getIncomePerArea(), calc.getSpendingsPerArea());
		incomeSpendings[2] = new IncomeSpendings(calc.getIncomePerPopulation(), calc.getSpendingsPerPopulation());
		for (int x = 0; x < incomeSpendings.length; x++) {
			int base = (8 * x);
			addMinMaxValue(incomeSpendings[x].getIncome(), base + MIN_INCOME, base + MAX_INCOME);
			addMinMaxValue(incomeSpendings[x].getSpending(), base + MIN_SPENDINGS, base + MAX_SPENDINGS);
			addDifference(incomeSpendings[x].getIncome(), incomeSpendings[x].getSpending(), base + MIN_POS_DIFF, base
					+ MAX_POS_DIFF, base + MIN_NEG_DIFF, base + MAX_NEG_DIFF);
		}
	}

	private void addDifference(Long income, Long spendings, int pmin, int pmax, int nmin, int nmax) {
		if (income != null || spendings != null) {
			if (income == null)
				income = Long.valueOf(0);
			if (spendings == null)
				spendings = Long.valueOf(0);
			long diff = income - spendings;
			if (diff >= 0) {
				if (data[pmin] == null)
					data[pmin] = diff;
				if (data[pmax] == null)
					data[pmax] = diff;
				data[pmin] = Math.min(data[pmin], diff);
				data[pmax] = Math.max(data[pmax], diff);
			}
			else {
				if (data[nmin] == null)
					data[nmin] = diff;
				if (data[nmax] == null)
					data[nmax] = diff;
				data[nmin] = Math.min(data[nmin], diff);
				data[nmax] = Math.max(data[nmax], diff);
			}
		}
	}

	private void addMinMaxValue(Long value, int min, int max) {
		if (value != null) {
			if (data[min] == null)
				data[min] = value;
			if (data[max] == null)
				data[max] = value;
			data[min] = Math.min(data[min], value);
			data[max] = Math.max(data[max], value);
		}
	}

	public Long[] getData() {
		return data;
	}

	public Long getDataValue(int index) {
		return data[index];
	}
}
