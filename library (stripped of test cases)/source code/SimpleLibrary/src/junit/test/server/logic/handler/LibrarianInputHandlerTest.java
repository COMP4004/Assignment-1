package junit.test.server.logic.handler;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import server.logic.handler.InputHandler;
import server.logic.handler.OutputHandler;
import server.logic.handler.model.Output;
import server.logic.handler.model.ServerOutput;
import server.logic.tables.LibrarianTable;
import server.logic.tables.TitleTable;
import server.logic.tables.UserTable;

public class LibrarianInputHandlerTest extends TestCase {
	  private InputHandler inputHandler = null;
	  private ServerOutput output = null;
	  
	  @Before
	  protected void setUp(){
		  inputHandler = new InputHandler();
		  output = new ServerOutput("", InputHandler.WAITING);
	  }
	  
	  @Test
	  public void testAddItem() {
		  output = inputHandler.processInput("add item", InputHandler.LIBRARIAN);
		  
		  // Test screen output
		  assertEquals("Please enter the ISBN of the book to be entered:", output.getOutput());
		  // Test system state
		  assertEquals(InputHandler.ADD_ITEM, output.getState());
	  }
	  
	  @Test
	  public void testAddUser() {
		  output = inputHandler.processInput("add user", InputHandler.LIBRARIAN);
		  
		  // Test screen output
		  assertEquals("Please enter the user ID information:'username,password'", output.getOutput());
		  // Test system state
		  assertEquals(InputHandler.ADD_USER, output.getState());
	  }
	  
	  @Test
	  public void testAddTitle() {
		  output = inputHandler.processInput("add title", InputHandler.LIBRARIAN);
		  
		  // Test screen output
		  assertEquals("Please enter the ISBN of the title:", output.getOutput());
		  // Test system state
		  assertEquals(InputHandler.ADD_TITLE, output.getState());
	  }
	  
	  @Test
	  public void testBorrowLoanCopy() {
		  output = inputHandler.processInput("borrow loancopy", InputHandler.LIBRARIAN);
		  
		  // Test screen output
		  assertEquals("Please enter the user ID and item ID:'user ID,item ID'", output.getOutput());
		  // Test system state
		  assertEquals(InputHandler.BORROW_LOANCOPY, output.getState());
	  }

	  @Test
	  public void testCollectFine() {
		  output = inputHandler.processInput("collect fine", InputHandler.LIBRARIAN);
		  
		  // Test screen output
		  assertEquals("Please enter the user ID:", output.getOutput());
		  // Test system state
		  assertEquals(InputHandler.COLLECT_FINE, output.getState());
	  }
	  
	  @Test
	  public void testRemoveItem() {
		  output = inputHandler.processInput("remove item", InputHandler.LIBRARIAN);
		  
		  // Test screen output
		  assertEquals("Please enter the item ID:", output.getOutput());
		  // Test system state
		  assertEquals(InputHandler.REMOVE_ITEM, output.getState());
	  }
	  
	  @Test
	  public void testRemoveTitle() {
		  output = inputHandler.processInput("remove title", InputHandler.LIBRARIAN);
		  
		  // Test screen output
		  assertEquals("Please enter the ISBN:", output.getOutput());
		  // Test system state
		  assertEquals(InputHandler.REMOVE_TITLE, output.getState());
	  }
	  
	  @Test
	  public void testRemoveUser() {
		  output = inputHandler.processInput("remove user", InputHandler.LIBRARIAN);
		  
		  // Test screen output
		  assertEquals("Please enter the user ID:", output.getOutput());
		  // Test system state
		  assertEquals(InputHandler.REMOVE_USER, output.getState());
	  }
	  
	  @Test
	  public void testRenewLoan() {
		  output = inputHandler.processInput("renew loan", InputHandler.LIBRARIAN);
		  
		  // Test screen output
		  assertEquals("Please enter the user ID and item ID:'user ID,item ID'", output.getOutput());
		  // Test system state
		  assertEquals(InputHandler.RENEW_LOAN_PRINT_LOAN, output.getState());
	  }
	  
	  @Test
	  public void testReturnLoanCopy() {
		  output = inputHandler.processInput("return loancopy", InputHandler.LIBRARIAN);
		  
		  // Test screen output
		  assertEquals("Please enter the user ID and item ID:'user ID,item ID'", output.getOutput());
		  // Test system state
		  assertEquals(InputHandler.RETURN_LOANCOPY, output.getState());
	  }
	  
	  @Test
	  public void testMonitorSystem() {
		  output = inputHandler.processInput("monitor system", InputHandler.LIBRARIAN);
		 
		  // The system output should only contain the default users and titles.
		  String expectedSystemOutput = "------\nUsers:\n------\n" +
			  "[Name][User ID]==[Zhibo@carleton.ca][0]\n" +
			  "[Name][User ID]==[Yu@carleton.ca][1]\n" +
			  "[Name][User ID]==[Michelle@carleton.ca][2]\n" +
			  "[Name][User ID]==[Kevin@carleton.ca][3]\n" +
			  "[Name][User ID]==[Sun@carleton.ca][4]\n" +
			  "\n-------\nTitles:\n-------\n" +
			  "[Book title][ISBN]==[By the grace of God][9781442668584]\n" +
			  "[Book title][ISBN]==[Dante's lyric poetry ][9781442616899]\n" +
			  "[Book title][ISBN]==[Courtesy lost][9781442667181]\n" +
			  "[Book title][ISBN]==[Writing for justice][9781611687910]\n" +
			  "[Book title][ISBN]==[The act in context][9781317594277]\n";
		  
		  // Test screen output
		  assertEquals(expectedSystemOutput, output.getOutput());
		  // Test system state
		  assertEquals(InputHandler.LIBRARIANLOGIN, output.getState());
	  }
	  
	  @After
	  protected void tearDown() {
		  inputHandler = null;
		  output = null;
	  }
}
