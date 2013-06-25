package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalueCalculatorTest {

	ValueCalculator calcNullValues = new ValueCalculator(null, 10, 2);
	ValueCalculator calc = new ValueCalculator(Long.valueOf(2000), 10, 2);

	@Test
	public void itShouldReturnIncomeAndSpendingsUnmodified() throws Exception {
		assertNull(calcNullValues.getValue());
		assertEquals(Long.valueOf(2000), calc.getValue());
	}

	@Test
	public void itShouldReturnPerAreaValues() throws Exception {
		assertNull(calcNullValues.getValuePerArea());
		assertEquals(Long.valueOf(1000), calc.getValuePerArea());
	}

	@Test
	public void itShouldReturnPerPopulationValues() throws Exception {
		assertNull(calcNullValues.getValuePerPopulation());
		assertEquals(Long.valueOf(200), calc.getValuePerPopulation());
	}
}
