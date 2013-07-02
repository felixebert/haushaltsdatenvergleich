package de.ifcore.hdv.converter.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BalanceItem {

	@JsonIgnore
	private String ks;
	@JsonIgnore
	private String areaLabel;
	private String label;
	private BalanceValue current;
	private BalanceValue previous;

	public BalanceItem(String ks, String areaLabel, String label) {
		this.ks = ks;
		this.areaLabel = areaLabel;
		this.label = label;
	}

	public BalanceValue getCurrent() {
		return current;
	}

	public void setCurrent(BalanceValue current) {
		this.current = current;
	}

	public BalanceValue getPrevious() {
		return previous;
	}

	public void setPrevious(BalanceValue previous) {
		this.previous = previous;
	}

	public String getKs() {
		return ks;
	}

	public String getAreaLabel() {
		return areaLabel;
	}

	public String getLabel() {
		return label;
	}
}
