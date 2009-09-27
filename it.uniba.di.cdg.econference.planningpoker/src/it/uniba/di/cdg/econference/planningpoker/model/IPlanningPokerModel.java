package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.Voters;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModel;

public interface IPlanningPokerModel extends IConferenceModel {

	
	/**
	 * Get the current backlog in the model
	 * @return the current backlog
	 */
	Backlog getBacklog();
	
	/**
	 * <p>Set the backlog content</p>
	 * 
	 * @param backlog
	 */
	void setBacklog(Backlog backlog);
	

	
	CardDeck getCardDeck();
	
	void setCardDeck(CardDeck deck);	
	
	Voters getVoters();
	
	void setVoters(Voters voters);

	IEstimatesList getEstimateSession();
	
	void openEstimateSession(IEstimatesList estimates);

}
