package it.uniba.di.cdg.econference.planningpoker.dialogs;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeck;

public interface IUserStoryDialog {

	public void show();
	
	//set a Story to edit
	public void setStory(IUserStory story);
	
	//return created or modified story 
	public IUserStory getStory();
	
	public void setCardDeck(ICardDeck deck);

}
