package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.Voters;

import it.uniba.di.cdg.xcore.econference.model.IConferenceModel;

public interface IPlanningPokerModel extends IConferenceModel {

	void setCardValue(Object point);
	
	void setBacklog(IBacklog backlog);
	
	void setModelFactory(IModelAbstractFactory factory);
	
	void setCardDeck(ICardDeck deck);
	
	void setVoters(Voters voters);
	
	Object getCardValue();
	
	IBacklog getBacklog();
	
	IModelAbstractFactory getFactory();	

	ICardDeck getCardDeck();
	
	Voters getVoters();
	
	
}
