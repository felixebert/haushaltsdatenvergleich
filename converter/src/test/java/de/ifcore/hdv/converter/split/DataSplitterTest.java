package de.ifcore.hdv.converter.split;

import static org.junit.Assert.*;

import org.junit.Test;

import de.ifcore.hdv.converter.DataMocks;
import de.ifcore.hdv.converter.data.MergedData;

public class DataSplitterTest {

	@Test
	public void itShouldSplit() throws Exception {
		MergedData mergedData = DataMocks.mockMergedData();
		DataSplitter dataSplitter = new DataSplitter(mergedData);
		DataSplit dataSplit = dataSplitter.split();
		assertNotNull(dataSplit);
		assertNotNull(dataSplit.getLabels());
		assertTrue(dataSplit.getProducts().containsKey(100));
	}
}
