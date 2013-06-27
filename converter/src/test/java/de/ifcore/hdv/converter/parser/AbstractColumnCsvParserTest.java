package de.ifcore.hdv.converter.parser;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AbstractColumnCsvParserTest {

	private AbstractColumnCsvParser parser = mock(AbstractColumnCsvParser.class);

	@Before
	public void setup() {
		when(parser.parseSingleColumn(anyString())).thenCallRealMethod();
		when(parser.parseColumnLine(any(String[].class))).thenCallRealMethod();
		when(parser.getColumnLine(any(Iterator.class), anyInt())).thenCallRealMethod();
	}

	@Test
	public void itShouldParseSingleColumnDefinition() throws Exception {
		String column = "111 Verwaltungssteuerung und Service";
		ColumnDefinition columnDefinition = parser.parseSingleColumn(column);
		assertEquals(111, columnDefinition.getKey());
		assertEquals("Verwaltungssteuerung und Service", columnDefinition.getLabel());
	}

	@Test
	public void itShouldReturnNullOnInvalidColumnData() throws Exception {
		ColumnDefinition columnDefinition = parser.parseSingleColumn("afsdlsdfhj");
		assertNull(columnDefinition);
	}

	@Test
	public void itShouldParseAllColumnDefinitions() throws Exception {
		String[] columns = { "111 Verwaltungssteuerung und Service", "121 Statistik und Wahlen",
				"122 Ordnungsangelegenheiten" };
		List<ColumnDefinition> colDefs = parser.parseColumnLine(columns);
		assertEquals(3, colDefs.size());
	}

	@Test
	public void itShouldIgnoreInvalidColumnDefinitions() throws Exception {
		String[] columns = { "abc Verwaltungssteuerung und Service", "121StatistikundWahlen", "" };
		List<ColumnDefinition> colDefs = parser.parseColumnLine(columns);
		assertEquals(0, colDefs.size());
	}

	@Test
	public void itShouldIterateTillLineWithColumnDef() throws Exception {
		String[] expected = new String[] { "567 Verwaltungssteuerung und Service", "876 Statistik und Wahlen",
				"876 Ordnungsangelegenheiten" };
		List<String[]> list = Arrays.asList(new String[] { "111 Verwaltungssteuerung und Service",
				"121 Statistik und Wahlen", "122 Ordnungsangelegenheiten" }, new String[] {
				"111 Verwaltungssteuerung und Service", "121 Statistik und Wahlen", "122 Ordnungsangelegenheiten" },
				expected, new String[] { "111 Verwaltungssteuerung und Service", "121 Statistik und Wahlen",
						"122 Ordnungsangelegenheiten" });
		String[] line = parser.getColumnLine(list.iterator(), 2);
		assertArrayEquals(expected, line);
	}
}
