package de.ifcore.hdv.converter.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ConverterConfig {

	public static final Set<String> AREA_KEYS_TO_IGNORE = new HashSet<>();
	static {
		ResourceBundle rb = ResourceBundle.getBundle("converter");
		String areakeysToIgnore = rb.getString("areakeys.to.ignore");
		AREA_KEYS_TO_IGNORE.addAll(Arrays.asList(areakeysToIgnore.split(",")));
	}
}
