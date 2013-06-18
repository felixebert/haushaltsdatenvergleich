package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import de.ifcore.hdv.converter.data.BalanceItem;
import de.ifcore.hdv.converter.utils.ResourceUtils;

public class BalanceParserTest {

	private BalanceParser parser = new BalanceParser(ResourceUtils.getResourceAsStream("bilanz.csv"));

	@Test
	public void itShouldParseOneItem() throws Exception {
		BalanceItem item = parser.parseItem(new String[] { "051110000", "1", "Duesseldorf", "0001",
				"Anlagevermoegen - AHK am 31.12. des Vorjahres", "11392961442" });
		assertEquals("0001", item.getNo());
		assertEquals("Anlagevermoegen - AHK am 31.12. des Vorjahres", item.getLabel());
		assertEquals(Long.valueOf(11392961442l), item.getValue());
	}

	@Test
	public void itShouldParseAllItems() throws Exception {
		List<BalanceItem> list = parser.parse();
		assertEquals(2, list.size());
	}
}
