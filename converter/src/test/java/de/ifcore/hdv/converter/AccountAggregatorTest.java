package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Account;
import de.ifcore.hdv.converter.data.LongValue;
import de.ifcore.hdv.converter.mapreduce.AccountAggregator;

public class AccountAggregatorTest {

	@Test
	public void itShouldReduceAreaKeyForCountyCode() throws Exception {
		Collection<Account> accounts = new ArrayList<>();
		accounts.add(new Account("01002123", 10, "product1", 1, "name1", LongValue.valueOf(10l)));
		accounts.add(new Account("01002123", 10, "product1", 2, "name2", LongValue.valueOf(20l)));
		accounts.add(new Account("01002124", 10, "product1", 3, "name1", LongValue.valueOf(30l)));
		accounts.add(new Account("01002124", 10, "product1", 4, "name2", LongValue.valueOf(40l)));
		List<Account> aggregatedAccounts = AccountAggregator.reduceAreaKeyForCounty(accounts);
		assertNotNull(aggregatedAccounts);
		assertEquals(4, aggregatedAccounts.size());
		for (Account account : aggregatedAccounts) {
			assertEquals("01002", account.getAreaKey());
		}
	}
}
