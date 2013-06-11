package de.ifcore.hdv.converter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;

public class CountyFilter {

	public static Map<String, Object> readCounties(InputStream in) {
		try {
			Map<String, Object> map = new ObjectMapper().readValue(
					in,
					MapType.construct(HashMap.class, SimpleType.construct(String.class),
							SimpleType.construct(Object.class)));
			return map;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, Object> filterCountyByKey(Map<String, Object> geoJson, String key,
			Collection<String> propertiesToKeep) {
		Map<String, Object> result = Utils.asMap("type", geoJson.get("type"));
		List<Map<String, Object>> filteredFeatures = new ArrayList<>();
		result.put("features", filteredFeatures);
		List<Map<String, Object>> features = (List<Map<String, Object>>)geoJson.get("features");
		Iterator<Map<String, Object>> it = features.iterator();
		while (it.hasNext()) {
			Map<String, Object> feature = it.next();
			Map<String, Object> properties = (Map<String, Object>)feature.get("properties");
			String lkNr = (String)properties.get("SN_L");
			if (key.equals(lkNr)) {
				Map<String, Object> newProperties = new HashMap<>();
				for (String propertyToKeep : propertiesToKeep) {
					newProperties.put(propertyToKeep, properties.get(propertyToKeep));
				}
				Map<String, Object> newFeature = Utils.asMap("type", feature.get("type"), "geometry",
						feature.get("geometry"), "properties", newProperties);
				filteredFeatures.add(newFeature);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		if (args.length == 2) {
			try {
				String countiesFile = args[0];
				String lkNr = args[1];
				Map<String, Object> countiesMap = readCounties(new FileInputStream(countiesFile));
				Map<String, Object> filteredMap = filterCountyByKey(countiesMap, lkNr, Arrays.asList("AGS", "GEN"));
				String newFileName = countiesFile.substring(0, countiesFile.lastIndexOf('.')) + "-" + lkNr + ".json";
				System.out.println("Schreibe Ausgabe nach " + newFileName);
				new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream(newFileName),
						filteredMap);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			System.out.println("Usage: <geojson mit lankreisen> <landkreis-nr>");
	}
}
