package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.StoryPoints;
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

public class EditUserStoryAction extends Action {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.actions.editStory";
	
	private IViewPart view;

	public EditUserStoryAction(IViewPart view) {
		super();
		this.view = view;
		setImageDescriptor(PlanningPokerPlugin.imageDescriptorFromPlugin(
				PlanningPokerPlugin.ID, "icons/editStory.gif" ));
		setText( "Edit User Story" );
		setToolTipText( "Edit the content of selected User Story" );
	}


	@Override
	public void run() {				
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
				storyView.getModel().getBacklog().addItem(dialog.getStory());
			}
		}
	}


}
