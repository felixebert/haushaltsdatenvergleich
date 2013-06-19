package de.ifcore.hdv.converter.parser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class AreaSizeParser extends AbstractExcelParser {

	public AreaSizeParser(InputStream in) {
		super(in, 1, 8);
	}

	public Map<String, Double> parse() {
		return parseRows(rowIterator);
	}

	private Map<String, Double> parseRows(Iterator<Row> rowIterator) {
		Map<String, Double> result = new HashMap<String, Double>();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			String areaKey = parseAreaKey(row);
			if (areaKeyAccepted(areaKey)) {
				Double size = parseSize(row);
				if (size != null)
					result.put(areaKey, size);
			}
		}
		return result;
	}

	private boolean areaKeyAccepted(String areaKey) {
		return areaKey != null && !areaKey.isEmpty();
	}

	private Double parseSize(Row row) {
		Cell cell = row.getCell(8);
		if (cell != null
				&& (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA)) {
			return cell.getNumericCellValue();
		}
		else
			return null;
	}

	private String parseAreaKey(Row row) {
		return parseCellsIntoOne(row, new int[] { 2, 3, 4, 6 });
	}
}
