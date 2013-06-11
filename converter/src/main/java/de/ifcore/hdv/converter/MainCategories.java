package de.ifcore.hdv.converter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import de.ifcore.hdv.converter.data.Category;

public class MainCategories {

	private SortedSet<Category> categories = new TreeSet<>();
	private static final MainCategories INSTANCE = new MainCategories();

	private MainCategories() {
		Properties properties = new Properties();
		try {
			properties.load(new InputStreamReader(ResourceUtils.getResourceAsStream("maincategories.properties"),
					"utf8"));
			for (Entry<Object, Object> entry : properties.entrySet()) {
				int key = Integer.parseInt((String)entry.getKey());
				String label = (String)entry.getValue();
				categories.add(new Category(key, label));
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static MainCategories getInstance() {
		return INSTANCE;
	}

	public SortedSet<Category> getCategories() {
		return categories;
	}
}
