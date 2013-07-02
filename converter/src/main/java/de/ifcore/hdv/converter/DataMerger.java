package de.ifcore.hdv.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.ifcore.hdv.converter.data.Account;
import de.ifcore.hdv.converter.data.AccountValue;
import de.ifcore.hdv.converter.data.AccountsPerArea;
import de.ifcore.hdv.converter.data.Category;
import de.ifcore.hdv.converter.data.CategoryTree;
import de.ifcore.hdv.converter.data.InOutProduct;
import de.ifcore.hdv.converter.data.MergedData;
import de.ifcore.hdv.converter.data.Population;
import de.ifcore.hdv.converter.utils.AreaUtils;

public class DataMerger {

	private Map<Integer, MinMaxProduct> productMap = new HashMap<>();
	private CategoryTree tree;
	private AccountLabels productLabels = new AccountLabels();
	private AccountLabels incomeLabels = new AccountLabels();
	private AccountLabels spendingsLabels = new AccountLabels();

	public MergedData mergeData(Map<String, Population> populationMap, Map<String, Double> areaSizes,
			List<Account> income, List<Account> spendings) {
		List<AccountsPerArea> result = new ArrayList<AccountsPerArea>();
		Set<String> areaKeys = AreaUtils.createUniqueAreaKeys(income, spendings);
		createCategoryMap(income, spendings);
		for (String areaKey : areaKeys) {
			Population population = populationMap.get(areaKey);
			Double areaSize = areaSizes.get(areaKey);
			Map<Integer, InOutProduct> inOutMap = new HashMap<>();
			processDataForArea(areaKey, inOutMap, income);
			processDataForArea(areaKey, inOutMap, spendings);
			if (population == null) {
				System.out.println("Keine Einwohnerzahlen für " + areaKey);
			}
			else if (areaSize == null) {
				System.out.println("Keine Fläche für " + areaKey);
			}
			else {
				Collection<InOutProduct> accountValues = inOutMap.values();
				Map<Integer, InOutProduct> accountValuesMap = convertToMap(accountValues);
				AccountsPerArea accountsPerArea = new AccountsPerArea(areaKey, population.getPopulation(),
						areaSize.doubleValue(), accountValuesMap);
				processMinMax(accountValues, accountsPerArea);
				result.add(accountsPerArea);
			}
		}
		filterNullValuesInProducts();
		return new MergedData(result, productMap, tree.getTree(), productLabels.asSortedMap(),
				incomeLabels.asSortedMap(), spendingsLabels.asSortedMap());
	}

	private void filterNullValuesInProducts() {
		for (MinMaxProduct product : productMap.values()) {
			product.filterNullValues();
		}
	}

	private void createCategoryMap(List<Account> income, List<Account> spendings) {
		collectProductsIntoProductMap(income, true);
		collectProductsIntoProductMap(spendings, false);
		tree = CategoryMerger.createTree(MainCategories.getInstance().getCategories(), productMap.values());
		for (Category category : MainCategories.getInstance().getCategories()) {
			productLabels.add(category.getKey(), category.getLabel());
		}
	}

	private void collectProductsIntoProductMap(Collection<Account> accounts, boolean income) {
		for (Account account : accounts) {
			productLabels.add(account.getProductKey(), account.getProductName());
			if (income)
				incomeLabels.add(account.getAccountKey(), account.getAccountName());
			else
				spendingsLabels.add(account.getAccountKey(), account.getAccountName());
			MinMaxProduct product = productMap.get(account.getProductKey());
			if (product == null) {
				product = new MinMaxProduct(account.getProductKey(), account.getProductName());
				productMap.put(account.getProductKey(), product);
			}
			product.addAccount(account.getAccountKey(), account.getAccountName());
		}
	}

	private Map<Integer, InOutProduct> convertToMap(Collection<InOutProduct> accountValues) {
		Map<Integer, InOutProduct> result = new HashMap<>();
		for (InOutProduct inOutProduct : accountValues) {
			inOutProduct.filterNullValues();
			if (hasValue(inOutProduct)) {
				result.put(inOutProduct.getProductKey(), inOutProduct);
			}
		}
		return result;
	}

	private boolean hasValue(InOutProduct inOutProduct) {
		return !inOutProduct.getAccounts().isEmpty();
	}

	private void processMinMax(Collection<InOutProduct> accountValues, AccountsPerArea accountsPerArea) {
		for (InOutProduct inOutAccount : accountValues) {
			MinMaxProduct minMaxAccount = productMap.get(inOutAccount.getProductKey());
			for (AccountValue accountValue : inOutAccount.getAccounts().values()) {
				minMaxAccount.addValue(accountValue.getKey(), accountValue.getValue(), accountsPerArea);
			}
		}
	}

	private void processDataForArea(String areaKey, Map<Integer, InOutProduct> inOutMap, List<Account> income) {
		for (Account account : income) {
			if (areaKey.equals(account.getAreaKey())) {
				InOutProduct inOut = getInOutProduct(inOutMap, account);
				inOut.setValue(account);
			}
		}
	}

	private InOutProduct getInOutProduct(Map<Integer, InOutProduct> inOutMap, Account account) {
		int productKey = account.getProductKey();
		InOutProduct inOut = inOutMap.get(productKey);
		if (inOut == null) {
			inOut = new InOutProduct(productKey);
			inOutMap.put(productKey, inOut);
		}
		return inOut;
	}
}
