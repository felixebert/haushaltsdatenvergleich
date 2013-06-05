package de.ifcore.hdv.converter;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractExcelParser {

	protected Sheet sheet;
	protected Iterator<Row> rowIterator;

	protected AbstractExcelParser(InputStream in, int sheetToUse, int headerRowsToSkip) {
		try {
			Workbook wb = new HSSFWorkbook(in);
			sheet = wb.getSheetAt(sheetToUse);
			rowIterator = sheet.rowIterator();
			for (int x = 0; x < headerRowsToSkip; x++) {
				rowIterator.next();
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
