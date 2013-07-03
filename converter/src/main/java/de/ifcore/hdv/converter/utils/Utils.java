package de.ifcore.hdv.converter.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import de.ifcore.hdv.converter.split.DataSplit;
import de.ifcore.hdv.converter.split.Product;

public class Utils {

	public static void writeData(Object object, String filename) throws JsonProcessingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JsonModule());
		ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(object);
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename), Charset.forName("UTF8"));
		out.write(json);
		out.flush();
		out.close();
	}

	public static List<String[]> readCsvFile(String filename) {
		try {
			return readCsvFile(new FileInputStream(filename));
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private static long getUsedMemory() {
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
	}

	public static List<String[]> readCsvFile(InputStream in) {
		try {
			System.out.println("MemUse before: " + getUsedMemory() + " MB");
			long time = System.currentTimeMillis();
			Reader input = new InputStreamReader(in, Charset.forName("ISO-8859-1"));
			CsvListReader reader = new CsvListReader(input, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
			List<String[]> result = new ArrayList<>();
			List<String> lineOfStrings = null;
			do {
				lineOfStrings = reader.read();
				if (lineOfStrings != null) {
					String[] line = new String[lineOfStrings.size()];
					for (int x = 0; x < lineOfStrings.size(); x++) {
						String cell = lineOfStrings.get(x);
						if (cell != null) {
							line[x] = cell.intern();
						}
						else
							line[x] = "";
					}
					result.add(line);
				}
			} while (lineOfStrings != null);
			reader.close();
			System.out.println("readAll: " + (System.currentTimeMillis() - time) + " ms");
			System.out.println("MemUse after: " + getUsedMemory() + " MB");
			return result;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, Object> asMap(Object... objects) {
		Map<String, Object> result = new HashMap<>();
		for (int x = 0; x < objects.length; x += 2) {
			result.put((String)objects[x], objects[x + 1]);
		}
		return result;
	}

	public static boolean hasText(String s) {
		return s != null && !s.trim().isEmpty();
	}

	public static boolean isCounty(String areaKey) {
		return areaKey.endsWith("001");
	}

	public static boolean isKreisfreieCity(String areaKey) {
		return areaKey.endsWith("000");
	}

	public static void writeSplittedData(DataSplit split, String outputDir) throws JsonProcessingException, IOException {
		File output = new File(outputDir);
		if (output.exists() || output.mkdirs()) {
			writeData(split.getMergedData(), output.getAbsolutePath() + File.separator + "metadata.json");
			for (Entry<Integer, Product> entry : split.getProducts().entrySet()) {
				writeData(entry.getValue(), output.getAbsolutePath() + File.separator + entry.getKey() + ".json");
			}
		}
		else {
			System.out.println("Kann Ausgabeverzeichnis " + output.getAbsolutePath() + " nicht erstellen");
		}
	}
}
