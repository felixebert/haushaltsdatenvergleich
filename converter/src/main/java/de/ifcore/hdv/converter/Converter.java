package de.ifcore.hdv.converter;

import java.io.FileInputStream;
import java.io.FileWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.ifcore.hdv.converter.data.MergedData;

public class Converter {

	public static void main(String[] args) {
		if (args.length == 4) {
			String incomeFile = args[0];
			String spendingsFile = args[1];
			String population = args[2];
			String areaSize = args[3];
			try {
				AreaSizeParser areaSizeParser = new AreaSizeParser(new FileInputStream(areaSize));
				PopulationParser populationParser = new PopulationParser(new FileInputStream(population));
				AccountParser incomeParser = new AccountParser(new FileInputStream(incomeFile));
				AccountParser spendingsParser = new AccountParser(new FileInputStream(spendingsFile));
				DataMerger dataMerger = new DataMerger();
				MergedData mergedData = dataMerger.mergeData(populationParser.parse(), areaSizeParser.parse(),
						incomeParser.parse(), spendingsParser.parse());
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mergedData);
				FileWriter fw = new FileWriter("hdv.json");
				fw.write(json);
				fw.flush();
				fw.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			System.out.println("Usage: <income> <spendings> <population> <areaSize>");
	}
}
