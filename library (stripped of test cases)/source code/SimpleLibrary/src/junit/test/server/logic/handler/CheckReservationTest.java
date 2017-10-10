package junit.test.server.logic.handler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import server.logic.handler.InputHandler;
import server.logic.handler.model.ServerOutput;

public class CheckReservationTest  extends TestCase {
  	private InputHandler inputHandler = null;
  	private ServerOutput serverOutput = null;

	@Before
	protected void setUp() {
		inputHandler = new InputHandler();
		serverOutput = new ServerOutput("", InputHandler.WAITING);
	}

	@Test
	public void testBookOnLoan() {
		serverOutput = inputHandler.processInput("9781442668584,1", InputHandler.CHECK_RESERVATION);

		// Test screen output
		assertEquals("The title is on loan.", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@Test
	public void testBookNotOnLoan() {
		serverOutput = inputHandler.processInput("9781442616899,1", InputHandler.CHECK_RESERVATION);

		// Test screen output
		assertEquals("The title is free to loan.", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@After
	protected void tearDown() {
		inputHandler = null;
		serverOutput = null;
	}
}
