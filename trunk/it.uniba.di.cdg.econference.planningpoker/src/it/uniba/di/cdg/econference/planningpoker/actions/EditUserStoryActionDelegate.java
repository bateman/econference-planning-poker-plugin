package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.workbench.StoriesListView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class EditUserStoryActionDelegate implements IViewActionDelegate {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.actions.editStory";
	
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
		IUserStoryDialog dialog = storyView.getModel().getBacklogFactory().createUserStoryDialog(page.getActivePart().getSite().getShell());
		ISelection selection = storyView.getViewer().getSelection();
		if (selection != null && selection instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) selection;	
			IUserStory selectedStory = (IUserStory)sel.getFirstElement();
			dialog.setStory(selectedStory);
			dialog.show();
			if (dialog.getStory() != null) {
				storyView.getModel().getBacklog().removeUserStory(selectedStory);
			}
		}

		
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}

}
