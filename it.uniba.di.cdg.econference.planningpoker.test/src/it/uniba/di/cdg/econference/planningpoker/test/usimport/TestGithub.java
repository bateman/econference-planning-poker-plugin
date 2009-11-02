package it.uniba.di.cdg.econference.planningpoker.test.usimport;

import static org.junit.Assert.assertEquals;

import it.uniba.di.cdg.econference.planningpoker.usimport.Github;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;



public class TestGithub {
	private File expectedTemp1;
	private File expectedTemp2;
	private String addr;
	private boolean open,closed;
	private LinkedList<String> note;
	private LinkedList<String> storyText;
	private LinkedList<String> id;
	
	
	@Before
	public void defaultValues() throws Exception {
		expectedTemp1=new File("tempGithubTest.xml");
		expectedTemp2=new File("temp2GithubTest.xml");
		open=true;
		closed=true;
		addr="http://github.com/api/v2/xml/issues/list/antogrim2/test-uniba/@alltickets@";
		

		note=new LinkedList<String>();
		note.offer("As a tester I want to test us import");
		note.offer("Testin second issue");
		note.offer("another closed issue for testing");
		note.offer("Create a closed issue for testing");
		
		storyText=new LinkedList<String>();
		storyText.offer("User Story for testing");
		storyText.offer("Another user story for testing");
		storyText.offer("Another closed issue");
		storyText.offer("Closed Issue");

		
		id=new LinkedList<String>();
		id.offer("3");
		id.offer("4");
		id.offer("5");
		id.offer("6");
	}
	
	
	@Test
	public void retrieveOpenTickets(){
		try {
		@SuppressWarnings("unused")
		FileWriter temp1=Github.retrieveOpenTickets(addr);
		FileReader f=new FileReader("temp1.xml"); //from fileWriter temp1
		FileReader f2=new FileReader("tempGithubTest.xml");
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
	public void retrieveClosedTickets(){
		try {
		@SuppressWarnings("unused")
		FileWriter temp2=Github.retrieveOpenTickets(addr);
		FileReader f=new FileReader("temp2.xml"); //from fileWriter temp2
		FileReader f2=new FileReader("temp2GithubTest.xml");
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
		
		Github.readFile(open,closed,expectedTemp1,expectedTemp2);
	
		assertEquals(note,Github.note);
		assertEquals(storyText,Github.storyText);
		assertEquals(id,Github.id);

		for (int i=0; i<Github.id.size();i++){
			System.out.println(Github.id.get(i));
		}
		
		
		
	}
}
