package de.ifcore.hdv.converter.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	public static void writeData(Object object, String filename) throws JsonProcessingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		FileWriter fw = new FileWriter(filename);
		fw.write(json);
		fw.flush();
		fw.close();
	}

	public static Map<String, Object> asMap(Object... objects) {
		Map<String, Object> result = new HashMap<>();
		for (int x = 0; x < objects.length; x += 2) {
			result.put((String)objects[x], objects[x + 1]);
		}
		return result;
	}
}
