package de.ifcore.hdv.converter.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import de.ifcore.hdv.converter.CategoryRangeComparator;
import de.ifcore.hdv.converter.data.Category;

public class FromToRanges {

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
		return reorderByCategoryLabel(result, categories);
	}

	public static List<int[]> reorderByCategoryLabel(List<int[]> ranges, SortedSet<Category> categories) {
		Collections.sort(ranges, new CategoryRangeComparator(categories));
		return ranges;
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
