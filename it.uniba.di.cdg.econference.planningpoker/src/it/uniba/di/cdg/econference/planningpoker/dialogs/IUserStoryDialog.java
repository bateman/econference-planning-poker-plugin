package it.uniba.di.cdg.econference.planningpoker.dialogs;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;

public interface IUserStoryDialog {

	int show();
	
	//set a Story to edit
	void setStory(IUserStory story);
	
	//return created or modified story 
	IUserStory getStory();
	
	void setCardDeck(CardDeck deck);

}
