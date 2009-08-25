package it.uniba.di.cdg.econference.planningpoker.workbench;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.xcore.ui.views.IActivatableView;

public interface IBacklogView extends IActivatableView, IPlanningPokerView {

	/**
	 * This method is used by actions (edit/delete/estimate) action)
	 * to retrieve User Story to (edit/delete/estimate)
	 * 
	 * @return the current selected User Story in the view
	 */
	IUserStory getSelectedStory();
}
