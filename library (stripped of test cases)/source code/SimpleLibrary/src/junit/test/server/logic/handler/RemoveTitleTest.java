package junit.test.server.logic.handler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import server.logic.handler.InputHandler;
import server.logic.handler.model.ServerOutput;

public class RemoveTitleTest extends TestCase {
  	private InputHandler inputHandler = null;
  	private ServerOutput serverOutput = null;

	@Before
	protected void setUp() {
		inputHandler = new InputHandler();
		serverOutput = new ServerOutput("", InputHandler.WAITING);
	}

	@Test
	public void testRemoveInvalidTitle() {
		serverOutput = inputHandler.processInput("1234567890123", InputHandler.REMOVE_TITLE);

		// Test screen output
		assertEquals("Title does not exist.", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@Test
	public void testRemoveValidTitle() {
		serverOutput = inputHandler.processInput("9781442667181", InputHandler.REMOVE_TITLE);

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
