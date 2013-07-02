package de.ifcore.hdv.converter;

import de.ifcore.hdv.converter.data.AccountsPerArea;
import de.ifcore.hdv.converter.data.LongValue;

public class MinMax {

	public static final int MAX_VALUE = 0;
	public static final int MIN_VALUE = 1;
	public static final int MAX_VALUE_PER_POP = 2;
	public static final int MIN_VALUE_PER_POP = 3;
	public static final int MAX_VALUE_PER_AREA = 4;
	public static final int MIN_VALUE_PER_AREA = 5;
	private int key;
	private LongValue[] data = new LongValue[3 * 2];

	public MinMax(int key) {
		this.key = key;
		for (int x = 0; x < data.length; x++) {
			data[x] = LongValue.NA;
		}
	}

	public int getKey() {
		return key;
	}

	protected void addValue(LongValue value, AccountsPerArea accountsPerArea) {
		ValueCalculator calc = new ValueCalculator(value, accountsPerArea.getPopulation(), accountsPerArea.getSize());
		addMinMaxValue(calc.getValue(), MIN_VALUE, MAX_VALUE);
		addMinMaxValue(calc.getValuePerArea(), MIN_VALUE_PER_AREA, MAX_VALUE_PER_AREA);
		addMinMaxValue(calc.getValuePerPopulation(), MIN_VALUE_PER_POP, MAX_VALUE_PER_POP);
	}

	private void addMinMaxValue(LongValue value, int min, int max) {
		if (value.isValid()) {
			if (!data[min].isValid())
				data[min] = value;
			if (!data[max].isValid())
				data[max] = value;
			data[min] = LongValue.valueOf(Math.min(data[min].getValue(), value.getValue()));
			data[max] = LongValue.valueOf(Math.max(data[max].getValue(), value.getValue()));
		}
	}

	public LongValue[] getData() {
		return data;
	}

	public boolean hasData() {
		boolean result = false;
		for (LongValue l : data) {
			result = result || l.isValid();
		}
		return result;
	}
}
