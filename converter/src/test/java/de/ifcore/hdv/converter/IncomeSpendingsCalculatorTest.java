package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import org.junit.Test;

public class IncomeSpendingsCalculatorTest {

	IncomeSpendingsCalculator calcNullValues = new IncomeSpendingsCalculator(null, null, 10, 2);
	IncomeSpendingsCalculator calc = new IncomeSpendingsCalculator(Long.valueOf(1000), Long.valueOf(2000), 10, 2);

	@Test
	public void itShouldReturnIncomeAndSpendingsUnmodified() throws Exception {
		assertNull(calcNullValues.getIncome());
		assertNull(calcNullValues.getSpendings());
		assertEquals(Long.valueOf(1000), calc.getIncome());
		assertEquals(Long.valueOf(2000), calc.getSpendings());
	}

	@Test
	public void itShouldReturnPerAreaValues() throws Exception {
		assertNull(calcNullValues.getIncomePerArea());
		assertNull(calcNullValues.getSpendingsPerArea());
		assertEquals(Long.valueOf(500), calc.getIncomePerArea());
		assertEquals(Long.valueOf(1000), calc.getSpendingsPerArea());
	}

	@Test
	public void itShouldReturnPerPopulationValues() throws Exception {
		assertNull(calcNullValues.getIncomePerPopulation());
		assertNull(calcNullValues.getSpendingsPerPopulation());
		assertEquals(Long.valueOf(100), calc.getIncomePerPopulation());
		assertEquals(Long.valueOf(200), calc.getSpendingsPerPopulation());
	}
}
