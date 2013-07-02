package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import org.junit.Test;

import de.ifcore.hdv.converter.data.LongValue;

public class CalueCalculatorTest {

	ValueCalculator calcNullValues = new ValueCalculator(LongValue.NA, 10, 2);
	ValueCalculator calc = new ValueCalculator(LongValue.valueOf(2000), 10, 2);

	@Test
	public void itShouldReturnIncomeAndSpendingsUnmodified() throws Exception {
		assertFalse(calcNullValues.getValue().isValid());
		assertLongValue(2000, calc.getValue());
	}

	@Test
	public void itShouldReturnPerAreaValues() throws Exception {
		assertFalse(calcNullValues.getValuePerArea().isValid());
		assertLongValue(1000, calc.getValuePerArea());
	}

	@Test
	public void itShouldReturnPerPopulationValues() throws Exception {
		assertFalse(calcNullValues.getValuePerPopulation().isValid());
		assertLongValue(200, calc.getValuePerPopulation());
	}

	private static void assertLongValue(long expected, LongValue actual) {
		assertEquals(Long.valueOf(expected), actual.getValue());
	}
}
