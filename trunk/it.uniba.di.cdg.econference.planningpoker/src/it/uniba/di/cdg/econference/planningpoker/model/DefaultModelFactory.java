package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.dialogs.DefaultUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.DefaultDeckViewUIHelper;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IDeckViewUIHelper;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.DefaultEstimatesViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.internal.DefaultBacklogContextLoader;

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
	public IEstimatesViewUIProvider createEstimateViewUIHelper() {
		return new DefaultEstimatesViewUIProvider();
	}
	
	@Override
	public IBacklogContextLoader createBacklogContextLoader(){
		return new DefaultBacklogContextLoader();
	}

}
