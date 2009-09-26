package it.uniba.di.cdg.econference.planningpoker.ui.dialogs;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;

public interface IUserStoryDialog {

	int show();
	
	/**
	 * 
	 * Set a Story to edit
	 * @param story
	 */
	void setStory(IUserStory story);
	
	/**
	 * Return the created or modified story 
	 * 
	 * @return created or modified story 
	 */
	IUserStory getStory();
	
	/**
	 * <p>Set the current CardDeck</p>
	 * CardDeck is useful to add all card values to the UI
	 * in order to allow user to select one of these.
	 * 
	 * @param deck the card deck
	 */
	void setCardDeck(CardDeck deck);

	/**
	 * 
	 * <p>Set the current backlog</p>
	 * In the case of User Story add the backlog is useful to 
	 * check if new inserted id already exists
	 * 
	 * @param backlog the backlog
	 */
	void setBacklog(Backlog backlog);

}
