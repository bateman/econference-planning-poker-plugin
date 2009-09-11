package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.workbench.BacklogView;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class AddUserStoryAction extends Action {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.addUserStoryAction";
	
	
	//private Role role;

	private IViewPart view;
	
//	private IPropertyChangeListener propListener = new IPropertyChangeListener() {
//		public void propertyChange(PropertyChangeEvent event) {
//			action.setEnabled(Role.MODERATOR.equals(role));
//		}
//	};
	
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
//		IParticipant participant = storyView.getModel().getLocalUser();
//		participant.addPropertyChangeListener(propListener);
//		role = participant.getRole();
		IUserStoryDialog dialog = backlogView.getModel().getFactory().createUserStoryDialog(page.getActivePart().getSite().getShell());
		dialog.setCardDeck(backlogView.getModel().getCardDeck());
		dialog.show();
		if (dialog.getStory() != null) {
			backlogView.getModel().getBacklog().addItem(dialog.getStory());
			//notify the backlog's content
			backlogView.getManager().notifyItemListToRemote();		
		}
	}

}
