package de.ifcore.hdv.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.ifcore.hdv.converter.data.Account;
import de.ifcore.hdv.converter.data.AccountsPerArea;
import de.ifcore.hdv.converter.data.CategoryTree;
import de.ifcore.hdv.converter.data.InOutAccount;
import de.ifcore.hdv.converter.data.MergedData;
import de.ifcore.hdv.converter.data.Population;
import de.ifcore.hdv.converter.utils.AreaUtils;

public class DataMerger {

	private Map<Integer, MinMaxAccount> productMap = new HashMap<>();
	private CategoryTree tree;

	public MergedData mergeData(Map<String, Population> populationMap, Map<String, Double> areaSizes,
			List<Account> income, List<Account> spendings) {
		List<AccountsPerArea> result = new ArrayList<AccountsPerArea>();
		Set<String> areaKeys = AreaUtils.createUniqueAreaKeys(income, spendings);
		createCategoryMap(income, spendings);
		for (String areaKey : areaKeys) {
			Population population = populationMap.get(areaKey);
			Double areaSize = areaSizes.get(areaKey);
			Map<Integer, InOutAccount> inOutMap = new HashMap<>();
			processIncomeForArea(areaKey, inOutMap, income);
			processSpendingsForArea(areaKey, inOutMap, spendings);
			if (population == null) {
				System.out.println("Keine Einwohnerzahlen für " + areaKey);
			}
			else if (areaSize == null) {
				System.out.println("Keine Fläche für " + areaKey);
			}
			else {
				Collection<InOutAccount> accountValues = inOutMap.values();
				Map<Integer, Long[]> accountValuesMap = convertToMap(accountValues);
				AccountsPerArea accountsPerArea = new AccountsPerArea(areaKey, population.getPopulation(),
						areaSize.doubleValue(), accountValuesMap);
				processMinMax(accountValues, accountsPerArea);
				result.add(accountsPerArea);
			}
		}
		return new MergedData(result, productMap, tree.getTree());
	}

	private void createCategoryMap(List<Account> income, List<Account> spendings) {
		collectAccountsIntoAccountMap(income);
		collectAccountsIntoAccountMap(spendings);
		tree = CategoryMerger.createTree(MainCategories.getInstance().getCategories(), productMap.values());
	}

	private Map<Integer, Long[]> convertToMap(Collection<InOutAccount> accountValues) {
		Map<Integer, Long[]> result = new HashMap<>();
		for (InOutAccount inOutAccount : accountValues) {
			if (inOutAccount.getIncome() != null || inOutAccount.getSpending() != null) {
				result.put(inOutAccount.getKey(), new Long[] { inOutAccount.getIncome(), inOutAccount.getSpending() });
			}
		}
		return result;
	}

	private void processMinMax(Collection<InOutAccount> accountValues, AccountsPerArea accountsPerArea) {
		for (InOutAccount inOutAccount : accountValues) {
			MinMaxAccount minMaxAccount = productMap.get(inOutAccount.getKey());
			minMaxAccount.addValue(inOutAccount.getIncome(), inOutAccount.getSpending(), accountsPerArea);
		}
	}

	private void processIncomeForArea(String areaKey, Map<Integer, InOutAccount> inOutMap, List<Account> income) {
		for (Account account : income) {
			if (areaKey.equals(account.getAreaKey())) {
				int productKey = account.getProductKey();
				InOutAccount inOutAccount = inOutMap.get(productKey);
				Long value = account.getValue();
				if (inOutAccount == null) {
					inOutAccount = new InOutAccount(productKey, value, null);
					inOutMap.put(productKey, inOutAccount);
				}
				else {
					inOutAccount.incIncome(value);
				}
			}
		}
	}

	private void processSpendingsForArea(String areaKey, Map<Integer, InOutAccount> inOutMap, List<Account> spendings) {
		for (Account account : spendings) {
			if (areaKey.equals(account.getAreaKey())) {
				int productKey = account.getProductKey();
				InOutAccount inOutAccount = inOutMap.get(productKey);
				Long value = account.getValue();
				if (inOutAccount == null) {
					inOutAccount = new InOutAccount(productKey, null, value);
					inOutMap.put(productKey, inOutAccount);
				}
				else {
					inOutAccount.incSpendings(value);
				}
			}
		}
	}

	private void collectAccountsIntoAccountMap(Collection<Account> accounts) {
		for (Account account : accounts) {
			productMap.put(account.getProductKey(),
					new MinMaxAccount(account.getProductKey(), account.getProductName()));
		}
	}
}
