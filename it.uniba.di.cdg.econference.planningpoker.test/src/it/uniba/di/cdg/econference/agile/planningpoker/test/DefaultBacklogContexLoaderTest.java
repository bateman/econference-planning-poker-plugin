package it.uniba.di.cdg.econference.agile.planningpoker.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultBacklogContextLoader;
import it.uniba.di.cdg.econference.planningpoker.utils.XMLUtils;

import java.io.FileInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

public class DefaultBacklogContexLoaderTest {

	private DefaultBacklogContextLoader backlogLoader;
	private FileInputStream is;

	@Before
	public void setUp() throws Exception {
		
		backlogLoader = new DefaultBacklogContextLoader();
		is = new FileInputStream( "Standard.xml" );
		

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoad() throws Exception {
		
		Document doc = XMLUtils.loadDocument(is);
		Backlog backlog = backlogLoader.load(doc);
		assertNotNull(backlog);
		assertTrue(backlog.getUserStories().length>0);
	}

}
