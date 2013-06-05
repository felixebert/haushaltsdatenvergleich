package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class AreaSizeParserTest {

	@Test(expected = RuntimeException.class)
	public void itShouldThrowRuntimeExceptionOnFailure() throws Exception {
		new AreaSizeParser(null);
	}

	@Test
	public void itShouldReadAreaSizesAsMap() throws Exception {
		AreaSizeParser areaSizeParser = new AreaSizeParser(ResourceUtils.getResourceAsStream("areas.xls"));
		Map<String, Double> areaSizes = areaSizeParser.parse();
		assertNotNull(areaSizes);
		assertFalse(areaSizes.isEmpty());
		assertTrue(areaSizes.containsKey("01001000"));
		Double size = areaSizes.get("01001000");
		assertEquals(56.74, size.doubleValue(), 0.1);
		assertEquals(6, areaSizes.size());
	}
}
