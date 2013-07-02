package de.ifcore.hdv.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import de.ifcore.hdv.converter.data.AccountClass;
import de.ifcore.hdv.converter.data.BalanceItem;
import de.ifcore.hdv.converter.data.BalanceSheet;

public class BalanceSheetBuilder {

	private Map<String, Map<String, List<BalanceItem>>> groupedItems = new LinkedHashMap<>();
	private Map<String, AccountClass> mainClassLookup = new HashMap<>();
	private Map<String, AccountClass> subClassLookup = new HashMap<>();
	private SortedSet<AccountClass> mainAccountClasses;

	public BalanceSheetBuilder(SortedSet<AccountClass> mainAccountClasses, SortedSet<AccountClass> subAccountClasses) {
		this.mainAccountClasses = mainAccountClasses;
		for (AccountClass mainAccountClass : mainAccountClasses) {
			mainClassLookup.put(mainAccountClass.getPrefix(), mainAccountClass);
			Map<String, List<BalanceItem>> subClasses = new LinkedHashMap<String, List<BalanceItem>>();
			for (AccountClass subAccountClass : subAccountClasses) {
				subClasses.put(subAccountClass.getLabel(), new ArrayList<BalanceItem>());
				subClassLookup.put(subAccountClass.getPrefix(), subAccountClass);
			}
			groupedItems.put(mainAccountClass.getLabel(), subClasses);
		}
	}

	public BalanceSheet createBalanceSheet(Iterable<BalanceItem> items, String label) {
		for (BalanceItem balanceItem : items) {
			addItem(balanceItem);
		}
		BalanceSheet sheet = new BalanceSheet(label);
		for (AccountClass accountClass : mainAccountClasses) {
			if (AccountClasses.isAsset(accountClass.getPrefix())) {
				sheet.addAssets(accountClass.getLabel(), groupedItems.get(accountClass.getLabel()));
			}
			else {
				sheet.addLiabilities(accountClass.getLabel(), groupedItems.get(accountClass.getLabel()));
			}
		}
		return sheet;
	}

	private void addItem(BalanceItem balanceItem) {
		String no = extractAccountNo(balanceItem);
		if (no != null) {
			AccountClass mainAccountClass = mainClassLookup.get(no.subSequence(0, 1));
			AccountClass subAccountClass = subClassLookup.get(no.subSequence(0, 2));
			if (mainAccountClass == null || subAccountClass == null) {
				System.out.println("Kontenklasse unbekannt: " + no);
			}
			else {
				Map<String, List<BalanceItem>> subClasses = groupedItems.get(mainAccountClass.getLabel());
				if (subClasses != null) {
					List<BalanceItem> items = subClasses.get(subAccountClass.getLabel());
					items.add(balanceItem);
				}
				else
					System.out.println("Kontenklasse fehlt: " + no + " / " + balanceItem.getLabel());
			}
		}
		else {
			System.out.println("Bilanzeintrag ohne Kontenklasse gefunden: " + balanceItem.getKs() + " / "
					+ balanceItem.getAreaLabel() + " / " + balanceItem.getLabel());
		}
	}

	private String extractAccountNo(BalanceItem bi) {
		if (bi.getCurrent() != null)
			return bi.getCurrent().getNo();
		else if (bi.getPrevious() != null)
			return bi.getPrevious().getNo();
		else
			return null;
	}
}
