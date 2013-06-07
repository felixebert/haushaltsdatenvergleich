package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Account;
import de.ifcore.hdv.converter.data.AccountsPerArea;
import de.ifcore.hdv.converter.data.MergedData;
import de.ifcore.hdv.converter.data.Population;

public class DataMergerTest {

	private static final String AREA_KEY = "12345678";

	@Test
	public void itShouldMergeData() throws Exception {
		DataMerger dataMerger = new DataMerger();
		Map<String, Double> areaSizes = new HashMap<>();
		areaSizes.put(AREA_KEY, Double.valueOf(12345.67));
		Map<String, Population> population = new HashMap<>();
		population.put(AREA_KEY, new Population("Test Area", 1234));
		List<Account> income = Arrays.asList(new Account(AREA_KEY, 123, "Testaccount", Long.valueOf(100)), new Account(
				AREA_KEY, 124, "Testaccount2", Long.valueOf(200)));
		List<Account> spendings = Arrays.asList(new Account(AREA_KEY, 123, "Testaccount", Long.valueOf(100)),
				new Account(AREA_KEY, 124, "Testaccount2", Long.valueOf(200)));
		MergedData mergeData = dataMerger.mergeData(population, areaSizes, income, spendings);
		List<AccountsPerArea> result = mergeData.getAreas();
		assertNotNull(result);
		AccountsPerArea accountsPerArea = result.get(0);
		assertEquals(AREA_KEY, accountsPerArea.getKey());
		assertEquals("Test Area", accountsPerArea.getName());
		assertEquals(12345.67, accountsPerArea.getSize(), 0.001);
		assertEquals(1234, accountsPerArea.getPopulation());
		assertFalse(accountsPerArea.getAccounts().isEmpty());
		assertAccount(123, 100, 100, accountsPerArea.getAccounts());
		assertAccount(124, 200, 200, accountsPerArea.getAccounts());
		assertEquals("Testaccount", mergeData.getAccounts().get(123).getLabel());
		assertEquals("Testaccount2", mergeData.getAccounts().get(124).getLabel());
	}

	private static void assertAccount(int expectedKey, long expectedIn, long expectedOut, Map<Integer, Long[]> accounts) {
		Long[] is = accounts.get(expectedKey);
		assertEquals(expectedIn, is[0].longValue());
		assertEquals(expectedOut, is[1].longValue());
	}
}
