package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import de.ifcore.hdv.converter.data.BalanceItem;
import de.ifcore.hdv.converter.data.BalanceSheet;

public class BalanceConverterTest {

	private BalanceConverter converter = new BalanceConverter(mockItems());

	@Test
	public void itShouldCollectAllKs() throws Exception {
		Set<String> ksList = converter.collectAllKs();
		assertNotNull(ksList);
		assertTrue(ksList.contains("051110001"));
		assertTrue(ksList.contains("051110002"));
		assertTrue(ksList.contains("051110003"));
	}

	@Test
	public void itShouldReturnOnlyItemsWithGivenKs() throws Exception {
		List<BalanceItem> list = converter.getItemsProKs("051110001");
		assertFalse(list.isEmpty());
		for (BalanceItem balanceItem : list) {
			assertTrue(balanceItem.getNo().equals("0001"));
		}
	}

	@Test
	public void itShouldCreateABalanceSheet() throws Exception {
		BalanceSheet sheet = converter.createBalanceSheet("051110001");
	}

	private static List<BalanceItem> mockItems() {
		return Arrays.asList(new BalanceItem("051110001", "0001", "test1", Long.valueOf(1)), new BalanceItem(
				"051110002", "0002", "test2", Long.valueOf(2)),
				new BalanceItem("051110003", "0003", "test3", Long.valueOf(3)));
	}
}
