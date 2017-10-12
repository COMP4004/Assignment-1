package junit.test.server.logic.handler;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import server.logic.handler.InputHandler;
import server.logic.handler.model.ServerOutput;
import server.logic.model.Loan;
import server.logic.tables.FeeTable;
import server.logic.tables.LoanTable;

public class RenewLoanTest extends TestCase {
  	private InputHandler inputHandler = null;
  	private ServerOutput serverOutput = null;

	@Before
	protected void setUp() {
		inputHandler = new InputHandler();
		serverOutput = new ServerOutput("", InputHandler.WAITING);
	}

	@Test
	public void testRenewNonExistantLoan() {
		serverOutput = inputHandler.processInput("0,0", InputHandler.RENEW_LOAN_PRINT_LOAN);

		// Test screen output
		assertEquals("Loan does not exist!", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@Test
	public void testRenewNoUserPrivilege() {
		// Attempt to renew a book when the user has already exceeded MAX_BORROWED_ITEMS.
	 	Loan loan=new Loan(0,"9781442667181","1",new Date(),"0");
	 	Loan loan2=new Loan(0,"9781442616899","1",new Date(),"0");
	 	Loan loan3=new Loan(0,"9781442668584","1",new Date(),"0");
		
	 	LoanTable.getInstance().getLoanTable().add(loan);
	 	LoanTable.getInstance().getLoanTable().add(loan2);
	 	LoanTable.getInstance().getLoanTable().add(loan3);
	 	
		serverOutput = inputHandler.processInput("0,0", InputHandler.RENEW_LOAN);

		// Test screen output
		assertEquals("Limit reached. Unable to renew the loan!", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@Test
	public void testRenewWithFine() {
		LoanTable.getInstance().getLoanTable().clear();
		
		serverOutput = inputHandler.processInput("0,3", InputHandler.RENEW_LOAN);

		// Test screen output
		assertEquals("The user has a fine!", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.RENEW_LOAN_COLLECT_FINE, serverOutput.getState());
	}
	
	@Test
	public void testRenewWhenBookIsReserved() {
		Loan loan=new Loan(1,"9781442668584","1",new Date(),"0");
		
	 	LoanTable.getInstance().getLoanTable().add(loan);
	 	FeeTable.getInstance().getFeeTable().clear();
		
		serverOutput = inputHandler.processInput("0,0", InputHandler.RENEW_LOAN);

		// Test screen output
		assertEquals("The title is reserved. Unable to renew!", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}

	@Test
	public void testSuccessfulRenewal() {
		Loan loan=new Loan(3,"9781442616899","1",new Date(),"0");
		
	 	LoanTable.getInstance().getLoanTable().add(loan);
		
	 	serverOutput = inputHandler.processInput("3,1", InputHandler.RENEW_LOAN);

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
