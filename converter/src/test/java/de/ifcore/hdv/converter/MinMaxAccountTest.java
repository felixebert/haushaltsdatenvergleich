package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import org.junit.Test;

public class MinMaxAccountTest {

	@Test
	public void itShouldTolerateNulls() throws Exception {
		MinMaxAccount account = new MinMaxAccount(0, null);
		account.addValue(null, null);
		assertNull(account.getIMin());
		assertNull(account.getIMax());
		assertNull(account.getSMin());
		assertNull(account.getSMax());
		assertNull(account.getDMin());
		assertNull(account.getDMax());
	}

	@Test
	public void itShouldCalculateMinMax() throws Exception {
		MinMaxAccount account = new MinMaxAccount(0, null);
		account.addValue(Long.valueOf(123), Long.valueOf(432));
		account.addValue(Long.valueOf(512), Long.valueOf(32));
		assertEquals(123, account.getIMin().longValue());
		assertEquals(512, account.getIMax().longValue());
		assertEquals(32, account.getSMin().longValue());
		assertEquals(432, account.getSMax().longValue());
		assertEquals(123 - 432, account.getDMin().longValue());
		assertEquals(512 - 32, account.getDMax().longValue());
	}

	@Test
	public void itShouldCalculateDiffWithNullValues() throws Exception {
		MinMaxAccount account = new MinMaxAccount(0, null);
		account.addValue(Long.valueOf(123), null);
		account.addValue(null, Long.valueOf(32));
		assertEquals(-32, account.getDMin().longValue());
		assertEquals(123, account.getDMax().longValue());
	}
}
