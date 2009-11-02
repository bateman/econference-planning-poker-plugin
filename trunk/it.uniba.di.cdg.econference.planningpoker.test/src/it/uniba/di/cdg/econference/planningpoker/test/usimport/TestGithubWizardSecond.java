package it.uniba.di.cdg.econference.planningpoker.test.usimport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import it.uniba.di.cdg.econference.planningpoker.usimport.wizard.GithubWizardSecond;

import org.junit.Before;
import org.junit.Test;


public class TestGithubWizardSecond {
		
private String projectName,username,expectedURL;
private boolean ob,cb;

	
	
	
	@Before
	public void defaultValues() throws Exception {
		
		projectName="test-uniba";
		username="antogrim2";
		ob=true;
		cb=true;
		expectedURL="http://github.com/api/v2/xml/issues/list/antogrim2/test-uniba/@alltickets@";
	}

	@Test
	public void makeURL1(){
		GithubWizardSecond gwS=new GithubWizardSecond();
		assertEquals(expectedURL,gwS.makeURL(projectName,username,ob,cb));
	}
	
	@Test
	public void makeURL2(){
		GithubWizardSecond gwS=new GithubWizardSecond();
		ob=true;
		cb=false;
		expectedURL="http://github.com/api/v2/xml/issues/list/antogrim2/test-uniba/open";
		assertEquals(expectedURL,gwS.makeURL(projectName,username,ob,cb));
	}
	
	@Test
	public void makeURL3(){
		GithubWizardSecond gwS=new GithubWizardSecond();
		ob=false;
		cb=false;
		assertNull(gwS.makeURL(projectName,username,ob,cb));
	}
	
	
}
