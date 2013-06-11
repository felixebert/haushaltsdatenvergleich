package de.ifcore.hdv.converter;

public interface MapReduce<T> {

	String extractKey(String key);

	T reduce(T value1, T value2);
}
