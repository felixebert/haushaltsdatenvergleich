package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import de.ifcore.hdv.converter.data.LabelAgs;
import de.ifcore.hdv.converter.parser.AreaLabelParser;
import de.ifcore.hdv.converter.utils.ResourceUtils;

public class AreaLabelParserTest {

	@Test
	public void itShouldParseLabels() throws Exception {
		AreaLabelParser parser = new AreaLabelParser(ResourceUtils.getResourceAsStream("areas.xls"));
		Map<String, LabelAgs> labels = parser.parse();
		assertNotNull(labels);
		assertEquals("Brunsbüttel, Stadt", labels.get("010510011011").getLabel());
		assertEquals("01051", labels.get("010510011011").getAgs());
	}
}
