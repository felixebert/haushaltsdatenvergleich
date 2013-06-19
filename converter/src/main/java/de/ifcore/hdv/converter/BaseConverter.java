package de.ifcore.hdv.converter;

import java.util.List;
import java.util.Map;

import de.ifcore.hdv.converter.data.Account;
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
}
