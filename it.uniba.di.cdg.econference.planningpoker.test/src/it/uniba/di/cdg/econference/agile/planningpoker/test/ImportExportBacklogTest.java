package it.uniba.di.cdg.econference.agile.planningpoker.test;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultBacklogContextLoader;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory;
import it.uniba.di.cdg.econference.planningpoker.utils.XMLUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

public class ImportExportBacklogTest extends TestCase {

	private DefaultBacklogContextLoader backlogLoader;
	private FileInputStream is;
	private FileInputStream is2;
	private Document doc;
	private Document doc2;
	private Backlog backlog;
	private Backlog backlog2;


	@Before
	public void setUp() throws Exception {
		backlogLoader = new DefaultBacklogContextLoader();
		is = new FileInputStream( "META-INF/StandardInput.xml" );
		doc = XMLUtils.loadDocument(is);
		backlog = backlogLoader.load(doc);
		
		String output = XMLUtils.convertDefaultBacklogToStandardXML(backlog);		
		OutputStreamWriter osw = new OutputStreamWriter( new FileOutputStream( "META-INF/StandardOutput.xml" ) );
		osw.write( output );
		osw.close();
		is2 = new FileInputStream("META-INF/StandardOutput.xml");
		doc2 = XMLUtils.loadDocument(is2);
		backlog2 = backlogLoader.load(doc2);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testInputBacklogEqualsOutputBacklog() {		
		assertEquals(backlog.size(), backlog2.size());
		DefaultUserStory inputStory;
		DefaultUserStory outputStory;
		for (int i = 0; i < backlog.size(); i++) {
			inputStory = (DefaultUserStory) backlog.getUserStory(i);
			outputStory = (DefaultUserStory) backlog2.getUserStory(i);
			compareStory(inputStory,outputStory );
		}			
	}
	
	
	private void compareStory(DefaultUserStory inputStory, DefaultUserStory outputStory){
		
		assertEquals(inputStory.getId(), outputStory.getId());
		assertEquals(inputStory.getEstimate(), outputStory.getEstimate());
		assertEquals(inputStory.getStoryText(), outputStory.getStoryText());
		assertEquals(inputStory.getNotes(), outputStory.getNotes());
		assertEquals(inputStory.getMilestoneId(), outputStory.getMilestoneId());
		assertEquals(inputStory.getMilestoneName(), outputStory.getMilestoneName());
		assertEquals(inputStory.getMilestoneCreationDate(), outputStory.getMilestoneCreationDate());
	}
	
	@After
	public void deleteTempFiles(){
		File f2=new File("META-INF/StandardOutput.xml");
		f2.delete();
	}
	
	
	
	
	

}
