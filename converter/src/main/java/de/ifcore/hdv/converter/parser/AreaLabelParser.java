package de.ifcore.hdv.converter.parser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

import de.ifcore.hdv.converter.data.LabelAgs;

public class AreaLabelParser extends AbstractExcelParser {

	public AreaLabelParser(InputStream in) {
		super(in, 1, 8);
	}

	public Map<String, LabelAgs> parse() {
		return parseRows(rowIterator);
	}

	private Map<String, LabelAgs> parseRows(Iterator<Row> rowIterator) {
		Map<String, LabelAgs> result = new HashMap<>();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			String areaKey = parseCellsIntoOne(row, new int[] { 2, 3, 4, 5, 6 });
			String ags = parseCellsIntoOne(row, new int[] { 2, 3, 4 });
			if (areaKey != null && !areaKey.isEmpty()) {
				String label = getCellStringValue(row, 7);
				if (label != null)
					result.put(ags, new LabelAgs(label, ags));
			}
		}
		return result;
	}
}
