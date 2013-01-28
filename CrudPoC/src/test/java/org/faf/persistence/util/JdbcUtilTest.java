package org.faf.persistence.util;


import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

public class JdbcUtilTest {

	@Test
	public void testSimpleLineStatement() throws Exception {
		Assert.assertEquals(true, JdbcUtils.executeScript("testSimpleStatement.sql"));
	}
	
	@Test
	public void testMultipleLineStatement() throws Exception {
		Assert.assertEquals(true, JdbcUtils.executeScript("testComplexStatement.sql"));
	}
	
	@Test(expected=SQLException.class)
	public void testWrongStatementException() throws Exception {
		JdbcUtils.executeScript("testWrongStatement.sql");
	}
	
	@Test(expected=NullPointerException.class)
	public void testWrongFileException() throws Exception {
		JdbcUtils.executeScript("testUnexistentStatement.sql");
	}
	
	@Test
	public void testFileWithComment() throws Exception {
		Assert.assertEquals(true, JdbcUtils.executeScript("testStatementsAndComments.sql"));
	}
}
