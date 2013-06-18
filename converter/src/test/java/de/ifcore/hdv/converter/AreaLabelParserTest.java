package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import de.ifcore.hdv.converter.data.LabelAgs;
import de.ifcore.hdv.converter.parser.AreaLabelParser;
import de.ifcore.hdv.converter.utils.ResourceUtils;

public class AreaLabelParserTest {

	@Test
	@Ignore
	public void itShouldParseLabels() throws Exception {
		AreaLabelParser parser = new AreaLabelParser(ResourceUtils.getResourceAsStream("areas.xls"));
		Map<String, LabelAgs> labels = parser.parse();
		assertNotNull(labels);
		assertEquals("Brunsbüttel, Stadt", labels.get("01051").getLabel());
		assertEquals("01051", labels.get("01051").getAgs());
	}
}
