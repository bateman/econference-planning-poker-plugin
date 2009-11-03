package it.uniba.di.cdg.econference.planningpoker.test.usimport;

import static org.junit.Assert.assertEquals;

import it.uniba.di.cdg.econference.planningpoker.usimport.GoogleCode;

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



public class TestGoogleCode {
	private File expectedTempXML;
	private FileReader expectedTempCSV;

	private String addr;
	private LinkedList<String> note;
	private LinkedList<String> storyText;
	private LinkedList<String> id;
	private LinkedList<String> idC;
	private LinkedList<String> milestone;
	private LinkedList<String> clearedMilestone;
	
	
	@Before
	public void defaultValues() throws Exception {
		expectedTempXML=new File("tempGoogleCodeTest.xml");
		expectedTempCSV=new FileReader("tempGoogleCodeTest.csv");
		addr="http://code.google.com/p/test-uniba/issues/csv?can=1&q=status=new OR status=accepted OR status=started OR status=fixed OR status=verified OR status=invalid OR status=duplicate OR status=wontfix OR status=done type=enhancement OR type=task OR type=Story &colspec=ID+Type+Status+Milestone+Summary";
		
		note=new LinkedList<String>();
		note.offer("As a tester I want to download this story");
		note.offer("As a user I want to read...");
		note.offer("As a tester I want to insert this story without Milestone");
		note.offer("Another us without milestone");
		note.offer("Another US with Milestone");
		note.offer("testing new issue");
		
		storyText=new LinkedList<String>();
		storyText.offer("first user story");
		storyText.offer("second user story");
		storyText.offer("Third user story");
		storyText.offer("Fourth User story");
		storyText.offer("Fifth US");
		storyText.offer("test new issue");
		
		id=new LinkedList<String>();
		id.offer("1");
		id.offer("2");
		id.offer("3");
		id.offer("4");
		id.offer("5");
		id.offer("6");
		
		idC=new LinkedList<String>();
		idC.offer("1");
		idC.offer("2");
		idC.offer("3");
		idC.offer("4");
		idC.offer("5");
		idC.offer("6");
		
		milestone=new LinkedList<String>();
		milestone.offer("Release1.0");
		milestone.offer("Release1.0");
		milestone.offer("");
		milestone.offer("");
		milestone.offer("Release2.0");
		milestone.offer("");

		clearedMilestone=new LinkedList<String>();
		clearedMilestone.offer("Release1.0");
		clearedMilestone.offer("");
		clearedMilestone.offer("Release2.0");
		
		
		
	}
	
	@Test
	public void downloadCSV(){
		try {
			@SuppressWarnings("unused")
			FileWriter temp=GoogleCode.downloadCSV(addr);
			FileReader f=new FileReader("temp.csv"); //from fileWriter temp
			expectedTempCSV=new FileReader("tempGoogleCodeTest.csv");
			BufferedReader readerTemp = new BufferedReader(f);
			String strTemp;
			StringWriter w = new StringWriter();

			while ((strTemp = readerTemp.readLine()) != null) {
			    w.write(strTemp);
			} 
			w.close();
			readerTemp.close();

			BufferedReader readerExpectedTemp = new BufferedReader(expectedTempCSV);
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
	
	@Test
	public void csv2xml(){
		try {
			FileReader in=new FileReader("tempGoogleCodeTest.csv");
			@SuppressWarnings("unused")
			FileWriter temp=GoogleCode.csv2xml(in);
			FileReader f=new FileReader("temp.xml"); //from fileWriter temp
			FileReader f2=new FileReader("tempGoogleCodeTest.xml");
			BufferedReader readerTemp = new BufferedReader(f);
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
	
	
	
	@Test
	public void readFile(){
		
		GoogleCode.readFile(expectedTempXML);

		
		assertEquals(storyText,GoogleCode.storyText);
		assertEquals(id,GoogleCode.id);
		assertEquals(milestone,GoogleCode.milestone);		
		assertEquals(clearedMilestone,GoogleCode.clearedMilestone);
		
	}
	
	@Test
	public void reatrieveStoryDescription(){
		
		GoogleCode.retrieveStoryDescription(addr,idC);
		assertEquals(note,GoogleCode.note);

	}
	
	@After
	public void deleteTempFiles(){
		File f=new File("temp.xml");
		f.delete();
		File f2=new File("temp.csv");
		f2.delete();
	}
}
