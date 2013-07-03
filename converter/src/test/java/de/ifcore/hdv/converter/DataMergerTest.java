package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Account;
import de.ifcore.hdv.converter.data.AccountDataAndLabels;
import de.ifcore.hdv.converter.data.AccountValue;
import de.ifcore.hdv.converter.data.AccountsPerArea;
import de.ifcore.hdv.converter.data.InOutProduct;
import de.ifcore.hdv.converter.data.LongValue;
import de.ifcore.hdv.converter.data.MergedData;
import de.ifcore.hdv.converter.data.Population;

public class DataMergerTest {

	private static final String AREA_KEY = "12345678";
	private static final String AREA_LABEL = "Stadt";

	@Test
	public void itShouldMergeData() throws Exception {
		DataMerger dataMerger = new DataMerger();
		Map<String, Double> areaSizes = new HashMap<>();
		areaSizes.put(AREA_KEY, Double.valueOf(12345.67));
		Map<String, Population> population = new HashMap<>();
		population.put(AREA_KEY, new Population(1234));
		List<Account> incomeList = new ArrayList<>(Arrays.asList(new Account(AREA_KEY, AREA_LABEL, 223, "Testproduct",
				123, "Testaccount", LongValue.valueOf(100)), new Account(AREA_KEY, AREA_LABEL, 224, "Testproduct2",
				124, "Testaccount2", LongValue.valueOf(200))));
		List<Account> spendingsList = new ArrayList<>(Arrays.asList(new Account(AREA_KEY, AREA_LABEL, 223,
				"Testproduct", 123, "Testaccount", LongValue.valueOf(100)), new Account(AREA_KEY, AREA_LABEL, 224,
				"Testproduct2", 124, "Testaccount2", LongValue.valueOf(200))));
		AccountDataAndLabels income = new AccountDataAndLabels(incomeList, new AccountLabels());
		AccountDataAndLabels spendings = new AccountDataAndLabels(spendingsList, new AccountLabels());
		MergedData mergedData = dataMerger.mergeData(population, areaSizes, income, spendings);
		List<AccountsPerArea> result = mergedData.getAreas();
		assertNotNull(result);
		AccountsPerArea accountsPerArea = result.get(0);
		assertEquals(AREA_KEY, accountsPerArea.getKey());
		assertEquals(AREA_LABEL, accountsPerArea.getLabel());
		assertEquals(12345.67, accountsPerArea.getSize(), 0.001);
		assertEquals(1234, accountsPerArea.getPopulation());
		assertFalse(accountsPerArea.getProducts().isEmpty());
		assertAccount(223, 123, 100, accountsPerArea.getProducts());
		assertAccount(224, 124, 200, accountsPerArea.getProducts());
	}

	private static void assertAccount(int expectedProductKey, int expectedAccountKey, long expectedValue,
			Map<Integer, InOutProduct> accounts) {
		InOutProduct product = accounts.get(expectedProductKey);
		AccountValue accountValue = product.getAccounts().get(expectedAccountKey);
		assertEquals(expectedValue, accountValue.getValue().getValue().longValue());
	}
}
