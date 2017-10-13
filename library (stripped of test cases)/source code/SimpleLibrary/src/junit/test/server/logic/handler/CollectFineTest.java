package junit.test.server.logic.handler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import server.logic.handler.InputHandler;
import server.logic.handler.model.ServerOutput;
import server.logic.tables.LoanTable;

public class CollectFineTest extends TestCase {
  	private InputHandler inputHandler = null;
  	private ServerOutput serverOutput = null;

	@Before
	protected void setUp() {
		inputHandler = new InputHandler();
		serverOutput = new ServerOutput("", InputHandler.WAITING);
	}

	@Test
	public void testInValidCollectFineNoSuchUser() {
		serverOutput = inputHandler.processInput("5", InputHandler.COLLECT_FINE);
		  
		// Test screen output
		assertEquals(serverOutput.getOutput(), "User does not exist.");
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@Test
	public void testValidCollectFine() {
		serverOutput = inputHandler.processInput("0", InputHandler.COLLECT_FINE);
		  
		// Test screen output
		assertEquals("success", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@After
	protected void tearDown() {
		inputHandler = null;
		serverOutput = null;
	}
}
