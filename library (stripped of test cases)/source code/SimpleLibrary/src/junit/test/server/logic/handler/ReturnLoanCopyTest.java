package junit.test.server.logic.handler;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import server.logic.handler.InputHandler;
import server.logic.handler.model.ServerOutput;
import server.logic.model.Item;
import server.logic.model.Loan;
import server.logic.tables.FeeTable;
import server.logic.tables.ItemTable;
import server.logic.tables.LoanTable;
import utilities.Config;

public class ReturnLoanCopyTest extends TestCase {
  	private InputHandler inputHandler = null;
  	private ServerOutput serverOutput = null;

	@Before
	protected void setUp() {
		inputHandler = new InputHandler();
		serverOutput = new ServerOutput("", InputHandler.WAITING);
	}

	@Test
	public void testReturnNonExistantLoan() {
		serverOutput = inputHandler.processInput("1,0", InputHandler.RETURN_LOANCOPY);

		// Test screen output
		assertEquals("Loan does not exist!", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@Test
	public void testReturnBookOverdue() {
		FeeTable.getInstance().getFeeTable().clear(); // Clear the table to check that the fee is applied.
	 	
		ItemTable.getInstance().getItemTable().clear();
		Item item = new Item(0, "9781442667181", "1");
		ItemTable.getInstance().getItemTable().add(item);
	
		LoanTable.getInstance().getLoanTable().clear();
		Loan loan = new Loan(0,"9781442667181","1",new Date(300001),"0");
	 	LoanTable.getInstance().getLoanTable().add(loan);
	 	
		serverOutput = inputHandler.processInput("0,0", InputHandler.RETURN_LOANCOPY);

		boolean feeApplied = FeeTable.getInstance().lookup(0);
		// Test that the fee has been applied.
		assertFalse(feeApplied);
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@Test
	public void testReturnWithFine() {
		LoanTable.getInstance().getLoanTable().clear();
		
		serverOutput = inputHandler.processInput("0,3", InputHandler.RENEW_LOAN);

		// Test screen output
		assertEquals("The user has a fine!", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.RENEW_LOAN_COLLECT_FINE, serverOutput.getState());
	}
	
	@Test
	public void testSuccessfulReturn() {
	 	FeeTable.getInstance().getFeeTable().clear();
		
		serverOutput = inputHandler.processInput("0,0", InputHandler.RENEW_LOAN);

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
