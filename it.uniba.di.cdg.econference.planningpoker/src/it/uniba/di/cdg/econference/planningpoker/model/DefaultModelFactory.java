package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultBacklogContextLoader;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogContextLoader;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IDeckViewUIHelper;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.ui.DefaultBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.ui.DefaultDeckViewUIHelper;
import it.uniba.di.cdg.econference.planningpoker.ui.DefaultEstimatesViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.ui.dialogs.DefaultUserStoryDialog;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class DefaultModelFactory implements IModelAbstractFactory {


	@Override
	public IUserStoryDialog createUserStoryDialog(Shell shell) {
		return new DefaultUserStoryDialog(shell);
	}

	@Override
	public IBacklogViewUIProvider createBacklogViewUIProvider() {
		return new DefaultBacklogViewUIProvider();
	}
	
	@Override
	public IDeckViewUIHelper createCardDeckViewUIHelper(Composite parent, CardDeck deck) {
		return new DefaultDeckViewUIHelper(parent,deck);
	}

	@Override
	public IEstimatesViewUIProvider createEstimateViewUIProvider() {
		return new DefaultEstimatesViewUIProvider();
	}
	
	@Override
	public IBacklogContextLoader createBacklogContextLoader(){
		return new DefaultBacklogContextLoader();
	}

}
