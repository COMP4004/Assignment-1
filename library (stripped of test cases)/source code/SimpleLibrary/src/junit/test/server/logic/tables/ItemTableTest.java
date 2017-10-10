package junit.test.server.logic.tables;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import server.logic.handler.InputHandler;
import server.logic.handler.OutputHandler;
import server.logic.handler.model.Output;
import server.logic.tables.ItemTable;

public class ItemTableTest {
	@Test
	public void testLookupISBN() {
		 String isbn = ItemTable.getInstance().lookupISBN(0);
		  
		// Test that the book ISBN was found.
		assertEquals("9781442668584", isbn);
	}
}
