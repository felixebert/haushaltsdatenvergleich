package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class CountyParserTest {

	@Test
	public void itShouldParseCountiesAsSet() throws Exception {
		CountyParser countyParser = new CountyParser(ResourceUtils.getResourceAsStream("areas.xls"));
		Set<String> counties = countyParser.parse();
		assertNotNull(counties);
		assertFalse(counties.isEmpty());
		assertTrue(counties.contains("01051"));
	}
}
