package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.views.StoriesListView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class AddUserStoryActionDelegate implements IViewActionDelegate {

	private IViewPart view;

	
	@Override
	public void init(IViewPart view) {
		this.view = view;
	}	

	@Override
	public void run(IAction action) {		
		IWorkbenchWindow window = view.getViewSite().getWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		StoriesListView storyView = (StoriesListView) page.findView(StoriesListView.ID);
		IUserStoryDialog dialog = storyView.getBacklogFactory().createUserStoryDialog(page.getActivePart().getSite().getShell());	
		dialog.show();
		if (dialog.getStory() != null) {
			storyView.getBacklog().addUserStory(dialog.getStory());
			storyView.refreshBacklogContent();		
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
