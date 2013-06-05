package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import org.junit.Test;

import de.ifcore.hdv.converter.data.InOutAccount;

public class InOutAccountTest {

	@Test
	public void itShouldAddToNull() throws Exception {
		InOutAccount account = new InOutAccount(null, null, null);
		account.incIncome(100L);
		account.incSpendings(200L);
		assertEquals(100L, account.getIn().longValue());
		assertEquals(200L, account.getOut().longValue());
	}

	@Test
	public void itShouldAddNull() throws Exception {
		InOutAccount account = new InOutAccount(null, Long.valueOf(100), Long.valueOf(200));
		account.incIncome(null);
		account.incSpendings(null);
		assertEquals(100L, account.getIn().longValue());
		assertEquals(200L, account.getOut().longValue());
	}
}
