package de.ifcore.hdv.converter.data;

import java.util.Collection;

public class AccountsPerArea {

	private String areaKey;
	private String areaName;
	private long population;
	private double size;
	private Collection<InOutAccount> accounts;

	public AccountsPerArea(String areaKey, String areaName, long population, double size,
			Collection<InOutAccount> accounts) {
		this.areaKey = areaKey;
		this.areaName = areaName;
		this.population = population;
		this.size = size;
		this.accounts = accounts;
	}

	public String getAreaKey() {
		return areaKey;
	}

	public String getAreaName() {
		return areaName;
	}

	public long getPopulation() {
		return population;
	}

	public double getSize() {
		return size;
	}

	public Collection<InOutAccount> getAccounts() {
		return accounts;
	}
}
