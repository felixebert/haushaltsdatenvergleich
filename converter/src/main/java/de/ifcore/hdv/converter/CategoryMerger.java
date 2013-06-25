package de.ifcore.hdv.converter;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import de.ifcore.hdv.converter.data.Category;
import de.ifcore.hdv.converter.data.CategoryTree;
import de.ifcore.hdv.converter.utils.FromToRanges;

public class CategoryMerger {

	public static CategoryTree createTree(SortedSet<Category> categories, Collection<MinMaxProduct> accounts) {
		CategoryTree result = new CategoryTree();
		List<int[]> ranges = FromToRanges.findFromToRanges(categories);
		for (int[] range : ranges) {
			SortedSet<Category> subCategories = findSubCategories(range[0], range[1], accounts);
			result.addCategory(range[0], subCategories);
		}
		return result;
	}

	public static SortedSet<Category> findSubCategories(int startKey, int nextKey, Collection<MinMaxProduct> accounts) {
		SortedSet<Category> result = new TreeSet<>();
		for (MinMaxProduct account : accounts) {
			if (account.getKey() >= startKey * 10 && account.getKey() < nextKey * 10) {
				result.add(new Category(account.getKey(), account.getLabel()));
			}
		}
		return result;
	}
}
