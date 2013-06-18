package de.ifcore.hdv.converter;

import de.ifcore.hdv.converter.mapreduce.AccountAggregator;
import de.ifcore.hdv.converter.mapreduce.Aggregator;
import de.ifcore.hdv.converter.mapreduce.PopulationMapReduce;
import de.ifcore.hdv.converter.mapreduce.SizeMapReduce;

public class BaseCountyConverter extends BaseConverter {

	public BaseCountyConverter(String incomeFile, String spendingsFile, String population, String areaSize) {
		super(incomeFile, spendingsFile, population, areaSize);
	}

	@Override
	protected void processData() {
		parsedIncome = AccountAggregator.aggregate(parsedIncome);
		parsedSpendings = AccountAggregator.aggregate(parsedSpendings);
		parsedPopulation = Aggregator.aggregateMap(parsedPopulation, new PopulationMapReduce());
		parsedArea = Aggregator.aggregateMap(parsedArea, new SizeMapReduce());
	}
}
