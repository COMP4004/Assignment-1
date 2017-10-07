package junit.test.server.logic.tables;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import server.logic.tables.LibrarianTable;

public class LibrarianTableTest extends TestCase {
	  private LibrarianTable librarianTable = null;
	  private String librarianName = "TestName@test.ca";
	  private String librarianPassword = "Test Password";
	  private int USER_EXISTS = 0;
	  private int USER_DOES_NOT_EXIST = 2;
	  
	  @Before
	  protected void setUp(){
		  librarianTable = LibrarianTable.getInstance();
	  }

	  @Test
	  public void testAddLibrarian() {
		  librarianTable.createLibrarian(librarianName, librarianPassword);
 
		  assertEquals(USER_EXISTS, librarianTable.checkUser(librarianName, librarianPassword));
	  }
	  
	  @Test
	  public void testRemoveUser() {
		  librarianTable.delete(librarianTable.lookup(librarianName));
		  
		  assertEquals(USER_DOES_NOT_EXIST, librarianTable.checkUser(librarianName, librarianPassword));
	  }
	  
	  @After
	  protected void tearDown() {
		  librarianTable = null;
	  }
}
