package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.workbench.BacklogView;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class EstimateStoryAction extends SingleSelectionAction {

	
	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.EstimateStoryAction";
	
	public EstimateStoryAction(IViewPart view) {
		super(view);		
		setImageDescriptor(PlanningPokerPlugin.imageDescriptorFromPlugin(
				PlanningPokerPlugin.ID, "icons/estimate-story.gif" ));
		setText("Estimate User Story");
		setToolTipText("Set selected story as a Story to estimate");
	}

	@Override
	public void run() {
		IWorkbenchWindow window = view.getViewSite().getWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		BacklogView backlogView = (BacklogView) page.findView(BacklogView.ID);
		IUserStory selectedStory = backlogView.getSelectedStory();
		if(selectedStory!=null){			
			Backlog backlog = backlogView.getModel().getBacklog();
			for(int i=0; i < backlog.getUserStories().length; i++){
				if(backlog.getUserStories()[i].equals(selectedStory)){
					//backlog.setCurrentItemIndex(i);
					backlogView.getManager().notifyCurrentAgendaItemChanged(String.format("%d",i));
					break;
				}
			}	
						
		}
	}


}
