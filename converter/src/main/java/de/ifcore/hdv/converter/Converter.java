package de.ifcore.hdv.converter;

import java.io.FileInputStream;

import de.ifcore.hdv.converter.data.MergedData;
import de.ifcore.hdv.converter.parser.AreaSizeParser;
import de.ifcore.hdv.converter.parser.CommuneAccountParser;
import de.ifcore.hdv.converter.parser.PopulationParser;
import de.ifcore.hdv.converter.split.DataSplit;
import de.ifcore.hdv.converter.split.DataSplitter;
import de.ifcore.hdv.converter.utils.Utils;

public class Converter {

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
				CommuneAccountParser incomeParser = new CommuneAccountParser(Utils.readCsvFile(incomeFile));
				CommuneAccountParser spendingsParser = new CommuneAccountParser(Utils.readCsvFile(spendingsFile));
				BaseConverter baseConverter = new BaseConverter(areaSizeParser, populationParser, incomeParser,
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
			System.out.println("Usage: <income> <spendings> <population> <areaSize> <output-dir>");
	}
}
