package junit.test.server.logic.handler;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import server.logic.handler.InputHandler;
import server.logic.handler.OutputHandler;
import server.logic.handler.model.Output;
import server.logic.tables.TitleTable;
import server.logic.tables.UserTable;

public class AddUserTest extends TestCase {
	private OutputHandler outputHandler = null;
  	private Output output = null;
  	private String newUserInfo = "TestUser@carleton.ca,password";

	@Before
	protected void setUp() {
		outputHandler = new OutputHandler();
		output = new Output("", InputHandler.WAITING);
	}

	@Test
	public void testAddingInvalidUser() {
		output = outputHandler.createUser("InvalidUser");

		assertNotEquals("Success!", output.getOutput());
	}
	
	@Test
	public void testInvalidUserNotAddedToTable() {
		int userId = UserTable.getInstance().lookup("InvalidUser");

		assertTrue(userId == -1);
	}
	
	@Test
	public void testAddingValidUser() {
		output = outputHandler.createUser(newUserInfo);

		assertEquals("Success!", output.getOutput());
	}
	  
	@Test
	public void testUserAddedToTable() {
		int userId = UserTable.getInstance().lookup("TestUser@carleton.ca");
		  
		assertTrue(userId != -1);
	}
	
	@After
	protected void tearDown() {
		outputHandler = null;
		output = null;
	}
}
