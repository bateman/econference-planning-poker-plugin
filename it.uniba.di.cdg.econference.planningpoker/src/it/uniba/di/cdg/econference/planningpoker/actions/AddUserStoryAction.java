package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.ui.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.BacklogView;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class AddUserStoryAction extends Action {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.addUserStoryAction";


	private IViewPart view;
	
	public AddUserStoryAction(IViewPart view) {
		super();
		this.view = view;
		setImageDescriptor(PlanningPokerPlugin.imageDescriptorFromPlugin(
				PlanningPokerPlugin.ID, "icons/backlog/new-user-story.gif" ));
		setText("New User Story");
		setToolTipText("Add new User Story to the Backlog");		
	}
		

	@Override
	public void run() {
		IWorkbenchWindow window = view.getViewSite().getWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		BacklogView backlogView = (BacklogView) page.findView(BacklogView.ID);
		IUserStoryDialog dialog = backlogView.getModel().getFactory().createUserStoryDialog(window.getShell());		
		dialog.setCardDeck(backlogView.getModel().getCardDeck());
		Backlog backlog = backlogView.getModel().getBacklog();
		dialog.setBacklog(backlog);
		dialog.show();
		IUserStory newStory = dialog.getStory();
		if (newStory != null) {
			backlogView.getModel().getBacklog().addUserStory(newStory);
			//notify the backlog's content
			backlogView.getManager().notifyItemListToRemote();		
		}
	}

}
