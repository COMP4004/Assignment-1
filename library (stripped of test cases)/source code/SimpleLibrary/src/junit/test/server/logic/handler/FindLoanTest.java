package junit.test.server.logic.handler;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import server.logic.handler.InputHandler;
import server.logic.handler.model.ServerOutput;

public class FindLoanTest extends TestCase {
  	private InputHandler inputHandler = null;
  	private ServerOutput serverOutput = null;

	@Before
	protected void setUp() {
		inputHandler = new InputHandler();
		serverOutput = new ServerOutput("", InputHandler.WAITING);
	}

	@Test
	public void testNoExistingLoan() {
		serverOutput = inputHandler.processInput("1,0", InputHandler.FIND_LOAN);

		// Test screen output
		assertEquals("Loan does not exist!", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@Test
	public void testExistingLoan() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		serverOutput = inputHandler.processInput("0,0", InputHandler.FIND_LOAN);
	
		// Test screen output
		assertEquals("[0,9781442668584,1,"+dateFormat.format(new Date())+",0]", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@After
	protected void tearDown() {
		inputHandler = null;
		serverOutput = null;
	}
}
