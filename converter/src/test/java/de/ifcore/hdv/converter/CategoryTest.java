package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Category;

public class CategoryTest {

	@Test
	public void itShouldSortAlphabetically() throws Exception {
		Category c1 = new Category(0, "Abc");
		Category c2 = new Category(0, "Xyz");
		assertTrue(c1.compareTo(c2) < 0);
	}
}
