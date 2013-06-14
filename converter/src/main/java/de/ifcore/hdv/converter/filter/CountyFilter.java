package de.ifcore.hdv.converter.filter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;

import de.ifcore.hdv.converter.data.LabelAgs;
import de.ifcore.hdv.converter.parser.AreaLabelParser;
import de.ifcore.hdv.converter.utils.Utils;

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
			Map<String, LabelAgs> labels) {
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
				LabelAgs labelAgs = labels.get(rs);
				if (labelAgs != null) {
					Map<String, Object> newProperties = new HashMap<>();
					newProperties.put("GEN", labelAgs.getLabel());
					newProperties.put("AGS", labelAgs.getAgs());
					Map<String, Object> newFeature = Utils.asMap("type", feature.get("type"), "geometry",
							feature.get("geometry"), "properties", newProperties);
					filteredFeatures.add(newFeature);
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		if (args.length == 3) {
			try {
				String countiesFile = args[0];
				String lkNr = args[1];
				String areaLabels = args[2];
				AreaLabelParser parser = new AreaLabelParser(new FileInputStream(areaLabels));
				Map<String, LabelAgs> labels = parser.parse();
				Map<String, Object> countiesMap = readCounties(new FileInputStream(countiesFile));
				Map<String, Object> filteredMap = filterCountyByKey(countiesMap, lkNr, labels);
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
			System.out.println("Usage: <geojson mit lankreisen> <landkreis-nr> <datei mit labels und größen>");
	}
}
