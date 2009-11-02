package it.uniba.di.cdg.econference.planningpoker.test.usimport;

import static org.junit.Assert.assertEquals;

import it.uniba.di.cdg.econference.planningpoker.usimport.wizard.GoogleCodeWizardThird;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;



public class TestGoogleCodeWizardThird {
	
	private String projectName,expectedURL;
	private LinkedList<String> status,type;

	@Before
	public void defaultValues() throws Exception {
		
		projectName="test-uniba";
		status=new LinkedList<String>();
		type=new LinkedList<String>();
		expectedURL="http://code.google.com/p/test-uniba/issues/csv?can=1&q=status=new OR status=accepted OR status=started OR status=fixed OR status=verified OR status=invalid OR status=duplicate OR status=wontfix OR status=done type=Story&colspec=ID+Type+Status+Milestone+Summary";
	}

	@Test
	public void makeURL1(){
		GoogleCodeWizardThird gcwS=new GoogleCodeWizardThird();
		status.offer("new");
		status.offer("accepted");
		status.offer("started");
		status.offer("fixed");
		status.offer("verified");
		status.offer("invalid");
		status.offer("duplicate");
		status.offer("wontfix");
		status.offer("done");
		
		type.offer("Story");
		assertEquals(expectedURL,gcwS.makeURL(projectName,status,type));
	}
	
	@Test
	public void makeURL2(){
		GoogleCodeWizardThird gcwS=new GoogleCodeWizardThird();
		
		status.offer("accepted");
		
		type.offer("enhancement");
		type.offer("task");
		type.offer("Story");
		expectedURL="http://code.google.com/p/test-uniba/issues/csv?can=1&q=status=accepted type=enhancement OR type=task OR type=Story &colspec=ID+Type+Status+Milestone+Summary";

		assertEquals(expectedURL,gcwS.makeURL(projectName,status,type));
	}
	
	

}
