package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import org.eclipse.swt.widgets.Shell;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;

public interface IBacklogAbstractFactory {
	
	IBacklog createBacklog();
	
	IUserStoryDialog createUserStoryDialog(Shell parentShell);

}
