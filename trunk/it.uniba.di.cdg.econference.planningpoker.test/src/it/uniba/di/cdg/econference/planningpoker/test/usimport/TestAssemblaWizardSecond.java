package it.uniba.di.cdg.econference.planningpoker.test.usimport;
import static org.junit.Assert.*;
import it.uniba.di.cdg.econference.planningpoker.usimport.wizard.AssemblaWizardSecond;


import org.junit.Before;
import org.junit.Test;

public class TestAssemblaWizardSecond {

	private String projectName,projectStatus,expectedURL;

	
	
	
	@Before
	public void defaultValues() throws Exception {
		
		projectName="test-uniba";
		projectStatus="4";
		expectedURL="http://www.assembla.com/spaces/test-uniba/tickets?batch=false&tickets_report_id=4&ticket_id=&commit=Go+%C2%BB";
	}

	@Test
	public void makeURL(){
		AssemblaWizardSecond awS=new AssemblaWizardSecond();
		assertEquals(expectedURL,awS.makeURL(projectName,projectStatus));
	}
	


}
