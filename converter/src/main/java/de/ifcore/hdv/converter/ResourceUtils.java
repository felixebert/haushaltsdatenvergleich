package de.ifcore.hdv.converter;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourceUtils {

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
