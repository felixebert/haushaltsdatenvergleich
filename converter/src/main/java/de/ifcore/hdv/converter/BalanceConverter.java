package de.ifcore.hdv.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.ifcore.hdv.converter.data.BalanceItem;
import de.ifcore.hdv.converter.data.BalanceSheet;

public class BalanceConverter {

	private List<BalanceItem> items;

	public BalanceConverter(List<BalanceItem> items) {
		this.items = items;
	}

	public Set<String> collectAllKs() {
		Set<String> result = new HashSet<>();
		for (BalanceItem item : items) {
			result.add(item.getNo());
		}
		return result;
	}

	public List<BalanceItem> getItemsProKs(String ks) {
		List<BalanceItem> result = new ArrayList<BalanceItem>();
		for (BalanceItem balanceItem : items) {
			if (balanceItem.getNo().equals(ks))
				result.add(balanceItem);
		}
		return result;
	}

	public BalanceSheet createBalanceSheet(String ks) {
		// TODO Auto-generated method stub
		return null;
	}
}
