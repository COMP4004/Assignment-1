package junit.test.server.logic.handler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import server.logic.handler.InputHandler;
import server.logic.handler.OutputHandler;
import server.logic.handler.model.Output;
import server.logic.tables.TitleTable;

public class AddTitleTest extends TestCase {
  	private OutputHandler outputHandler = null;
  	private Output output = null;
  	private String newTitleISBN = "1234567890123";

	@Before
	protected void setUp() {
		outputHandler = new OutputHandler();
		output = new Output("", InputHandler.WAITING);
	}

	@Test
	public void testAddingTitle() {
		output = outputHandler.createTitle(newTitleISBN + ",Test title");

		assertEquals("Success!", output.getOutput());
	}
	  
	@Test
	public void testTitleAddedToTable() {
		boolean titleAdded = TitleTable.getInstance().lookup(newTitleISBN);
		  
		assertTrue(titleAdded);
	}
	
	@After
	protected void tearDown() {
		outputHandler = null;
		output = null;
	}
}
