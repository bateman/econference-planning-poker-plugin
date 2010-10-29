package it.uniba.di.cdg.econference.planningpoker.test.usimport;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;




import it.uniba.di.cdg.econference.planningpoker.usimport.Jira;


public class TestJira {
	private File expectedTemp;
	private String addr;
	private LinkedList<String> storyText;
	private LinkedList<String> id;
	private LinkedList<String> idC;
	private LinkedList<String> milestone;
	private LinkedList<String> note;
	private LinkedList<String> estimates;
	private LinkedList<String> clearedMilestone;
	
	
	@Before
	public void defaultValues() throws Exception {
		expectedTemp=new File("tempJiraTest.xml");
		addr="http://jira.atlassian.com/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml?jqlQuery=project+%3D+TST+AND+reporter+%3D+luigi87&tempMax=200";
		
		
		
		storyText=new LinkedList<String>();
		storyText.offer("As a tester i try this task");
		storyText.offer("as a student i must be up");
		storyText.offer("As a student must try this feature");
		storyText.offer("As a student i must make this exam");
		storyText.offer("bye bye");
		storyText.offer("test-uniba");
		
		id=new LinkedList<String>();
		id.offer("TST-23201");
		id.offer("TST-23142");
		id.offer("TST-22398");
		id.offer("TST-22363");
		id.offer("TST-22355");
		id.offer("TST-22353");
		
		idC=new LinkedList<String>();
		idC.offer("TST-23201");
		idC.offer("TST-23142");
		idC.offer("TST-22398");
		idC.offer("TST-22363");
		idC.offer("TST-22355");
		idC.offer("TST-22353");
		
		estimates=new LinkedList<String>();
		estimates.offer("1051200");
		estimates.offer("1051200");
		estimates.offer("1051200");
		estimates.offer("144000");
		estimates.offer("144000");
		estimates.offer("115200");
		
		
		milestone=new LinkedList<String>();
		milestone.offer("Release1.0");
		milestone.offer("Release1.0");
		milestone.offer("");
		milestone.offer("");
		milestone.offer("Release2.0");
		milestone.offer("");
		
		note=new LinkedList<String>();
		note.offer("As a tester I want to download this story");
		note.offer("As a user I want to read...");
		note.offer("As a tester I want to insert this story without Milestone");
		note.offer("Another us without milestone");
		note.offer("Another US with Milestone");
		note.offer("testing new issue");
		
		clearedMilestone=new LinkedList<String>();
		clearedMilestone.offer("Release1.0");
	}
	
	
	@Test
	public void retrieveOpenTickets(){
		try {
			@SuppressWarnings("unused")
			FileWriter temp=Jira.retrieveOpenTickets(addr);
			FileReader f3=new FileReader("temp.xml"); 
			FileReader f2=new FileReader(expectedTemp);
			BufferedReader readerTemp = new BufferedReader(f3);
			String strTemp;
			StringWriter w = new StringWriter();

			while ((strTemp = readerTemp.readLine()) != null) {
				w.write(strTemp);
			} 
			w.close();
			readerTemp.close();

			BufferedReader readerExpectedTemp = new BufferedReader(f2);
			String strExpectedTemp;
			StringWriter w2 = new StringWriter();

			while ((strExpectedTemp = readerExpectedTemp.readLine()) != null) {
				w2.write(strExpectedTemp);
			} 
			w2.close();
			readerExpectedTemp.close();

			assertEquals(w.toString(),w2.toString());
		}catch (IOException e){}
	}
/*	@Test
	public void readFile(){
		
		assertEquals(estimates,Jira.estimates);
		assertEquals(storyText,Jira.storyText);
		assertEquals(id,Jira.id);
		assertEquals(milestone,Jira.milestone);
		assertEquals(clearedMilestone,Jira.clearedMilestone);
	}*/
	
	
	
	
	@After
	public void deleteTempFiles(){
		File f3=new File("temp.xml");
		f3.delete();
		
	}
}
