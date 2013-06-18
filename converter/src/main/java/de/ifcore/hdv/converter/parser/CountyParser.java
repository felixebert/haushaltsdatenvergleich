package de.ifcore.hdv.converter.parser;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;

public class CountyParser extends AbstractExcelParser {

	public CountyParser(InputStream in) {
		super(in, 1, 8);
	}

	public Set<String> parse() {
		Set<String> result = new HashSet<>();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			String countyNr = parseCountyNr(row);
			if (countyNr != null)
				result.add(countyNr);
		}
		return result;
	}

	private String parseCountyNr(Row row) {
		String subCountyVerband = getCellStringValue(row, 5);
		String subCounty = getCellStringValue(row, 6);
		if (subCountyVerband == null && subCounty == null) {
			return parseCellsIntoOne(row, new int[] { 2, 3, 4 });
		}
		else {
			return null;
		}
	}
}
