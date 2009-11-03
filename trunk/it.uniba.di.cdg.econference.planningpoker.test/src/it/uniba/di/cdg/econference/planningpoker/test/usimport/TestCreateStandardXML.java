package it.uniba.di.cdg.econference.planningpoker.test.usimport;

import static org.junit.Assert.assertEquals;
import it.uniba.di.cdg.econference.planningpoker.usimport.CreateStandardXML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestCreateStandardXML {
	private FileReader expectedStandard;
	private LinkedList<String> note;
	private LinkedList<String> storyText;
	private LinkedList<String> id;
	private LinkedList<String> milestone;
	private LinkedList<String> estimates;
	private LinkedList<String> milestoneDesc;
	private LinkedList<String> milestoneDate;
	private LinkedList<String> milestoneTitle;
	
	
	@Before
	public void defaultValues() throws Exception {
		expectedStandard=new FileReader("StandardInput.xml");
		note=new LinkedList<String>();
		note.offer("First User Story");
		note.offer("Importing from assembla.com");
		note.offer("As a tester, I'm testing import US");
		note.offer("As a tester...");
		note.offer("Test US no milestone");
		note.offer("testing us....");
		
		storyText=new LinkedList<String>();
		storyText.offer("As a tester, I want to show featured projects as userstory");
		storyText.offer("As a user, I want to import user stories from assembla");
		storyText.offer("Testing import");
		storyText.offer("another test");
		storyText.offer("Test without milestone");
		storyText.offer("Test US second milestone");
		
		id=new LinkedList<String>();
		id.offer("522682");
		id.offer("522691");
		id.offer("525321");
		id.offer("525404");
		id.offer("528656");
		id.offer("541453");
		
		milestone=new LinkedList<String>();
		milestone.offer("120919");
		milestone.offer("120919");
		milestone.offer("110320");
		milestone.offer("110320");
		milestone.offer("");
		milestone.offer("113258");
		
		milestoneDate=new LinkedList<String>();
		milestoneDate.offer("2009-11-03T14:11:06+00:00");
		milestoneDate.offer("2008-11-12T04:22:47+00:00");
		milestoneDate.offer("2009-10-02T10:01:22+00:00");
		
		milestoneDesc=new LinkedList<String>();
		milestoneDesc.offer("First Milestone Desc");
		milestoneDesc.offer("Put your to-do's and feature requests here.  Pull them into the current milestone when you are ready to work on them.");
		milestoneDesc.offer("Second milestone for testing");
		
		milestoneTitle=new LinkedList<String>();
		milestoneTitle.offer("First Milestone");
		milestoneTitle.offer("Backlog");
		milestoneTitle.offer("Second milestone");
		
		estimates=new LinkedList<String>();
		estimates.offer("");
		estimates.offer("");
		estimates.offer("");
		estimates.offer("");
		estimates.offer("");
		estimates.offer("");
		
	}
	
	
	
	@Test
	public void createStandardFile(){
		CreateStandardXML c=new CreateStandardXML(storyText,note,id,estimates,milestone,milestoneDesc,milestoneDate,milestoneTitle);
		c.saveFile("StandardOutput.xml");
		try {
			FileReader standard = new FileReader("StandardOutput.xml");
			BufferedReader readerTemp = new BufferedReader(standard);
			String strTemp;
			StringWriter w = new StringWriter();

			while ((strTemp = readerTemp.readLine()) != null) {
			    w.write(strTemp);
			} 
			w.close();
			readerTemp.close();

			BufferedReader readerExpectedTemp = new BufferedReader(expectedStandard);
			String strExpectedTemp;
			StringWriter w2 = new StringWriter();

			while ((strExpectedTemp = readerExpectedTemp.readLine()) != null) {
			    w2.write(strExpectedTemp);
			} 
			w2.close();
			readerExpectedTemp.close();
			
			assertEquals(w.toString(),w2.toString());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e){}
		
		
		
	}
	
	@After
	public void deleteTempFiles(){
		File f2=new File("StandardOutput.xml");
		f2.delete();
	}
	
}
