package de.ifcore.hdv.converter.mapreduce;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Aggregator {

	public static <T> Map<String, T> aggregateMap(Map<String, T> map, MapReduce<T> mapReduce) {
		Map<String, T> result = new HashMap<>();
		for (Entry<String, T> entry : map.entrySet()) {
			String key = mapReduce.extractKey(entry.getKey());
			T value = result.get(key);
			if (value == null) {
				result.put(key, entry.getValue());
			}
			else {
				result.put(key, mapReduce.reduce(value, entry.getValue()));
			}
		}
		return result;
	}
}
