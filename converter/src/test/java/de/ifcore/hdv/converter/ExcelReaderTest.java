package de.ifcore.hdv.converter;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

public class ExcelReaderTest {

	@Test
	public void itShouldReadAnExcelFile() throws Exception {
		Workbook wb = new HSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream("data.xls");
		fileOut.close();
	}
}
