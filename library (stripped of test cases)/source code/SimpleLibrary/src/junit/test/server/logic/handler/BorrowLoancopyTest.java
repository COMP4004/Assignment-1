package junit.test.server.logic.handler;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import server.logic.handler.InputHandler;
import server.logic.handler.OutputHandler;
import server.logic.handler.model.Output;
import server.logic.handler.model.ServerOutput;
import server.logic.model.Loan;
import server.logic.tables.ItemTable;
import server.logic.tables.LoanTable;
import server.logic.tables.TitleTable;

public class BorrowLoancopyTest extends TestCase {
  	private InputHandler inputHandler = null;
  	private ServerOutput serverOutput = null;

	@Before
	protected void setUp() {
		inputHandler = new InputHandler();
		serverOutput = new ServerOutput("", InputHandler.WAITING);
		LoanTable.getInstance().getLoanTable().clear();
	}

	@Test
	public void testBorrowWithInvalidInput() {
		serverOutput = inputHandler.processInput("invalid input", InputHandler.BORROW_LOANCOPY);
		  
		// Test screen output
		assertEquals("Your input should be in this format:'userID,itemID'", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@Test
	public void testBorrowWithInvalidUserId() {
		serverOutput = inputHandler.processInput("123,123", InputHandler.BORROW_LOANCOPY);
		  
		// Test screen output
		assertEquals("User does not exist.", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	  
	@Test
	public void testBorrowWithInvalidItemId() {
		serverOutput = inputHandler.processInput("0,123", InputHandler.BORROW_LOANCOPY);
		  
		// Test screen output
		assertEquals("Item does not exist.", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@Test
	public void testBorrowWithNoPrivilage() {
		// Attempt to borrow a book when the user has already exceeded MAX_BORROWED_ITEMS.
	 	Loan loan=new Loan(1,"9781442667181","1",new Date(),"0");
	 	Loan loan2=new Loan(1,"9781442616899","1",new Date(),"0");
	 	Loan loan3=new Loan(1,"9781442668584","1",new Date(),"0");
		
	 	LoanTable.getInstance().getLoanTable().add(loan);
	 	LoanTable.getInstance().getLoanTable().add(loan2);
	 	LoanTable.getInstance().getLoanTable().add(loan3);
	 	
		serverOutput = inputHandler.processInput("1,3", InputHandler.BORROW_LOANCOPY);
		  
		// Test screen output
		assertEquals("The Maximun Number of Items is Reached!", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@Test
	public void testBorrowWithBookOnLoan() {
		Loan loan=new Loan(1,"9781442668584","1",new Date(),"0");
		
	 	LoanTable.getInstance().getLoanTable().add(loan);
		
		// Attempt to borrow a book that is on loan.	 	
		serverOutput = inputHandler.processInput("0,0", InputHandler.BORROW_LOANCOPY);
		  
		// Test screen output
		assertEquals("The Item is Not Available!", serverOutput.getOutput());
		// Test system state
		assertEquals(InputHandler.LIBRARIAN, serverOutput.getState());
	}
	
	@Test
	public void testBorrowLoanValid() {		
		ItemTable.getInstance().createitem("1234567890123");
		TitleTable.getInstance().createtitle("1234567890123", "Test title");
		
		serverOutput = inputHandler.processInput("2,3", InputHandler.BORROW_LOANCOPY);
		  
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
