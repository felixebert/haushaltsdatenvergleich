package de.ifcore.hdv.converter.data;

public class Population {

	private String areaName;
	private long population;

	public Population(String areaName, long population) {
		this.areaName = areaName;
		this.population = population;
	}

	public String getAreaName() {
		return areaName;
	}

	public long getPopulation() {
		return population;
	}
}
