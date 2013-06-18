package de.ifcore.hdv.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.ifcore.hdv.converter.data.BalanceItem;

public class BalanceFilter {

	private List<BalanceItem> items;

	public BalanceFilter(List<BalanceItem> items) {
		this.items = items;
	}

	public Set<String> collectAllKs() {
		Set<String> result = new HashSet<>();
		for (BalanceItem item : items) {
			result.add(item.getKs());
		}
		return result;
	}

	public List<BalanceItem> getItemsProKs(String ks) {
		List<BalanceItem> result = new ArrayList<BalanceItem>();
		for (BalanceItem balanceItem : items) {
			if (balanceItem.getKs().equals(ks))
				result.add(balanceItem);
		}
		return result;
	}
}
