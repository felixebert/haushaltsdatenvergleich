package de.ifcore.hdv.converter.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

public class PropertyUtils {

	public static <T> List<T> loadProperties(String fileName, PropertyParser<T> parser) {
		List<T> result = new ArrayList<>();
		Properties properties = new Properties();
		try {
			properties.load(new InputStreamReader(ResourceUtils.getResourceAsStream(fileName), "utf8"));
			for (Entry<Object, Object> entry : properties.entrySet()) {
				result.add(parser.parse(entry));
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
