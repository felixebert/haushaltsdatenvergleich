package de.ifcore.hdv.converter.parser;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Account;

public class AccountParserTest {

	@Test
	public void itShouldParseItem() throws Exception {
		AccountParser parser = mock(AccountParser.class);
		when(parser.parseItem(any(String[].class))).thenCallRealMethod();
		when(parser.isAreaKeyAcceptable(any(String.class))).thenReturn(Boolean.TRUE);
		Account account = parser.parseItem(new String[] { "05978040", "Werne, Stadt", "7", "Auszahlungen insgesamt",
				"611", "Steuern, allg. Zuweisungen u. allg. Umlagen", "18525219" });
		assertEquals("05978040", account.getAreaKey());
		assertEquals(7, account.getAccountKey());
		assertEquals("Auszahlungen insgesamt", account.getAccountName());
		assertEquals(611, account.getProductKey());
		assertEquals("Steuern, allg. Zuweisungen u. allg. Umlagen", account.getProductName());
		assertEquals(Long.valueOf(18525219), account.getValue());
	}
}
