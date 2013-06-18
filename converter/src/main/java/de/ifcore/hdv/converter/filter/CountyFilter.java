package de.ifcore.hdv.converter.filter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;

import de.ifcore.hdv.converter.utils.Utils;

public class CountyFilter {

	public static Map<String, Object> readCounties(InputStream in) {
		try {
			Map<String, Object> map = new ObjectMapper().readValue(
					new InputStreamReader(in, "ISO-8859-1"),
					MapType.construct(HashMap.class, SimpleType.construct(String.class),
							SimpleType.construct(Object.class)));
			return map;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, Object> filterCountyByKey(Map<String, Object> geoJson, String key) {
		Map<String, Object> result = Utils.asMap("type", geoJson.get("type"));
		List<Map<String, Object>> filteredFeatures = new ArrayList<>();
		result.put("features", filteredFeatures);
		List<Map<String, Object>> features = (List<Map<String, Object>>)geoJson.get("features");
		Iterator<Map<String, Object>> it = features.iterator();
		while (it.hasNext()) {
			Map<String, Object> feature = it.next();
			Map<String, Object> properties = (Map<String, Object>)feature.get("properties");
			String rs = (String)properties.get("RS");
			if (rs != null && rs.startsWith(key)) {
				Map<String, Object> newProperties = new HashMap<>();
				newProperties.put("GEN", properties.get("GEN"));
				newProperties.put("AGS", rs);
				newProperties.put("DES", properties.get("DES"));
				Map<String, Object> newFeature = Utils.asMap("type", feature.get("type"), "geometry",
						feature.get("geometry"), "properties", newProperties);
				filteredFeatures.add(newFeature);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		if (args.length == 3) {
			try {
				String countiesFile = args[0];
				String lkNr = args[1];
				String outputFile = args[2];
				Map<String, Object> countiesMap = readCounties(new FileInputStream(countiesFile));
				Map<String, Object> filteredMap = filterCountyByKey(countiesMap, lkNr);
				System.out.println("Schreibe Ausgabe nach " + outputFile);
				new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream(outputFile),
						filteredMap);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			System.out.println("Usage: <geojson mit lankreisen> <landkreis-nr> <ausgabe datei>");
	}
}
