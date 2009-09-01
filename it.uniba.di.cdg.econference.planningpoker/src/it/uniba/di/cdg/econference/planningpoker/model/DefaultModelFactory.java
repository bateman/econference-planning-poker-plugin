package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.dialogs.SimpleUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.deck.DefaultDeckViewUIHelper;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IDeckViewUIHelper;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.DefaultEstimatesViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesViewUIProvider;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class DefaultModelFactory implements IModelAbstractFactory {


	@Override
	public IUserStoryDialog createUserStoryDialog(Shell shell) {
		return new SimpleUserStoryDialog(shell);
	}

	@Override
	public IBacklogViewUIProvider createBacklogViewUIProvider() {
		return new DefaultBacklogViewUIProvider();
	}
	
	@Override
	public IDeckViewUIHelper createCardDeckViewUIHelper(Composite parent) {
		return new DefaultDeckViewUIHelper(parent);
	}

	@Override
	public IEstimatesViewUIProvider createEstimateViewUIHelper(Composite parent) {
		return new DefaultEstimatesViewUIProvider(parent);
	}

}
