package de.ifcore.hdv.converter.utils;

import com.fasterxml.jackson.databind.module.SimpleModule;

import de.ifcore.hdv.converter.MinMax;
import de.ifcore.hdv.converter.data.AccountValue;
import de.ifcore.hdv.converter.json.AccountValueSerializer;
import de.ifcore.hdv.converter.json.MinMaxSerializer;

public class JsonModule extends SimpleModule {

	public JsonModule() {
		addSerializer(new AccountValueSerializer(AccountValue.class));
		addSerializer(new MinMaxSerializer(MinMax.class));
	}
}
