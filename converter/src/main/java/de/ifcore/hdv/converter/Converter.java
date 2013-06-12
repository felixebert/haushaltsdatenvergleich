package de.ifcore.hdv.converter;

import de.ifcore.hdv.converter.data.MergedData;

public class Converter {

	public static void main(String[] args) {
		if (args.length == 5) {
			String incomeFile = args[0];
			String spendingsFile = args[1];
			String population = args[2];
			String areaSize = args[3];
			String outputFile = args[4];
			try {
				BaseConverter baseConverter = new BaseConverter(incomeFile, spendingsFile, population, areaSize);
				MergedData mergedData = baseConverter.createMergedData();
				baseConverter.writeData(mergedData, outputFile);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			System.out.println("Usage: <income> <spendings> <population> <areaSize> <output-file>");
	}
}
