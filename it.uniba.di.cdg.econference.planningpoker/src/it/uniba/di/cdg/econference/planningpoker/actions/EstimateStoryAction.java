package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;

import org.eclipse.ui.IViewPart;

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
		// TODO Auto-generated method stub

	}


}
