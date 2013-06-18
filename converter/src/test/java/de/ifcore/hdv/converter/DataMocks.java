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
		return Arrays.asList(new BalanceItem("051110001", "0100", "test1", Long.valueOf(1)), new BalanceItem(
				"051110002", "1000", "test2", Long.valueOf(2)),
				new BalanceItem("051110003", "2000", "test3", Long.valueOf(3)));
	}

	public static SortedSet<AccountClass> mockMainAccountClasses() {
		return new TreeSet<>(Arrays.asList(new AccountClass("0", "imma"), new AccountClass("1", "finan"),
				new AccountClass("2", "eigen"), new AccountClass("3", "verb")));
	}

	public static SortedSet<AccountClass> mockSubAccountClasses() {
		return new TreeSet<>(Arrays.asList(new AccountClass("01", "test1"), new AccountClass("10", "test2"),
				new AccountClass("20", "test3")));
	}
}
