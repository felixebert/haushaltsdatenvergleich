package de.ifcore.hdv.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import de.ifcore.hdv.converter.data.AccountClass;
import de.ifcore.hdv.converter.data.BalanceItem;
import de.ifcore.hdv.converter.data.Category;

public class DataMocks {

	public static SortedSet<Category> mockCategories() {
		SortedSet<Category> categories = new TreeSet<>();
		categories.add(new Category(31, "Cat3"));
		categories.add(new Category(21, "Alpha"));
		categories.add(new Category(11, "Cat1"));
		return categories;
	}

	public static List<MinMaxAccount> mockAccounts() {
		List<MinMaxAccount> accounts = new ArrayList<>();
		accounts.add(new MinMaxAccount(121, "Abc121"));
		accounts.add(new MinMaxAccount(122, "Abc122"));
		accounts.add(new MinMaxAccount(221, "Abc221"));
		accounts.add(new MinMaxAccount(321, "Abc321"));
		accounts.add(new MinMaxAccount(421, "Abc421"));
		return accounts;
	}

	public static List<BalanceItem> mockBalanceItems() {
		return Arrays.asList(new BalanceItem("05111001", "ort", "0100", "test1", Long.valueOf(1)), new BalanceItem(
				"05111002", "ort", "1000", "test2", Long.valueOf(2)), new BalanceItem("05111003", "ort", "2000",
				"test3", Long.valueOf(3)));
	}

	public static SortedSet<AccountClass> mockMainAccountClasses() {
		return new TreeSet<>(Arrays.asList(new AccountClass("0", "imma"), new AccountClass("1", "finan"),
				new AccountClass("2", "eigen"), new AccountClass("3", "verb")));
	}

	public static SortedSet<AccountClass> mockSubAccountClasses() {
		return new TreeSet<>(Arrays.asList(new AccountClass("01", "test1"), new AccountClass("10", "test2"),
				new AccountClass("20", "test3")));
	}

	public static List<String[]> mockAccountCsvData() {
		return Arrays.asList(new String[] { "GENESIS-Tabelle: Temporär" }, new String[] {
				"Auszahlungen insgesamt nach Produktgruppen", "", "", "", "" }, new String[] {
				"- Land, Gemeinden, Gemeindeverbände - Jahr", "", "", "", "" }, new String[] {
				"Finanzrechnungsstatistik der Gemeinden/GV", "", "", "", "" }, new String[] { "Auszahlungen (EUR)", "",
				"", "", "" }, new String[] { "Auszahlungskonten: Auszahlungen insgesamt", "", "", "", "" },
				new String[] { "", "", "", "", "Jahr" }, new String[] { "", "", "", "", "2009" }, new String[] { "05",
						"Nordrhein-Westfalen", "111", "Verwaltungssteuerung und Service", "7950660525" }, new String[] {
						"05", "Nordrhein-Westfalen", "121", "Statistik und Wahlen", "72717205" }, new String[] { "05",
						"Nordrhein-Westfalen", "122", "Ordnungsangelegenheiten", "929640752" }, new String[] { "05",
						"Nordrhein-Westfalen", "126", "Brandschutz", "706599561" }, new String[] { "05978040",
						"Werne, Stadt", "611", "Steuern, allg. Zuweisungen u. allg. Umlagen", "18525219" },
				new String[] { "05978040", "Werne, Stadt", "612", "Sonstige allgemeine Finanzwirtschaft", "12678523" },
				new String[] { "05978040", "Werne, Stadt", "999", "Summe aller Produktgruppen", "70535060" });
	}
}
