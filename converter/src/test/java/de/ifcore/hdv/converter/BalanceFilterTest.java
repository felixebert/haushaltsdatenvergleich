package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import de.ifcore.hdv.converter.data.BalanceItem;

public class BalanceFilterTest {

	private BalanceFilter filter = new BalanceFilter(DataMocks.mockBalanceItems());

	@Test
	public void itShouldCollectAllKs() throws Exception {
		Set<String> ksList = filter.collectAllKs();
		assertNotNull(ksList);
		assertTrue(ksList.contains("051110001"));
		assertTrue(ksList.contains("051110002"));
		assertTrue(ksList.contains("051110003"));
	}

	@Test
	public void itShouldReturnOnlyItemsWithGivenKs() throws Exception {
		List<BalanceItem> list = filter.getItemsProKs("051110001");
		assertFalse(list.isEmpty());
		for (BalanceItem balanceItem : list) {
			assertTrue(balanceItem.getNo().equals("0100"));
		}
	}
}
