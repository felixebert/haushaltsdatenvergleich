package de.ifcore.hdv.converter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import de.ifcore.hdv.converter.data.Population;

public class PopulationParser extends AbstractExcelParser {

	public PopulationParser(InputStream in) {
		super(in, 1, 8);
	}

	public Map<String, Population> parse() {
		Map<String, Population> result = new HashMap<>();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			String areaKey = parseAreaKey(row);
			if (areaKey != null && !areaKey.isEmpty()) {
				String areaName = parseAreaName(row);
				long population = parsePopulation(row);
				result.put(areaKey, new Population(areaName, population));
			}
		}
		return result;
	}

	private String parseAreaKey(Row row) {
		Cell land = row.getCell(0);
		Cell code = row.getCell(2);
		if (land != null && code != null) {
			return land.getStringCellValue() + code.getStringCellValue();
		}
		else
			return null;
	}

	private String parseAreaName(Row row) {
		return row.getCell(3).getStringCellValue();
	}

	private long parsePopulation(Row row) {
		return Double.valueOf(row.getCell(4).getNumericCellValue()).longValue();
	}
}
