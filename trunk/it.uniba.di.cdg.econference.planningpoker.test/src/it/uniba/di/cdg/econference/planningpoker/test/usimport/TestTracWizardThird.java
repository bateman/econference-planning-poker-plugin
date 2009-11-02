package it.uniba.di.cdg.econference.planningpoker.test.usimport;

import static org.junit.Assert.assertEquals;

import it.uniba.di.cdg.econference.planningpoker.usimport.wizard.TracWizardThird;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;



public class TestTracWizardThird {
	private String projectURL,expectedURL;
	private LinkedList<String> status,type;

	@Before
	public void defaultValues() throws Exception {
		
		projectURL="http://econf.di.uniba.it/econference-over-ecf";
		status=new LinkedList<String>();
		type=new LinkedList<String>();
		expectedURL="http://econf.di.uniba.it/econference-over-ecf/query?status=accepted&order=priority&col=id&col=summary&col=status&col=owner&col=type&col=priority&col=milestone&type=task";
	}

	@Test
	public void makeURL1(){
		TracWizardThird twT=new TracWizardThird();
		status.offer("accepted");
		type.offer("task");
		assertEquals(expectedURL,twT.makeURL(projectURL,status,type));
	}
	
	@Test
	public void makeURL2(){
		TracWizardThird twT=new TracWizardThird();
		status.offer("new");
		status.offer("accepted");
		status.offer("assigned");
		status.offer("closed");
		status.offer("reopened");
	
		type.offer("enhancement");
		type.offer("task");
		type.offer("user-story");
		
		expectedURL="http://econf.di.uniba.it/econference-over-ecf/query?status=new&status=accepted&status=assigned&status=closed&status=reopened&order=priority&col=id&col=summary&col=status&col=owner&col=type&col=priority&col=milestone&type=enhancement&type=task&type=user-story&";

		assertEquals(expectedURL,twT.makeURL(projectURL,status,type));
	}
}
