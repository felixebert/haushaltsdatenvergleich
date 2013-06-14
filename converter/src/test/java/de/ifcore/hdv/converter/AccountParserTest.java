package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Account;
import de.ifcore.hdv.converter.parser.AccountParser;

public class AccountParserTest {

	@Test
	public void itShouldParseDataAsList() throws Exception {
		AccountParser parser = new AccountParser(ResourceUtils.getResourceAsStream("testdata.csv"));
		List<Account> result = parser.parse();
		assertNotNull(result);
		assertFalse(result.isEmpty());
		Account account = result.get(0);
		assertEquals("05978040", account.getAreaKey());
		assertEquals(611, account.getAccountKey());
		assertEquals("Steuern, allg. Zuweisungen u. allg. Umlagen", account.getAccountName());
		assertEquals(Long.valueOf(18525219), account.getValue());
		assertEquals(3, result.size());
	}
}
