package de.ifcore.hdv.converter;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccountClassesTest {

	@Test
	public void itShouldReadAccountClasses() throws Exception {
		AccountClasses accountClasses = AccountClasses.getInstance();
		assertFalse(accountClasses.getMainAccountClasses().isEmpty());
		assertFalse(accountClasses.getSubAccountClasses().isEmpty());
	}
}
