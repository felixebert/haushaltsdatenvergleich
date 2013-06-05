package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Map;

import org.junit.Test;

public class AreaSizeParserTest {

	@Test
	public void itShouldReadAreaSizesAsMap() throws Exception {
		InputStream in = null;
		Map<String, Double> areaSizes = AreaSizeParser.parse(in);
		assertNotNull(areaSizes);
	}
}
