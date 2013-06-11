package de.ifcore.hdv.converter;

import java.util.HashMap;
import java.util.Map;

public class Utils {

	public static Map<String, Object> asMap(Object... objects) {
		Map<String, Object> result = new HashMap<>();
		for (int x = 0; x < objects.length; x += 2) {
			result.put((String)objects[x], objects[x + 1]);
		}
		return result;
	}
}
