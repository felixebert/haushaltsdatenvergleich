package de.ifcore.hdv.converter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.ifcore.hdv.converter.data.MergedData;
import de.ifcore.hdv.converter.parser.AccountParser;
import de.ifcore.hdv.converter.parser.AreaSizeParser;
import de.ifcore.hdv.converter.parser.PopulationParser;

public class BaseConverter {

	private String incomeFile;
	private String spendingsFile;
	private String population;
	private String areaSize;

	public BaseConverter(String incomeFile, String spendingsFile, String population, String areaSize) {
		this.incomeFile = incomeFile;
		this.spendingsFile = spendingsFile;
		this.population = population;
		this.areaSize = areaSize;
	}

	public MergedData createMergedData() throws FileNotFoundException {
		AreaSizeParser areaSizeParser = new AreaSizeParser(new FileInputStream(areaSize));
		PopulationParser populationParser = new PopulationParser(new FileInputStream(population));
		AccountParser incomeParser = new AccountParser(new FileInputStream(incomeFile));
		AccountParser spendingsParser = new AccountParser(new FileInputStream(spendingsFile));
		DataMerger dataMerger = new DataMerger();
		MergedData mergedData = dataMerger.mergeData(populationParser.parse(), areaSizeParser.parse(),
				incomeParser.parse(), spendingsParser.parse());
		return mergedData;
	}

	public void writeData(MergedData mergedData, String filename) throws JsonProcessingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mergedData);
		FileWriter fw = new FileWriter(filename);
		fw.write(json);
		fw.flush();
		fw.close();
	}
}
