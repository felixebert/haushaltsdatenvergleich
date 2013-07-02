package de.ifcore.hdv.converter;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

	public Collection<BalanceItem> getItemsProKs(String ks) {
		Map<String, BalanceItem> itemMap = new HashMap<>();
		for (BalanceItem balanceItem : items) {
			if (balanceItem.getKs().equals(ks)) {
				BalanceItem bi = itemMap.get(balanceItem.getLabel());
				itemMap.put(balanceItem.getLabel(), mergeBalanceItems(balanceItem, bi));
			}
		}
		return itemMap.values();
	}

	private BalanceItem mergeBalanceItems(BalanceItem bi1, BalanceItem bi2) {
		BalanceItem bi = null;
		if (bi1 != null) {
			bi = clone(bi1);
		}
		else if (bi2 != null) {
			bi = clone(bi2);
		}
		if (bi != null) {
			mergeValues(bi1, bi);
			mergeValues(bi2, bi);
		}
		return bi;
	}

	private BalanceItem clone(BalanceItem bi1) {
		return new BalanceItem(bi1.getKs(), bi1.getAreaLabel(), bi1.getLabel());
	}

	private void mergeValues(BalanceItem source, BalanceItem target) {
		if (source != null) {
			if (source.getCurrent() != null)
				target.setCurrent(source.getCurrent());
			if (source.getPrevious() != null)
				target.setPrevious(source.getPrevious());
		}
	}
}
