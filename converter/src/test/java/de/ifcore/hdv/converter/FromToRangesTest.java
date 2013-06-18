package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Category;
import de.ifcore.hdv.converter.utils.FromToRanges;

public class FromToRangesTest {

	@Test
	public void itShouldReturnCategoryRangesSortedByCategoryLabel() throws Exception {
		SortedSet<Category> categories = DataMocks.mockCategories();
		List<int[]> fromToRanges = FromToRanges.findFromToRanges(categories);
		assertArrayEquals(new int[] { 21, 31 }, fromToRanges.get(0));
		assertArrayEquals(new int[] { 11, 21 }, fromToRanges.get(1));
		assertArrayEquals(new int[] { 31, 10000 }, fromToRanges.get(2));
	}

	@Test
	public void itShouldReorderByCategoryLabel() throws Exception {
		List<int[]> fromToRanges = FromToRanges.reorderByCategoryLabel(
				Arrays.asList(new int[] { 31, 10000 }, new int[] { 11, 21 }, new int[] { 21, 31 }),
				DataMocks.mockCategories());
		assertArrayEquals(new int[] { 21, 31 }, fromToRanges.get(0));
		assertArrayEquals(new int[] { 11, 21 }, fromToRanges.get(1));
		assertArrayEquals(new int[] { 31, 10000 }, fromToRanges.get(2));
	}
}
