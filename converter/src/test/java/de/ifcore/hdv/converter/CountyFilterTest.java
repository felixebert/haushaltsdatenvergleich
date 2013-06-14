package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import de.ifcore.hdv.converter.data.LabelAgs;
import de.ifcore.hdv.converter.filter.CountyFilter;
import de.ifcore.hdv.converter.utils.ResourceUtils;
import de.ifcore.hdv.converter.utils.Utils;

public class CountyFilterTest {

	@Test
	public void itShouldFilterCounties() throws Exception {
		Map<String, Object> map = CountyFilter.readCounties(ResourceUtils.getResourceAsStream("landkreise-klein.json"));
		assertNotNull(map.get("features"));
	}

	@Test
	public void itShouldFilterCountiesByKey() throws Exception {
		Map<String, Object> geoJson = mockGeoJson();
		Map<String, Object> result = CountyFilter.filterCountyByKey(geoJson, "05", new HashMap<String, LabelAgs>());
		List<Object> features = (List<Object>)result.get("features");
		assertTrue(features.isEmpty());
	}

	@Test
	public void itShouldFilterProperties() throws Exception {
		Map<String, Object> geoJson = mockGeoJson();
		Map<String, Object> result = CountyFilter.filterCountyByKey(geoJson, "03", mockLabels());
		List<Map<String, Object>> features = (List<Map<String, Object>>)result.get("features");
		Map<String, Object> feature = features.get(0);
		Map<String, Object> properties = (Map<String, Object>)feature.get("properties");
		assertEquals("03156004", properties.get("AGS"));
		assertEquals("gen", properties.get("GEN"));
	}

	@Test
	public void itShouldFilterCountinesByKeyIntegeration() throws Exception {
		Map<String, Object> geoJson = CountyFilter.readCounties(ResourceUtils
				.getResourceAsStream("landkreise-klein.json"));
		Map<String, Object> result = CountyFilter.filterCountyByKey(geoJson, "07", mockLabels());
		List<Object> features = (List<Object>)result.get("features");
		assertFalse(features.isEmpty());
	}

	private Map<String, LabelAgs> mockLabels() {
		Map<String, LabelAgs> result = new HashMap<String, LabelAgs>();
		result.put("031565401004", new LabelAgs("gen", "03156004"));
		result.put("072315007206", new LabelAgs("gen", "07231206"));
		return result;
	}

	private Map<String, Object> mockGeoJson() {
		Map<String, Object> geoJson = Utils.asMap("features",
				Arrays.asList(Utils.asMap("properties", Utils.asMap("RS", "031565401004"))));
		return geoJson;
	}
}
