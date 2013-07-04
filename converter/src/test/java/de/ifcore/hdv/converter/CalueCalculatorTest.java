package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import org.junit.Test;

import de.ifcore.hdv.converter.data.LongValue;

public class CalueCalculatorTest {

	ValueCalculator calcNullValues = new ValueCalculator(LongValue.NA, 10, 2);
	ValueCalculator calc = new ValueCalculator(LongValue.valueOf(2000), 10, 2);

	@Test
	public void itShouldReturnIncomeAndSpendingsUnmodified() throws Exception {
		assertTrue(Double.isNaN(calcNullValues.getValue()));
		assertValue(2000, calc.getValue());
	}

	@Test
	public void itShouldReturnPerAreaValues() throws Exception {
		assertValue(1000, calc.getValuePerArea());
	}

	@Test
	public void itShouldReturnPerPopulationValues() throws Exception {
		assertValue(200, calc.getValuePerPopulation());
	}

	private static void assertValue(long expected, Double actual) {
		assertEquals(expected, actual.doubleValue(), 0.001);
	}
}
