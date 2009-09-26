package it.uniba.di.cdg.econference.agile.planningpoker.test.ui;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory;
import it.uniba.di.cdg.econference.planningpoker.ui.dialogs.DefaultUserStoryDialog;
import junit.framework.TestCase;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DefaultUserStoryDialogTest extends TestCase{
	
	private DefaultUserStoryDialog dus;
	private DefaultUserStory story;
	
	@Override
	protected void setUp() throws Exception {
		Display display = new Display();
		Shell shell = new Shell(display);
		
		story = new DefaultUserStory("id1", "storytext 1", "Closed", "some notes", null);
    	dus = new DefaultUserStoryDialog(shell);
    	dus.setStory(story);
	}
	
	
	public void testDialog() {			
		if(dus.show() == Dialog.OK){							
		}else{
			assertEquals(dus.getStory(), story);
		}
		
	}	

}
