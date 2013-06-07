package de.ifcore.hdv.converter.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public class CategoryTree {

	private Map<Integer, List<Integer>> tree = new LinkedHashMap<>();

	public void addCategory(int categoryKey, SortedSet<Category> subCategories) {
		List<Integer> sub = new ArrayList<Integer>();
		for (Category category : subCategories) {
			sub.add(category.getKey());
		}
		tree.put(categoryKey, sub);
	}

	public Map<Integer, List<Integer>> getTree() {
		return tree;
	}
}
