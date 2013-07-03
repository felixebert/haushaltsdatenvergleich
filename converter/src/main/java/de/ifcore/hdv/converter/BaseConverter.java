package de.ifcore.hdv.converter;

import java.util.List;
import java.util.Map;

import de.ifcore.hdv.converter.data.Account;
import de.ifcore.hdv.converter.data.AccountDataAndLabels;
import de.ifcore.hdv.converter.data.MergedData;
import de.ifcore.hdv.converter.data.Population;
import de.ifcore.hdv.converter.parser.AccountParser;
import de.ifcore.hdv.converter.parser.AreaSizeParser;
import de.ifcore.hdv.converter.parser.PopulationParser;

public class BaseConverter {

	private AreaSizeParser areaSizeParser;
	private PopulationParser populationParser;
	private AccountParser incomeParser;
	private AccountParser spendingsParser;
	protected Map<String, Population> parsedPopulation;
	protected Map<String, Double> parsedArea;
	protected List<Account> parsedIncome;
	protected List<Account> parsedSpendings;

	public BaseConverter(AreaSizeParser areaSizeParser, PopulationParser populationParser, AccountParser incomeParser,
			AccountParser spendingsParser) {
		this.areaSizeParser = areaSizeParser;
		this.populationParser = populationParser;
		this.incomeParser = incomeParser;
		this.spendingsParser = spendingsParser;
	}

	public MergedData createMergedData() {
		long time = System.currentTimeMillis();
		parseData();
		System.out.println("Daten parsen: " + (System.currentTimeMillis() - time) + " ms");
		time = System.currentTimeMillis();
		processData();
		System.out.println("Daten aggregieren: " + (System.currentTimeMillis() - time) + " ms");
		time = System.currentTimeMillis();
		DataMerger dataMerger = new DataMerger();
		AccountDataAndLabels income = new AccountDataAndLabels(parsedIncome, incomeParser.getLabels());
		AccountDataAndLabels spendings = new AccountDataAndLabels(parsedSpendings, spendingsParser.getLabels());
		MergedData mergedData = dataMerger.mergeData(parsedPopulation, parsedArea, income, spendings);
		System.out.println("Daten verarbeiten: " + (System.currentTimeMillis() - time) + " ms");
		return mergedData;
	}

	protected void processData() {
	}

	private void parseData() {
		parsedPopulation = populationParser.parse();
		parsedArea = areaSizeParser.parse();
		parsedIncome = incomeParser.parse();
		parsedSpendings = spendingsParser.parse();
	}
}
