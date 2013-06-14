package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Account;
import de.ifcore.hdv.converter.mapreduce.AccountAggregator;

public class AccountAggregatorTest {

	@Test
	public void itShouldAggregateAccountsByCountyCode() throws Exception {
		Collection<Account> accounts = new ArrayList<>();
		accounts.add(new Account("01002123", 1, "name1", 10l));
		accounts.add(new Account("01002123", 2, "name2", 20l));
		accounts.add(new Account("01002124", 1, "name1", 30l));
		accounts.add(new Account("01002124", 2, "name2", 40l));
		List<Account> aggregatedAccounts = AccountAggregator.aggregate(accounts);
		assertNotNull(aggregatedAccounts);
		assertEquals(2, aggregatedAccounts.size());
		Account account = aggregatedAccounts.get(0);
		assertEquals("01002", account.getAreaKey());
	}

	@Test
	public void itShouldAggragteTwoAccounts() throws Exception {
		String countyKey = "01234";
		Account a1 = new Account(null, 123, "name123", 10l);
		Account a2 = new Account(null, 123, "name123", 30l);
		Account aNullValue = new Account(null, 123, "name123", null);
		assertNull(AccountAggregator.aggragteAccounts(null, null, null));
		assertEqualAccounts(countyKey, 123, "name123", 30l, AccountAggregator.aggragteAccounts(countyKey, null, a2));
		assertEqualAccounts(countyKey, 123, "name123", 10l, AccountAggregator.aggragteAccounts(countyKey, a1, null));
		assertEqualAccounts(countyKey, 123, "name123", 40l, AccountAggregator.aggragteAccounts(countyKey, a1, a2));
		assertEqualAccounts(countyKey, 123, "name123", null,
				AccountAggregator.aggragteAccounts(countyKey, aNullValue, aNullValue));
		assertEqualAccounts(countyKey, 123, "name123", 10l,
				AccountAggregator.aggragteAccounts(countyKey, aNullValue, a1));
		assertEqualAccounts(countyKey, 123, "name123", 30l,
				AccountAggregator.aggragteAccounts(countyKey, aNullValue, a2));
	}

	private void assertEqualAccounts(String countyKey, int key, String name, Long value, Account account) {
		assertEquals(countyKey, account.getAreaKey());
		assertEquals(key, account.getAccountKey());
		assertEquals(name, account.getAccountName());
		assertEquals(value, account.getValue());
	}
}
