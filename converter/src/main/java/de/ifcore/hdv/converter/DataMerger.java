package de.ifcore.hdv.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import de.ifcore.hdv.converter.data.Account;
import de.ifcore.hdv.converter.data.AccountsPerArea;
import de.ifcore.hdv.converter.data.Category;
import de.ifcore.hdv.converter.data.CategoryTree;
import de.ifcore.hdv.converter.data.InOutAccount;
import de.ifcore.hdv.converter.data.MergedData;
import de.ifcore.hdv.converter.data.Population;

public class DataMerger {

	private Map<Integer, MinMaxAccount> accountMap = new HashMap<>();
	private CategoryTree tree;

	public MergedData mergeData(Map<String, Population> populationMap, Map<String, Double> areaSizes,
			List<Account> income, List<Account> spendings) {
		List<AccountsPerArea> result = new ArrayList<AccountsPerArea>();
		Set<String> areaKeys = createAreaKeys(income, spendings);
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
				System.out.println("Keine Fläche für " + areaKey + " / " + population.getAreaName());
			}
			else {
				Collection<InOutAccount> accountValues = inOutMap.values();
				Map<Integer, Long[]> accountValuesMap = convertToMap(accountValues);
				processMinMax(accountValues);
				AccountsPerArea accountsPerArea = new AccountsPerArea(areaKey, population.getAreaName(),
						population.getPopulation(), areaSize.doubleValue(), accountValuesMap);
				result.add(accountsPerArea);
			}
		}
		return new MergedData(result, accountMap, tree.getTree());
	}

	private void createCategoryMap(List<Account> income, List<Account> spendings) {
		collectAccounts(income);
		collectAccounts(spendings);
		tree = CategoryMerger.createTree(MainCategories.getInstance().getCategories(), accountMap.values());
		injectMainCategoriesIntoAccountMap(MainCategories.getInstance().getCategories());
	}

	private void injectMainCategoriesIntoAccountMap(SortedSet<Category> categories) {
		for (Category category : categories) {
			accountMap.put(category.getKey(), new MinMaxAccount(category.getKey(), category.getLabel()));
		}
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

	private void processMinMax(Collection<InOutAccount> accountValues) {
		for (InOutAccount inOutAccount : accountValues) {
			MinMaxAccount minMaxAccount = accountMap.get(inOutAccount.getKey());
			minMaxAccount.addValue(inOutAccount.getIncome(), inOutAccount.getSpending());
		}
	}

	private void processIncomeForArea(String areaKey, Map<Integer, InOutAccount> inOutMap, List<Account> income) {
		for (Account account : income) {
			if (areaKey.equals(account.getAreaKey())) {
				int accountKey = account.getAccountKey();
				InOutAccount inOutAccount = inOutMap.get(accountKey);
				Long value = account.getValue();
				if (inOutAccount == null) {
					inOutAccount = new InOutAccount(accountKey, value, null);
					inOutMap.put(accountKey, inOutAccount);
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
				int accountKey = account.getAccountKey();
				InOutAccount inOutAccount = inOutMap.get(accountKey);
				Long value = account.getValue();
				if (inOutAccount == null) {
					inOutAccount = new InOutAccount(accountKey, null, value);
					inOutMap.put(accountKey, inOutAccount);
				}
				else {
					inOutAccount.incSpendings(value);
				}
			}
		}
	}

	private Set<String> createAreaKeys(List<Account> income, List<Account> spendings) {
		Set<String> areaKeys = new HashSet<String>();
		collectAreaKeys(areaKeys, income);
		collectAreaKeys(areaKeys, spendings);
		return areaKeys;
	}

	private void collectAreaKeys(Set<String> areaKeys, Collection<Account> accounts) {
		for (Account account : accounts) {
			areaKeys.add(account.getAreaKey());
		}
	}

	private void collectAccounts(Collection<Account> accounts) {
		for (Account account : accounts) {
			accountMap.put(account.getAccountKey(),
					new MinMaxAccount(account.getAccountKey(), account.getAccountName()));
		}
	}
}
