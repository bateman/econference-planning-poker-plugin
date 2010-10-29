package it.uniba.di.cdg.econference.planningpoker.test.usimport;

import static org.junit.Assert.assertEquals;


import it.uniba.di.cdg.econference.planningpoker.usimport.Fogbugz;

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



public class TestFogbugz {
	private File expectedTempXML;
	private String addr;
	private LinkedList<String> storyText;
	private LinkedList<String> id;
//	private LinkedList<String> milestone;
	private LinkedList<String> estimates;
	
	
	@Before
	public void defaultValues() throws Exception {
		expectedTempXML=new File("tempFogbugzTest.xml");
		addr="https://test-uniba.fogbugz.com/api.asp?cmd=search&cols=ixBug,hrsOrigEst,sTitle,ixFixFor,sStatus,sFixFor&token=oj35de3tameup8sirjjvmviltc5ko2";

	
		
		storyText=new LinkedList<String>();
		storyText.offer("as a tester i must try");
		storyText.offer("as a proof i must try");
		storyText.offer("as i am i must be");
		storyText.offer("as a tester i must try");
		storyText.offer("as a proof i must try");
		storyText.offer("as a new user i must try");
		
		
		id=new LinkedList<String>();
		id.offer("1");
		id.offer("2");
		id.offer("4");
		id.offer("6");
		id.offer("3");
		id.offer("5");
		
		
		estimates=new LinkedList<String>();
		estimates.offer("0");
		estimates.offer("0");
		estimates.offer("29");
		estimates.offer("450");
		estimates.offer("20");
		estimates.offer("225");
		
		
		
		

	
		
		
		
	}
	
	
	@Test
	public void retrieveOpenTickets(){
		try {
			@SuppressWarnings("unused")
			FileWriter temp1=Fogbugz.retrieveOpenTickets(addr);
			FileReader f3=new FileReader("temp.xml"); 
			FileReader f2=new FileReader("tempFogbugzTest.xml");
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

	
	
	
	
	

	
	@After
	public void deleteTempFiles(){
		File f3=new File("temp.xml");
		f3.delete();
		
	}
}
