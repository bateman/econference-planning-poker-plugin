package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.workbench.BacklogView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class DeleteUserStoryAction extends SingleSelectionAction {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.deleteStoryAction";

	public DeleteUserStoryAction(IViewPart view) {
		super(view);
		setImageDescriptor(PlanningPokerPlugin.imageDescriptorFromPlugin(
				PlanningPokerPlugin.ID, "icons/backlog/deleteStory.gif" ));
		setText( "Remove User Story" );
		setToolTipText( "Remove selected User Story from Backlog" );
		
	}

	@Override
	public void run() {
		IWorkbenchWindow window = view.getViewSite().getWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		BacklogView backlogView = (BacklogView) page.findView(BacklogView.ID);
		IUserStory story = backlogView.getSelectedStory();
		if(story!=null){	
				MessageBox messageBox = new MessageBox(window.getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		        messageBox.setMessage("Do you really want to delete this story?");
		        messageBox.setText("Deleting User Story");
		        int response = messageBox.open();
		        if (response == SWT.YES){		          
					backlogView.getModel().getBacklog().removeUserStory(story);
					int itemIndex = backlogView.getModel().getBacklog().indexOf(story);
					backlogView.getManager().notifyRemoveBacklogItem(String.valueOf(itemIndex));
					//notify the backlog's content
					backlogView.getManager().notifyItemListToRemote();
					
		        }
		}
	}

}
