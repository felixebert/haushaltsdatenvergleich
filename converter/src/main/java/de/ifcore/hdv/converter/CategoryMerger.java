package de.ifcore.hdv.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import de.ifcore.hdv.converter.data.Category;
import de.ifcore.hdv.converter.data.CategoryTree;

public class CategoryMerger {

	public static CategoryTree createTree(SortedSet<Category> categories, Collection<MinMaxAccount> accounts) {
		CategoryTree result = new CategoryTree();
		List<int[]> ranges = findFromToRanges(categories);
		for (int[] range : ranges) {
			SortedSet<Category> subCategories = findSubCategories(range[0], range[1], accounts);
			result.addCategory(range[0], subCategories);
		}
		return result;
	}

	public static SortedSet<Category> findSubCategories(int startKey, int nextKey, Collection<MinMaxAccount> accounts) {
		SortedSet<Category> result = new TreeSet<>();
		for (MinMaxAccount account : accounts) {
			if (account.getKey() >= startKey * 10 && account.getKey() < nextKey * 10) {
				result.add(new Category(account.getKey(), account.getLabel()));
			}
		}
		return result;
	}

	public static List<int[]> findFromToRanges(SortedSet<Category> categories) {
		SortedSet<Category> sortedByKey = reorderByKey(categories);
		List<int[]> result = new ArrayList<int[]>();
		Iterator<Category> it = sortedByKey.iterator();
		if (it.hasNext()) {
			Category category = it.next();
			int currentKey = category.getKey();
			while (it.hasNext()) {
				category = it.next();
				result.add(new int[] { currentKey, category.getKey() });
				currentKey = category.getKey();
			}
			result.add(new int[] { currentKey, 10000 });
		}
		return result;
	}

	private static SortedSet<Category> reorderByKey(SortedSet<Category> categories) {
		SortedSet<Category> sortedByKey = new TreeSet<>(new Comparator<Category>() {

			@Override
			public int compare(Category o1, Category o2) {
				return o1.getKey() - o2.getKey();
			}
		});
		sortedByKey.addAll(categories);
		return sortedByKey;
	}
}
