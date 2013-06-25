package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Category;
import de.ifcore.hdv.converter.data.CategoryTree;

public class CategoryMergerTest {

	@Test
	public void itShouldCreateACategoryTree() throws Exception {
		List<MinMaxProduct> accounts = DataMocks.mockAccounts();
		SortedSet<Category> categories = DataMocks.mockCategories();
		CategoryTree categoryTree = CategoryMerger.createTree(categories, accounts);
		assertNotNull(categoryTree);
		Map<Integer, List<Integer>> tree = categoryTree.getTree();
		assertArrayEquals(new Integer[] { 121, 122 }, tree.get(11).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] { 221 }, tree.get(21).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] { 321, 421 }, tree.get(31).toArray(new Integer[0]));
	}

	@Test
	public void itShouldFindSuitableSubCategories() throws Exception {
		List<MinMaxProduct> accounts = DataMocks.mockAccounts();
		SortedSet<Category> result = CategoryMerger.findSubCategories(12, 22, accounts);
		assertEquals(2, result.size());
	}
}
