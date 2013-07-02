package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import de.ifcore.hdv.converter.data.BalanceItem;

public class BalanceParserTest {

	@Test
	public void itShouldParseOneItem() throws Exception {
		List<BalanceItem> items = new BalanceParser(null).parseItem(new String[] { "123456780", "1", "Duesseldorf",
				"0001", "Anlagevermoegen - AHK am 31.12. des Vorjahres", "11392961442" });
		BalanceItem item = items.get(0);
		assertEquals("12345678", item.getKs());
		assertEquals("Duesseldorf", item.getAreaLabel());
		assertEquals("0001", item.getPrevious().getNo());
		assertEquals("Anlagevermoegen - AHK am 31.12.", item.getLabel());
		assertEquals(Long.valueOf(11392961442l), item.getPrevious().getValue());
	}

	@Test
	public void itShouldParseCurrentYearOneItem() throws Exception {
		List<BalanceItem> items = new BalanceParser(null).parseItem(new String[] { "123456780", "1", "Duesseldorf",
				"0001", "Anlagevermoegen - AHK am 31.12. des Haushaltsjahres", "11392961442" });
		BalanceItem item = items.get(0);
		assertEquals("12345678", item.getKs());
		assertEquals("Duesseldorf", item.getAreaLabel());
		assertEquals("0001", item.getCurrent().getNo());
		assertEquals("Anlagevermoegen - AHK am 31.12.", item.getLabel());
		assertEquals(Long.valueOf(11392961442l), item.getCurrent().getValue());
	}
}
