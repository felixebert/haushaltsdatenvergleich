package de.ifcore.hdv.converter;

import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

public class ExcelReaderTest {

	@Test
	public void itShouldReadAnExcelFile() throws Exception {
		FileInputStream fileIn = new FileInputStream(getResource("data.xls"));
		Workbook wb = new HSSFWorkbook(fileIn);
		fileIn.close();
	}

	public static File getResource(String filename) {
		URL url = ExcelReaderTest.class.getClassLoader().getResource(filename);
		try {
			return new File(url.toURI());
		}
		catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}
}
