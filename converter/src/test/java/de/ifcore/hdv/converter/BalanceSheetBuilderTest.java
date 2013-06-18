package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import org.junit.Test;

import de.ifcore.hdv.converter.data.BalanceSheet;

public class BalanceSheetBuilderTest {

	@Test
	public void itShouldCreateABalanceSheet() throws Exception {
		BalanceSheetBuilder builder = new BalanceSheetBuilder(DataMocks.mockMainAccountClasses(),
				DataMocks.mockSubAccountClasses());
		BalanceSheet sheet = builder.createBalanceSheet(DataMocks.mockBalanceItems());
		assertNotNull(sheet.getAssets().get("imma"));
		assertNotNull(sheet.getAssets().get("finan"));
		assertNotNull(sheet.getLiabilities().get("eigen"));
		assertNotNull(sheet.getLiabilities().get("verb"));
	}
}
