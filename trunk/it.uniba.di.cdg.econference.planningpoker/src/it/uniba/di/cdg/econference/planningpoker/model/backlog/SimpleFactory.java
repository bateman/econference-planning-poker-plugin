package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.dialogs.SimpleUserStoryDialog;

import org.eclipse.swt.widgets.Shell;

public class SimpleFactory implements IBacklogAbstractFactory {

	@Override
	public IBacklog createBacklog() {
		return new SimpleBacklog();
	}

	@Override
	public IUserStoryDialog createUserStoryDialog(Shell shell) {
		return new SimpleUserStoryDialog(shell);
	}

}
