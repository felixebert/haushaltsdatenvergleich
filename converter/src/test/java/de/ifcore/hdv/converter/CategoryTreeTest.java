package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Category;
import de.ifcore.hdv.converter.data.CategoryTree;

public class CategoryTreeTest {

	@Test
	public void itShouldReturnNullIfNotFound() throws Exception {
		CategoryTree categoryTree = new CategoryTree();
		assertNull(categoryTree.findMainCategory(11));
	}

	@Test
	public void itShouldReturnTheMainCategoryForASubCategory() throws Exception {
		CategoryTree categoryTree = new CategoryTree();
		SortedSet<Category> subCats = new TreeSet<Category>();
		subCats.add(new Category(121, "abc"));
		categoryTree.addCategory(11, subCats);
		assertEquals(Integer.valueOf(11), categoryTree.findMainCategory(121));
	}
}
