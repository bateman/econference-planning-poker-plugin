package it.uniba.di.cdg.econference.agile.planningpoker.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for it.uniba.di.cdg.econference.agile.planningpoker.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(CardDeckTest.class);
		suite.addTestSuite(VotersTest.class);
		suite.addTestSuite(BacklogTest.class);
		suite.addTestSuite(DefaultBacklogContexLoaderTest.class);
		suite.addTestSuite(ImportExportBacklogTest.class);
		//$JUnit-END$
		return suite;
	}

}
