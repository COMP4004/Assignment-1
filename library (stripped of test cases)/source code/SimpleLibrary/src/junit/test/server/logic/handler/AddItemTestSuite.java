package junit.test.server.logic.handler;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.test.server.logic.tables.TitleTableTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  TitleTableTest.class, // Simulate Find Title use case.
  AddTitleTest.class, // Simulate Add Title use case.
})

public class AddItemTestSuite {   } 
