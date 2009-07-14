package it.uniba.di.cdg.econference.planningpoker.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class EstimateStoryAction extends Action {

	private IViewPart view;
	
	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.EstimateStoryAction";
	
	public EstimateStoryAction(IViewPart view) {
		super();
		this.view = view;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}


}
