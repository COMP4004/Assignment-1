package junit.test.server.logic.handler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import server.logic.handler.InputHandler;
import server.logic.handler.model.ServerOutput;

public class RemoveUserTest extends TestCase {
  	private InputHandler inputHandler = null;
  	private ServerOutput serverOutput = null;

	@Before
	protected void setUp() {
		inputHandler = new InputHandler();
		serverOutput = new ServerOutput("", InputHandler.WAITING);
	}

	@Test
	public void testRemoveInvalidUser() {
		serverOutput = inputHandler.processInput("7", InputHandler.REMOVE_USER);

		// Test screen output
		assertEquals("User does not exist.", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@Test
	public void testRemoveValidUser() {
		serverOutput = inputHandler.processInput("3", InputHandler.REMOVE_USER);

		// Test screen output
		assertEquals("Success!", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@After
	protected void tearDown() {
		inputHandler = null;
		serverOutput = null;
	}
}
