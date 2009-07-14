package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.workbench.StoriesListView;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class DeleteUserStoryAction extends Action {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.deleteStoryAction";
	
	private IViewPart view;

	public DeleteUserStoryAction(IViewPart view) {
		super();
		this.view = view;
		setImageDescriptor(PlanningPokerPlugin.imageDescriptorFromPlugin(
				PlanningPokerPlugin.ID, "icons/deleteStory.gif" ));
		setText( "Remove User Story" );
		setToolTipText( "Remove selected User Story from Backlog" );
	}


	@Override
	public void run() {
		IWorkbenchWindow window = view.getViewSite().getWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		StoriesListView storyView = (StoriesListView) page.findView(StoriesListView.ID);
		ISelection selection = storyView.getViewer().getSelection();
		if(selection!=null && selection instanceof IStructuredSelection){
			IStructuredSelection sel = (IStructuredSelection)selection;
			storyView.getModel().getBacklog().removeUserStory((IUserStory) sel.getFirstElement());
			//notify the backlog's content
			storyView.getManager().notifyItemListToRemote();
		}
	}

}
