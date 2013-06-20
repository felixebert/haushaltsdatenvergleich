package de.ifcore.hdv.converter.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	public static void writeData(Object object, String filename) throws JsonProcessingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
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

	public static List<String[]> readCsvFile(InputStream in) {
		try {
			CSVReader reader = new CSVReader(new InputStreamReader(in, Charset.forName("ISO-8859-1")), ';');
			List<String[]> list = reader.readAll();
			reader.close();
			return list;
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

	public static Long parseLongSafe(String value) {
		try {
			return Long.valueOf(value);
		}
		catch (NumberFormatException e) {
			return null;
		}
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
}
