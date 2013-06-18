package de.ifcore.hdv.converter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import de.ifcore.hdv.converter.data.Account;
import de.ifcore.hdv.converter.data.MergedData;
import de.ifcore.hdv.converter.data.Population;
import de.ifcore.hdv.converter.parser.AccountParser;
import de.ifcore.hdv.converter.parser.AreaSizeParser;
import de.ifcore.hdv.converter.parser.PopulationParser;

public class BaseConverter {

	private String incomeFile;
	private String spendingsFile;
	private String population;
	private String areaSize;
	private AreaSizeParser areaSizeParser;
	private PopulationParser populationParser;
	private AccountParser incomeParser;
	private AccountParser spendingsParser;
	protected Map<String, Population> parsedPopulation;
	protected Map<String, Double> parsedArea;
	protected List<Account> parsedIncome;
	protected List<Account> parsedSpendings;

	public BaseConverter(String incomeFile, String spendingsFile, String population, String areaSize) {
		this.incomeFile = incomeFile;
		this.spendingsFile = spendingsFile;
		this.population = population;
		this.areaSize = areaSize;
	}

	public MergedData createMergedData() throws FileNotFoundException {
		readData();
		parseData();
		processData();
		DataMerger dataMerger = new DataMerger();
		return dataMerger.mergeData(parsedPopulation, parsedArea, parsedIncome, parsedSpendings);
	}

	protected void processData() {
	}

	private void parseData() {
		parsedPopulation = populationParser.parse();
		parsedArea = areaSizeParser.parse();
		parsedIncome = incomeParser.parse();
		parsedSpendings = spendingsParser.parse();
	}

	private void readData() throws FileNotFoundException {
		areaSizeParser = new AreaSizeParser(new FileInputStream(areaSize));
		populationParser = new PopulationParser(new FileInputStream(population));
		incomeParser = new AccountParser(new FileInputStream(incomeFile));
		spendingsParser = new AccountParser(new FileInputStream(spendingsFile));
	}
}
