package de.ifcore.hdv.converter.utils;

import java.util.Map.Entry;

public interface PropertyParser<T> {

	T parse(Entry<Object, Object> entry);
}
