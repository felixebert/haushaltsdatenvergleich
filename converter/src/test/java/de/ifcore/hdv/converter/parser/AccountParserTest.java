package de.ifcore.hdv.converter.parser;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Account;

public class AccountParserTest {

	@Test
	public void itShouldParseItem() throws Exception {
		AccountParser parser = mock(AccountParser.class);
		when(parser.parseItem(any(String[].class))).thenCallRealMethod();
		when(parser.isAreaKeyAcceptable(any(String.class))).thenReturn(Boolean.TRUE);
		when(parser.getColumnDefinitions()).thenReturn(
				Arrays.asList(new ColumnDefinition(611, "Steuern, allg. Zuweisungen u. allg. Umlagen"),
						new ColumnDefinition(111, "Verwaltungssteuerung und Service")));
		List<Account> accounts = parser.parseItem(new String[] { "2011", "05978040", "Werne, Stadt", "7",
				"Auszahlungen insgesamt", "1", "2" });
		Account account = accounts.get(0);
		assertEquals("05978040", account.getAreaKey());
		assertEquals(7, account.getAccountKey());
		assertEquals("Auszahlungen insgesamt", account.getAccountName());
		assertEquals(611, account.getProductKey());
		assertEquals("Steuern, allg. Zuweisungen u. allg. Umlagen", account.getProductName());
		assertEquals(Long.valueOf(1), account.getValue().getValue());
		account = accounts.get(1);
		assertEquals("05978040", account.getAreaKey());
		assertEquals(7, account.getAccountKey());
		assertEquals("Auszahlungen insgesamt", account.getAccountName());
		assertEquals(111, account.getProductKey());
		assertEquals("Verwaltungssteuerung und Service", account.getProductName());
		assertEquals(Long.valueOf(2), account.getValue().getValue());
	}
}
