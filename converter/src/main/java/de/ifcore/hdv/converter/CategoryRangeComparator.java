package de.ifcore.hdv.converter;

import java.util.Comparator;
import java.util.SortedSet;

import de.ifcore.hdv.converter.data.Category;

public class CategoryRangeComparator implements Comparator<int[]> {

	private SortedSet<Category> categories;

	public CategoryRangeComparator(SortedSet<Category> categories) {
		this.categories = categories;
	}

	@Override
	public int compare(int[] o1, int[] o2) {
		int cat1 = o1[0];
		int cat2 = o2[0];
		int pos1 = findCategoryPosition(cat1);
		int pos2 = findCategoryPosition(cat2);
		return pos1 - pos2;
	}

	private int findCategoryPosition(int cat1) {
		int pos = 0;
		for (Category category : categories) {
			if (category.getKey() == cat1)
				return pos;
			pos++;
		}
		return 0;
	}
}
