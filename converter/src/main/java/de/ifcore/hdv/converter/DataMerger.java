package de.ifcore.hdv.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.ifcore.hdv.converter.data.Account;
import de.ifcore.hdv.converter.data.AccountDataAndLabels;
import de.ifcore.hdv.converter.data.AccountValue;
import de.ifcore.hdv.converter.data.AccountsPerArea;
import de.ifcore.hdv.converter.data.AreaLabel;
import de.ifcore.hdv.converter.data.Category;
import de.ifcore.hdv.converter.data.CategoryTree;
import de.ifcore.hdv.converter.data.InOutProduct;
import de.ifcore.hdv.converter.data.MergedData;
import de.ifcore.hdv.converter.data.Population;
import de.ifcore.hdv.converter.utils.AreaUtils;

public class DataMerger {

	private Map<Integer, MinMaxProduct> productMap = new HashMap<>(10000);
	private CategoryTree tree;
	private AccountLabels productLabels = new AccountLabels();

	public MergedData mergeData(Map<String, Population> populationMap, Map<String, Double> areaSizes,
			AccountDataAndLabels income, AccountDataAndLabels spendings) {
		System.out.println("Elemente in Einnahmen: " + income.getAccounts().size() + ", in Ausgaben: "
				+ spendings.getAccounts().size());
		List<AccountsPerArea> result = new ArrayList<AccountsPerArea>();
		long time = System.currentTimeMillis();
		Set<AreaLabel> areaKeys = AreaUtils.createUniqueAreaKeys(income.getAccounts(), spendings.getAccounts());
		System.out.println("uniqueAreaKeys: " + (System.currentTimeMillis() - time) + " ms");
		time = System.currentTimeMillis();
		createCategoryMap(income.getAccounts(), spendings.getAccounts());
		System.out.println("categoryMap: " + (System.currentTimeMillis() - time) + " ms");
		long processData = 0;
		for (AreaLabel areaLabel : areaKeys) {
			long inLoop = System.currentTimeMillis();
			Population population = populationMap.get(areaLabel.getKey());
			Double areaSize = areaSizes.get(areaLabel.getKey());
			Map<Integer, InOutProduct> inOutMap = new HashMap<>();
			processDataForArea(areaLabel.getKey(), inOutMap, income.getAccounts());
			processDataForArea(areaLabel.getKey(), inOutMap, spendings.getAccounts());
			processData += (System.currentTimeMillis() - inLoop);
			if (population == null) {
				System.out.println("Keine Einwohnerzahlen für " + areaLabel.getKey());
			}
			else if (areaSize == null) {
				System.out.println("Keine Fläche für " + areaLabel.getKey());
			}
			else {
				Collection<InOutProduct> accountValues = inOutMap.values();
				Map<Integer, InOutProduct> accountValuesMap = convertToMap(accountValues);
				AccountsPerArea accountsPerArea = new AccountsPerArea(areaLabel.getKey(), areaLabel.getLabel(),
						population.getPopulation(), areaSize.doubleValue(), accountValuesMap);
				processMinMax(accountValues, accountsPerArea);
				result.add(accountsPerArea);
			}
		}
		System.out.println("processDataForArea: " + processData + " ms");
		filterNullValuesInProducts();
		return new MergedData(result, productMap, tree.getTree(), productLabels.getKeyAccounts(), income
				.getAccountLabels().getKeyAccounts(), spendings.getAccountLabels().getKeyAccounts());
	}

	private void filterNullValuesInProducts() {
		for (MinMaxProduct product : productMap.values()) {
			product.filterNullValues();
		}
	}

	private void createCategoryMap(List<Account> income, List<Account> spendings) {
		long time = System.currentTimeMillis();
		collectProductsIntoProductMap(income);
		System.out.println("categoryMap income: " + (System.currentTimeMillis() - time) + " ms");
		time = System.currentTimeMillis();
		collectProductsIntoProductMap(spendings);
		System.out.println("categoryMap spendings: " + (System.currentTimeMillis() - time) + " ms");
		time = System.currentTimeMillis();
		tree = CategoryMerger.createTree(MainCategories.getInstance().getCategories(), productMap.values());
		System.out.println("categoryMap createTree: " + (System.currentTimeMillis() - time) + " ms");
		time = System.currentTimeMillis();
		for (Category category : MainCategories.getInstance().getCategories()) {
			productLabels.add(category.getKey(), category.getLabel());
		}
		System.out.println("categoryMap productLabel: " + (System.currentTimeMillis() - time) + " ms");
	}

	private void collectProductsIntoProductMap(Collection<Account> accounts) {
		for (Account account : accounts) {
			productLabels.add(account.getProductKey(), account.getProductName());
			addAccountToProductMap(account);
		}
	}

	private void addAccountToProductMap(Account account) {
		Integer accountProductKey = account.getProductKey();
		MinMaxProduct product = productMap.get(accountProductKey);
		if (product == null) {
			product = new MinMaxProduct(account.getProductKey(), account.getProductName());
			productMap.put(accountProductKey, product);
		}
		product.addAccount(account.getAccountKey(), account.getAccountName());
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

	private void processDataForArea(String areaKey, Map<Integer, InOutProduct> inOutMap, List<Account> accountList) {
		for (Iterator<Account> it = accountList.iterator(); it.hasNext();) {
			Account account = it.next();
			if (areaKey.equals(account.getAreaKey())) {
				InOutProduct inOut = getInOutProduct(inOutMap, account);
				inOut.setValue(account);
				it.remove();
			}
		}
	}

	private InOutProduct getInOutProduct(Map<Integer, InOutProduct> inOutMap, Account account) {
		Integer productKey = Integer.valueOf(account.getProductKey());
		InOutProduct inOut = inOutMap.get(productKey);
		if (inOut == null) {
			inOut = new InOutProduct(productKey);
			inOutMap.put(productKey, inOut);
		}
		return inOut;
	}
}
