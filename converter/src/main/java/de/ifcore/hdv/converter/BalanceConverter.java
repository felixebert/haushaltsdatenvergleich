package de.ifcore.hdv.converter;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import de.ifcore.hdv.converter.data.BalanceItem;
import de.ifcore.hdv.converter.data.BalanceSheet;
import de.ifcore.hdv.converter.utils.Utils;

public class BalanceConverter {

	public static void main(String[] args) {
		if (args.length == 2) {
			String balanceFile = args[0];
			String outputDir = args[1];
			try {
				AccountClasses accountClasses = AccountClasses.getInstance();
				BalanceParser balanceParser = new BalanceParser(Utils.readCsvFile(new FileInputStream(balanceFile)));
				List<BalanceItem> balance = balanceParser.parse();
				BalanceFilter balanceFilter = new BalanceFilter(balance);
				Set<String> allKs = balanceFilter.collectAllKs();
				for (String ks : allKs) {
					Collection<BalanceItem> itemsProKs = balanceFilter.getItemsProKs(ks);
					if (!itemsProKs.isEmpty()) {
						BalanceItem areaLabelContainer = extractAreaLabel(itemsProKs);
						BalanceSheetBuilder builder = new BalanceSheetBuilder(accountClasses.getMainAccountClasses(),
								accountClasses.getSubAccountClasses());
						BalanceSheet sheet = builder.createBalanceSheet(itemsProKs, areaLabelContainer.getAreaLabel());
						String outputFile = outputDir + File.separatorChar + ks + ".json";
						Utils.writeData(sheet, outputFile);
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			System.out.println("Usage: <bilanz datei> <ausgabeverzeichnis>");
	}

	private static BalanceItem extractAreaLabel(Collection<BalanceItem> itemsProKs) {
		return itemsProKs.iterator().next();
	}
}
