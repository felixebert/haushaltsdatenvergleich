package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import org.junit.Test;

public class CategoryRangeComparatorTest {

	@Test
	public void itShouldCompare() throws Exception {
		CategoryRangeComparator comparator = new CategoryRangeComparator(CategoryMergerTest.mockCategories());
		assertTrue(comparator.compare(new int[] { 11, 31 }, new int[] { 11, 31 }) == 0);
		assertTrue(comparator.compare(new int[] { 21, 31 }, new int[] { 11, 21 }) < 0);
		assertTrue(comparator.compare(new int[] { 11, 21 }, new int[] { 21, 31 }) > 0);
	}
}
