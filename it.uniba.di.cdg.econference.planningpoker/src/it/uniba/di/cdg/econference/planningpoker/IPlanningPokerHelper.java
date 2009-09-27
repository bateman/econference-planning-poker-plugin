package it.uniba.di.cdg.econference.planningpoker;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import it.uniba.di.cdg.econference.planningpoker.model.IModelAbstractFactory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogContextLoader;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IDeckViewUIHelper;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesViewUIProvider;
import it.uniba.di.cdg.xcore.econference.IEConferenceHelper;

public interface IPlanningPokerHelper extends IEConferenceHelper {

	void setModelFactory(IModelAbstractFactory factory);
	
	IUserStoryDialog getUserStoryDialog(Shell shell);
	
	IBacklogContextLoader getBacklogContextLoader();
	
	IBacklogViewUIProvider getBacklogViewUIProvider();
	
	IEstimatesViewUIProvider getEstimateViewUIProvider();
	
	IDeckViewUIHelper getCardDeckViewUIHelper(Composite parent, CardDeck deck);
	
}
