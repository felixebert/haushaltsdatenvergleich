package de.ifcore.hdv.converter.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommuneAccountParserTest {

	@Test
	public void itShouldIgnoreCountyAreaKeys() throws Exception {
		CommuneAccountParser parser = new CommuneAccountParser(null);
		assertFalse(parser.isAreaKeyAcceptable("12345001"));
		assertTrue(parser.isAreaKeyAcceptable("12345000"));
	}
}
