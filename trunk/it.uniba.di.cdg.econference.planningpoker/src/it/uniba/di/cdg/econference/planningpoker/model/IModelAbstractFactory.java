package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IDeckViewUIHelper;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.ui.dialogs.IUserStoryDialog;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public interface IModelAbstractFactory {


	IUserStoryDialog createUserStoryDialog(Shell shell);
	
	IBacklogViewUIProvider createBacklogViewUIProvider();
	
	IBacklogContextLoader createBacklogContextLoader();	
	
	/**
	 * The UI Helper takes the Composite control of the view, in order to create
	 * the graphic component n the card that are actually in the
	 * card deck
	 * @param parent 
	 * @param deck 
	 * 
	 * 
	 * @return the UI Helper
	 */
	IDeckViewUIHelper createCardDeckViewUIHelper(Composite parent, CardDeck deck);
	
	
	IEstimatesViewUIProvider createEstimateViewUIHelper();

}
