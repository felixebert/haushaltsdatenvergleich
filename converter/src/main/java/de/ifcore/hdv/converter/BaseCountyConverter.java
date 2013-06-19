package de.ifcore.hdv.converter;

import de.ifcore.hdv.converter.mapreduce.AccountAggregator;
import de.ifcore.hdv.converter.mapreduce.Aggregator;
import de.ifcore.hdv.converter.mapreduce.PopulationMapReduce;
import de.ifcore.hdv.converter.mapreduce.SizeMapReduce;
import de.ifcore.hdv.converter.parser.AccountParser;
import de.ifcore.hdv.converter.parser.AreaSizeParser;
import de.ifcore.hdv.converter.parser.PopulationParser;

public class BaseCountyConverter extends BaseConverter {

	public BaseCountyConverter(AreaSizeParser areaSizeParser, PopulationParser populationParser,
			AccountParser incomeParser, AccountParser spendingsParser) {
		super(areaSizeParser, populationParser, incomeParser, spendingsParser);
	}

	@Override
	protected void processData() {
		parsedIncome = AccountAggregator.aggregate(parsedIncome);
		parsedSpendings = AccountAggregator.aggregate(parsedSpendings);
		parsedPopulation = Aggregator.aggregateMap(parsedPopulation, new PopulationMapReduce());
		parsedArea = Aggregator.aggregateMap(parsedArea, new SizeMapReduce());
	}
}
