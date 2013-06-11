package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class CountyFilterTest {

	@Test
	public void itShouldFilterCounties() throws Exception {
		Map<String, Object> map = CountyFilter.readCounties(ResourceUtils.getResourceAsStream("landkreise-klein.json"));
		assertNotNull(map.get("features"));
	}

	@Test
	public void itShouldFilterCountiesByKey() throws Exception {
		Map<String, Object> geoJson = mockGeoJson();
		Map<String, Object> result = CountyFilter.filterCountyByKey(geoJson, "05", new ArrayList<String>());
		List<Object> features = (List<Object>)result.get("features");
		assertTrue(features.isEmpty());
	}

	@Test
	public void itShouldFilterProperties() throws Exception {
		Map<String, Object> geoJson = mockGeoJson();
		Map<String, Object> result = CountyFilter.filterCountyByKey(geoJson, "07", Arrays.asList("AGS", "GEN"));
		List<Map<String, Object>> features = (List<Map<String, Object>>)result.get("features");
		Map<String, Object> feature = features.get(0);
		Map<String, Object> properties = (Map<String, Object>)feature.get("properties");
		assertFalse(properties.containsKey("SN_L"));
		assertEquals("ags", properties.get("AGS"));
		assertEquals("gen", properties.get("GEN"));
	}

	@Test
	public void itShouldFilterCountinesByKeyIntegeration() throws Exception {
		Map<String, Object> geoJson = CountyFilter.readCounties(ResourceUtils
				.getResourceAsStream("landkreise-klein.json"));
		Map<String, Object> result = CountyFilter.filterCountyByKey(geoJson, "05", Arrays.asList("AGS", "GEN"));
		List<Object> features = (List<Object>)result.get("features");
		assertFalse(features.isEmpty());
	}

	private Map<String, Object> mockGeoJson() {
		Map<String, Object> geoJson = Utils.asMap("features",
				Arrays.asList(Utils.asMap("properties", Utils.asMap("SN_L", "07", "AGS", "ags", "GEN", "gen"))));
		return geoJson;
	}
}
