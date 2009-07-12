package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.SimpleUserStory;
import it.uniba.di.cdg.econference.planningpoker.workbench.StoriesListView;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant.Role;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.ui.part.ViewPart;

public class AddUserStoryActionDelegate implements IViewActionDelegate {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.addUserStoryAction";
	
	
	//private Role role;

	private IViewPart view;
	
//	private IPropertyChangeListener propListener = new IPropertyChangeListener() {
//		public void propertyChange(PropertyChangeEvent event) {
//			action.setEnabled(Role.MODERATOR.equals(role));
//		}
//	};
	
	@Override
	public void init(IViewPart view) {
		this.view = view;
	}	

	@Override
	public void run(IAction newAction) {
		IWorkbenchWindow window = view.getViewSite().getWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		StoriesListView storyView = (StoriesListView) page.findView(StoriesListView.ID);
//		IParticipant participant = storyView.getModel().getLocalUser();
//		participant.addPropertyChangeListener(propListener);
//		role = participant.getRole();
		IUserStoryDialog dialog = storyView.getModel().getBacklogFactory().createUserStoryDialog(page.getActivePart().getSite().getShell());
		dialog.show();
		if (dialog.getStory() != null) {
			storyView.getModel().getBacklog().addItem(dialog.getStory());
			//notify the backlog's content
			storyView.getManager().notifyItemListToRemote();		
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
