package de.ifcore.hdv.converter;

import de.ifcore.hdv.converter.data.AccountsPerArea;

public class MinMaxAccountTest {

	private AccountsPerArea apa1 = new AccountsPerArea(null, 10, 5.0, null);
	private AccountsPerArea apa2 = new AccountsPerArea(null, 20, 2.0, null);
	//
	//	@Test
	//	public void itShouldTolerateNulls() throws Exception {
	//		MinMaxProduct account = new MinMaxProduct(0, null);
	//		account.addValue(null, null, apa1);
	//		assertNull(account.getDataValue(MinMaxProduct.MIN_INCOME));
	//		assertNull(account.getDataValue(MinMaxProduct.MAX_INCOME));
	//		assertNull(account.getDataValue(MinMaxProduct.MIN_SPENDINGS));
	//		assertNull(account.getDataValue(MinMaxProduct.MAX_SPENDINGS));
	//		assertNull(account.getDataValue(MinMaxProduct.MIN_POS_DIFF));
	//		assertNull(account.getDataValue(MinMaxProduct.MAX_POS_DIFF));
	//	}
	//
	//	@Test
	//	public void itShouldCalculateMinMax() throws Exception {
	//		MinMaxProduct account = new MinMaxProduct(0, null);
	//		account.addValue(Long.valueOf(100), Long.valueOf(400), apa1);
	//		account.addValue(Long.valueOf(100), Long.valueOf(200), apa1);
	//		account.addValue(Long.valueOf(500), Long.valueOf(100), apa2);
	//		assertEquals(100, account.getDataValue(MinMaxProduct.MIN_INCOME).longValue());
	//		assertEquals(500, account.getDataValue(MinMaxProduct.MAX_INCOME).longValue());
	//		assertEquals(100, account.getDataValue(MinMaxProduct.MIN_SPENDINGS).longValue());
	//		assertEquals(400, account.getDataValue(MinMaxProduct.MAX_SPENDINGS).longValue());
	//		assertEquals(400, account.getDataValue(MinMaxProduct.MIN_POS_DIFF).longValue());
	//		assertEquals(400, account.getDataValue(MinMaxProduct.MAX_POS_DIFF).longValue());
	//		assertEquals(-100, account.getDataValue(MinMaxProduct.MIN_NEG_DIFF).longValue());
	//		assertEquals(-300, account.getDataValue(MinMaxProduct.MAX_NEG_DIFF).longValue());
	//		assertEquals(10, account.getDataValue(MinMaxProduct.MIN_INCOME_PER_POP).longValue());
	//		assertEquals(25, account.getDataValue(MinMaxProduct.MAX_INCOME_PER_POP).longValue());
	//		assertEquals(20, account.getDataValue(MinMaxProduct.MIN_INCOME_PER_AREA).longValue());
	//		assertEquals(250, account.getDataValue(MinMaxProduct.MAX_INCOME_PER_AREA).longValue());
	//		assertEquals(5, account.getDataValue(MinMaxProduct.MIN_SPENDINGS_PER_POP).longValue());
	//		assertEquals(40, account.getDataValue(MinMaxProduct.MAX_SPENDINGS_PER_POP).longValue());
	//		assertEquals(40, account.getDataValue(MinMaxProduct.MIN_SPENDINGS_PER_AREA).longValue());
	//		assertEquals(80, account.getDataValue(MinMaxProduct.MAX_SPENDINGS_PER_AREA).longValue());
	//		assertEquals(20, account.getDataValue(MinMaxProduct.MIN_POS_DIFF_PER_POP).longValue());
	//		assertEquals(20, account.getDataValue(MinMaxProduct.MAX_POS_DIFF_PER_POP).longValue());
	//		assertEquals(200, account.getDataValue(MinMaxProduct.MIN_POS_DIFF_PER_AREA).longValue());
	//		assertEquals(200, account.getDataValue(MinMaxProduct.MAX_POS_DIFF_PER_AREA).longValue());
	//		assertEquals(-10, account.getDataValue(MinMaxProduct.MIN_NEG_DIFF_PER_POP).longValue());
	//		assertEquals(-30, account.getDataValue(MinMaxProduct.MAX_NEG_DIFF_PER_POP).longValue());
	//		assertEquals(-20, account.getDataValue(MinMaxProduct.MIN_NEG_DIFF_PER_AREA).longValue());
	//		assertEquals(-60, account.getDataValue(MinMaxProduct.MAX_NEG_DIFF_PER_AREA).longValue());
	//	}
	//
	//	@Test
	//	public void itShouldCalculateDiffWithNullValues() throws Exception {
	//		MinMaxProduct account = new MinMaxProduct(0, null);
	//		account.addValue(Long.valueOf(123), null, apa1);
	//		account.addValue(null, Long.valueOf(32), apa2);
	//		assertEquals(123, account.getDataValue(MinMaxProduct.MIN_POS_DIFF).longValue());
	//		assertEquals(123, account.getDataValue(MinMaxProduct.MAX_POS_DIFF).longValue());
	//		assertEquals(-32, account.getDataValue(MinMaxProduct.MIN_NEG_DIFF).longValue());
	//		assertEquals(-32, account.getDataValue(MinMaxProduct.MAX_NEG_DIFF).longValue());
	//	}
}
