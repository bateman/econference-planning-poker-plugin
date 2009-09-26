package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.ui.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.BacklogView;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class EditUserStoryAction extends SingleSelectionAction {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.actions.editStory";
	

	public EditUserStoryAction(IViewPart view) {
		super(view);
		setImageDescriptor(PlanningPokerPlugin.imageDescriptorFromPlugin(
				PlanningPokerPlugin.ID, "icons/backlog/editStory.gif" ));
		setText( "Edit User Story" );
		setToolTipText( "Edit the content of selected User Story" );
	}


	@Override
	public void run() {				
		IWorkbenchWindow window = view.getViewSite().getWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		BacklogView backlogView = (BacklogView) page.findView(BacklogView.ID);
		IUserStoryDialog dialog = backlogView.getModel().getFactory().createUserStoryDialog(page.getActivePart().getSite().getShell());
		IUserStory selectedStory = backlogView.getSelectedStory();
		if(selectedStory!=null){			
			dialog.setStory(selectedStory);		
			dialog.setCardDeck(backlogView.getModel().getCardDeck());
			dialog.show();
			if (dialog.getStory() != null) {				
				//notify the backlog's content
				backlogView.getManager().notifyItemListToRemote();
			}
		}
	}


}
