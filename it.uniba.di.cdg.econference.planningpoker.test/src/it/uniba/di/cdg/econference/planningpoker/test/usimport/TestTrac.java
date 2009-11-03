package it.uniba.di.cdg.econference.planningpoker.test.usimport;

import static org.junit.Assert.assertEquals;

import it.uniba.di.cdg.econference.planningpoker.usimport.Trac;

import java.io.File;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class TestTrac {
	private File expectedTemp;
	private String host;
	private LinkedList<String> note;
	private LinkedList<String> storyText;
	private LinkedList<String> id;
	private LinkedList<String> milestone;
	private LinkedList<String> clearedMilestone;


	@Before
	public void defaultValues() throws Exception {
		expectedTemp=new File("tempTracTest.xml");
		host="http://econf.di.uniba.it/econference-over-ecf";

		note=new LinkedList<String>();
		note.offer("As conference caller I can enable participants to speak so that they can write messages. ");
		note.offer("As conference caller I want to grant speech rights to a participant so that he can speak. ");
		note.offer("As conference caller I want to remove speech rights from a participant so that he cannot speak. ");
		note.offer("As conference caller I want to set an online participant as scribe so that he can update the shared whiteboard. ");
		note.offer("As scribe I want to change the content of the whiteboard so that the others can see the changes. ");

		storyText=new LinkedList<String>();
		storyText.offer("A participant can be given or removed the rights to speak");
		storyText.offer("A caller may grant speech rights as he wishes");
		storyText.offer("A caller may remove speech rights as he wishes");
		storyText.offer("A caller choose the scribe");
		storyText.offer("The scribe updates the whiteboard");

		id=new LinkedList<String>();
		id.offer("6");
		id.offer("9");
		id.offer("12");
		id.offer("15");
		id.offer("16");


		milestone=new LinkedList<String>();
		milestone.offer("M4");
		milestone.offer("M4");
		milestone.offer("M4");
		milestone.offer("M4");
		milestone.offer("M4");


		clearedMilestone=new LinkedList<String>();
		clearedMilestone.offer("M4");


	}

	@Test
	public void readFile(){

		Trac.readFile(expectedTemp,host);

		assertEquals(note,Trac.note);
		assertEquals(storyText,Trac.storyText);
		assertEquals(id,Trac.id);
		assertEquals(milestone,Trac.milestone);		
		assertEquals(clearedMilestone,Trac.clearedMilestone);


	}
	
	@After
	public void deleteTempFiles(){
		File f=new File("temp.xml");
		f.delete();
	}

}
