package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.workbench.StoriesListView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class DeleteUserStoryActionDelegate implements IViewActionDelegate {

	private IViewPart view;

	public DeleteUserStoryActionDelegate() {
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
		ISelection selection = storyView.getViewer().getSelection();
		if(selection!=null && selection instanceof IStructuredSelection){
			IStructuredSelection sel = (IStructuredSelection)selection;
			storyView.getBacklog().removeUserStory((IUserStory) sel.getFirstElement());
			storyView.refreshBacklogContent();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
