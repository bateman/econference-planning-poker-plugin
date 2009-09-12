package it.uniba.di.cdg.econference.planningpoker.workbench;

import org.eclipse.ui.ISaveablePart;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.xcore.ui.views.IActivatableView;

public interface IBacklogView extends IActivatableView, IPlanningPokerView, ISaveablePart {

	/**
	 * This method is used by actions (edit/delete/estimate) action)
	 * to retrieve User Story to (edit/delete/estimate)
	 * 
	 * @return the current selected User Story in the view
	 */
	IUserStory getSelectedStory();
}
