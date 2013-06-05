package de.ifcore.hdv.converter;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

public class ExcelReaderTest {

	@Test
	public void itShouldReadAnExcelFile() throws Exception {
		FileInputStream fileIn = new FileInputStream(ResourceUtils.getResource("data.xls"));
		Workbook wb = new HSSFWorkbook(fileIn);
		fileIn.close();
	}
}
