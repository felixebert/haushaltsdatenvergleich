package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import de.ifcore.hdv.converter.data.BalanceItem;
import de.ifcore.hdv.converter.utils.ResourceUtils;
import de.ifcore.hdv.converter.utils.Utils;

public class AbstractCsvParserTest {

	@Test
	public void itShouldParseAllItems() throws Exception {
		BalanceParser parser = new BalanceParser(Utils.readCsvFile(ResourceUtils.getResourceAsStream("bilanz.csv")));
		List<BalanceItem> list = parser.parse();
		assertEquals(2, list.size());
	}
}
