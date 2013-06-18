package de.ifcore.hdv.converter;

import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import de.ifcore.hdv.converter.data.Category;
import de.ifcore.hdv.converter.utils.PropertyParser;
import de.ifcore.hdv.converter.utils.PropertyUtils;

public class MainCategories {

	private SortedSet<Category> categories = new TreeSet<>();
	private static final MainCategories INSTANCE = new MainCategories();

	private MainCategories() {
		categories.addAll(PropertyUtils.loadProperties("maincategories.properties", new PropertyParser<Category>() {

			@Override
			public Category parse(Entry<Object, Object> entry) {
				int key = Integer.parseInt((String)entry.getKey());
				String label = (String)entry.getValue();
				return new Category(key, label);
			}
		}));
	}

	public static MainCategories getInstance() {
		return INSTANCE;
	}

	public SortedSet<Category> getCategories() {
		return categories;
	}
}
