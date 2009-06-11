/**
 * 
 */
package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import org.eclipse.swt.widgets.Shell;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.dialogs.SimpleUserStoryDialog;

/**
 * @author Alessandro Brucoli
 *
 */
public class SimpleFactory implements IBacklogAbstractFactory {

	/* (non-Javadoc)
	 * @see it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogAbstractFactory#createBacklog()
	 */
	@Override
	public IBacklog createBacklog() {
		return new SimpleBacklog();
	}

	/* (non-Javadoc)
	 * @see it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogAbstractFactory#createUserStory()
	 */
	@Override
	public IUserStoryDialog createUserStoryDialog(Shell parentShell) {
		return new SimpleUserStoryDialog(parentShell);
	}



}
