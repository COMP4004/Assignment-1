package junit.test.server.logic.handler.suites.runner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import junit.test.server.logic.handler.suites.AddItemTestSuite;

public class AddItemTestSuiteRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(AddItemTestSuite.class);

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println("AddItemTestSuite passed! Result --> " + result.wasSuccessful());
   }
}
