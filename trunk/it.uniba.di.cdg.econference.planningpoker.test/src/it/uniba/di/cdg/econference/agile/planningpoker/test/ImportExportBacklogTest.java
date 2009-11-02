package it.uniba.di.cdg.econference.agile.planningpoker.test;

import static org.junit.Assert.*;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultBacklogContextLoader;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.utils.XMLUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

public class ImportExportBacklogTest {

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
		is = new FileInputStream( "StandardTest.xml" );
		doc = XMLUtils.loadDocument(is);
		backlog = backlogLoader.load(doc);
		
		String output = XMLUtils.convertDefaultBacklogToStandardXML(backlog);		
		OutputStreamWriter osw = new OutputStreamWriter( new FileOutputStream( "prova2.xml" ) );
		osw.write( output );
		osw.close();
		is2 = new FileInputStream("Standard.xml");
		doc2 = XMLUtils.loadDocument(is2);
		backlog2 = backlogLoader.load(doc2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInputFileEqualsOutputFile() {
		String input = getFileContent("Standard.xml");
		String output = XMLUtils.convertDefaultBacklogToStandardXML(backlog);
		//String output = getFileContent("StandardTest.xml");
		assertEquals(input,output);
		
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
	
	
	
	
	private String getFileContent(String filePath){
		String content = null;
		try {
			FileReader input = new FileReader(filePath);
			BufferedReader reader = new BufferedReader(input);
			String line = "";
			StringWriter inputWriter = new StringWriter();

			while ((line = reader.readLine()) != null) {
				inputWriter.write(line);
			} 
			inputWriter.close();
			content = inputWriter.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

}
