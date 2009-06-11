package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.views.StoriesListView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class EditUserStoryActionDelegate implements IViewActionDelegate {

	private IViewPart view;

	public EditUserStoryActionDelegate() {
		// TODO Auto-generated constructor stub
	}

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
		ISelection selection = storyView.getViewer().getSelection();
		if (selection != null && selection instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) selection;			
			dialog.setStory((IUserStory)sel.getFirstElement());
			dialog.show();
			if (dialog.getStory() != null) {
				storyView.refreshBacklogContent();			
			}
		}

		
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}

}
