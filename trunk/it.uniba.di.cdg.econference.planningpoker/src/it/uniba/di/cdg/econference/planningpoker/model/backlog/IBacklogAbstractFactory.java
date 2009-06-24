package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;

import org.eclipse.swt.widgets.Shell;

public interface IBacklogAbstractFactory {
	
	public IBacklog createBacklog();

	public IUserStoryDialog createUserStoryDialog(Shell shell);

}
