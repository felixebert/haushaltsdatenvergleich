package de.ifcore.hdv.converter.data;

public class Category implements Comparable<Category> {

	private int key;
	private String label;

	public Category(int key, String label) {
		this.key = key;
		this.label = label;
	}

	public int getKey() {
		return key;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public int compareTo(Category o) {
		return label.compareTo(o.label);
	}
}
