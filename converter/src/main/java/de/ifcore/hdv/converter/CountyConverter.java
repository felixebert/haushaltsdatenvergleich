package de.ifcore.hdv.converter;

import java.io.FileInputStream;

import de.ifcore.hdv.converter.data.MergedData;
import de.ifcore.hdv.converter.parser.AccountParser;
import de.ifcore.hdv.converter.parser.AreaSizeParser;
import de.ifcore.hdv.converter.parser.CountyAccountParser;
import de.ifcore.hdv.converter.parser.PopulationParser;
import de.ifcore.hdv.converter.split.DataSplit;
import de.ifcore.hdv.converter.split.DataSplitter;
import de.ifcore.hdv.converter.utils.Utils;

public class CountyConverter {

	public static void main(String[] args) {
		if (args.length == 5) {
			String incomeFile = args[0];
			String spendingsFile = args[1];
			String population = args[2];
			String areaSize = args[3];
			String outputDir = args[4];
			try {
				AreaSizeParser areaSizeParser = new AreaSizeParser(new FileInputStream(areaSize));
				PopulationParser populationParser = new PopulationParser(new FileInputStream(population));
				AccountParser incomeParser = new CountyAccountParser(Utils.readCsvFile(incomeFile));
				AccountParser spendingsParser = new CountyAccountParser(Utils.readCsvFile(spendingsFile));
				BaseConverter baseConverter = new BaseCountyConverter(areaSizeParser, populationParser, incomeParser,
						spendingsParser);
				MergedData mergedData = baseConverter.createMergedData();
				DataSplitter splitter = new DataSplitter(mergedData);
				DataSplit split = splitter.split();
				Utils.writeSplittedData(split, outputDir);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			System.out.println("Usage: <income> <spendings> <population> <areaSize> <output>");
	}
}
