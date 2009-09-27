package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogContextLoader;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IDeckViewUIHelper;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesViewUIProvider;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * <p>This factory provides several products to isolate the user stories
 * representation ( {@link IUserStory}), cards deck representation ( {@link IPokerCard})
 * and estimates representation ( {@link IEstimatesList}) from the system</p>
 * 
 * @author Alessandro Brucoli
 *
 */
public interface IModelAbstractFactory {


	/**
	 * Create the dialog to edit or create a user story
	 * @param shell
	 * @return the dialog
	 */
	IUserStoryDialog createUserStoryDialog(Shell shell);
		
	
	IBacklogContextLoader createBacklogContextLoader();	
	
	/**
	 * The UI Helper takes the Composite control of the view, in order to create
	 * the graphic component and cards that are actually in the
	 * card deck
	 * @param parent 
	 * @param deck 
	 * 
	 * 
	 * @return the UI Helper
	 */
	IDeckViewUIHelper createCardDeckViewUIHelper(Composite parent, CardDeck deck);
	
	IBacklogViewUIProvider createBacklogViewUIProvider();
	
	IEstimatesViewUIProvider createEstimateViewUIProvider();

}
