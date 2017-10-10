package junit.test.server.logic.tables;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import server.logic.handler.InputHandler;
import server.logic.handler.OutputHandler;
import server.logic.handler.model.Output;
import server.logic.handler.model.ServerOutput;

/**
 * Add Item use case tests.
 * @author Alexei
 */
public class TitleTableTest extends TestCase {
	  	private OutputHandler outputHandler = null;
	  	private Output output = null;
	
		@Before
		protected void setUp() {
			outputHandler = new OutputHandler();
			output = new Output("", InputHandler.WAITING);
		}
	
		@Test
		public void testFindExistingTitle() {
			 output = outputHandler.findTitle("9781442668584");
			  
			// Test that the book ISBN was found.
			assertEquals(OutputHandler.TITLE_EXISTS, output.getState());
		}
		  
		@Test
		public void testFindNonExistingTitle() {
			Output output = outputHandler.findTitle("1234567890123");
			  
			// Test that the book ISBN was not found.
			assertEquals(OutputHandler.TITLE_DOESNT_EXIST, output.getState());
		}
		
		@After
		protected void tearDown() {
			outputHandler = null;
			output = null;
		}
}
