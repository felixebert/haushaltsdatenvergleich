package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.SortedSet;

import org.junit.Test;

import de.ifcore.hdv.converter.data.Category;

public class MainCategoriesTest {

	@Test
	public void itShouldReadPropertiesOnCreate() throws Exception {
		MainCategories mainCategories = MainCategories.getInstance();
		SortedSet<Category> categories = mainCategories.getCategories();
		assertNotNull(categories);
		Category first = categories.first();
		Category last = categories.last();
		assertEquals("Innere Verwaltung", first.getLabel());
		assertEquals("Sicherheit und Ordnungöäüß", last.getLabel());
	}
}
