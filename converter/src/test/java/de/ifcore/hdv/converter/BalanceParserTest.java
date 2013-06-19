package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import org.junit.Test;

import de.ifcore.hdv.converter.data.BalanceItem;

public class BalanceParserTest {

	@Test
	public void itShouldParseOneItem() throws Exception {
		BalanceItem item = new BalanceParser(null).parseItem(new String[] { "051110000", "1", "Duesseldorf", "0001",
				"Anlagevermoegen - AHK am 31.12. des Vorjahres", "11392961442" });
		assertEquals("0001", item.getNo());
		assertEquals("Anlagevermoegen - AHK am 31.12. des Vorjahres", item.getLabel());
		assertEquals(Long.valueOf(11392961442l), item.getValue());
	}
}
