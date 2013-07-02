package de.ifcore.hdv.converter.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class LongValueTest {

	@Test
	public void itShouldReturnFalseForNA() throws Exception {
		assertFalse(LongValue.NA.isValidForOutput());
	}
}
