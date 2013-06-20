package de.ifcore.hdv.converter.parser;

import java.util.Collection;

import de.ifcore.hdv.converter.utils.ConverterConfig;
import de.ifcore.hdv.converter.utils.Utils;

public class CountyAccountParser extends AccountParser {

	public CountyAccountParser(Collection<String[]> lines) {
		super(lines);
	}

	@Override
	protected boolean isAreaKeyAcceptable(String areaKey) {
		return !ConverterConfig.AREA_KEYS_TO_IGNORE.contains(areaKey)
				&& (Utils.isCounty(areaKey) || Utils.isKreisfreieCity(areaKey));
	}
}
