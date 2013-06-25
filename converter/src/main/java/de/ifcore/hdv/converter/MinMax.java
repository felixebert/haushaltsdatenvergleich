package de.ifcore.hdv.converter;

import de.ifcore.hdv.converter.data.AccountsPerArea;

public class MinMax {

	public static final int MAX_VALUE = 0;
	public static final int MIN_VALUE = 1;
	public static final int MAX_VALUE_PER_POP = 2;
	public static final int MIN_VALUE_PER_POP = 3;
	public static final int MAX_VALUE_PER_AREA = 4;
	public static final int MIN_VALUE_PER_AREA = 5;
	private int key;
	private Long[] data = new Long[3 * 2];

	public MinMax(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}

	protected void addValue(Long value, AccountsPerArea accountsPerArea) {
		ValueCalculator calc = new ValueCalculator(value, accountsPerArea.getPopulation(), accountsPerArea.getSize());
		addMinMaxValue(calc.getValue(), MIN_VALUE, MAX_VALUE);
		addMinMaxValue(calc.getValuePerArea(), MIN_VALUE_PER_AREA, MAX_VALUE_PER_AREA);
		addMinMaxValue(calc.getValuePerPopulation(), MIN_VALUE_PER_POP, MAX_VALUE_PER_POP);
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

	public boolean hasData() {
		boolean result = false;
		for (Long l : data) {
			result = result || l != null;
		}
		return result;
	}
}
