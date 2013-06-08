package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Category;
import de.ifcore.hdv.converter.data.CategoryTree;

public class CategoryMergerTest {

	@Test
	public void itShouldCreateACategoryTree() throws Exception {
		List<MinMaxAccount> accounts = mockAccounts();
		SortedSet<Category> categories = mockCategories();
		CategoryTree categoryTree = CategoryMerger.createTree(categories, accounts);
		assertNotNull(categoryTree);
		Map<Integer, List<Integer>> tree = categoryTree.getTree();
		assertArrayEquals(new Integer[] { 121, 122 }, tree.get(11).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] { 221 }, tree.get(21).toArray(new Integer[0]));
		assertArrayEquals(new Integer[] { 321, 421 }, tree.get(31).toArray(new Integer[0]));
	}

	@Test
	public void itShouldReturnCategoryRanges() throws Exception {
		SortedSet<Category> categories = mockCategories();
		List<int[]> fromToRanges = CategoryMerger.findFromToRanges(categories);
		assertArrayEquals(new int[] { 11, 21 }, fromToRanges.get(0));
		assertArrayEquals(new int[] { 21, 31 }, fromToRanges.get(1));
		assertArrayEquals(new int[] { 31, 10000 }, fromToRanges.get(2));
	}

	@Test
	public void itShouldFindSuitableSubCategories() throws Exception {
		List<MinMaxAccount> accounts = mockAccounts();
		SortedSet<Category> result = CategoryMerger.findSubCategories(12, 22, accounts);
		assertEquals(2, result.size());
	}

	private SortedSet<Category> mockCategories() {
		SortedSet<Category> categories = new TreeSet<>();
		categories.add(new Category(31, "Cat3"));
		categories.add(new Category(21, "Alpha"));
		categories.add(new Category(11, "Cat1"));
		return categories;
	}

	private List<MinMaxAccount> mockAccounts() {
		List<MinMaxAccount> accounts = new ArrayList<>();
		accounts.add(new MinMaxAccount(121, "Abc121"));
		accounts.add(new MinMaxAccount(122, "Abc122"));
		accounts.add(new MinMaxAccount(221, "Abc221"));
		accounts.add(new MinMaxAccount(321, "Abc321"));
		accounts.add(new MinMaxAccount(421, "Abc421"));
		return accounts;
	}
}
